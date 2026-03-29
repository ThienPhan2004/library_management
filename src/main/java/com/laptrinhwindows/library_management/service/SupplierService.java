package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    List<Supplier> searchSuppliers(String name, String phone, String email);

    Supplier findSupplierById(Integer id);

    Supplier addSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier);

    void deleteSupplier(Integer id);
}
