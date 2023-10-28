package org.heapminds.controller;

import javax.validation.Valid;

import org.heapminds.dto.LoginDto;
import org.heapminds.dto.RegisterDto;
import org.heapminds.ro.BaseRo;
import org.heapminds.ro.BaseSro;
import org.heapminds.ro.LoginRo;
import org.heapminds.ro.RegisterRo;
import org.heapminds.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public BaseRo login(@RequestBody LoginDto loginDto) throws InterruptedException {

        LoginRo loginRo = userService.login(loginDto);
        return new BaseSro<LoginRo>(loginRo);
    }

    @PostMapping("/register")
    public BaseRo registerUser(@Valid @RequestBody RegisterDto registerDto) {
        String response = userService.register(registerDto);
        RegisterRo registerRo = new RegisterRo();
        registerRo.setResponse(response);
        return new BaseSro<String>(registerRo.getResponse());
    }

    @GetMapping("/token")
    public String getToken() {
        return "Token";
    }

}
