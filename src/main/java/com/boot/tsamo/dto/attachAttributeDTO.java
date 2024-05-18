package com.boot.tsamo.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class attachAttributeDTO {

    private int maxsize;

    private int maxcnt;

    private List<String> extension;

}
