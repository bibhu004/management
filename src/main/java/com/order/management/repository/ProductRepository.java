package com.order.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.management.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
