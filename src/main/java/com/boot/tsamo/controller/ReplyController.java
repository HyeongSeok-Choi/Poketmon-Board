package com.boot.tsamo.controller;

import com.boot.tsamo.dto.*;
import com.boot.tsamo.entity.ReReply;
import com.boot.tsamo.entity.Reply;
import com.boot.tsamo.service.ReReplyService;
import com.boot.tsamo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

        String author ="";
        if(principal != null) {
            //작성자
            author = principal.getName();

        }
        contents.setUserid(author);

        Reply savedReply = replyService.addReply(contents);


        return ResponseEntity.ok().body(new Reply());


    }

    //댓글 조회 수정본
    @GetMapping("/api/allcomments/{boardId}")
    public Page<ViewReplyDTO> getAllComments(@PathVariable long boardId,
                                                             @PageableDefault(page=0,size = 3)Pageable pageable) {

        List<Reply> replieList = replyService.findAll(boardId);

        List<ViewReplyDTO> replyDTOS = replieList.stream()
                .map(a -> new ViewReplyDTO(a))
                .collect(Collectors.toList());


       Page<ViewReplyDTO> replyDTOPage = replyService.getreplyPage(pageable, replyDTOS);

       for(ViewReplyDTO rwe :replyDTOPage ){
           System.out.println(rwe.getContent()+"테스트");
           System.out.println(rwe.getUserid()+"테스트");
       }


        return replyDTOPage;

    }

    //기존 댓글 수정
    @PostMapping("/api/modify")
    public ResponseEntity<modifyReplyDTO> updateComment(@RequestBody modifyReplyDTO dto) {

        replyService.update(dto);

        return ResponseEntity.ok().body(dto);
    }

    //기존 댓글 삭제
    @DeleteMapping ("/api/delete/{id}")
    public ResponseEntity<Long> deletecomment(@PathVariable Long id) {


        System.out.println(id+"삭제 아이디 들어오나");
        replyService.deleteById(id);

        return ResponseEntity.ok().body(id);
    }


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
    @GetMapping("/api/allReComment/{replyId}")
    public ResponseEntity<List<ViewReReplyDTO>> getAllReComments(@PathVariable long replyId) {
        List<ReReply> reReplieList = reReplyService.findByReplyId(replyId);

        List<ViewReReplyDTO> reReplyDTOS = reReplieList.stream()
                .map(ViewReReplyDTO::new)
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(reReplyDTOS);
    }


    //대댓글 수정
    @PostMapping("/api/Remodify")
    public ResponseEntity<modifyReReplyDTO> updateReComment(@RequestBody modifyReReplyDTO dto) {

        reReplyService.update(dto);

        return ResponseEntity.ok().body(dto);
    }

    //대댓글 삭제
    @DeleteMapping ("/api/redelete/{id}")
    public ResponseEntity<Long> deleteRecomment(@PathVariable Long id) {


        System.out.println(id+"삭제 아이디 들어오나");
        reReplyService.deleteById(id);

        return ResponseEntity.ok().body(id);
    }


}