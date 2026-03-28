package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.UserDao;
import com.laptrinhwindows.library_management.dao.impl.UserDaoImpl;
import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.model.entity.Role;
import com.laptrinhwindows.library_management.model.entity.User;
import com.laptrinhwindows.library_management.service.AuthService;

public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    public AuthServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    // Kiem tra thong tin dang nhap va tra ve nguoi dung neu hop le.
    @Override
    public LoginUserDTO login(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        }

        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());

        Role role = user.getRole();
        if (role != null) {
            loginUser.setRoleId(role.getId());
            loginUser.setRoleName(resolveRoleName(role));
        }

        return loginUser;
    }

    // Doi ten vai tro sang cach hien thi don gian theo yeu cau de bai.
    private String resolveRoleName(Role role) {
        if (role.getId() == null) {
            return role.getName();
        }
        if (role.getId() == 1) {
            return "Admin";
        }
        if (role.getId() == 2) {
            return "Quan ly";
        }
        return role.getName();
    }
}
