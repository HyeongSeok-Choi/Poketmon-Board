package com.boot.tsamo.dto;

import com.boot.tsamo.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class ViewReplyDTO {

    private Long replyId;
    private Long boardId;
    private String userid;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ViewReplyDTO(){}



    /* entity -> dto*/
    public ViewReplyDTO(Reply reply) {
        this.replyId = reply.getReplyId();
        this.boardId = reply.getBoardId().getId();
        this.userid = reply.getUserid().getUserId();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.updatedAt = reply.getUpdatedAt();
    }

}
