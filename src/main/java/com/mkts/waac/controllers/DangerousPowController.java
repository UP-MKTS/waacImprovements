package com.mkts.waac.controllers;

import com.mkts.waac.Dto.DangerousPowDto;
import com.mkts.waac.services.DangerousPowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DangerousPowController {

    private DangerousPowService dangerousPowService;

    @Autowired
    public DangerousPowController(DangerousPowService dangerousPowService) {
        this.dangerousPowService = dangerousPowService;
    }

    @GetMapping("/dangerous-pow")
    public String showDangerousPow(Model model) {

        List<DangerousPowDto> dangerousPowDtos = dangerousPowService.getAll();
        model.addAttribute("dangerousPows", dangerousPowDtos);
        return "catalog/dangerous-pow";
    }
}
