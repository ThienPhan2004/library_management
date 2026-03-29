package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.view.manager.ReturnBookPanel;

import java.time.LocalDate;
import java.util.List;

public class ReturnBookPanelController {
    private final ManagerContext context;
    private final BorrowOrderService borrowOrderService;
    private final ReturnBookPanel panel;

    public ReturnBookPanelController(ManagerContext context) {
        this.context = context;
        this.borrowOrderService = context.getBorrowOrderService();
        this.panel = context.getReturnBookPanel();
    }

    public void init() {
        panel.addSearchListener(event -> handleSearch());
        panel.addResetListener(event -> handleReset());
        panel.addBorrowOrderSelectionListener(event -> handleSelection());
        panel.addConfirmReturnListener(event -> handleConfirmReturn());
    }

    public void loadBorrowOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchBorrowingOrders(null, null, null);
        panel.showBorrowOrders(borrowOrders);
        panel.clearReturnForm();
    }

    private void handleSearch() {
        try {
            Integer orderId = parseOrderId(panel.getSearchOrderId());
            List<BorrowOrder> borrowOrders = borrowOrderService.searchBorrowingOrders(
                    orderId,
                    panel.getSearchStudentCode(),
                    panel.getSearchStudentName()
            );
            panel.showBorrowOrders(borrowOrders);
            panel.clearReturnForm();
            context.setStatusMessage("Đã tìm thấy " + borrowOrders.size() + " phiếu mượn cần xử lý trả sách.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        }
    }

    private void handleReset() {
        panel.clearSearchFields();
        loadBorrowOrders();
        context.setStatusMessage("Đã làm mới danh sách phiếu mượn.");
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedBorrowOrderId();
        if (selectedId == null) {
            panel.showBorrowOrderDetails(null);
            return;
        }

        BorrowOrder borrowOrder = borrowOrderService.findBorrowOrderById(selectedId);
        panel.showBorrowOrderDetails(borrowOrder);
    }

    private void handleConfirmReturn() {
        Integer selectedId = panel.getSelectedBorrowOrderId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn phiếu mượn cần trả.");
            return;
        }

        if (!panel.confirmReturn()) {
            return;
        }

        try {
            BorrowOrder borrowOrder = borrowOrderService.findBorrowOrderById(selectedId);
            if (borrowOrder == null) {
                throw new IllegalArgumentException("Không tìm thấy phiếu mượn.");
            }

            LocalDate returnDate = panel.getReturnDate();
            if (borrowOrder.getBorrowDate() != null && returnDate.isBefore(borrowOrder.getBorrowDate())) {
                throw new IllegalArgumentException("Ngày trả không được nhỏ hơn ngày mượn.");
            }

            borrowOrderService.returnBooks(selectedId, returnDate);
            context.refreshCirculationViews();
            context.setStatusMessage("Xác nhận trả sách thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể xác nhận trả sách. Vui lòng kiểm tra lại dữ liệu.");
        }
    }

    private Integer parseOrderId(String orderIdText) {
        if (orderIdText == null || orderIdText.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(orderIdText.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Mã phiếu phải là số nguyên.");
        }
    }
}
