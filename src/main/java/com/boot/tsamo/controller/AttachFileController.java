package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.service.AttachFileService;
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

    @GetMapping(value = "/attachFile")
    public String attachFileForm(Model model){

        model.addAttribute("attachFileFormDto", new AttachFileFormDto());
        return "attachFile/attachFile";
    }

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
}
