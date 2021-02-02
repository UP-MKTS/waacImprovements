package com.mkts.waac.controllers;

import com.mkts.waac.Dto.*;
import com.mkts.waac.mappers.Pod9Mapper;
import com.mkts.waac.mappers.Pod9OwnWasteMapper;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.Department;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.security.services.SecurityService;

import com.mkts.waac.services.*;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.cglib.core.Predicate;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cglib.core.CollectionUtils.filter;

@Controller
public class POD9Controller {

    private Pod9OwnWasteService pod9OwnWasteService;

    private DepartmentService departmentService;

    private WasteTypeService wasteTypeService;

    private OrganizationService organizationService;

    private GoalService goalService;

    private SecurityService securityService;

    @Autowired
    private AccompPasspService accompPasspService;

    @Autowired
    private WasteTypeMapper wasteTypeMapper;

    @Autowired
    private Pod9OwnWasteMapper  pod9OwnWasteMapper;

    @Autowired
    private Pod9Mapper pod9Mapper;

    @Autowired
    public POD9Controller(Pod9OwnWasteService pod9OwnWasteService, DepartmentService departmentService, WasteTypeService wasteTypeService, OrganizationService organizationService, GoalService goalService, SecurityService securityService) {
        this.pod9OwnWasteService = pod9OwnWasteService;
        this.departmentService = departmentService;
        this.wasteTypeService = wasteTypeService;
        this.organizationService = organizationService;
        this.goalService = goalService;
        this.securityService = securityService;
    }

    @GetMapping("/pod9")
    public String ShowPod9(Model model) {
        SignedInAccountDto signedInAccount = securityService.getSignedInAccount();
        GrantedAuthority primaryAuthority = signedInAccount.getPrimaryAuthority();
        String userRole = primaryAuthority.getAuthority();

        List<WasteType> wasteTypes = new ArrayList<>();

        List<DepartmentDto> departmentDtos = new ArrayList<>();
        Integer departmentId = signedInAccount.getDepartmentId();
        if (userRole.equals("admin")||userRole.equals("supervisor")) {
            departmentDtos = departmentService.getAll("shortName");
            departmentId = departmentDtos.get(0).getId();
        } else {
            departmentDtos.add(departmentService.getOne(departmentId));
        }

        List<WasteTypeDto> wasteTypeDtos = wasteTypeService.getAll();
        List<OrganizationDto> organizationDtos = organizationService.getAll();
        List<GoalDto> goalDtos = goalService.getAll();

        LocalDate localDate = LocalDate.now();
        Integer month = localDate.getMonthValue();
        String correctMonth = month.toString();
        correctMonth = month<10?"0"+correctMonth:correctMonth;
        Integer year = localDate.getYear();
        List<Pod9OwnWaste> pod9Dto = pod9OwnWasteService.getAllByDate(correctMonth,year.toString());

        Integer finalDepartmentId = departmentId;
        Predicate pod9Department = new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Pod9OwnWaste pod = (Pod9OwnWaste) o;

                if(pod.getAccompPasspWaste()!=null) {
                    List<AccompPasspDepartment> departments = pod.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

                    for (AccompPasspDepartment department : departments) {
                        if (department.getDepartment().getId() == finalDepartmentId) {
                            return true;
                        }
                    }
                }else
                {
                    if (pod.getDepartment().getId() == finalDepartmentId) {
                        return true;
                    }
                }

                return false;
            }
        };

        filter(pod9Dto,pod9Department);



        List<WasteTypeDto> wastes = new ArrayList<>();

        for (Pod9OwnWaste temp: pod9Dto)
        {
            if(temp.getAccompPasspWaste()!=null) {
                wastes.add(wasteTypeMapper.convertToDto(temp.getAccompPasspWaste().getWasteTypes()));
            }
            else
            {
                wastes.add(wasteTypeMapper.convertToDto(temp.getWasteType()));
            }
        }

        wastes = wastes.stream().distinct().collect(Collectors.toList());
        List<Pod9Dto> dtoList = new ArrayList<>();
        if(wastes.stream().count()>0) {
            Integer wasteId = wastes.get(0).getId();

            Predicate pod9Waste = new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    Pod9OwnWaste pod = (Pod9OwnWaste) o;

                    if (pod.getAccompPasspWaste() != null) {
                        if (pod.getAccompPasspWaste().getWasteTypes().getId() == wasteId) {
                            return true;
                        }
                    } else {
                        if (pod.getWasteType().getId() == wasteId) {
                            return true;
                        }
                    }
                    return false;
                }
            };

            filter(pod9Dto, pod9Waste);
            dtoList = pod9Mapper.convertToDtoList(pod9Dto);
        }else
        {
            dtoList = null;
        }

        YearMonth yearMonth = YearMonth.now();
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("departments", departmentDtos);
        model.addAttribute("wasteTypes", wastes);
        model.addAttribute("organizations", organizationDtos);
        model.addAttribute("goals", goalDtos);
        model.addAttribute("pod9", dtoList);

        return "pod9";
    }
}
