package com.boot.tsamo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
