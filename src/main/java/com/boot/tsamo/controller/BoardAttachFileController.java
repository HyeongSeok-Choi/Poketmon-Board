package com.boot.tsamo.controller;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("board")
@Controller
@RequiredArgsConstructor
public class BoardAttachFileController {

    private final AttachFileService fileService;

    @GetMapping("/getAttach/{bno}")
    public String getAttach(@PathVariable Long bno, Model model){

        List<AttachFile> attachFiles = fileService.getAttachFileByBoardId(bno);

        model.addAttribute("attachFiles", attachFiles);

        return "attachFile/boardAttachFile";
    }
}
