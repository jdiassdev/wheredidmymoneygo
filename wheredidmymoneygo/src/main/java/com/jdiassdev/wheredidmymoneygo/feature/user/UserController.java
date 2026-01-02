package com.jdiassdev.wheredidmymoneygo.feature.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.jdiassdev.wheredidmymoneygo.dto.AuthUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

     private final UserService userService;

     public UserController(UserService userService) {
          this.userService = userService;
     }

     @PostMapping("/auth/register")
     public ResponseEntity<UserDTO.CreateResponse> create(@RequestBody @Valid UserDTO.CreateRequest dto) {
          UserDTO.CreateResponse response = userService.create(dto);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @PostMapping("/auth/login")
     public ResponseEntity<UserDTO.LoginResponse> login(@RequestBody @Valid UserDTO.LoginRequest dto) {
          UserDTO.LoginResponse response = userService.login(dto);
          return ResponseEntity.ok(response);
     }

     @GetMapping("/me")
     public UserDTO.GetByIdResponse me(@AuthenticationPrincipal AuthUser user) {

          return userService.findByEmail(user.email());
     }

     @PatchMapping("/complete-data")
     public ResponseEntity<UserDTO.PatchDataResponse> completeUserData(
               @AuthenticationPrincipal AuthUser user,
               @RequestBody @Valid UserDTO.PatchDataRequest dto) {
          UserDTO.PatchDataResponse response = userService.completeDataUser(user.email(), dto);
          return ResponseEntity.ok(response);
     }
}
