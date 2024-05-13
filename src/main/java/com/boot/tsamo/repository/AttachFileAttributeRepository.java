package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFileAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachFileAttributeRepository extends JpaRepository<AttachFileAttribute,Long> {
}
