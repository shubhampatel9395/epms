package com.epms.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
        CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
         
        String redirectURL = request.getContextPath();
         
        if (userDetails.getRoleName().equalsIgnoreCase("ROLE_CUSTOMER")) {
            redirectURL = "index"; //customer index
        } else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
            redirectURL = "admin/dashboard";
        } else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_SERVICEPROVIDER")) {
            redirectURL = "serviceprovider/index";
        }
         
        response.sendRedirect(redirectURL);
         
    }
 
}

