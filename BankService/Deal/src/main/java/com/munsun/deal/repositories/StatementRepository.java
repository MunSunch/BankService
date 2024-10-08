package com.munsun.deal.repositories;

import com.munsun.deal.models.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {
}
