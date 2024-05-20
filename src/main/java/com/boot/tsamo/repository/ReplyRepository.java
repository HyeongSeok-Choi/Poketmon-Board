package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Reply;
import org.h2.mvstore.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {

   
}
