package com.boot.tsamo.service;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.ReReply;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.ReReplyRepository;
import com.boot.tsamo.repository.ReplyRepository;
import com.boot.tsamo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ReReplyServiceTest {

    @Autowired
    private ReReplyService rereplyService;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReReplyRepository rereplyRepository;
//더미 데이터
    public void based() {

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
            reply.setContent("댓글" + i);
            replyRepository.save(reply);

            ReReply rereply = new ReReply();
            rereply.setReplyId(reply);
            rereply.setUserid(user);
            rereply.setContent("대댓글" + i);
            rereplyRepository.save(rereply);

        }

    }


    @Test
    @DisplayName("대댓글 생성 기능 테스트")
    public void saveReReplyTest() {

        this.based();



    }


    @Test
    @DisplayName("대댓글 조회 기능 테스트")
    public void findByReReplyTest() {

        this.based();

        Users user = usersRepository.findById("aaa").orElseThrow();
        List<ReReply> rereplyList = rereplyService.findAll(1L);
      System.out.println(rereplyList);

    }


}