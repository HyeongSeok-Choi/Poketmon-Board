package com.boot.tsamo.controller;


import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.service.BoardService;
import com.boot.tsamo.service.FileService;
import com.boot.tsamo.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {


    //board서비스
    private final BoardService boardService;
    //File서비스
    private final FileService fileService;
    //HashTag서비스
    private final HashTagService hashTagService;

    @GetMapping(value = "/test")
    public String test() {


        return "test";
    }



    @GetMapping(value = "/")
    public String main(Model model,@PageableDefault(page=0,size = 10,sort = "id", direction = Sort.Direction.DESC) Pageable pageable,String title) {


            Page<Board> Boards ;

            if(title == null){
                Boards = boardService.findAll(pageable);

            }else{
                //제목으로 검색하기
                Boards = boardService.findAllByTitle(pageable,title);
            }


            int nowPage = Boards.getPageable().getPageNumber()+1;
            int startPage=Math.max(nowPage-4,1);
            int endPage=Math.min(nowPage+5,Boards.getTotalPages());



            if(!Boards.isEmpty()) {
                model.addAttribute("boards", Boards);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                return "main";
            }

            model.addAttribute("nowPage", null);
            model.addAttribute("startPage", null);
            model.addAttribute("endPage", null);

            return "main";
        }


    @GetMapping(value = "/createBoard")
    public String createBoard(Model model) {

        model.addAttribute("board", new Board());

        return "createBoard";
    }

    @PostMapping(value = "/createBoard")
    public String createBoardRequest(@ModelAttribute addBoardDTO addBoarddto,
                                     @RequestParam("uploadFiles") List<MultipartFile> files,
                                     @RequestParam("hashTagValue")String hashTagValue) {


        Board board = boardService.save(addBoarddto.toEntity());

        fileService.saveFile(files,board);

        hashTagService.saveHashTags(hashTagValue,board);



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
