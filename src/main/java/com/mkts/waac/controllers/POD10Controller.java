package com.mkts.waac.controllers;

import com.mkts.waac.Dto.Pod10Dto;
import com.mkts.waac.services.Pod10Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.YearMonth;
import java.util.List;

@Controller
public class POD10Controller {

    private Pod10Service pod10Service;

    @Autowired
    public POD10Controller(Pod10Service pod10Service) {
        this.pod10Service = pod10Service;
    }

    @GetMapping("/pod10")
    public String ShowPod10(Model model) {
        YearMonth yearMonth = YearMonth.now();
        Pod10Dto pod10Dto = pod10Service.getPod10ByMonth(yearMonth);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("pod10", pod10Dto);
        return "pod10";
    }
}
