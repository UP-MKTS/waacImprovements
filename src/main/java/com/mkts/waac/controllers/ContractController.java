package com.mkts.waac.controllers;

import com.mkts.waac.Dto.ContractDto;
import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.services.ContractService;
import com.mkts.waac.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContractController {

    private ContractService contractService;

    private OrganizationService organizationService;

    @Autowired
    public ContractController(ContractService contractService, OrganizationService organizationService) {
        this.contractService = contractService;
        this.organizationService = organizationService;
    }

    @GetMapping("/contracts")
    public String showContracts(Model model) {

        List<ContractDto> contractDtos = contractService.getAll();
        List<OrganizationDto> organizationDtos = organizationService.getAll();
        model.addAttribute("contracts", contractDtos);
        model.addAttribute("organizations", organizationDtos);
        return "catalog/contracts";
    }
}
