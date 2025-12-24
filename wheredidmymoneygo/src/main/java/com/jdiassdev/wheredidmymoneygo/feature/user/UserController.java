package com.jdiassdev.wheredidmymoneygo.feature.user;

import java.util.UUID;

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

     @GetMapping("/{id}")
     public UserDTO.getByIdResponse me(@PathVariable UUID id) {
          return userService.getById(id);
     }

     @PostMapping
     public UserDTO.CreateResponse create(@RequestBody @Valid UserDTO.CreateRequest dto) {
          return userService.create(dto);
     }
}
