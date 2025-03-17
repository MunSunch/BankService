package com.munsun.audit.repositories;

import com.munsun.audit.entities.AuditAction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface AuditRepository extends CrudRepository<AuditAction, UUID>, QueryByExampleExecutor<AuditAction> {}