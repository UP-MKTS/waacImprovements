package com.mkts.waac.controllers;

import com.mkts.waac.Dto.ActivityKindDto;
import com.mkts.waac.services.ActivityKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ActivityKindController {

    private ActivityKindService activityKindService;

    @Autowired
    public ActivityKindController(ActivityKindService activityKindService) {
        this.activityKindService = activityKindService;
    }

    @GetMapping("/activity-kinds")
    public String showActivityKinds(Model model) {
        List<ActivityKindDto> activityKindDtos = activityKindService.getAll();
        model.addAttribute("activityKinds", activityKindDtos);
        return "catalog/activity-kinds";
    }
}
