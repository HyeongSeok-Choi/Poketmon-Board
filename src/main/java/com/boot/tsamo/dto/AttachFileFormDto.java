package com.boot.tsamo.dto;



import com.boot.tsamo.entity.AttachFile;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AttachFileFormDto {

    private Long id;

    private List<AttachFileDto> attachFileDtoList = new ArrayList<>();

    private List<Long> attachFileIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public AttachFile createAttachFile(){
        return modelMapper.map(this, AttachFile.class);
    }
    public static AttachFileDto of(AttachFile attachFile){
        return modelMapper.map(attachFile,AttachFileDto.class);
    }
}
