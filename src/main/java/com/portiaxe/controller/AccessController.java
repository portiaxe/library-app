package com.portiaxe.controller;

import com.portiaxe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class AccessController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/test-message")
    public String testMessage( ) {
        return "Authenticated Greeting";
    }

    @RequestMapping("/test-login")
    public ResponseEntity<?> testLogin(HttpServletRequest req ) throws ServletException {
        User user = new User();
        user.setUsername("jerico");
        user.setPassword("123");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        HttpSession session = req.getSession(true);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        sessionRegistry.getAllSessions(authentication.getName(), false).stream().forEach(item->item.expireNow());
        sessionRegistry.registerNewSession(session.getId(), user.getUsername());
        return ResponseEntity.ok(sessionRegistry.getAllPrincipals());
    }

    @RequestMapping("/password")
    public String password() {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        String hash = b.encode("123");
        return hash;
    }
    @RequestMapping("/user")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }

    @RequestMapping("/users")
    public ResponseEntity<?> getCurrentUsers() {
        return ResponseEntity.ok(sessionRegistry.getAllPrincipals());
    }
    @RequestMapping("/sessions")
    public ResponseEntity<?> getSessions(Principal principal) {
        return ResponseEntity.ok(sessionRegistry.getAllSessions(principal, false));
    }

}
