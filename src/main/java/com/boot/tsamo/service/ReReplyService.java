package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.dto.AddReReplyDTO;
import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.dto.modifyReReplyDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.ReReply;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.ReReplyRepository;
import com.boot.tsamo.repository.ReplyRepository;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReReplyService {

    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final ReReplyRepository rereplyRepository;

    //대댓글 생성
    public ReReply addReReply(AddReReplyDTO addReReplyDTO) {

        //각 Entity들의 객체 얻기
        Reply reply = replyRepository.findById(addReReplyDTO.getReplyId()).get();
        Users user = userRepository.findById(addReReplyDTO.getUserid()).get();

        ReReply addreReply = new ReReply();

        //reply객체 값 저장
        addreReply.setContent(addReReplyDTO.getContent());

        addreReply.setUserid(user);

        addreReply.setReplyId(reply);

        rereplyRepository.save(addreReply);

        return addreReply;
    }


    // 대댓글 상세 조회
    public List<ReReply> findByReplyId(Long id) {

        Reply reply = replyRepository.findById(id).get();

        List<ReReply> rereplies = reply.getRereplies();

        return rereplies;
    }

    //대댓글 수정
    @Transactional
    public ReReply update(modifyReReplyDTO modifyReReplyDTO) {

        ReReply reReply = rereplyRepository.findById(modifyReReplyDTO.getReplyId()).get();

        reReply.reReplyupdate(modifyReReplyDTO.getContent());

        ReReply updateReReplyResult = rereplyRepository.save(reReply);

        return updateReReplyResult;

    }

    //대댓글 삭제
    public void deleteById(Long id) {
        rereplyRepository.deleteById(id);
    }
}
