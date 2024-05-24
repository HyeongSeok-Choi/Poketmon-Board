package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {

    Users findByUserIdAndIsDeletedFalse(String userId);
    // 추가된 메서드: userId로 존재 여부 확인
    boolean existsByUserId(String userId);

}
