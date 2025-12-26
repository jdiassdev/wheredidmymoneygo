package com.jdiassdev.wheredidmymoneygo.feature.user;

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

    public UserDTO.CreateResponse create(UserDTO.CreateRequest dto) {

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        // System.out.println("User before save: " + user);
        user = userRepository.save(user);
        // System.out.println("User after save: " + user);

        return new UserDTO.CreateResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public UserDTO.LoginResponse login(UserDTO.LoginRequest dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return new UserDTO.LoginResponse(
                user.getEmail());

    }

    public UserDTO.GetByIdResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserDTO.GetByIdResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public UserDTO.CompleteDataResponse completeDataUser(UserDTO.CompleteDataRequest dto) {
        
        // intancia o usuario q ele achar a variavel user
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // insert or update os campos que estavam faltando
        if (dto.monthlySalary() != null) {
            user.setMonthlySalary(dto.monthlySalary());
        }
        if (dto.expensiveThreshold() != null) {
            user.setExpensiveThreshold(dto.expensiveThreshold());
        }

        user = userRepository.save(user);

        return new UserDTO.CompleteDataResponse(
                user.getEmail());
    }

}
