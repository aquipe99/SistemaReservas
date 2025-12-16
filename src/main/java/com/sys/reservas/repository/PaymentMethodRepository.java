package com.sys.reservas.repository;

import com.sys.reservas.entity.PaymentMethod;
import com.sys.reservas.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long>, JpaSpecificationExecutor<PaymentMethod> {
    Optional<PaymentMethod> findByName(String name);
}
