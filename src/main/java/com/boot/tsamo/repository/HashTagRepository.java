package com.boot.tsamo.repository;

import com.boot.tsamo.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {
}
