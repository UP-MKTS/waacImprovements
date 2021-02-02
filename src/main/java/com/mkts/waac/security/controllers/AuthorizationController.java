package com.mkts.waac.security.controllers;

import com.mkts.waac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @RequestMapping("/authorization")
    public String authorizationPage (HttpServletRequest httpServletRequest, HttpSession session, @RequestParam String username){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm:ss)");
        String clientIp = httpServletRequest.getRemoteAddr();

        userService.userLogIn(username, date);

        System.out.println(date.format(dateformat) + " WAAC: ==== User " + username + " logged in ==== IP: " + clientIp + ", Session ID: " + session.getId());

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String userRole = authorities.get(0).getAuthority();

        if(userRole.equals("admin")  || userRole.equals("editor")) {
            return "redirect:accomp-passp";
        }
        return "redirect:pod9";
    }
}
