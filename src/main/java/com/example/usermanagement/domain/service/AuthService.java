package com.example.usermanagement.domain.service;

import com.example.usermanagement.config.JwtUtils;
import com.example.usermanagement.config.PasswordEncrypt;
import com.example.usermanagement.domain.entity.Role;
import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.repo.UserRepository;
import com.example.usermanagement.dto.user.input.LoginInput;
import com.example.usermanagement.dto.user.input.UserDataInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    /**
     * Create user
     *
     * @param userDataInput - input user register
     */

    /**
     * Authenticate user
     *
     * @param loginInput - input login inform
     */
    public String authenticate(LoginInput loginInput, AuthenticationManager authenticationManager, JwtUtils jwtUtil) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginInput.getEmail(),
                loginInput.getPassword()
        );

        Authentication login = authenticationManager.authenticate(authentication);
        log.info(login.getPrincipal().toString());
        User user = (User) login.getPrincipal();


        String token = jwtUtil.createToken(user);

        log.info("Create token success: {}", token);
        return token;
    }
}