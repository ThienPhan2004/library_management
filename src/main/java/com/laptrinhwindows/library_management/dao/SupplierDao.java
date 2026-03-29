package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.model.entity.Supplier;

import java.util.List;

public interface SupplierDao {
    List<Supplier> findAll();

    List<Supplier> search(String name, String phone, String email);

    Supplier findById(Integer id);

    Supplier save(Supplier supplier);

    Supplier update(Supplier supplier);

    void deleteById(Integer id);
}
