package com.user_service.user_service.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService Uservice;


    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestBody @Valid UserRequest request
    ){
        return ResponseEntity.ok(this.Uservice.createUser(request));
    }
    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest request
    ) {
        this.Uservice.updateUser(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){

        return ResponseEntity.ok(this.Uservice.findAllUsers());
    }
    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean> existsById(@PathVariable("userId") String userId) {
        // Add logic here
        return ResponseEntity.ok(Uservice.existsById(userId));
    }
    @GetMapping("/details/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable("userId") String userId) {
        // Add logic here
        return ResponseEntity.ok(Uservice.findById(userId));
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        // Add logic here
       this.Uservice.deleteUser(userId);
       return ResponseEntity.accepted().build();
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint is working!");
    }

}


