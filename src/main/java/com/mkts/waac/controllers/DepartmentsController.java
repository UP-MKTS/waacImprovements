package com.mkts.waac.controllers;

import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DepartmentsController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public String showDepartments(Model model) {
        List<DepartmentDto> departmentDtos = departmentService.getAll("fullName");
        model.addAttribute("departments", departmentDtos);
        return "catalog/departments";
    }
}
