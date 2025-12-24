package com.jdiassdev.wheredidmymoneygo.feature.user;

import java.util.UUID;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UserDTO.getByIdResponse getById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserDTO.getByIdResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public UserDTO.CreateResponse create(UserDTO.CreateRequest dto) {

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        user = userRepository.save(user);

        return new UserDTO.CreateResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    // public User
}
