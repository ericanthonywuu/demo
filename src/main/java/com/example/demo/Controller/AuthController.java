package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Model.UserRepository;
import com.example.demo.Request.AuthRequest;
import com.example.demo.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private UserRepository UserRepo;

    @RequestMapping(path = "/login")
    public Map<String, String> login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("foo", "bar");
        map.put("aa", "bb");
        return map;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody AuthRequest request) {
        if (request.username == null || request.password == null) {
            return Response.generateResponse("username and password is required", null, HttpStatus.BAD_REQUEST);
        }

        // TODO: add another validation such username must have more than 6 character


        UserRepo.save(new User(request.username, request.password));

        return Response.generateResponse("User created!", HttpStatus.CREATED);
    }

}

