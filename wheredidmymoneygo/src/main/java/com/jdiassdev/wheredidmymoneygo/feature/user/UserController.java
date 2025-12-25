package com.jdiassdev.wheredidmymoneygo.feature.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

     private final UserService userService;

     public UserController(UserService userService) {
          this.userService = userService;
     }

     @PostMapping
     public ResponseEntity<UserDTO.CreateResponse> create(@RequestBody @Valid UserDTO.CreateRequest dto) {
          UserDTO.CreateResponse response = userService.create(dto);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @PostMapping("/auth")
     public ResponseEntity<UserDTO.LoginResponse> login(@RequestBody @Valid UserDTO.LoginRequest dto) {
          UserDTO.LoginResponse response = userService.login(dto);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @GetMapping("/{id}")
     public UserDTO.GetByIdResponse me(@PathVariable Long id) {
          return userService.findUserById(id);
     }
}
