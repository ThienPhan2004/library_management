package com.laptrinhwindows.library_management.controller.shared;

import com.laptrinhwindows.library_management.controller.common.LibraryContext;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.view.shared.StudentManagementPanel;

import java.util.List;

public class StudentPanelController {
    private final LibraryContext context;
    private final StudentService studentService;
    private final StudentManagementPanel panel;

    public StudentPanelController(LibraryContext context) {
        this.context = context;
        this.studentService = context.getStudentService();
        this.panel = context.getStudentManagementPanel();
    }

    public void init() {
        panel.addStudentSearchListener(event -> handleSearch());
        panel.addStudentResetListener(event -> handleReset());
        panel.addStudentAddListener(event -> handleAdd());
        panel.addStudentUpdateListener(event -> handleUpdate());
        panel.addStudentDeleteListener(event -> handleDelete());
        panel.addStudentTableSelectionListener(event -> handleSelection());
    }

    public void loadData() {
        List<Student> students = studentService.getAllStudents();
        panel.showStudents(students);
        panel.clearStudentForm();
    }

    private void handleSearch() {
        List<Student> students = studentService.searchStudents(
                panel.getSearchStudentCode(),
                panel.getSearchStudentName(),
                panel.getSearchStudentClass()
        );
        panel.showStudents(students);
        context.setStatusMessage("Đã tìm thấy " + students.size() + " học sinh.");
    }

    private void handleReset() {
        panel.clearStudentSearchFields();
        loadData();
        context.setStatusMessage("Đã làm mới danh sách học sinh.");
    }

    private void handleAdd() {
        try {
            Student student = buildStudentFromForm(null);
            studentService.addStudent(student);
            loadData();
            context.setStatusMessage("Thêm học sinh thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể thêm học sinh. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleUpdate() {
        Integer selectedId = panel.getSelectedStudentId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn học sinh cần sửa.");
            return;
        }

        try {
            Student student = buildStudentFromForm(selectedId);
            studentService.updateStudent(student);
            loadData();
            context.setStatusMessage("Cập nhật học sinh thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật học sinh. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleDelete() {
        Integer selectedId = panel.getSelectedStudentId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn học sinh cần xóa.");
            return;
        }

        if (!panel.confirmDeleteStudent()) {
            return;
        }

        try {
            studentService.deleteStudent(selectedId);
            loadData();
            context.setStatusMessage("Xóa học sinh thành công.");
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể xóa học sinh. Có thể học sinh này đã phát sinh dữ liệu mượn trả.");
        }
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedStudentId();
        if (selectedId == null) {
            return;
        }

        Student student = studentService.findStudentById(selectedId);
        if (student != null) {
            panel.fillStudentForm(student);
        }
    }

    private Student buildStudentFromForm(Integer id) {
        String studentCode = panel.getFormStudentCode();
        String fullName = panel.getFormStudentName();
        String className = panel.getFormStudentClass();
        String phone = panel.getFormStudentPhone();

        if (studentCode.isBlank()) {
            throw new IllegalArgumentException("Mã học sinh không được để trống.");
        }
        if (fullName.isBlank()) {
            throw new IllegalArgumentException("Tên học sinh không được để trống.");
        }
        if (className.isBlank()) {
            throw new IllegalArgumentException("Lớp không được để trống.");
        }

        Student student = new Student();
        student.setId(id);
        student.setStudentCode(studentCode);
        student.setFullName(fullName);
        student.setClassName(className);
        student.setPhone(phone);
        return student;
    }
}
