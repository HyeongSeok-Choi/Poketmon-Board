package com.boot.tsamo.controller;


import com.boot.tsamo.dto.DeleteFileRequestDTO;
import com.boot.tsamo.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeleteController {

    private final AttachFileService attachFileService;

    // 비동기 api를 사용하여 파일 제거 로직
    @PostMapping("/api/deleteFile")
    public ResponseEntity<String> fetchDeleteFile(@RequestBody List<DeleteFileRequestDTO> requestDTOList) {
        try {
            for (DeleteFileRequestDTO requestDTO : requestDTOList) {
                attachFileService.deleteFile(requestDTO.getBno(), requestDTO.getFno());
            }
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류가 발생했습니다.");
        }
    }

}
