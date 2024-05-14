package com.boot.tsamo.controller;


import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.dto.attachAttributeDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.service.BoardService;
import com.boot.tsamo.service.FileAttributeService;
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
    //attachAttribute서비스
    private final FileAttributeService fileAttributeService;

    //test뷰(없어도 됨)
    @GetMapping(value = "/test")
    public String test() {

        return "test";
    }

    //test뷰(없어도 됨)
    @GetMapping(value = "/admin")
    public String admin() {

        return "adminpage";
    }

    //게시물 목록(main페이지) 검색, 페이징 기능 포함
    @GetMapping(value = "/")
    public String main(Model model,@PageableDefault(page=0,size = 1,sort = "id", direction = Sort.Direction.DESC) Pageable pageable,String searchvalue,String searchtype) {


            Page<Board> Boards ;

            if(searchvalue == null){
                Boards = boardService.findAll(pageable);

            }else{
                //제목으로 검색하기
                if(searchtype.equals("title")){
                    Boards = boardService.findAllByTitle(pageable,searchvalue);
                }else if(searchtype.equals("content")){
                    //본문으로 검색하기
                    Boards = boardService.findAllByContent(pageable,searchvalue);
                }else if(searchtype.equals("userid")){
                    //작성자으로 검색하기
                    Boards = boardService.findAllByUserId(pageable,searchvalue);
                }

                else{

                    Boards = boardService.findAllByHashTag(pageable,searchvalue);
                    //Boards = boardService.findAll(pageable);
                }

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


    //게시물 등록 뷰
    @GetMapping(value = "/createBoard")
    public String createBoard(Model model) {

        model.addAttribute("board", new Board());

        return "createBoard";
    }

    //게시물 등록
    @PostMapping(value = "/createBoard")
    public String createBoardRequest(@ModelAttribute addBoardDTO addBoarddto,
                                     @RequestParam("uploadFiles") List<MultipartFile> files,
                                     @RequestParam("hashTagValue")String hashTagValue) {


        Board board = boardService.save(addBoarddto.toEntity());

        fileService.saveFile(files,board);

        hashTagService.saveHashTags(hashTagValue,board);


        return "redirect:/";
    }


    //게시물 상세보기
    @GetMapping(value = "/BoardDetailView")
    public String boardDetailView(Model model,@RequestParam Long id) {

        Board detailBoard = boardService.findById(id);


        model.addAttribute("board", detailBoard);

        return "boardDetail";
    }


    @GetMapping(value = "/modifyBoard")
    public String modifyBoard(Model model,@RequestParam Long id) {

        Board detailBoard = boardService.findById(id);

        model.addAttribute("board", detailBoard);

        return "createBoard";
    }

    @PostMapping(value = "/deleteBoard")
    public String deleteBoard(Model model,@RequestParam Long id) {

        boardService.deleteById(id);

        return"redirect:/";
    }

    @PostMapping(value = "/attachatt")
    public String attachatt(@RequestParam List<String> extension, int maxcnt,int maxsize) {

        System.out.println(extension);
        System.out.println(maxcnt);
        System.out.println(maxsize);

        attachAttributeDTO attachAttributeDTO = new attachAttributeDTO();
        attachAttributeDTO.setExtension(extension);
        attachAttributeDTO.setMaxsize(maxsize);
        attachAttributeDTO.setMaxcnt(maxcnt);

        fileAttributeService.attachFileAttribute(attachAttributeDTO);

        return"redirect:/";
    }


}
