package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> findAll();

    List<Student> search(String studentCode, String fullName, String className);

    Student save(Student student);

    Student update(Student student);

    void deleteById(Integer id);

    Student findById(Integer id);
}
