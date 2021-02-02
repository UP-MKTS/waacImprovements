package com.mkts.waac.config;

import com.mkts.waac.services.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class SessionListener implements HttpSessionListener, ApplicationContextAware {

    private static ApplicationContext context;

    private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm:ss)");


    @Override
    public void sessionCreated(HttpSessionEvent event) {
        LocalDateTime DATE = LocalDateTime.now();
        System.out.println(DATE.format(DATEFORMAT) + " WAAC: ==== Session is created ==== Session ID: " + event.getSession().getId());
        event.getSession().setMaxInactiveInterval(60 * 30);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        LocalDateTime date = LocalDateTime.now();
        HttpSession session = event.getSession();
        SecurityContext context = (SecurityContext) session.getAttribute
                (HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserService userService = SessionListener.getContext().getBean(UserService.class);

        String clientIp = null;
        Object details = context.getAuthentication().getDetails();
        if (details instanceof WebAuthenticationDetails) {
            clientIp = ((WebAuthenticationDetails) details).getRemoteAddress();
        }

        userService.userLogOut(userDetails.getUsername(), date);

        System.out.println(date.format(DATEFORMAT) + " WAAC: ==== Session " + userDetails.getUsername() + " is destroyed ==== IP: " + clientIp + ", Session ID: " + session.getId());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
