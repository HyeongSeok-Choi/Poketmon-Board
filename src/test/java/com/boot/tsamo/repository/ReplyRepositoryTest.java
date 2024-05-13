package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("save 기능 테스트")
    public void replySave() {
        Users user = new Users();
        user.setUserId("aaa");
        user.setPassword("1234");
        user.setEmail("aaa@aaa.aaa");
        user.setNickName("aaa");
        usersRepository.save(user);

        for (int i = 0; i <= 5; i++) {
            Board board = new Board();
            board.setTitle("제목" + i);
            board.setContent("내용" + i);
            board.setUserid(user);
            boardRepository.save(board);

            Reply reply = new Reply();
            reply.setUserid(user);
            reply.setBoardId(board);
            reply.setContent("댓글"+i);
            replyRepository.save(reply);

            System.out.println(reply.toString());
        }
    }

    @Test
    @DisplayName("댓글 조회 기능 테스트")
    public void findByReplyTest(){
        this.replySave();
        List<Reply> replyList = replyRepository.findAll();
        for (Reply reply : replyList) {
            System.out.println(reply.toString());
        }
    }
}