package com.munsun.calculator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.munsun.calculator.config.annotations.Mask;
import com.munsun.calculator.config.filters.RequestResponseInterceptor;
import com.munsun.calculator.config.serializers.MaskingSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RequestResponseInterceptor loggingFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingFilter);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .setAnnotationIntrospector(maskAnnotationIntrospector());
    }

    @Bean
    public JacksonAnnotationIntrospector maskAnnotationIntrospector() {
        return new JacksonAnnotationIntrospector() {
            @Override
            public Object findSerializer(Annotated am) {
                Mask annotation = am.getAnnotation(Mask.class);
                if (annotation != null)
                    return MaskingSerializer.class;
                return super.findSerializer(am);
            }
        };
    }
}
