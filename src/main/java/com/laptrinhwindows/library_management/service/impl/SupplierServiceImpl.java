package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.SupplierDao;
import com.laptrinhwindows.library_management.dao.impl.SupplierDaoImpl;
import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.service.SupplierService;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final SupplierDao supplierDao;

    public SupplierServiceImpl() {
        this.supplierDao = new SupplierDaoImpl();
    }

    // Lấy toàn bộ nhà cung cấp để hiển thị.
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDao.findAll();
    }

    // Tìm nhà cung cấp theo các điều kiện trên giao diện.
    @Override
    public List<Supplier> searchSuppliers(String name, String phone, String email) {
        return supplierDao.search(name, phone, email);
    }

    // Tìm nhà cung cấp theo mã để phục vụ thao tác sửa.
    @Override
    public Supplier findSupplierById(Integer id) {
        return supplierDao.findById(id);
    }

    // Thêm mới một nhà cung cấp.
    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    // Cập nhật thông tin nhà cung cấp.
    @Override
    public Supplier updateSupplier(Supplier supplier) {
        return supplierDao.update(supplier);
    }

    // Xóa nhà cung cấp khỏi hệ thống.
    @Override
    public void deleteSupplier(Integer id) {
        supplierDao.deleteById(id);
    }
}
