package com.order.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
