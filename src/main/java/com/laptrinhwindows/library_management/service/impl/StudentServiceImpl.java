package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.StudentDao;
import com.laptrinhwindows.library_management.dao.impl.StudentDaoImpl;
import com.laptrinhwindows.library_management.service.StudentService;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }
}
