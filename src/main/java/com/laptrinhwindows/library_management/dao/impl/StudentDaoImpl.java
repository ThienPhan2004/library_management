package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.StudentDao;
import com.laptrinhwindows.library_management.model.entity.Student;

public class StudentDaoImpl extends GenericDaoImpl<Student> implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }
}
