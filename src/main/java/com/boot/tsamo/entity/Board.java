package com.boot.tsamo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private Long viewCount;

    private boolean deleted;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private Users userid;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "boardId",fetch = FetchType.EAGER ,cascade = CascadeType.REMOVE)
    private List<Reply>replies= new ArrayList<Reply>();

    @OneToMany(mappedBy = "boardId",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<AttachFile>attachFileArrayList= new ArrayList<AttachFile>();

    @OneToMany(mappedBy = "boardId",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<HashTag>hashTags= new ArrayList<HashTag>();


}
