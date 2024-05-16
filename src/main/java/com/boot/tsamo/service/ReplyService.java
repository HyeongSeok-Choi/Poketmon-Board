package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.ReplyRepository;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    public Users createUser(){
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUserId("test02");
        userFormDto.setPassword(passwordEncoder.encode("12341234"));
        userFormDto.setEmail("test02@gmail.com");
        userFormDto.setNickName("test02");
        return Users.createUser(userFormDto, passwordEncoder);
    }

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

//    public void register(ReplyDto replyDto){
//        // 1 : ReplyDto -> Reply 변환
//
//        // 2 : repository의 save 메소드를 통해 엔티티 db로 저장
//
//    }

    //더미 데이터 생성
    public Reply save(Reply reply) {

        Users users = new Users();

        users.setUserId("qnftlstm");

        users.setEmail("qnftlstm@naver.com");

        users.setRole(Role.pUSER);

        users.setPassword("chl153");

        users.setNickName("뿡뿡이");

        userRepository.save(users);

        Board board = new Board();
        board.setTitle("제목");
        board.setContent("내용");
        board.setUserid(users);
        boardRepository.save(board);

        reply.setUserid(users);
        reply.setBoardId(board);
        reply.setContent("댓글 내용");
        replyRepository.save(reply);

        return reply;
    }



    public Reply addReply(AddReplyDTO addReplyDTO, Long boardId){

           Board board = boardRepository.findById(1L).get();

           //Users user = userRepository.findById(addReplyDTO.getUserid()).get();

           Reply addreply= new Reply();

           addreply.setContent(addReplyDTO.getContent());
           Users user =new Users();
            user.setUserId("qnftlstm78");
           addreply.setUserid(user);

           addreply.setBoardId(board);

           replyRepository.save(addreply);

        return addreply;
    }



    //모든 댓글 조회
    public List<Reply> findAll(Long id) {

        Board board = boardRepository.findById(id).get();

        List<Reply> replies = board.getReplies();

        return replies;
    }

    //댓글 수정
    @Transactional
    public Reply update(long id, String content) {

        Reply reply = replyRepository.findById(id).get();

        reply.update(content);

        Reply updateReply = replyRepository.save(reply);

        return updateReply;

    }

    //댓글 삭제
    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }


}
