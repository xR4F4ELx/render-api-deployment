package com.myshop.userws.controller;

import com.myshop.userws.dbaccess.*;

import jakarta.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserController {  // Changed class name to start with uppercase
    
	@RequestMapping(
	    path = "/createUser",
	    consumes = "application/json",
	    method = RequestMethod.POST)
	public int createUser(@RequestBody User user) {
	    int rec = 0;
	    try {
	        UserDAO db = new UserDAO();
	        
	        rec = db.insertUser(user.getAge(), user.getGender());
	        if (rec > 0) {
	            System.out.println("User created successfully with ID: " + rec);
	        }
	        System.out.println("...done create user.." + rec);
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    }
	    return rec;
	}

    @RequestMapping(
    	    path = "/updateUser/{uid}",
    	    consumes = "application/json",
    	    method = RequestMethod.PUT)
    	public int updateUser(@PathVariable String uid, @RequestBody User user) {
    	    int rec = 0;
    	    try {
    	        UserDAO db = new UserDAO();
    	        // Use uid from path parameter instead of user object
    	        int uAge = user.getAge();
    	        String uGender = user.getGender();
    	        rec = db.updateUser(uid, uAge, uGender); // Using uid from path
    	        System.out.println("...in UserController-done update user.." + rec);
    	    } catch (Exception e) {
    	        System.out.println(e.toString());
    	    }
    	    return rec;
    }

    @RequestMapping(
        path="/deleteUser/{uid}",
        method = RequestMethod.DELETE)
    public int deleteUser(@PathVariable String uid) {
        int rec = 0;
        try {
            UserDAO db = new UserDAO();
            rec = db.deleteUser(uid);
            System.out.println("...in UserController-done deleting user.. " + rec);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rec;
    }
    
    @RequestMapping(
    	    path="/getAllUsers",
    	    produces = MediaType.APPLICATION_JSON,  // Using APPLICATION_JSON constant
    	    method = RequestMethod.GET)
    	public ArrayList<User> getAllUsers() {
    	    try {
    	        UserDAO db = new UserDAO();
    	        return db.getAllUsers();
    	    } catch (Exception e) {
    	        System.out.println(e.toString());
    	        return new ArrayList<>();
    	    }
    	}
}