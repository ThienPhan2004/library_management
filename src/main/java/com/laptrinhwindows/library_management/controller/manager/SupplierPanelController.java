package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.SupplierService;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;

import java.util.List;

public class SupplierPanelController {
    private final ManagerContext context;
    private final SupplierService supplierService;
    private final SupplierManagementPanel panel;

    public SupplierPanelController(ManagerContext context) {
        this.context = context;
        this.supplierService = context.getSupplierService();
        this.panel = context.getSupplierManagementPanel();
    }

    public void init() {
        panel.addSupplierSearchListener(event -> handleSearch());
        panel.addSupplierResetListener(event -> handleReset());
        panel.addSupplierAddListener(event -> handleAdd());
        panel.addSupplierUpdateListener(event -> handleUpdate());
        panel.addSupplierDeleteListener(event -> handleDelete());
        panel.addSupplierTableSelectionListener(event -> handleSelection());
    }

    public void loadData() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        panel.showSuppliers(suppliers);
        panel.clearSupplierForm();
    }

    private void handleSearch() {
        List<Supplier> suppliers = supplierService.searchSuppliers(
                panel.getSearchName(),
                panel.getSearchPhone(),
                panel.getSearchEmail()
        );
        panel.showSuppliers(suppliers);
        context.setStatusMessage("Đã tìm thấy " + suppliers.size() + " nhà cung cấp.");
    }

    private void handleReset() {
        panel.clearSupplierSearchFields();
        loadData();
        context.setStatusMessage("Đã làm mới danh sách nhà cung cấp.");
    }

    private void handleAdd() {
        try {
            Supplier supplier = buildSupplierFromForm(null);
            supplierService.addSupplier(supplier);
            loadData();
            context.setStatusMessage("Thêm nhà cung cấp thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể thêm nhà cung cấp. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleUpdate() {
        Integer selectedId = panel.getSelectedSupplierId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn nhà cung cấp cần sửa.");
            return;
        }

        try {
            Supplier supplier = buildSupplierFromForm(selectedId);
            supplierService.updateSupplier(supplier);
            loadData();
            context.setStatusMessage("Cập nhật nhà cung cấp thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật nhà cung cấp. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleDelete() {
        Integer selectedId = panel.getSelectedSupplierId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn nhà cung cấp cần ngừng sử dụng.");
            return;
        }

        if (!panel.confirmDeleteSupplier()) {
            return;
        }

        try {
            supplierService.deleteSupplier(selectedId);
            loadData();
            context.setStatusMessage("Chuyển nhà cung cấp sang INACTIVE thành công.");
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật trạng thái nhà cung cấp. Có thể dữ liệu đang không hợp lệ.");
        }
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedSupplierId();
        if (selectedId == null) {
            return;
        }

        Supplier supplier = supplierService.findSupplierById(selectedId);
        if (supplier != null) {
            panel.fillSupplierForm(supplier);
        }
    }

    private Supplier buildSupplierFromForm(Integer id) {
        String name = panel.getFormName();
        String phone = panel.getFormPhone();
        String email = panel.getFormEmail();
        String address = panel.getFormAddress();
        RecordStatus status = panel.getFormStatus();

        if (name.isBlank()) {
            throw new IllegalArgumentException("Tên nhà cung cấp không được để trống.");
        }

        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setName(name);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setStatus(status == null ? RecordStatus.ACTIVE : status);
        return supplier;
    }
}
