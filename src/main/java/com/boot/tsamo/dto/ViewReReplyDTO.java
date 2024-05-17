package com.boot.tsamo.dto;

import com.boot.tsamo.entity.ReReply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewReReplyDTO {

    private Long Id;
    private String userid;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ViewReReplyDTO(ReReply reReply) {
        this.Id = reReply.getId();
        this.userid = reReply.getUserid().getUserId();
        this.content = reReply.getContent();
        this.createdAt = reReply.getCreatedAt();
        this.updatedAt = reReply.getUpdatedAt();
    }

}
