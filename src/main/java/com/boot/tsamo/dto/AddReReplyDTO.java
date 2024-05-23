package com.boot.tsamo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddReReplyDTO {
    private Long replyId;
    private String userid;
    private String content;

}
