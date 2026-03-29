package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.model.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    List<Student> searchStudents(String studentCode, String fullName, String className);

    Student addStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudent(Integer id);

    Student findStudentById(Integer id);
}
