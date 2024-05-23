package com.boot.tsamo.controller;


import com.boot.tsamo.dto.ExcelDTO;
import com.boot.tsamo.dto.ViewReplyDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class excelController {

    private final BoardService boardService;

    //엑셀
    @GetMapping(value = "/ExcelApi")
    public ResponseEntity<List<ExcelDTO>> getAllBoards() {

        List<Board> allBoards = boardService.getDeletedBoards();

        List<ExcelDTO> excelDTOS = allBoards.stream()
                .map(a -> new ExcelDTO(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(excelDTOS);
    }
}
