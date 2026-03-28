package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;

public interface AuthService {
    LoginUserDTO login(String username, String password);
}
