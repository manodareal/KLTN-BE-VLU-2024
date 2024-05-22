package com.example.usermanagement.web;

import com.example.usermanagement.config.JwtUtils;
import com.example.usermanagement.domain.service.AuthService;
import com.example.usermanagement.dto.user.input.LoginInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;



    @PostMapping("/login")
    public String login(@RequestBody LoginInput loginInput) {
        String token = authService.authenticate(loginInput, authenticationManager, jwtUtils);
        log.info("Login successfully");
        return token;
    }
}
