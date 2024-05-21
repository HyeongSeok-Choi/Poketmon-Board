package com.boot.tsamo.repository;

import com.boot.tsamo.config.multikey;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Likes;
import com.boot.tsamo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes, multikey> {


}
