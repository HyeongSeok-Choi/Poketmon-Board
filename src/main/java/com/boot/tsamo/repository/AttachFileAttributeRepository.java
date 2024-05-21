package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFileAttribute;
import com.boot.tsamo.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachFileAttributeRepository extends JpaRepository<AttachFileAttribute,Long> {


}
