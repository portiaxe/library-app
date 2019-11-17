package com.portiaxe.security;

import com.portiaxe.model.User;
import com.portiaxe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User u = userService.getUser(name, password);
        if(u != null){
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        }else{
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println(authentication);
        logger.debug("AUTHENTICATION", authentication);
        boolean isEqual = authentication.equals(UsernamePasswordAuthenticationToken.class);
        System.out.println("IS EQUAL: "+isEqual);
        return  isEqual;
    }
}