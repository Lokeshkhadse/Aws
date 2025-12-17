package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aws")
public class AwsController {


    private  final UserRepository userRepository;


    public AwsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/addUser")
    public User adduser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/get")
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
