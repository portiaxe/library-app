package com.portiaxe.security;

import com.portiaxe.model.User;
import org.springframework.security.core.session.SessionRegistryImpl;

public class CustomSessionRegistry extends SessionRegistryImpl {

    public void registerNewSession(String sessionId, Object principal) {
        super.registerNewSession(sessionId, principal);
        System.out.println("STRING: "+principal instanceof String);
        System.out.println("USER: "+ (principal instanceof User));
        System.out.println("Registering session " + sessionId + ", for principal " + principal);
    }
}
