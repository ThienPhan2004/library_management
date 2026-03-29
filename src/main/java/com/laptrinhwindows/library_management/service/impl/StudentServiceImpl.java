package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.StudentDao;
import com.laptrinhwindows.library_management.dao.impl.StudentDaoImpl;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    // Lấy toàn bộ học sinh phục vụ màn hình quản lý.
    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    // Tìm kiếm học sinh theo nhiều tiêu chí.
    @Override
    public List<Student> searchStudents(String studentCode, String fullName, String className) {
        return studentDao.search(studentCode, fullName, className);
    }

    // Thêm học sinh mới.
    @Override
    public Student addStudent(Student student) {
        return studentDao.save(student);
    }

    // Cập nhật thông tin học sinh.
    @Override
    public Student updateStudent(Student student) {
        return studentDao.update(student);
    }

    // Xóa học sinh theo mã định danh.
    @Override
    public void deleteStudent(Integer id) {
        studentDao.deleteById(id);
    }

    // Tìm học sinh theo khóa chính.
    @Override
    public Student findStudentById(Integer id) {
        return studentDao.findById(id);
    }
}
