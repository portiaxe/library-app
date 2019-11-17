package com.portiaxe.service;

import com.portiaxe.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users;

    public UserService(){
        users = new ArrayList<>();
        users.add(new User().username("jerico").password("123").role("user"));
        users.add(new User().username("test").password("123").role("user"));

    }

    public User getUser(String username, String password){
        User user = null;
        for(User u: users){
            if(u.getPassword().equals(password) && u.getUsername().equals(username)){
                user = u;
                break;
            }
        }
        return  user;
    }

    public User getUserByUsername(String username){
        User user = null;
        for(User u: users){
            if(u.getUsername().equals(username)){
                user = u;
                break;
            }
        }
        return  user;
    }
}
