package com.boot.tsamo.controller;


import com.boot.tsamo.dto.DeleteFileRequestDTO;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class DeleteController {

    private final AttachFileService attachFileService;

    @PostMapping("/api/deleteFile")
    public ResponseEntity<String> fetchDeleteFile(@RequestBody DeleteFileRequestDTO requestDTO) {
        try {
            attachFileService.deleteFile(requestDTO.getBno(), requestDTO.getFno());
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류가 발생했습니다.");
        }
    }

}
