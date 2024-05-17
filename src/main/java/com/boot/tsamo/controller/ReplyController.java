package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.dto.modifyReplyDTO;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    // 신규 댓글 생성
    @PostMapping("/api/addComment")
    public ResponseEntity<Reply> saveComment(
            @RequestBody AddReplyDTO contents, Principal principal) {

        //Long boardId = contents.getBoardId();

        //작성자
        String author = principal.getName();
        contents.setUserid(author);

        Reply savedReply = replyService.addReply(contents);

        return ResponseEntity.ok().body(new Reply());

    }

    // 댓글 조회
    @GetMapping("/api/allcomments")
    public ResponseEntity<List<ViewReplyDTO>> getAllComments(Long id, Pageable pageable) {

        id = 1L;

        List<Reply> replieList = replyService.findAll(id);

        List<ViewReplyDTO> replyDTOS = replieList.stream()
                .map(a -> new ViewReplyDTO(a))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(replyDTOS);

    }

    //기존 댓글 수정
    @PostMapping("/api/modify")
    public ResponseEntity<modifyReplyDTO> updateComment(@RequestBody modifyReplyDTO dto) {

        replyService.update(dto);

        return ResponseEntity.ok().body(dto);
    }

}
