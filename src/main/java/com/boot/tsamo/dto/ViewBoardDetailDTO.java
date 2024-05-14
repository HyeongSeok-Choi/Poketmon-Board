package com.boot.tsamo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewBoardDetailDTO {

    private Long id;

    private String title;

    private String content;

    private String userId;

    private String nickName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
