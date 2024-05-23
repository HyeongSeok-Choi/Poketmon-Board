package com.boot.tsamo.entity;


import com.boot.tsamo.config.multikey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Likes {


    @EmbeddedId
    private multikey multikey;

}
