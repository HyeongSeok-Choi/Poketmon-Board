package com.boot.tsamo.service;

import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor //
@Log
@Transactional
public class FileService {

    @Value("${attachFileLocation}")
    private String attachFileLocation;
    private final AttachFileRepository attachFileRepository;

    public void saveAttachFileList(List<MultipartFile> attachFileList) throws Exception{

        for(int i=0; i<attachFileList.size(); i++){
            AttachFile attachFile = new AttachFile();
//            attachFile.setBoardId(boardId);
            saveAttachFile(attachFile, attachFileList.get(i));
        }
    }


    // 이미지 원본 이름이 있다면 이미지 이름값과 URL을 종합하여 IMGURL에 저장하여 ENTITY에 값들을 저장하여 DB에 저장하는 서비스 메소드.
    public void saveAttachFile(AttachFile attachFile, MultipartFile multipartFile) throws Exception{
        String oriImgName = multipartFile.getOriginalFilename();
        String attachFileName="";
        String attachFileUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            attachFileName = uploadFile(attachFileLocation, oriImgName, multipartFile.getBytes());
            attachFileUrl = attachFileLocation + attachFileName;
        }

        attachFile.updateItemImg(oriImgName, attachFileName, attachFileUrl);
        attachFileRepository.save(attachFile);
    }


    // 해당 경로에 폴더가 없으면 폴더를 생성하고 경로에 파일 바이트코드를 기반으로 파일을 생성하며 UUID를 더한 파일명을 반환하는 서비스 메소드.
    // 파라미터 : 업로드 경로, 파일 원본 이름, 파일 바이트코드 데이터
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        // 디렉토리 유무 확인 추가 코드
        File targetDir = new File(uploadPath);
        if(!targetDir.exists()){
            if(!targetDir.mkdirs()){
                log.info("이미지 저장 경로 생성 실패");
                throw new RuntimeException();
            }
        }
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }
}
