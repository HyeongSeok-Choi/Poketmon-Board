package com.boot.tsamo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AttachFile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

    private String fileUrl;

    private String ori_fileName;

    private String uuid_fileName;

    // 파일 원본 이름과 uuid으로 변경한 이름 + 파일 주소를 AttachFile 엔티티에 저장하는 메소드.
    public void updateItemImg(String ori_fileName, String uuid_fileName, String fileUrl){
        this.ori_fileName = ori_fileName;
        this.uuid_fileName = uuid_fileName;
        this.fileUrl = fileUrl;
    }
}
