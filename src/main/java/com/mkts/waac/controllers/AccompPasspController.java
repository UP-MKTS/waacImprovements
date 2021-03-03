package com.mkts.waac.controllers;

import com.mkts.waac.Dao.*;
import com.mkts.waac.Dto.*;
import com.mkts.waac.models.*;
import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.security.services.SecurityService;
import com.mkts.waac.services.*;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import static org.springframework.cglib.core.CollectionUtils.filter;

@Controller
public class AccompPasspController {

    private AccompPasspService accompPasspService;

    private DepartmentService departmentService;

    private OrganizationService organizationService;

    private ContractService contractService;

    private WasteTypeService wasteTypeService;

    private DangerousPowService dangerousPowService;

    private DangerousClassService dangerousClassService;

    private GoalService goalService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    public AccompPasspController(AccompPasspService accompPasspService, DepartmentService departmentService, OrganizationService organizationService, ContractService contractService, WasteTypeService wasteTypeService, DangerousPowService dangerousPowService, DangerousClassService dangerousClassService, GoalService goalService) {
        this.accompPasspService = accompPasspService;
        this.departmentService = departmentService;
        this.organizationService = organizationService;
        this.contractService = contractService;
        this.wasteTypeService = wasteTypeService;
        this.dangerousPowService = dangerousPowService;
        this.dangerousClassService = dangerousClassService;
        this.goalService = goalService;
    }


    @GetMapping("/accomp-passp")
    public String ShowAccPassp(Model model) {

        SignedInAccountDto signedInAccount = securityService.getSignedInAccount();
        GrantedAuthority primaryAuthority = signedInAccount.getPrimaryAuthority();
        String userRole = primaryAuthority.getAuthority();

        List<WasteType> wasteTypes = new ArrayList<>();

        List<DepartmentDto> departmentDtos = new ArrayList<>();
        Integer departmentId = signedInAccount.getDepartmentId();
        if (userRole.equals("admin")||userRole.equals("supervisor")||userRole.equals("reader")) {
            departmentDtos = departmentService.getAll("shortName");
            departmentId = departmentDtos.get(0).getId();
        } else {
            departmentDtos.add(departmentService.getOne(departmentId));
        }

        List<AccompPasspDto> accompPasspDtos = accompPasspService.getAll();

        Integer finalDepartmentId = departmentId;
        Predicate accompPasspPredicate = new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                AccompPasspDto accopmpPassp = (AccompPasspDto) o;

                if(accopmpPassp.getWasteTypeIdList()!=null) {
                    List<AccompPasspWasteDto> departments = accopmpPassp.getWasteTypeIdList();

                    for (AccompPasspWasteDto department : departments) {
                        if (department.getDepartment().getId() == finalDepartmentId) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };

        if((!userRole.equals("admin"))&&(!userRole.equals("supervisor"))&&!userRole.equals("reader")) {
            filter(accompPasspDtos, accompPasspPredicate);
        }
        List<OrganizationDto> organizationDtos = organizationService.getAll();
        List<ContractDto> contractDtos = contractService.getAll();
        List<WasteTypeDto> wasteTypeDtos = wasteTypeService.getAll();
        List<DangerousPowDto> dangerousPowDtos = dangerousPowService.getAll();
        List<DangerousClassDto> dangerousClassDtos = dangerousClassService.getAll();
        List<GoalDto> goalDtos = goalService.getAll();

        if(accompPasspDtos.stream().count()==0)
        {
            accompPasspDtos = null;
        }

        model.addAttribute("accompPassps", accompPasspDtos);
        model.addAttribute("departments", departmentDtos);
        model.addAttribute("organizations", organizationDtos);
        model.addAttribute("contracts", contractDtos);
        model.addAttribute("wasteTypes", wasteTypeDtos);
        model.addAttribute("dangerousPows", dangerousPowDtos);
        model.addAttribute("dangerousClasses", dangerousClassDtos);
        model.addAttribute("goals", goalDtos);
        return "accomp-passp";
    }
}
