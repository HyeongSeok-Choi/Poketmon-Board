package com.boot.tsamo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@Setter
public class visitCountDTO {
    private Long id;
    private String ip_Address;
    private LocalDate visit_Date;
}
