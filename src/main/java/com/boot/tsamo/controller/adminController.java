package com.boot.tsamo.controller;

import com.boot.tsamo.dto.attachAttributeDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.service.AttachFileService;
import com.boot.tsamo.service.BoardService;
import com.boot.tsamo.service.FileAttributeService;
import com.boot.tsamo.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class adminController {

    //board서비스
    private final BoardService boardService;

    private final FileAttributeService fileAttributeService;

    @GetMapping(value = "/attachFileAtt")
    public String admin(Model model) {

        attachAttributeDTO attachAttributeDTO = fileAttributeService.getattachAttributedto();

        String extensions="";
        for (String extension : attachAttributeDTO.getExtension()){
            extensions+=extension+",";
        }

        model.addAttribute("extensions",extensions);
        model.addAttribute("attachAttributeDTO", attachAttributeDTO);


        return "adminpage";
    }

    //게시물 목록(main페이지) 검색, 페이징 기능 포함
    @GetMapping(value = "/deletedBoard")
    public String main(Model model, @PageableDefault(page=0,size = 10,sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable, String searchvalue, String searchtype, String sort,String mainOrAdmin) {

        mainOrAdmin="admin";

        model.addAttribute("mainOrAdmin", mainOrAdmin);


        if(sort == null || sort.equals("title")) {
            Sort.by(Sort.Direction.DESC, "title");
        } else if (sort.equals("createdAt")) {
            Sort.by(Sort.Direction.DESC, "createdAt");
        }else if (sort.equals("userid")) {
            Sort.by(Sort.Direction.DESC, "userid");
        }

        Page<Board> Boards = boardService.findAll(pageable);

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

            else if(searchtype.equals("hashTag")){

                Boards = boardService.findAllByHashTag(pageable,searchvalue);

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
            model.addAttribute("searchvalue", searchvalue);
            model.addAttribute("searchtype", searchtype);
            model.addAttribute("sort", sort);
            return "main";
        }

        model.addAttribute("nowPage", null);
        model.addAttribute("startPage", null);
        model.addAttribute("endPage", null);
        model.addAttribute("searchvalue", searchvalue);
        model.addAttribute("searchtype", searchtype);
        model.addAttribute("sort", sort);

        return "main";
    }

    @PostMapping(value = "/deletedBoard/restore/{id}")
    public ResponseEntity<?> restoreBoard (@PathVariable Long id){

       String confirmRestored = boardService.restore(id);

       return ResponseEntity.ok().body(confirmRestored);
    }


}
