package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {


    Page<Board> findByTitleContainingAndDeletedIsFalse(String title, Pageable pageable);

    Page<Board> findByContentContainingAndDeletedIsFalse(String content, Pageable pageable);

    Page<Board> findByUseridAndDeletedIsFalse(Users userId, Pageable pageable);

    Page<Board> findAllByDeletedIsFalse(Pageable pageable);

    Page<Board> findByTitleContainingAndDeletedIsTrue(String title, Pageable pageable);

    Page<Board> findByContentContainingAndDeletedIsTrue(String content, Pageable pageable);

    Page<Board> findByUseridAndDeletedIsTrue(Users userId, Pageable pageable);

    Page<Board> findAllByDeletedIsTrue(Pageable pageable);


    @Modifying
    @Query("update Board p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateViews(Long id);

    List<Board> findAllByDeleted(boolean deleted);
}
