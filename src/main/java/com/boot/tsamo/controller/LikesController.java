package com.boot.tsamo.controller;


import com.boot.tsamo.dto.addLikesDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.repository.LikeRepository;
import com.boot.tsamo.service.BoardService;
import com.boot.tsamo.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")

public class LikesController {

    private final LikeService likeService;
    private final BoardService boardService;


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody addLikesDTO likesDTO, Principal principal) throws Exception{

        boolean checkLikes ;

        if(principal != null) {
            //뷰에서 받아온 정보에 로그인 아이디 대입
            likesDTO.setUserId(principal.getName());

            //좋아요 존재유무 확인(true라면 이미 존재, false라면 좋아요 없음)
            checkLikes = likeService.checklikes(likesDTO);

        }else{
            checkLikes=false;
        }


        return ResponseEntity.ok().body(checkLikes);
    }

    @PostMapping(value = "/getCounting")
    public ResponseEntity<?> getLikeCount(@RequestBody addLikesDTO likesDTO) throws Exception{


        Board board = boardService.findById(likesDTO.getBoardId());


       int likeCount = likeService.countLike(board);


        return ResponseEntity.ok().body(likeCount);


    }

}
