package com.epam.domain.repository;

import com.epam.domain.entity.certificate.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByCreatedBy_Id(String username, Pageable pageable);
}