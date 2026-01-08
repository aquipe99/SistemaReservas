package com.sys.reservas.repository;

import com.sys.reservas.entity.Court;
import com.sys.reservas.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CourtRepository extends JpaRepository<Court,Long>, JpaSpecificationExecutor<Court> {
    Optional<Court> findByName(String name);
    Optional<Court> findByNameAndIdNot(String name, Long id);
}
