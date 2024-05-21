package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.HashTag;
import com.boot.tsamo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {


    List<HashTag> findByHashTagContentContaining(String hashTag);

    List<HashTag> findByHashTagContentContains(String hashTag);

    List<HashTag> findByHashTagContent(String hashTag);



}
