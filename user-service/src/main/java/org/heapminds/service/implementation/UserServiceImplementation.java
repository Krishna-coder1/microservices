package org.heapminds.service.implementation;

import java.util.List;

import org.heapminds.dto.LoginDto;
import org.heapminds.dto.RegisterDto;
import org.heapminds.enums.RoleEnum;
import org.heapminds.exception.HMException;
import org.heapminds.models.Role;
import org.heapminds.models.UserEntity;
import org.heapminds.repository.RoleRepository;
import org.heapminds.repository.UserRepository;
import org.heapminds.ro.LoginRo;
import org.heapminds.security.JwtTokenGenerator;
import org.heapminds.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Override
    public LoginRo login(LoginDto loginDto) {
        try {
            logger.info("Logging in the user {}", loginDto.getUsername());
            LoginRo loginRo = new LoginRo();
            if (!userRepository.findByUsername(loginDto.getUsername()).isPresent()) {
                logger.info("No user present with the username: {}", loginDto.getUsername());
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenGenerator.generateToken(authentication);
            loginRo.setAuthToken(token);
            loginRo.setRoles(userRepository.findByUsername(loginDto.getUsername()).get().getRoles());
            return loginRo;
        } catch (Exception e) {
            logger.info("Logging Exception: {}", e.getMessage());
            throw new HMException(e.getMessage());
        }
    }

    @Override
    public String register(RegisterDto registerDto) {
        try {
            if (userRepository.existsByUsername(registerDto.getUsername())) {
                throw new HMException("User already registered");
            }
            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            List<Role> roles = roleRepository.findAll();
            user.setRoles(roles.stream().filter(val -> val.getName() == RoleEnum.USER).toList());
            userRepository.save(user);
            return "User Registered";
        } catch (Exception e) {
            throw new HMException(e.getMessage());
        }
    }
}
