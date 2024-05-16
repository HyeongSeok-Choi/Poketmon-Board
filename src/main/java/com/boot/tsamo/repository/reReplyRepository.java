
package com.boot.tsamo.repository;

import com.boot.tsamo.entity.ReReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface reReplyRepository extends JpaRepository<ReReply,Long> {
}
