package org.heapminds.service;

import org.heapminds.dto.LoginDto;
import org.heapminds.dto.RegisterDto;
import org.heapminds.ro.LoginRo;

public interface UserService {

    public LoginRo login(LoginDto loginDto);
    public String register(RegisterDto registerDto);

    
}