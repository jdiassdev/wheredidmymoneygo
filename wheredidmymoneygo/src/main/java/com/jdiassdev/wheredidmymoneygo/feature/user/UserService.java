package com.jdiassdev.wheredidmymoneygo.feature.user;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.Utils.Security.TokenService;
import com.jdiassdev.wheredidmymoneygo.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;

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

    public UserDTO.LoginResponse login(UserDTO.LoginRequest dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = this.tokenService.generateToken(user);

        return new UserDTO.LoginResponse(
                token,
                user.getName());

    }

    public UserDTO.GetByIdResponse findByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserDTO.GetByIdResponse(
                user.getName(),
                user.getEmail(),
                user.getMonthlySalary(),
                user.getExpensiveThreshold());
    }

    public UserDTO.PatchDataResponse completeDataUser(String email, UserDTO.PatchDataRequest dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        System.out.println("User:>" + user.getName() + " " + dto.expensive_threshold() + " " + dto.monthly_salary());

        if (dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name());
        }
        if (dto.monthly_salary() != null && dto.monthly_salary().compareTo(BigDecimal.ZERO) >= 0) {
            user.setMonthlySalary(dto.monthly_salary());
        }
        if (dto.expensive_threshold() != null && dto.expensive_threshold().compareTo(BigDecimal.ZERO) >= 0) {
            user.setExpensiveThreshold(dto.expensive_threshold());
        }

        user = userRepository.save(user);

        return new UserDTO.PatchDataResponse(
                user.getName(),
                user.getEmail(),
                user.getMonthlySalary(),
                user.getExpensiveThreshold());
    }

}
