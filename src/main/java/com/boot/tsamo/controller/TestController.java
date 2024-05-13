package com.boot.tsamo.controller;


import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {


    //board서비스
    private final BoardService boardService;

    @GetMapping(value = "/test")
    public String test() {


        return "test";
    }

    @GetMapping(value = "/createBoard")
    public String createBoard(Model model) {

        model.addAttribute("board", new Board());

        return "createBoard";
    }

    @PostMapping(value = "/createBoard")
    public String createBoardRequest(@ModelAttribute addBoardDTO addBoarddto, @RequestParam("uploadFiles") List<MultipartFile> files) {

        System.out.println(files.get(0).getOriginalFilename());
        System.out.println(files.get(1).getOriginalFilename());
        System.out.println(files.get(2).getOriginalFilename());


        Users user = new Users();
        user.setUserId("최형석");

        boardService.save(addBoarddto.toEntity());

        return "test";
    }


    @GetMapping(value = "/BoardDetailView/{boardId}")
    public String boardDetailView(Model model,@PathVariable Long boardId) {

        Board detailBoard = boardService.findById(boardId);


        model.addAttribute("board", detailBoard);

        return "boardDetail";
    }

    /*
    @GetMapping(value = "/modifyBoard")
    public String modifyBoard(Model model,@PathVariable Long Id) {

        Board detailBoard = boardService.findById(Id);


        model.addAttribute("board", detailBoard);

        return "createBoard";
    }

    */
}
