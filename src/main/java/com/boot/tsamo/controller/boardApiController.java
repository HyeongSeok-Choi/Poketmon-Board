package com.boot.tsamo.controller;

import com.boot.tsamo.dto.ViewBoardDTO;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.repository.LikeRepository;
import com.boot.tsamo.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class boardApiController {

    private final BoardService boardService;
    private final LikeRepository likeRepository;


    @GetMapping(value = "/api/allboards")
    public ResponseEntity<?> getAllBoards() {

       List<Board> boards= boardService.findAll();


       List<ViewBoardDTO> viewBoardDTOs=
                        boards.stream()
                       .map(a->new ViewBoardDTO(a))
                       .collect(Collectors.toList());


       return ResponseEntity.ok(viewBoardDTOs);

    }
}
