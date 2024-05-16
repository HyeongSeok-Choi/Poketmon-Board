package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    // 신규 댓글 생성

    @PostMapping("/api/addComment")
    public ResponseEntity<Reply> saveComment(
                                      @RequestBody AddReplyDTO contents) {

        System.out.println(contents.getContent()+"수정됨");

        System.out.println("여기 들어왔어");
        System.out.println("여기 들어왔어");

        System.out.println("여기 들어왔어"+contents);

        System.out.println("여기 들어왔어"+contents);


        Long boardId = 1L;

/*
        //작성자
        String author = principal.getName();

*/
        AddReplyDTO addReplyDTO = new AddReplyDTO();



        addReplyDTO.setUserid("dds");
        addReplyDTO.setContent(contents.getContent());
        addReplyDTO.setBoardId(boardId);


        Reply savedReply = replyService.addReply(addReplyDTO,boardId);



        return ResponseEntity.ok().body(new Reply());


    }

//    //기존 댓글 수정
//    @PatchMapping("/posts/{postId}/comments/{id}")
//    public CommentDto updateComment(@PathVariable final Long postId, @PathVariable final Long id,
//                                    @RequestBody final CommentDto dto, @LoginUser UserSessionDto userDto) {
//
//        dto.setMdfId(userDto.getUserId());
//        log.debug("BoardDto :: {}", dto);
//
//        commentService.updateComment(dto);
//        return commentService.findCommentById(id);
//    }
}