package com.boot.tsamo.controller;


import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.dto.DeleteFileRequestDTO;
import com.boot.tsamo.dto.addBoardDTO;
import com.boot.tsamo.dto.attachAttributeDTO;
import com.boot.tsamo.entity.*;
import com.boot.tsamo.repository.VisitCountRepository;
import com.boot.tsamo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Data
public class TestController {
    public  static String id ="아 왜 안나와";

    private final AttachFileService attachFileService;
    private final LikeService likeService;
    private final VisitCountRepository visitCountRepository;

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


    //게시물 목록(main페이지) 검색, 페이징 기능 포함
    @GetMapping(value = "/")
    public String main(Model model, @PageableDefault(page = 0, size = 3, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable, String searchvalue,
                       String searchtype, String sort, HttpServletRequest request,String mainOrAdmin,Principal principal) {


        if(principal != null){
            id = principal.getName();
        }

        mainOrAdmin="main";

        model.addAttribute("mainOrAdmin", mainOrAdmin);

        String ipAddress = request.getRemoteAddr();
        LocalDate today = LocalDate.now();

        long totalVisitCount = visitCountRepository.count(); // 전체 방문자 수 조회
        model.addAttribute("totalVisitCount", totalVisitCount); // 모델에 방문자 수 추가

        // 중복 방문 여부 확인
        if (!visitCountRepository.findByIpAddressAndVisitDate(ipAddress, today).isPresent()) {
            // 중복 방문이 아니라면, 방문 기록 저장
            VisitCount visitCount = new VisitCount(ipAddress, today);
            visitCountRepository.save(visitCount);
        }


        if (sort == null || sort.equals("title")) {
            Sort.by(Sort.Direction.DESC, "title");
        } else if (sort.equals("createdAt")) {
            Sort.by(Sort.Direction.DESC, "createdAt");
        } else if (sort.equals("userid")) {
            Sort.by(Sort.Direction.DESC, "userid");
        }

        Page<Board> Boards = boardService.findAll(pageable,mainOrAdmin);

        if (searchvalue == null) {
            Boards = boardService.findAll(pageable,mainOrAdmin);

        } else {
            //제목으로 검색하기
            if (searchtype.equals("title")) {
                Boards = boardService.findAllByTitle(pageable, searchvalue,mainOrAdmin);
            } else if (searchtype.equals("content")) {
                //본문으로 검색하기
                Boards = boardService.findAllByContent(pageable, searchvalue,mainOrAdmin);
            } else if (searchtype.equals("userid")) {
                //작성자으로 검색하기
                Boards = boardService.findAllByUserId(pageable, searchvalue,mainOrAdmin);
            } else if (searchtype.equals("hashTag")) {

                    Boards = boardService.findAllByHashTag(pageable,searchvalue,mainOrAdmin);


            }

        }

        int nowPage = Boards.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, Boards.getTotalPages());

        if (!Boards.isEmpty()) {
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

    @GetMapping(value = "/visitCount")
    public String visitCountPage(Model model) {

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // 날짜별 방문자 수 조회
        List<Object[]> visitCountsByDate = visitCountRepository.countVisitsByDateSince(sevenDaysAgo);


        List<VisitCountByDate> visitCountByDateList = visitCountsByDate.stream()
                .map(result -> new VisitCountByDate((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        model.addAttribute("visitCountByDateList", visitCountByDateList);

        return "visitCount";
    }


    @ResponseBody
    @GetMapping(value = "/visitCountData")
    public ResponseEntity<?> visitCountData(Model model) {
        /*String ipAddress = request.getRemoteAddr();
        LocalDate today = LocalDate.now();

        // 전체 방문자 수 조회
        long totalVisitCount = visitCountRepository.count();
        model.addAttribute("totalVisitCount", totalVisitCount);

        // 중복 방문 여부 확인
        if (!visitCountRepository.findByIpAddressAndVisitDate(ipAddress, today).isPresent()) {
            // 중복 방문이 아니라면, 방문 기록 저장
            VisitCount visitCount = new VisitCount(ipAddress, today);
            visitCountRepository.save(visitCount);
        }
*/
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // 날짜별 방문자 수 조회
        List<Object[]> visitCountsByDate = visitCountRepository.countVisitsByDateSince(sevenDaysAgo);


        List<VisitCountByDate> visitCountByDateList = visitCountsByDate.stream()
                .map(result -> new VisitCountByDate((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

//        model.addAttribute("visitCountByDateList", visitCountByDateList);

        return ResponseEntity.ok(visitCountByDateList);
    }

    // 날짜별 방문자 수를 담는 DTO 클래스
    public static class VisitCountByDate {
        private LocalDate visitDate;
        private Long count;

        public VisitCountByDate(LocalDate visitDate, Long count) {
            this.visitDate = visitDate;
            this.count = count;
        }

        public LocalDate getVisitDate() {
            return visitDate;
        }

        public Long getCount() {
            return count;
        }
    }


    //게시물 등록 뷰
    @GetMapping(value = "/createBoard")
    public String createBoard(Model model) {

        List<Extension> extensions = fileService.getExtensions();
        Integer maxUploadCnt = fileAttributeService.getMaxRequestCnt(1L);


        List<HashTag> hashTags = new ArrayList<>();
        hashTags.add(new HashTag());

        model.addAttribute("board", new Board());
        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
        model.addAttribute("extensions", extensions);
        model.addAttribute("maxUploadCnt", maxUploadCnt);
        model.addAttribute("hashTags", hashTags);
        model.addAttribute("createOrModify","create");
        model.addAttribute("fileMaxCnt",attachFileService.getMaxCnt());
        model.addAttribute("fileMaxSize",attachFileService.getMaxSize());


        return "createBoard";
    }


    //게시물 등록 요청
    @PostMapping(value = "/createBoardRequest")
    public String createBoardRequest(@ModelAttribute addBoardDTO addBoarddto,
                                     @RequestParam("attachFile") List<MultipartFile> attachFileList,
                                     @RequestParam("hashTagValue")String hashTagValue,
                                     @RequestParam("createOrModify") String createOrModify,
                                     @RequestParam("deleteRequestDTOList") String deleteRequestJson,
                                     @RequestParam(value = "boardId",required = false) Long boardId,
                                     Model model,Principal principal) throws Exception {


        //더미 해시값(해시 값이 없을 경우)
        List<HashTag> hashTags = new ArrayList<>();
        hashTags.add(new HashTag());
        hashTags = hashTagService.getHashTagsByHashTagValue(hashTagValue);

        //확장자 받기
        List<Extension> extensions = fileService.getExtensions();
        Integer maxUploadCnt = fileAttributeService.getMaxRequestCnt(1L);

        model.addAttribute("hashTags", hashTags);
        model.addAttribute("createOrModify", createOrModify);
        model.addAttribute("fileMaxCnt",attachFileService.getMaxCnt());
        model.addAttribute("fileMaxSize",attachFileService.getMaxSize());
        model.addAttribute("attachFileList", attachFileList);
        model.addAttribute("board", addBoarddto);
        model.addAttribute("extensions", extensions);
        model.addAttribute("maxUploadCnt", maxUploadCnt);

        int maxsize = 0;

        if(createOrModify.equals("modify")) {
            List<AttachFile> attachFiles = fileService.getAttachFileByBoardId(boardId);
            Board board = boardService.findById(boardId);

            model.addAttribute("board", board);
            model.addAttribute("attachFiles", attachFiles);

//            for (AttachFile attachFile : attachFiles) {
//
//                maxsize += attachFile.getSize();
//            }
        }

        for (MultipartFile attachFile : attachFileList) {
            System.out.println(maxsize + "사이즈 본다잉");
            System.out.println();
            maxsize += attachFile.getSize();

        }

        if (maxsize > attachFileService.getMaxSize() * 1024 * 1024) {


            model.addAttribute("errorMessage", "최대 파일 업로드 용량을 초과하였습니다.");

            return "createBoard";
        }

        //연습

        //로직을 밖으로 빼기
        for (MultipartFile a : attachFileList) {

               if(a.getOriginalFilename() !="") {
                   if (!attachFileService.isAllowedExtension(a.getOriginalFilename())) {

                       model.addAttribute("errorMessage", "허용되지 않는 파일 형식입니다.");

                    return "createBoard";
                }
            }
        }

        //등록 수정을 포함하는 로직
        Map<String, Board> save = boardService.save(addBoarddto.toEntity(), principal, boardId);

        Board board;

        if (save.get("modify") != null ? true : false) {
            System.out.println("모디파이");

            board = save.get("modify");
            hashTagService.deleteHashTags(board);

            //create 혹은 modify 각 메소드 차별화된 기능이 추가되는 경우 사용할 것.
            if(createOrModify.equals("modify")) {
                try {
                    // JSON 문자열을 리스트로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<DeleteFileRequestDTO> deleteRequestDTOList = objectMapper.readValue(deleteRequestJson, new TypeReference<List<DeleteFileRequestDTO>>() {
                    });

                    for (DeleteFileRequestDTO requestDTO : deleteRequestDTOList) {
                        attachFileService.deleteFile(requestDTO.getBno(), requestDTO.getFno());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            hashTagService.saveHashTags(hashTagValue,board);
        }else{
            System.out.println("크리에이트");
            board = save.get("create");
            hashTagService.saveHashTags(hashTagValue,board);
        }

        fileService.saveAttachFileList(attachFileList,board);

        //연습끝
        return "redirect:/";

    }

    //게시물 수정 뷰
    @PostMapping(value = "/modifyBoard")
    public String modifyBoard(Model model,@RequestParam Long id) {

        List<Extension> extensions = fileService.getExtensions();
        Integer maxUploadCnt = fileAttributeService.getMaxRequestCnt(1L);
        List<AttachFile> attachFiles = fileService.getAttachFileByBoardId(id);

        Board board = boardService.findById(id);

        List<HashTag> hashTags;
        hashTags = board.getHashTags();

        if (hashTags.size() == 0) {
            hashTags.add(new HashTag());
        }

        model.addAttribute("attachFiles", attachFiles);
        model.addAttribute("board", board);
        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
        model.addAttribute("extensions", extensions);
        model.addAttribute("maxUploadCnt", maxUploadCnt);
        model.addAttribute("hashTags", hashTags);
        model.addAttribute("createOrModify","modify");
        model.addAttribute("fileMaxCnt",attachFileService.getMaxCnt());
        model.addAttribute("fileMaxSize",attachFileService.getMaxSize());

        return "createBoard";
    }


    //게시물 상세보기
    @GetMapping(value = "/BoardDetailView")
    public String BoardDetailView(Model model, @RequestParam Long id, Principal principal) {


        if(boardService.findById(id).isDeleted()){

            return "deletedWarning";

        }


        if(principal != null) {
            String userid = principal.getName();
            model.addAttribute("userid", userid);
        }


        List<AttachFile> attachFiles = fileService.getAttachFileByBoardId(id);

        for (AttachFile attachFile : attachFiles) {
            System.out.println(attachFile.getUuid_fileName() + "여기 있습니다.");

        }

        boardService.getViewCounting(id);

        model.addAttribute("attachFiles", attachFiles);


        Board detailBoard = boardService.findById(id);
        model.addAttribute("LikeCount", likeService.countLike(detailBoard));
        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
        model.addAttribute("board", detailBoard);
        model.addAttribute("hashTag", hashTagService.getHashTags(detailBoard));

        return "boardDetail";
    }


    @PostMapping(value = "/deleteBoard")
    public String deleteBoard(Model model, @RequestParam Long id) {

        boardService.deleteByIdbyboolean(id);
        //boardService.deleteById(id);

        return "redirect:/";
    }

    @PostMapping(value = "/attachatt")
    public String attachatt(@RequestParam(required = false) List<String> extension, int maxcnt, int maxsize) {

        //null처리
        if (extension == null) {
            extension = new ArrayList<>();
        }

        if (extension == null) {
            extension = new ArrayList<>();
        }

        attachAttributeDTO attachAttributeDTO = new attachAttributeDTO();
        attachAttributeDTO.setExtension(extension);
        attachAttributeDTO.setMaxsize(maxsize);
        attachAttributeDTO.setMaxcnt(maxcnt);

        fileAttributeService.attachFileAttribute(attachAttributeDTO);

        return "redirect:/";
    }


}