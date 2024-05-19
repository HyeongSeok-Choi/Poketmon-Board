package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.dto.modifyReplyDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.ReplyRepository;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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



    public Reply addReply(AddReplyDTO addReplyDTO){

            //각 Entity들의 객체 얻기
           Board board = boardRepository.findById(addReplyDTO.getBoardId()).get();
           Users user = userRepository.findById(addReplyDTO.getUserid()).get();

           Reply addreply= new Reply();

           //reply객체 값 저장
           addreply.setContent(addReplyDTO.getContent());

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
    public Reply update(modifyReplyDTO modifyReplyDTO){

        Reply reply= replyRepository.findById(modifyReplyDTO.getId()).get();

        reply.update(modifyReplyDTO.getContent());

        Reply updatedReply= replyRepository.save(reply);

        return updatedReply;

    }

    //댓글 삭제
    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }


    
    //댓글 페이징
    public Page<ViewReplyDTO> getreplyPage(Pageable pageable, List<ViewReplyDTO> replies) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), replies.size());
        Page<ViewReplyDTO> ReplyPage = new PageImpl<>(replies.subList(start, end), pageRequest, replies.size());

        return ReplyPage;
    }


}
