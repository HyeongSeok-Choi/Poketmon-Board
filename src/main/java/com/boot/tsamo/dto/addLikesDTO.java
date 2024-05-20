package com.boot.tsamo.dto;

import lombok.Data;

@Data
public class addLikesDTO {

    private Long boardId;

    private String userId;

    private String check;

    public addLikesDTO(Long boardId, String userId,String check) {
        this.boardId = boardId;
        this.userId = userId;
        this.check = check;
    }

}
