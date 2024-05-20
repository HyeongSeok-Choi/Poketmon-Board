package com.boot.tsamo.controller;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Controller
public class PreviewController {

    private AttachFileService attachFileService;

    public PreviewController(AttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    @GetMapping("/preview/{bno}/{fno}")
    public ResponseEntity<?> showPreviewFile(@PathVariable Long fno, @PathVariable Long bno){
        try {
            AttachFile attachFile = attachFileService.getAttachFile(fno, bno); // attachFileService에서 파일 정보 가져오기
            if (attachFile == null) {
                return ResponseEntity.notFound().build();
            }
            String fileName = attachFile.getUuid_fileName();
            String filePath = attachFile.getFileUrl(); // 파일 경로 가져오기
            File file = new File(filePath); // 서버에 저장된 파일 경로 지정

            byte[] fileContent = Files.readAllBytes(file.toPath());
            MediaType mediaType = attachFileService.determineMediaType(fileName); // 미디어 타입 결정

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
