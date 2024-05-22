package com.boot.tsamo.dto;

import com.boot.tsamo.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExcelDTO {

    private Long id;

    private String title;

    private String content;

    private String userId;

    private LocalDateTime createdAt;

    private Long viewCount;


    public ExcelDTO(Board board) {

        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUserid().getUserId();
        this.createdAt = board.getCreatedAt();
        this.viewCount = board.getViewCount();

    }

}