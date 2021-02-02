package com.mkts.waac.controllers;

import com.mkts.waac.Dto.GoalDto;
import com.mkts.waac.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoalController {

    private GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/goals")
    public String showGoals(Model model) {

        List<GoalDto> goalDtos = goalService.getAll();
        model.addAttribute("goals", goalDtos);
        return "catalog/goals";
    }
}
