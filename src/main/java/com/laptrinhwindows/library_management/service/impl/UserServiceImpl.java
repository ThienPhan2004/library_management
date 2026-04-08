package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.RoleDao;
import com.laptrinhwindows.library_management.dao.UserDao;
import com.laptrinhwindows.library_management.dao.impl.RoleDaoImpl;
import com.laptrinhwindows.library_management.dao.impl.UserDaoImpl;
import com.laptrinhwindows.library_management.model.entity.Role;
import com.laptrinhwindows.library_management.model.entity.User;
import com.laptrinhwindows.library_management.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.roleDao = new RoleDaoImpl();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> searchUsers(String username, Integer roleId) {
        return userDao.search(username, roleId);
    }

    @Override
    public User addUser(User user) {
        // Kiểm tra dữ liệu trước khi thêm tài khoản mới.
        validateUser(user, false);
        attachRole(user);
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user, boolean keepExistingPassword) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản cần cập nhật.");
        }

        User existingUser = userDao.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }

        if (keepExistingPassword) {
            // Nếu để trống mật khẩu khi sửa thì giữ lại mật khẩu cũ.
            user.setPassword(existingUser.getPassword());
        }

        validateUser(user, true);
        attachRole(user);
        return userDao.update(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    private void validateUser(User user, boolean isUpdate) {
        // Kiểm tra các thông tin bắt buộc của tài khoản.
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new IllegalArgumentException("Vui lòng chọn vai trò cho tài khoản.");
        }

        String username = user.getUsername().trim();
        user.setUsername(username);

        boolean duplicated = isUpdate
                ? userDao.existsByUsernameExceptId(username, user.getId())
                : userDao.existsByUsername(username);
        if (duplicated) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }
    }

    private void attachRole(User user) {
        // Lấy lại role từ database để tránh lưu role không hợp lệ.
        Role role = roleDao.findById(user.getRole().getId());
        if (role == null) {
            throw new IllegalArgumentException("Vai trò được chọn không hợp lệ.");
        }
        user.setRole(role);
    }
}
