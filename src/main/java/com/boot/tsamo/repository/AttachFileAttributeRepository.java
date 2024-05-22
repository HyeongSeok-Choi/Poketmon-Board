package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFileAttribute;
import com.boot.tsamo.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachFileAttributeRepository extends JpaRepository<AttachFileAttribute,Long> {

    @Query("SELECT a.max_upload_size FROM AttachFileAttribute a WHERE a.id = :id")
    Integer findMax_Upload_SizeById(Long id);

    @Query("SELECT a.max_upload_cnt FROM AttachFileAttribute a WHERE a.id = :id")
    Integer findMax_Upload_CntById(Long id);

}
