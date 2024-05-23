package com.boot.tsamo.repository;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachFileRepository extends JpaRepository<AttachFile,Long> {

    List<AttachFile> findByBoardIdId(Long boardId);
    AttachFile findByBoardIdIdAndId(Long boardId, Long id);
    Long deleteByBoardId(Board boardId);

    @Query("SELECT f.fileUrl FROM AttachFile f WHERE f.boardId.id = :bno AND f.id = :fno")
    String getAttachFileUrlByBoardIdAndId(Long bno, Long fno);

    @Modifying
    @Query("DELETE FROM AttachFile af WHERE af.boardId.id = :bno AND af.id = :fno")
    void deleteByBoardIdAndFileId(@Param("bno") Long bno, @Param("fno") Long fno);

}
