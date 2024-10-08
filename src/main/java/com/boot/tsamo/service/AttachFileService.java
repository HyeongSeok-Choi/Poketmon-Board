package com.boot.tsamo.service;

import com.boot.tsamo.dto.AttachFileDto;
import com.boot.tsamo.dto.AttachFileFormDto;
import com.boot.tsamo.entity.AttachFile;
import com.boot.tsamo.entity.AttachFileAttribute;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Extension;
import com.boot.tsamo.repository.AttachFileAttributeRepository;
import com.boot.tsamo.repository.AttachFileRepository;
import com.boot.tsamo.repository.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //
@Log
@Transactional
public class AttachFileService {

    @Value("${attachFileLocation}")
    private String attachFileLocation;
    private final AttachFileRepository attachFileRepository;
    private final ExtensionRepository extensionRepository;
    private final AttachFileAttributeRepository attachFileAttributeRepository;
    @Transactional
    public void deleteAttachFile(Board board) {

        attachFileRepository.deleteByBoardId(board);

    }


    public void saveAttachFileList(List<MultipartFile> attachFileList, Board boardId) throws Exception{

        for(int i=0; i<attachFileList.size(); i++){
            AttachFile attachFile = new AttachFile();
            attachFile.setBoardId(boardId);

            //첨부하지 않은 첨부란은 파일정보가 DB에 저장되지 않도록 설정
            if(!attachFileList.get(i).isEmpty()) {
                saveAttachFile(attachFile, attachFileList.get(i));
            }
        }
    }


    // 이미지 원본 이름이 있다면 이미지 이름값과 URL을 종합하여 IMGURL에 저장하여 ENTITY에 값들을 저장하여 DB에 저장하는 서비스 메소드.
    public void saveAttachFile(AttachFile attachFile, MultipartFile multipartFile) throws Exception{
        String oriImgName = multipartFile.getOriginalFilename();
        String attachFileName="";
        String attachFileUrl = "";
        Long fileSize = multipartFile.getSize();

        if(!StringUtils.isEmpty(oriImgName)){
            attachFileName = uploadFile(attachFileLocation, oriImgName, multipartFile.getBytes());
            attachFileUrl = attachFileLocation + "/" + attachFileName;
        }

        attachFile.updateItemImg(oriImgName, attachFileName, attachFileUrl, fileSize);
        attachFileRepository.save(attachFile);
    }

    // DB에서 확장자를 가져와 파일의 확장자가 존재하면 true 없으면 false 반환 메소드
    public boolean isAllowedExtension(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        System.out.println(fileExtension+"확장자뭔데");
        List<Extension> allowedExtensions = getExtensions();

        for (Extension extension : allowedExtensions) {
            if (extension.getExtension().equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    // +@ DB에 파일에 해당하는 확장자가 없으면 "허용되지 않는 파일 형식입니다." 예외 처리
    // 해당 경로에 폴더가 없으면 폴더를 생성하고 경로에 파일 바이트코드를 기반으로 파일을 생성하며 UUID를 더한 파일명을 반환하는 서비스 메소드.
    // 파라미터 : 업로드 경로, 파일 원본 이름, 파일 바이트코드 데이터
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{

        if (!isAllowedExtension(originalFileName)) {
            throw new RuntimeException("허용되지 않는 파일 형식입니다.");
        }

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

    // 게시판의 첨부 파일 리스트를 불러오는 메서드
    public List<AttachFile> getAttachFileByBoardId(Long boardId) {
        return attachFileRepository.findByBoardIdId(boardId);
    }

    public AttachFile getAttachFile(Long boardId, Long fno) {
        return attachFileRepository.findByBoardIdIdAndId(boardId, fno);
    }

    public List<Extension> getExtensions() {
        return extensionRepository.findAll();
    }

    //파일 최대 갯수
    public int getMaxCnt(){return attachFileAttributeRepository.findById(1L).get().getMax_upload_cnt();}

    //파일 최대 사이즈
    public int getMaxSize(){return attachFileAttributeRepository.findById(1L).get().getMax_upload_size();}


    // 파일 확장자에 따라 미디어 타입 결정
    public MediaType determineMediaType(String fileName) {
        if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (fileName.endsWith(".mp4")) {
            return MediaType.valueOf("video/mp4");
        } else if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM; // 기본값
        }
    }

    @Transactional
    public void deleteFile(long bno, long fno) {
        // 삭제할 파일의 저장 경로를 얻어온다.
        String filePath = attachFileRepository.getAttachFileUrlByBoardIdAndId(bno, fno);

        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    attachFileRepository.deleteByBoardIdAndFileId(bno, fno);
                } else {
                    throw new RuntimeException("파일 삭제에 실패했습니다.");
                }
            } else {
                throw new RuntimeException("파일이 존재하지 않습니다.");
            }
        } else {
            throw new RuntimeException("파일 경로가 null 입니다.");
        }
    }

}
