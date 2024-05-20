package com.boot.tsamo.controller;


import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.dto.attachAttributeDTO;
import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    //댓글 리스트 조회
    @GetMapping(value = "/reply")
    public String reply(Model model) {

        return "ReplyView";
    }

    //board서비스
    private final BoardService boardService;
    //File서비스
    private final AttachFileService fileService;
    //HashTag서비스
    private final HashTagService hashTagService;
    //attachAttribute서비스
    private final FileAttributeService fileAttributeService;

    //test뷰(없어도 됨)
    @GetMapping(value = "/test")
    public String test(Principal principal) {
        System.out.println(principal.getName());
        System.out.println("아니 들어오니");

        return "test";
    }

    @GetMapping(value = "/admin")
    public String admin() {

        return "adminpage";
    }

    //게시물 목록(main페이지) 검색, 페이징 기능 포함
    @GetMapping(value = "/")
    public String main(Model model,@PageableDefault(page=0,size = 3,sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable, String searchvalue, String searchtype,String sort) {


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
                    for(Board board : Boards){
                        board.getId();
                        board.getTitle();
                        board.getReplies();

                    }

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


    //게시물 등록 뷰
    @GetMapping(value = "/createBoard")
    public String createBoard(Model model) {

        model.addAttribute("board", new Board());
        model.addAttribute("attachFileFormDto", new AttachFileFormDto());

        return "createBoard";
    }

    //게시물 등록
    @PostMapping(value = "/createBoard2")
    public String createBoardRequest(@ModelAttribute addBoardDTO addBoarddto,
                                     @RequestParam("attachFile") List<MultipartFile> attachFileList,
                                     @RequestParam("hashTagValue")String hashTagValue,
                                     @Valid AttachFileFormDto attachFileFormDto, BindingResult bindingResult,
                                     Model model) {

        if(bindingResult.hasErrors()){
            return "createBoard";
        }

        if(attachFileList.get(0).isEmpty() && attachFileFormDto.getId()==null){
            model.addAttribute("errorMessage", "첨부파일 입력 필요");
            return "createBoard";
        }


        Board board = boardService.save(addBoarddto.toEntity());

        try{
            fileService.saveAttachFileList(attachFileList,board);
        } catch(Exception e){
            model.addAttribute("errorMessage", "첨부파일 DB 저장 에러");
            return "createBoard";
        }


        hashTagService.saveHashTags(hashTagValue,board);


        return "redirect:/";
    }


    //게시물 상세보기
    @GetMapping(value = "/BoardDetailView")
    public String BoardDetailView(Model model,@RequestParam Long id,Principal principal) {

        if(principal != null) {
            String userid = principal.getName();
            model.addAttribute("userid", userid);
        }


        List<AttachFile> attachFiles = fileService.getAttachFileByBoardId(id);

        for(AttachFile attachFile : attachFiles){
            System.out.println(attachFile.getUuid_fileName()+"여기 있습니다.");

        }



        model.addAttribute("attachFiles", attachFiles);



        Board detailBoard = boardService.findById(id);
        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
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
    public String attachatt(@RequestParam(required = false) List<String> extension, int maxcnt, int maxsize) {

        //null처리
        if(extension == null){
            extension = new ArrayList<>();
        }

        if(extension == null){
            extension = new ArrayList<>();
        }

        attachAttributeDTO attachAttributeDTO = new attachAttributeDTO();
        attachAttributeDTO.setExtension(extension);
        attachAttributeDTO.setMaxsize(maxsize);
        attachAttributeDTO.setMaxcnt(maxcnt);

        fileAttributeService.attachFileAttribute(attachAttributeDTO);

        return"redirect:/";
    }


}
