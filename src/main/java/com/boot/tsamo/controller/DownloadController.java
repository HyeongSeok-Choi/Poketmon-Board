package com.boot.tsamo.controller;

import com.boot.tsamo.dto.AttachFileDto;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class DownloadController {

    private final AttachFileService attachFileService;

    @GetMapping("/getAttach/{bno}/download/{fno}")
    public ResponseEntity<UrlResource> downloadAttachFile(@PathVariable Long fno, @PathVariable Long bno){

        AttachFileDto attachFileDto = AttachFileDto.of(attachFileService.getAttachFile(fno, bno));
        UrlResource resource;
        try{
            resource = new UrlResource("file:"+ attachFileDto.getFileUrl());
        }catch (MalformedURLException e){
//            log.error("the given File path is not valid");
            e.getStackTrace();
            throw new RuntimeException("the given URL path is not valid");
        }

        String originalFileName = attachFileDto.getOri_fileName();
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                .body(resource);
    }
}
