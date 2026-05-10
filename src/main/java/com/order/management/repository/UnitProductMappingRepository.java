package com.order.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.management.entity.UnitProductMapping;

@Repository
public interface UnitProductMappingRepository extends JpaRepository<UnitProductMapping, Long>{


    Optional<UnitProductMapping> findByProductIdAndUnitId(Long productId, Long unitId);

    Optional<UnitProductMapping> findFirstByProductIdAndUnitIsAvailableTrue(Long productId);
}
