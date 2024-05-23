package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension,Long> {
}
