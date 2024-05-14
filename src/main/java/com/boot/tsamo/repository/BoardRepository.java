package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findByTitleContaining(String title, Pageable pageable);

    Page<Board> findByContentContaining(String content, Pageable pageable);

    Page<Board> findByUserid(Users userId, Pageable pageable);

}
