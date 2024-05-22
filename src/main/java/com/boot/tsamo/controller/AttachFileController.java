package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.Extension;
import com.boot.tsamo.service.AttachFileService;
import com.boot.tsamo.service.FileAttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AttachFileController {

    private final AttachFileService fileService;
    private final FileAttributeService fileAttributeService;

    @GetMapping(value = "/attachFile")
    public String attachFileForm(Model model){

        List<Extension> extensions = fileService.getExtensions();
        Integer maxUploadCnt = fileAttributeService.getMaxRequestCnt(1L);

        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
        model.addAttribute("extensions", extensions);
        model.addAttribute("maxUploadCnt", maxUploadCnt);
        return "attachFile/attachFile";


    }

    /*
    // 상품등록
    @PostMapping(value ="/attachFile")
    public String attachFileNew(@Valid AttachFileFormDto attachFileFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("attachFile") List<MultipartFile> attachFileList){


        if(bindingResult.hasErrors()){
            return "attachFile/attachFile";
        }

        if(attachFileList.get(0).isEmpty() && attachFileFormDto.getId()==null){
            model.addAttribute("errorMessage", "첨부파일 입력 필요");
            return "attachFile/attachFile";
        }

        try{
            fileService.saveAttachFileList(attachFileList);
        } catch(Exception e){
            model.addAttribute("errorMessage", "첨부파일 DB 저장 에러");
            return "attachFile/attachFile";
        }

        return "redirect:/";
    }

     */
}
