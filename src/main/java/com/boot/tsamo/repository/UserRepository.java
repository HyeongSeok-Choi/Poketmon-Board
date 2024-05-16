package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {

    Users findByUserIdContaining(String userId);
}
