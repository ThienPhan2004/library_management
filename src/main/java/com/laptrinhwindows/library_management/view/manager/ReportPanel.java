package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowDetail;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ReportPanel extends JPanel {
    private final JComboBox<String> reportTypeComboBox;
    private final JTextField studentCodeField;
    private final JTextField studentNameField;
    private final JButton viewReportButton;
    private final JButton resetButton;
    private final JTable reportTable;
    private final DefaultTableModel reportTableModel;

    public ReportPanel() {
        reportTypeComboBox = new JComboBox<>(new String[]{
                "Sách đang mượn",
                "Sách quá hạn",
                "Lịch sử mượn của học sinh",
                "Số lượt mượn theo đầu sách"
        });
        studentCodeField = new JTextField(12);
        studentNameField = new JTextField(16);
        viewReportButton = new JButton("Xem báo cáo");
        resetButton = new JButton("Làm mới");

        reportTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(reportTableModel);
        reportTable.setRowHeight(24);

        initLayout();
    }

    // Tạo giao diện báo cáo đơn giản cho phần thống kê.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Loại báo cáo:"));
        filterPanel.add(reportTypeComboBox);
        filterPanel.add(new JLabel("Mã học sinh:"));
        filterPanel.add(studentCodeField);
        filterPanel.add(new JLabel("Họ tên:"));
        filterPanel.add(studentNameField);
        filterPanel.add(viewReportButton);
        filterPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(reportTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả báo cáo"));

        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    // Hiển thị báo cáo danh sách sách đang mượn.
    public void showBorrowingBooksReport(List<BorrowOrder> borrowOrders) {
        reportTableModel.setDataVector(
                new Object[][]{},
                new Object[]{"Mã phiếu", "Mã học sinh", "Học sinh", "ID sách", "Đầu sách", "Tác giả", "Ngày mượn", "Hạn trả"}
        );

        for (BorrowOrder borrowOrder : borrowOrders) {
            if (borrowOrder.getDetails() == null) {
                continue;
            }

            for (BorrowDetail detail : borrowOrder.getDetails()) {
                Book book = detail.getBook();
                reportTableModel.addRow(new Object[]{
                        borrowOrder.getId(),
                        borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : "",
                        borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : "",
                        book != null ? book.getId() : "",
                        book != null && book.getBookTitle() != null ? book.getBookTitle().getTitle() : "",
                        book != null && book.getBookTitle() != null ? book.getBookTitle().getAuthor() : "",
                        borrowOrder.getBorrowDate(),
                        borrowOrder.getDueDate()
                });
            }
        }
    }

    // Hiển thị báo cáo sách quá hạn.
    public void showOverdueBooksReport(List<BorrowOrder> borrowOrders) {
        reportTableModel.setDataVector(
                new Object[][]{},
                new Object[]{"Mã phiếu", "Mã học sinh", "Học sinh", "ID sách", "Đầu sách", "Hạn trả", "Số ngày trễ"}
        );

        for (BorrowOrder borrowOrder : borrowOrders) {
            if (borrowOrder.getDetails() == null) {
                continue;
            }

            long overdueDays = borrowOrder.getDueDate() == null ? 0 : java.time.temporal.ChronoUnit.DAYS.between(borrowOrder.getDueDate(), java.time.LocalDate.now());
            for (BorrowDetail detail : borrowOrder.getDetails()) {
                Book book = detail.getBook();
                reportTableModel.addRow(new Object[]{
                        borrowOrder.getId(),
                        borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : "",
                        borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : "",
                        book != null ? book.getId() : "",
                        book != null && book.getBookTitle() != null ? book.getBookTitle().getTitle() : "",
                        borrowOrder.getDueDate(),
                        overdueDays
                });
            }
        }
    }

    // Hiển thị lịch sử mượn của học sinh.
    public void showStudentHistoryReport(List<BorrowOrder> borrowOrders) {
        reportTableModel.setDataVector(
                new Object[][]{},
                new Object[]{"Mã phiếu", "Mã học sinh", "Học sinh", "Ngày mượn", "Hạn trả", "Ngày trả", "Trạng thái"}
        );

        for (BorrowOrder borrowOrder : borrowOrders) {
            reportTableModel.addRow(new Object[]{
                    borrowOrder.getId(),
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : "",
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : "",
                    borrowOrder.getBorrowDate(),
                    borrowOrder.getDueDate(),
                    borrowOrder.getReturnRecord() != null ? borrowOrder.getReturnRecord().getReturnDate() : "",
                    borrowOrder.getStatus()
            });
        }
    }

    // Hiển thị thống kê số lượt mượn theo đầu sách.
    public void showBookTitleBorrowStats(List<BookTitleBorrowStatDTO> stats) {
        reportTableModel.setDataVector(
                new Object[][]{},
                new Object[]{"ID đầu sách", "Tên sách", "Tác giả", "Số lượt mượn"}
        );

        for (BookTitleBorrowStatDTO stat : stats) {
            reportTableModel.addRow(new Object[]{
                    stat.getBookTitleId(),
                    stat.getTitle(),
                    stat.getAuthor(),
                    stat.getBorrowCount()
            });
        }
    }

    public String getSelectedReportType() {
        Object selectedItem = reportTypeComboBox.getSelectedItem();
        return selectedItem == null ? "" : selectedItem.toString();
    }

    public int getSelectedReportIndex() {
        return reportTypeComboBox.getSelectedIndex();
    }

    public String getStudentCodeFilter() {
        return studentCodeField.getText().trim();
    }

    public String getStudentNameFilter() {
        return studentNameField.getText().trim();
    }

    // Làm mới bộ lọc của màn hình báo cáo.
    public void clearFilters() {
        reportTypeComboBox.setSelectedIndex(0);
        studentCodeField.setText("");
        studentNameField.setText("");
    }

    // Gắn sự kiện xem báo cáo.
    public void addViewReportListener(ActionListener listener) {
        viewReportButton.addActionListener(listener);
    }

    // Gắn sự kiện làm mới bộ lọc báo cáo.
    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    // Hiển thị lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
}
