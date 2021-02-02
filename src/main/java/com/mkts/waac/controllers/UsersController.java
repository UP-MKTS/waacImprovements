package com.mkts.waac.controllers;

import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.Dto.RoleDto;
import com.mkts.waac.Dto.UserDto;
import com.mkts.waac.services.DepartmentService;
import com.mkts.waac.services.RoleService;
import com.mkts.waac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsersController {

    private UserService userService;

    private RoleService roleService;

    private DepartmentService departmentService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService, DepartmentService departmentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.departmentService = departmentService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<UserDto> userDtos = userService.getAll();
        List<RoleDto> roleDtos = roleService.getAll();
        List<DepartmentDto> departmentDtos = departmentService.getAll("shortName");
        model.addAttribute("users", userDtos);
        model.addAttribute("roles", roleDtos);
        model.addAttribute("departments", departmentDtos);
        return "catalog/users";
    }
}
