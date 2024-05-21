package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.dto.modifyReplyDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
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
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;


    public void based(){

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


        }

//        Board board = boardRepository.findById(1L).get();
//
//        Reply reply = new Reply();
//        reply.setUserid(user);
//        reply.setBoardId(board);
//        reply.setContent("댓글 2번째");
//        replyRepository.save(reply);
//
//        this.testSaveReply();
//        List<Reply> replyList = replyService.findAll(1L);
//        System.out.println(replyList.get(1).getBoardId().getId());
//        System.out.println(replyList.get(0).getContent());
//        System.out.println(replyList.get(0).getReplyId());
//        System.out.println(replyList.get(0).getUserid());
//        System.out.println(replyList.get(0).getUpdatedAt());
//        System.out.println(replyList.get(0).getCreatedAt());
//
//        System.out.println(replyList.get(1).getContent());
//        System.out.println(replyList.get(1).getReplyId());
//        System.out.println(replyList.get(1).getUserid());
//        System.out.println(replyList.get(1).getUpdatedAt());
//        System.out.println(replyList.get(1).getCreatedAt());
//
//
//        replyList.get(0).setContent("수정된 댓글입니다.");
//        replyRepository.save(replyList.get(0));
//
//        System.out.println(replyList.get(0).getContent());

    }




    @Test
    @DisplayName("댓글 저장 기능 테스트")
    public void testSaveReply() {
        // Given: Reply 객체 생성 및 초기 설정
        Reply reply = new Reply();
        reply.setContent("댓글 내용");

        // When: ReplyService의 save 메서드 호출
        Reply savedReply = replyService.save(reply);

        // Then: 저장된 Reply 객체 검증
        assertNotNull(savedReply.getReplyId(), "저장된 Reply 객체의 ID가 null이 아님");
        assertEquals("댓글 내용", savedReply.getContent(), "댓글 내용이 일치함");

        // 저장된 User 객체 검증
        Users savedUser = savedReply.getUserid();
        assertNotNull(savedUser, "저장된 User 객체가 null이 아님");
        assertEquals("qnftlstm", savedUser.getUserId(), "User ID가 일치함");

        // 저장된 Board 객체 검증
        Board savedBoard = savedReply.getBoardId();
        assertNotNull(savedBoard, "저장된 Board 객체가 null이 아님");
        assertEquals("제목", savedBoard.getTitle(), "Board 제목이 일치함");

        System.out.println(reply.toString());
//        //외래 키 제약 조건 때문에 삭제 안됨
//        // 저장된 User 객체 삭제 (청소 작업)
//        usersRepository.delete(savedUser);
//
//        // 저장된 Board 객체 삭제 (청소 작업)
//        boardRepository.delete(savedBoard);
//
//        // 저장된 Reply 객체 삭제 (청소 작업)
//        replyRepository.delete(savedReply);
    }


    @Test
    @DisplayName("댓글 조회 기능 테스트")
    public void findByReplyTest() {

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
        }

        Board board = boardRepository.findById(1L).get();

        Reply reply = new Reply();
        reply.setUserid(user);
        reply.setBoardId(board);
        reply.setContent("댓글 2번째");
        replyRepository.save(reply);

        this.testSaveReply();
        List<Reply> replyList = replyService.findAll(1L);
        System.out.println(replyList.get(1).getBoardId().getId());
        System.out.println(replyList.get(0).getContent());
        System.out.println(replyList.get(0).getReplyId());
        System.out.println(replyList.get(0).getUserid());
        System.out.println(replyList.get(0).getUpdatedAt());
        System.out.println(replyList.get(0).getCreatedAt());

        System.out.println(replyList.get(1).getContent());
        System.out.println(replyList.get(1).getReplyId());
        System.out.println(replyList.get(1).getUserid());
        System.out.println(replyList.get(1).getUpdatedAt());
        System.out.println(replyList.get(1).getCreatedAt());


        replyList.get(0).setContent("수정된 댓글입니다.");
        replyRepository.save(replyList.get(0));

        System.out.println(replyList.get(0).getContent());

    }

    @Test
    @DisplayName("댓글 수정 기능 테스트")
    public void replyServiceUpdateTest() {

        based();

       String beforeUpdated= replyRepository.findById(1L).get().getContent();

       replyService.update(new modifyReplyDTO());

       String updatedreply =replyRepository.findById(1L).get().getContent();

       assertNotEquals(beforeUpdated,updatedreply);

    }

    @Test
    @DisplayName("댓글 삭제 기능 테스트")
    public void replyServiceDeleteTest() {
        // Given: Reply 객체 생성 및 초기 설정
        Reply reply = new Reply();
        reply.setContent("댓글 내용");

        // When: ReplyService의 save 메서드 호출
        Reply savedReply = replyService.save(reply);

        // 댓글 삭제
        replyRepository.deleteById(1L);

        // 댓글 조회 및 검증
        Reply deletedReply = replyRepository.findById(reply.getReplyId()).orElse(null);
        assertNull(deletedReply);

        System.out.println("Reply successfully deleted");
    }


}
