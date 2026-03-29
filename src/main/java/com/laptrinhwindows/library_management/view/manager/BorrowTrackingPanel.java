package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.enumtype.OrderStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class BorrowTrackingPanel extends JPanel {
    private final JTextField studentCodeField;
    private final JTextField studentNameField;
    private final JSpinner fromDateSpinner;
    private final JSpinner toDateSpinner;
    private final JComboBox<String> filterModeComboBox;
    private final JButton searchButton;
    private final JButton resetButton;
    private final JTable trackingTable;
    private final DefaultTableModel trackingTableModel;

    public BorrowTrackingPanel() {
        studentCodeField = new JTextField(12);
        studentNameField = new JTextField(16);
        fromDateSpinner = createDateSpinner(LocalDate.now().minusMonths(1));
        toDateSpinner = createDateSpinner(LocalDate.now());
        filterModeComboBox = new JComboBox<>(new String[]{"Tất cả", "Đang mượn", "Quá hạn"});
        searchButton = new JButton("Lọc");
        resetButton = new JButton("Làm mới");

        trackingTableModel = new DefaultTableModel(
                new Object[]{"Mã phiếu", "Mã học sinh", "Học sinh", "Ngày mượn", "Hạn trả", "Trạng thái", "Theo dõi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        trackingTable = new JTable(trackingTableModel);
        trackingTable.setRowHeight(24);

        initLayout();
    }

    // Tạo giao diện theo dõi phiếu mượn và quá hạn.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Mã học sinh:"));
        filterPanel.add(studentCodeField);
        filterPanel.add(new JLabel("Họ tên:"));
        filterPanel.add(studentNameField);
        filterPanel.add(new JLabel("Từ ngày:"));
        filterPanel.add(fromDateSpinner);
        filterPanel.add(new JLabel("Đến ngày:"));
        filterPanel.add(toDateSpinner);
        filterPanel.add(new JLabel("Trạng thái:"));
        filterPanel.add(filterModeComboBox);
        filterPanel.add(searchButton);
        filterPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(trackingTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu mượn cần theo dõi"));

        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    // Hiển thị danh sách phiếu theo điều kiện lọc hiện tại.
    public void showBorrowOrders(List<BorrowOrder> borrowOrders) {
        trackingTableModel.setRowCount(0);
        LocalDate today = LocalDate.now();

        for (BorrowOrder borrowOrder : borrowOrders) {
            String followStatus = "";
            if (borrowOrder.getStatus() == OrderStatus.BORROWING) {
                if (borrowOrder.getDueDate() != null && borrowOrder.getDueDate().isBefore(today)) {
                    followStatus = "Quá hạn";
                } else {
                    followStatus = "Đang mượn";
                }
            } else {
                followStatus = "Đã trả";
            }

            trackingTableModel.addRow(new Object[]{
                    borrowOrder.getId(),
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : "",
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : "",
                    borrowOrder.getBorrowDate(),
                    borrowOrder.getDueDate(),
                    borrowOrder.getStatus(),
                    followStatus
            });
        }
    }

    public String getStudentCodeFilter() {
        return studentCodeField.getText().trim();
    }

    public String getStudentNameFilter() {
        return studentNameField.getText().trim();
    }

    public LocalDate getFromDate() {
        return convertToLocalDate((Date) fromDateSpinner.getValue());
    }

    public LocalDate getToDate() {
        return convertToLocalDate((Date) toDateSpinner.getValue());
    }

    public String getFilterMode() {
        Object selectedItem = filterModeComboBox.getSelectedItem();
        if ("Đang mượn".equals(selectedItem)) {
            return "BORROWING";
        }
        if ("Quá hạn".equals(selectedItem)) {
            return "OVERDUE";
        }
        return "ALL";
    }

    // Làm mới toàn bộ điều kiện lọc về trạng thái ban đầu.
    public void clearFilters() {
        studentCodeField.setText("");
        studentNameField.setText("");
        fromDateSpinner.setValue(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        toDateSpinner.setValue(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        filterModeComboBox.setSelectedIndex(0);
    }

    // Gắn sự kiện lọc danh sách phiếu mượn.
    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Gắn sự kiện làm mới bộ lọc.
    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    // Hiển thị lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    private JSpinner createDateSpinner(LocalDate localDate) {
        Date initialDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initialDate, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd-MM-yyyy"));
        spinner.setPreferredSize(new Dimension(120, 30));
        return spinner;
    }

    private LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
