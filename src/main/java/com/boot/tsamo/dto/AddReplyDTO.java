package com.boot.tsamo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddReplyDTO {
    private Long boardId;
    private String userid;
    private String content;

//    public Reply toEntity(Board board) {
//        Reply reply = new Reply();
//        reply.setBoardId(board);
//        reply.setContent(content);
//
//        return reply;
//    }
}
