package com.boot.tsamo.service;

import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AttachFileRepository AttachfileRepository;

    private final String uploadDir = Paths.get("C:", "boardUpload", "files").toString();

    public void saveFile(List<MultipartFile> files, Board savedBoard) {



        for (MultipartFile file : files) {
            AttachFile attachFile = new AttachFile();
            if (!file.isEmpty()) {
                attachFile.setBoardId(savedBoard);
                String orgFilename = file.getOriginalFilename();                                         // 원본 파일명
                attachFile.setOri_fileName(orgFilename);
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
                String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
                String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
                attachFile.setUuid_fileName(saveFilename);
                String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
                System.out.println(fileFullPath + "이거");

                // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
                File dir = new File(uploadDir);
                if (dir.exists() == false) {
                    dir.mkdirs();
                }

                try {
                    // 파일 저장 (write to disk)
                    File uploadFile = new File(fileFullPath);
                    file.transferTo(uploadFile);
                    attachFile.setFileUrl("/file-print/board?filename="+saveFilename);
                    AttachfileRepository.save(attachFile);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}
