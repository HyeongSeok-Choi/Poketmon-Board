package com.boot.tsamo.dto;

import com.boot.tsamo.entity.Board;
import lombok.Data;

@Data
public class addHashTagDTO {

    private Board boardId;

    private String HashTag_content;
}
