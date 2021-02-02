package com.mkts.waac.controllers;

import com.mkts.waac.Dto.ActivityKindDto;
import com.mkts.waac.Dto.DangerousClassDto;
import com.mkts.waac.Dto.DangerousPowDto;
import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.services.ActivityKindService;
import com.mkts.waac.services.DangerousClassService;
import com.mkts.waac.services.DangerousPowService;
import com.mkts.waac.services.WasteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WasteTypeController {

    private WasteTypeService wasteTypeService;

    private DangerousPowService dangerousPowService;

    private DangerousClassService dangerousClassService;

    private ActivityKindService activityKindService;

    @Autowired
    public WasteTypeController(WasteTypeService wasteTypeService, DangerousPowService dangerousPowService, DangerousClassService dangerousClassService, ActivityKindService activityKindService) {
        this.wasteTypeService = wasteTypeService;
        this.dangerousPowService = dangerousPowService;
        this.dangerousClassService = dangerousClassService;
        this.activityKindService = activityKindService;
    }

    @GetMapping("/waste-types")
    public String showWateTypes(Model model) {
        List<WasteTypeDto> wasteTypeDtos = wasteTypeService.getAll();
        List<DangerousPowDto> dangerousPowDtos = dangerousPowService.getAll();
        List<DangerousClassDto> dangerousClassDtos = dangerousClassService.getAll();
        List<ActivityKindDto> activityKindDtos = activityKindService.getAll();
        model.addAttribute("wasteTypes", wasteTypeDtos);
        model.addAttribute("dangerousPows", dangerousPowDtos);
        model.addAttribute("dangerousClasses", dangerousClassDtos);
        model.addAttribute("activityKinds", activityKindDtos);
        return "catalog/waste-types";
    }
}
