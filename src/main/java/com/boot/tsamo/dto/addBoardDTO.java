package com.boot.tsamo.dto;


import com.boot.tsamo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class addBoardDTO {

    private Long id;

    private String title;

    private List<AttachFileShowDto> attachFileShowList;

    private String content;

    private String userid;

    //
    public Board toEntity(){

        Board board = new Board();

        board.setTitle(title);
        board.setContent(content);

        return board;

    }


}
