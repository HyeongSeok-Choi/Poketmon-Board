package com.boot.tsamo.dto;

import com.boot.tsamo.entity.AttachFile;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AttachFileDto {

    private Long id;

    private String fileUrl;

    private String ori_fileName;

    private String uuid_fileName;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AttachFileDto of(AttachFile attachFile){
        return modelMapper.map(attachFile,AttachFileDto.class);
    }
}
