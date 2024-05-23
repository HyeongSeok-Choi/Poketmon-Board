package com.boot.tsamo.controller;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Controller
public class PreviewController {

    private final AttachFileService attachFileService;

    @GetMapping("/preview/{bno}/{fno}")
    public ResponseEntity<?> showPreviewFile(@PathVariable Long bno, @PathVariable Long fno){
        try {
            AttachFile attachFile = attachFileService.getAttachFile(bno, fno); // attachFileService에서 파일 정보 가져오기
            if (attachFile == null) {
                return ResponseEntity.notFound().build();
            }
            String fileName = attachFile.getUuid_fileName();
            String filePath = attachFile.getFileUrl(); // 파일 경로 가져오기
            File file = new File(filePath); // 서버에 저장된 파일 경로 지정

            byte[] fileContent = Files.readAllBytes(file.toPath());
            MediaType mediaType = attachFileService.determineMediaType(fileName); // 미디어 타입 결정

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(attachFile.getOri_fileName()).build());

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
