package com.boot.tsamo.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class AttachFileAttribute {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int max_upload_size;

    private int max_upload_cnt;

}
