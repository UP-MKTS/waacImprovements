package com.mkts.waac.controllers;

import com.mkts.waac.Dto.PersonDto;
import com.mkts.waac.services.PersonService;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PersonController {

    private PersonService personService;

    private DepartmentService departmentService;

    @Autowired
    public PersonController(PersonService personService, DepartmentService departmentService) {
        this.personService = personService;
        this.departmentService = departmentService;
    }

    @GetMapping("/persons")
    public String showPersons(Model model) {
        List<PersonDto> personDtos = personService.getAll();
        List<DepartmentDto> departmentDtos = departmentService.getAll("shortName");
        model.addAttribute("persons", personDtos);
        model.addAttribute("departments", departmentDtos);
        return "catalog/persons";
    }
}
