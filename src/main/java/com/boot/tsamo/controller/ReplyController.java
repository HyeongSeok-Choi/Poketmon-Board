package com.boot.tsamo.controller;

import com.boot.tsamo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    //댓글 리스트 조회
    @GetMapping(value = "/reply")
    public String reply() {

        return "ReplyView";
    }

//    // 신규 댓글 생성
//    @PostMapping("/posts/{postId}/replys")
//    public ReplyDto saveComment(@PathVariable final Long postId,
//                                final ReplyDto dto, @LoginUser UserSessionDto userDto) {
//
//        dto.setCrtId(userDto.getUserId());
//        dto.setMdfId(userDto.getUserId());
//
//        log.debug("BoardDto :: {}", dto);
//
//        ReplyDto saveComment = replyService.saveReply(dto);
//        return saveComment;
//    }
//
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