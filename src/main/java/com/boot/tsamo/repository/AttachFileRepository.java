package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachFileRepository extends JpaRepository<AttachFile,Long> {

    List<AttachFile> findByBoardIdId(Long boardId);
    AttachFile findByBoardIdIdAndId(Long boardId, Long id);
}
