package com.mkts.waac.controllers;

import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/organizations")
    public String showOrganizations(Model model) {

        List<OrganizationDto> organizationDtos = organizationService.getAll();
        model.addAttribute("organizations", organizationDtos);
        return "catalog/organizations";
    }
}
