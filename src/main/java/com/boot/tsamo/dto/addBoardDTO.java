package com.boot.tsamo.dto;


import com.boot.tsamo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class addBoardDTO {

    private String title;

    private String content;

    private String userid;


    public Board toEntity(){

        Board board = new Board();

        board.setTitle(title);
        board.setContent(content);

        return board;

    }


}
