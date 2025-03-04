package com.munsun.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingRequestResponseFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    log.info("Request: method={}, uri={}, body={}", request.getMethod(), request.getURI(), body);

                    ServerHttpRequest decoratedRequest = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return Flux.just(dataBuffer.factory().wrap(bytes));
                        }
                    };

                    ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            return super.writeWith(Flux.from(body).doOnNext(dataBuffer -> {
                                byte[] responseBytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(responseBytes);
                                String responseBody = new String(responseBytes, StandardCharsets.UTF_8);
                                log.info("Response: status={}, body={}", response.getStatusCode(), responseBody);
                            }));
                        }
                    };
                    return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build());
                });
    }
}