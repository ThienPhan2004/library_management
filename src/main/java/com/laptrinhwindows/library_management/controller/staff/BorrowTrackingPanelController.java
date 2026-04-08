package com.laptrinhwindows.library_management.controller.staff;

import com.laptrinhwindows.library_management.controller.common.LibraryContext;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.view.staff.BorrowTrackingPanel;

import java.time.LocalDate;
import java.util.List;

public class BorrowTrackingPanelController {
    private final LibraryContext context;
    private final BorrowOrderService borrowOrderService;
    private final BorrowTrackingPanel panel;

    public BorrowTrackingPanelController(LibraryContext context) {
        this.context = context;
        this.borrowOrderService = context.getBorrowOrderService();
        this.panel = context.getBorrowTrackingPanel();
    }

    public void init() {
        panel.addSearchListener(event -> handleSearch());
        panel.addResetListener(event -> handleReset());
    }

    public void loadDefaultTrackingOrders() {
        List<BorrowOrder> borrowOrders = borrowOrderService.searchTrackingOrders(null, null, null, null, "ALL");
        panel.showBorrowOrders(borrowOrders);
    }

    private void handleSearch() {
        try {
            LocalDate fromDate = panel.getFromDate();
            LocalDate toDate = panel.getToDate();

            if (fromDate != null && toDate != null && toDate.isBefore(fromDate)) {
                throw new IllegalArgumentException("Đến ngày không được nhỏ hơn từ ngày.");
            }

            List<BorrowOrder> borrowOrders = borrowOrderService.searchTrackingOrders(
                    panel.getStudentCodeFilter(),
                    panel.getStudentNameFilter(),
                    fromDate,
                    toDate,
                    panel.getFilterMode()
            );
            panel.showBorrowOrders(borrowOrders);
            context.setStatusMessage("Đã lọc được " + borrowOrders.size() + " phiếu mượn để theo dõi.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        }
    }

    private void handleReset() {
        panel.clearFilters();
        loadDefaultTrackingOrders();
        context.setStatusMessage("Đã làm mới danh sách theo dõi phiếu mượn.");
    }
}
