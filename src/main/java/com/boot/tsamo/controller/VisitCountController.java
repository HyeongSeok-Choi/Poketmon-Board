/*
package com.boot.tsamo.controller;

import com.boot.tsamo.entity.VisitCount;
import com.boot.tsamo.repository.VisitCountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class VisitCountController {

    @Autowired
    private VisitCountRepository visitCountRepository;

*/
/*    @GetMapping("/")
    public String home(HttpServletRequest request, HttpSession session) {
        String ipAddress = request.getRemoteAddr();
        LocalDate today = LocalDate.now();

        // 중복 방문 여부 확인
        if (!visitCountRepository.findByIpAddressAndVisitDate(ipAddress, today).isPresent()) {
            // 중복 방문이 아니라면, 방문 기록 저장
            VisitCount visitCount = new VisitCount(ipAddress, today);
            visitCountRepository.save(visitCount);
        }

        // 홈페이지로 리다이렉트 또는 뷰 반환
        return "main";
    }*//*


    @GetMapping("/visitCount")
    public String visitCount(Model model) {
        LocalDate today = LocalDate.now();
        List<VisitCount> visitCounts = visitCountRepository.findAllByVisitDate(today);
        model.addAttribute("visitCounts", visitCounts);
        return "visitCount";
    }
}*/
