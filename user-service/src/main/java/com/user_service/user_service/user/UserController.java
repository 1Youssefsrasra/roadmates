package com.user_service.user_service.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService Uservice;


    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestBody @Valid UserRequest request
    ){return ResponseEntity.ok(Uservice.createUser(request));
    }
    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest request
    ) {
        this.Uservice.updateUser(request);
        return ResponseEntity.accepted().build();
    }



}
