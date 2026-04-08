package com.laptrinhwindows.library_management.controller.admin;

import com.laptrinhwindows.library_management.dto.LoginUserDTO;
import com.laptrinhwindows.library_management.model.entity.Role;
import com.laptrinhwindows.library_management.model.entity.User;
import com.laptrinhwindows.library_management.service.UserService;
import com.laptrinhwindows.library_management.view.AdminFrame;
import com.laptrinhwindows.library_management.view.admin.AccountManagementPanel;

import java.util.List;

public class AccountManagementController {
    private final AdminFrame adminFrame;
    private final LoginUserDTO loginUser;
    private final UserService userService;
    private final AccountManagementPanel panel;

    public AccountManagementController(AdminFrame adminFrame, LoginUserDTO loginUser, UserService userService) {
        this.adminFrame = adminFrame;
        this.loginUser = loginUser;
        this.userService = userService;
        this.panel = adminFrame.getAccountManagementPanel();
    }

    public void init() {
        panel.addSearchListener(event -> handleSearch());
        panel.addResetListener(event -> handleReset());
        panel.addAddListener(event -> handleAdd());
        panel.addUpdateListener(event -> handleUpdate());
        panel.addDeleteListener(event -> handleDelete());
        panel.addTableSelectionListener(event -> handleSelection());
    }

    public void loadData() {
        List<Role> roles = userService.getAllRoles();
        panel.setRoleOptions(roles);
        panel.showUsers(userService.getAllUsers());
        panel.clearForm();
    }

    private void handleSearch() {
        List<User> users = userService.searchUsers(panel.getSearchUsername(), panel.getSearchRoleId());
        panel.showUsers(users);
        adminFrame.setStatusMessage("Đã tìm thấy " + users.size() + " tài khoản.");
    }

    private void handleReset() {
        panel.clearSearchFields();
        panel.showUsers(userService.getAllUsers());
        panel.clearForm();
        adminFrame.setStatusMessage("Đã làm mới danh sách tài khoản.");
    }

    private void handleAdd() {
        try {
            User user = buildUserFromForm(null);
            userService.addUser(user);
            refreshAfterMutation("Thêm tài khoản thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể thêm tài khoản. Vui lòng kiểm tra lại dữ liệu.");
        }
    }

    private void handleUpdate() {
        Integer selectedId = panel.getSelectedUserId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn tài khoản cần cập nhật.");
            return;
        }

        try {
            String password = panel.getFormPassword();
            User user = buildUserFromForm(selectedId);
            userService.updateUser(user, password.isBlank());
            refreshAfterMutation("Cập nhật tài khoản thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật tài khoản. Vui lòng kiểm tra lại dữ liệu.");
        }
    }

    private void handleDelete() {
        Integer selectedId = panel.getSelectedUserId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn tài khoản cần xóa.");
            return;
        }

        User selectedUser = userService.findUserById(selectedId);
        if (selectedUser == null) {
            panel.showErrorMessage("Tài khoản không tồn tại.");
            return;
        }

        if (loginUser.getUsername() != null && loginUser.getUsername().equalsIgnoreCase(selectedUser.getUsername())) {
            panel.showErrorMessage("Bạn không thể xóa chính tài khoản đang đăng nhập.");
            return;
        }

        if (!panel.confirmDeleteUser(selectedUser.getUsername())) {
            return;
        }

        try {
            userService.deleteUser(selectedId);
            refreshAfterMutation("Xóa tài khoản thành công.");
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể xóa tài khoản này.");
        }
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedUserId();
        if (selectedId == null) {
            return;
        }

        User user = userService.findUserById(selectedId);
        if (user != null) {
            panel.fillUserForm(user);
        }
    }

    private User buildUserFromForm(Integer id) {
        String username = panel.getFormUsername();
        String password = panel.getFormPassword();
        Integer roleId = panel.getFormRoleId();

        if (username.isBlank()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }
        if (id == null && password.isBlank()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }
        if (roleId == null) {
            throw new IllegalArgumentException("Vui lòng chọn vai trò cho tài khoản.");
        }

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        Role role = new Role();
        role.setId(roleId);
        user.setRole(role);
        return user;
    }

    private void refreshAfterMutation(String message) {
        panel.clearSearchFields();
        panel.showUsers(userService.getAllUsers());
        panel.clearForm();
        adminFrame.setStatusMessage(message);
    }
}
