package com.mkts.waac.controllers;

import com.mkts.waac.Dto.DangerousClassDto;
import com.mkts.waac.services.DangerousClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DangerousClassController {

    private DangerousClassService dangerousClassService;

    @Autowired
    public DangerousClassController(DangerousClassService dangerousClassService) {
        this.dangerousClassService = dangerousClassService;
    }

    @GetMapping("/dangerous-class")
    public String showDangerousClass(Model model) {

        List<DangerousClassDto> dangerousClassDtos = dangerousClassService.getAll();
        model.addAttribute("dangerousClasses", dangerousClassDtos);
        return "catalog/dangerous-class";
    }
}
