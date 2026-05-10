package com.order.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.management.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{

    List<Unit> findByIsAvailableTrue();
}
