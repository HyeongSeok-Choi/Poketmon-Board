package com.boot.tsamo.repository;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.ReReply;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.entity.ReReply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class reReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReReplyRepository reReplyRepository;

    @Autowired
    UserRepository userRepository;

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
        userRepository.save(user);

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

            System.out.println(reply.toString());

            ReReply rereply = new ReReply();
            rereply.setUserid(user);
            rereply.setReplyId(reply);
            rereply.setContent("대댓글" + i);
            reReplyRepository.save(rereply);

            System.out.println(rereply.toString());
        }
    }

    

}