package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;

import java.util.List;

public class ReportPanelController {
    private final ManagerContext context;
    private final BorrowOrderService borrowOrderService;
    private final ReportPanel panel;

    public ReportPanelController(ManagerContext context) {
        this.context = context;
        this.borrowOrderService = context.getBorrowOrderService();
        this.panel = context.getReportPanel();
    }

    public void init() {
        panel.addViewReportListener(event -> handleViewReport());
        panel.addResetListener(event -> handleReset());
    }

    public void loadDefaultReport() {
        List<BorrowOrder> borrowOrders = borrowOrderService.getBorrowingReport();
        panel.showBorrowingBooksReport(borrowOrders);
    }

    private void handleViewReport() {
        try {
            int reportIndex = panel.getSelectedReportIndex();

            if (reportIndex == 0) {
                List<BorrowOrder> borrowOrders = borrowOrderService.getBorrowingReport();
                panel.showBorrowingBooksReport(borrowOrders);
                context.setStatusMessage("Đã hiển thị báo cáo sách đang mượn.");
                return;
            }

            if (reportIndex == 1) {
                List<BorrowOrder> borrowOrders = borrowOrderService.getOverdueReport();
                panel.showOverdueBooksReport(borrowOrders);
                context.setStatusMessage("Đã hiển thị báo cáo sách quá hạn.");
                return;
            }

            if (reportIndex == 2) {
                List<BorrowOrder> borrowOrders = borrowOrderService.getStudentBorrowHistory(
                        panel.getStudentCodeFilter(),
                        panel.getStudentNameFilter()
                );
                panel.showStudentHistoryReport(borrowOrders);
                context.setStatusMessage("Đã hiển thị lịch sử mượn của học sinh.");
                return;
            }

            List<BookTitleBorrowStatDTO> stats = borrowOrderService.getBookTitleBorrowStatistics();
            panel.showBookTitleBorrowStats(stats);
            context.setStatusMessage("Đã hiển thị thống kê số lượt mượn theo đầu sách.");
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể tải báo cáo. Vui lòng kiểm tra lại dữ liệu.");
        }
    }

    private void handleReset() {
        panel.clearFilters();
        loadDefaultReport();
        context.setStatusMessage("Đã làm mới báo cáo.");
    }
}
