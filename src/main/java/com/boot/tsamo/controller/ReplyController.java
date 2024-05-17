package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AddReReplyDTO;
import com.boot.tsamo.dto.AddReplyDTO;
import com.boot.tsamo.dto.ViewReReplyDTO;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.entity.ReReply;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.service.ReReplyService;
import com.boot.tsamo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final ReReplyService reReplyService;

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

    //댓글 조회 수정본
    @GetMapping("/api/allcomments/{ReplyId}")
    public ResponseEntity<List<ViewReplyDTO>> getAllComments(@PathVariable long ReplyId) {

        List<Reply> replieList = replyService.findAll(ReplyId);

        List<ViewReplyDTO> replyDTOS = replieList.stream()
                .map(a -> new ViewReplyDTO(a))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(replyDTOS);

    }


    // 댓글 조회
//    @GetMapping("/api/allcomments")
//    public ResponseEntity<List<ViewReplyDTO>> getAllComments(Long id, Pageable pageable) {
//
//        id = 1L;
//
//        List<Reply> replieList = replyService.findAll(id);
//
//        List<ViewReplyDTO> replyDTOS = replieList.stream()
//                .map(a -> new ViewReplyDTO(a))
//                .collect(Collectors.toList());
//
//
//        return ResponseEntity.ok().body(replyDTOS);
//
//    }



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

    //대댓글 생성
    @PostMapping("/api/addReComment")
    public ResponseEntity<ReReply> saveReComment(
            @RequestBody AddReReplyDTO contents, Principal principal) {

        //Long boardId = contents.getBoardId();

        //작성자
        String author = principal.getName();
        contents.setUserid(author);

        ReReply savedReReply = reReplyService.addReReply(contents);


        return ResponseEntity.ok().body(new ReReply());


    }

    // 상세 대댓글 조회 수정본
    @GetMapping("/api/allReComment/{reReplyId}")
    public ResponseEntity<List<ViewReReplyDTO>> getAllReComments(@PathVariable long reReplyId) {
        List<ReReply> reReplieList = reReplyService.findByReplyId(reReplyId);

        List<ViewReReplyDTO> reReplyDTOS = reReplieList.stream()
                .map(ViewReReplyDTO::new)
                .collect(Collectors.toList());

        for (ViewReReplyDTO r : reReplyDTOS) {
            System.out.println(r.getUserid());
            System.out.println(r.getContent());
            System.out.println(r.getCreatedAt());
        }

        return ResponseEntity.ok().body(reReplyDTOS);
    }




//    // 상세 대댓글 조회
//    @GetMapping("/api/allReComment")
//    public ResponseEntity<List<ViewReReplyDTO>> getAllReComments() {
//
//        Long id = 1L;
//
//        List<ReReply> reReplieList = reReplyService.findByReplyId(id);
//
//        List<ViewReReplyDTO> reReplyDTOS = reReplieList.stream()
//                .map(a -> new ViewReReplyDTO(a))
//                .collect(Collectors.toList());
//
//        for (ViewReReplyDTO r : reReplyDTOS) {
//            System.out.println(r.getUserid());
//            System.out.println(r.getContent());
//            System.out.println(r.getCreatedAt());
//
//        }
//
//
//        return ResponseEntity.ok().body(reReplyDTOS);
//
//    }



//    // 모든 대댓글 조회
//    @GetMapping("/api/allReComment")
//    public ResponseEntity<List<ViewReReplyDTO>> getAllReComments(Long id, Pageable pageable) {
//
//        id = 1L;
//
//        List<ReReply> reReplieList = reReplyService.findAll(id);
//
//        List<ViewReReplyDTO> reReplyDTOS = reReplieList.stream()
//                .map(a -> new ViewReReplyDTO(a))
//                .collect(Collectors.toList());
//
//       for (ViewReReplyDTO r : reReplyDTOS){
//           System.out.println(r.getUserid());
//           System.out.println(r.getContent());
//           System.out.println(r.getCreatedAt());
//
//       }
//
//
//        return ResponseEntity.ok().body(reReplyDTOS);
//
//    }

}