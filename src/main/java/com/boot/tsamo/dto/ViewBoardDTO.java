package com.boot.tsamo.dto;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.HashTag;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.LikeRepository;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ViewBoardDTO {

    private Long id;

    private String title;

    private String content;

    private Long viewCount;

    private int LikeCount;

    private boolean deleted;

    private String userid;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> hashTagContent =new ArrayList<>();

    private List<AttachFileDto> attachFile = new ArrayList<>();


    public ViewBoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
        this.deleted = board.isDeleted();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.userid = board.getUserid().getUserId();
      for (HashTag hashs:board.getHashTags()){
          hashTagContent.add(hashs.getHashTagContent());
      }
      board.getAttachFileArrayList().forEach(attachFile->{
          AttachFileDto attachDTO = new AttachFileDto();
          attachDTO.setFileUrl(attachFile.getFileUrl());
          attachDTO.setOri_fileName(attachFile.getOri_fileName());
          attachDTO.setUuid_fileName(attachFile.getUuid_fileName());
          attachDTO.setId(attachFile.getId());

          this.attachFile.add(attachDTO);
      });

    }


}
