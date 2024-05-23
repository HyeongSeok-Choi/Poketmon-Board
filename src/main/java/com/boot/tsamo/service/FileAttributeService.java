package com.boot.tsamo.service;


import com.boot.tsamo.dto.attachAttributeDTO;
import com.boot.tsamo.entity.AttachFileAttribute;
import com.boot.tsamo.entity.Extension;
import com.boot.tsamo.repository.AttachFileAttributeRepository;
import com.boot.tsamo.repository.AttachFileRepository;
import com.boot.tsamo.repository.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileAttributeService {

    private final AttachFileAttributeRepository attachFileAttributeRepository;
    private final ExtensionRepository extensionRepository;

    @Transactional
    public void attachFileAttribute(attachAttributeDTO attachAttributeDTO){

        extensionRepository.deleteAll();

        List<String> extensions= attachAttributeDTO.getExtension();

        for(String extension : extensions){
            Extension extension1 = new Extension();

            extension1.setExtension(extension);
            extensionRepository.save(extension1);

        }

        AttachFileAttribute attachFileAttribute = new AttachFileAttribute();

        attachFileAttribute.setId(1L);
        attachFileAttribute.setMax_upload_cnt(attachAttributeDTO.getMaxcnt());
        attachFileAttribute.setMax_upload_size(attachAttributeDTO.getMaxsize());

        attachFileAttributeRepository.save(attachFileAttribute);

    }

    public Integer getMaxRequestSize(Long id){
        return attachFileAttributeRepository.findMax_Upload_SizeById(id);
    }

    //저장된 첨부파일 속성 불러오기
    public attachAttributeDTO getattachAttributedto(){

       AttachFileAttribute attachFileAttribute= attachFileAttributeRepository.findById(1L).get();

        attachAttributeDTO attachAttributeDTO = new attachAttributeDTO();
        attachAttributeDTO.setMaxsize(attachFileAttribute.getMax_upload_size());
        attachAttributeDTO.setMaxcnt(attachFileAttribute.getMax_upload_cnt());

        List<Extension> extensions= extensionRepository.findAll();
        List<String> savedExtentions = new ArrayList<>();

        for (Extension extension:extensions){
            savedExtentions.add(extension.getExtension());
        }

        attachAttributeDTO.setExtension(savedExtentions);

        return attachAttributeDTO;
    }

}
