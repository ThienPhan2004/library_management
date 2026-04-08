package com.laptrinhwindows.library_management.view.staff;

import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowDetail;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ReturnBookPanel extends JPanel {
    private final JTextField searchOrderIdField;
    private final JTextField searchStudentCodeField;
    private final JTextField searchStudentNameField;
    private final JButton searchButton;
    private final JButton resetButton;

    private final JTable borrowOrderTable;
    private final DefaultTableModel borrowOrderTableModel;

    private final JTextField orderIdField;
    private final JTextField studentField;
    private final JTextField borrowDateField;
    private final JTextField dueDateField;
    private final JSpinner returnDateSpinner;
    private final JButton confirmReturnButton;

    private final JTable borrowDetailTable;
    private final DefaultTableModel borrowDetailTableModel;

    public ReturnBookPanel() {
        searchOrderIdField = new JTextField(10);
        searchStudentCodeField = new JTextField(12);
        searchStudentNameField = new JTextField(14);
        searchButton = new JButton("Tìm phiếu");
        resetButton = new JButton("Làm mới");

        borrowOrderTableModel = new DefaultTableModel(
                new Object[]{"Mã phiếu", "Mã học sinh", "Học sinh", "Ngày mượn", "Hạn trả", "Trạng thái"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowOrderTable = new JTable(borrowOrderTableModel);
        borrowOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        borrowOrderTable.setRowHeight(24);

        orderIdField = createReadOnlyField();
        studentField = createReadOnlyField();
        borrowDateField = createReadOnlyField();
        dueDateField = createReadOnlyField();
        returnDateSpinner = createDateSpinner(LocalDate.now());
        confirmReturnButton = new JButton("Xác nhận trả");

        borrowDetailTableModel = new DefaultTableModel(
                new Object[]{"ID sách", "Đầu sách", "Tác giả", "Vị trí", "Trạng thái"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowDetailTable = new JTable(borrowDetailTableModel);
        borrowDetailTable.setRowHeight(24);

        initLayout();
    }

    // Tạo giao diện cho chức năng trả sách theo bố cục dễ nhìn hơn.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Mã phiếu:"));
        searchPanel.add(searchOrderIdField);
        searchPanel.add(new JLabel("Mã học sinh:"));
        searchPanel.add(searchStudentCodeField);
        searchPanel.add(new JLabel("Họ tên:"));
        searchPanel.add(searchStudentNameField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JScrollPane borrowOrderScrollPane = new JScrollPane(borrowOrderTable);
        borrowOrderScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu đang mượn"));
        borrowOrderScrollPane.setPreferredSize(new Dimension(0, 170));

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(borrowOrderScrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin trả sách"),
                BorderFactory.createEmptyBorder(10, 8, 8, 8)
        ));
        infoPanel.setPreferredSize(new Dimension(360, 250));

        JLabel orderIdLabel = new JLabel("Mã phiếu:");
        JLabel studentLabel = new JLabel("Học sinh:");
        JLabel borrowDateLabel = new JLabel("Ngày mượn:");
        JLabel dueDateLabel = new JLabel("Hạn trả:");
        JLabel returnDateLabel = new JLabel("Ngày trả:");

        orderIdLabel.setFont(formFont);
        studentLabel.setFont(formFont);
        borrowDateLabel.setFont(formFont);
        dueDateLabel.setFont(formFont);
        returnDateLabel.setFont(formFont);
        orderIdField.setFont(formFont);
        studentField.setFont(formFont);
        borrowDateField.setFont(formFont);
        dueDateField.setFont(formFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        infoPanel.add(orderIdLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(orderIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        infoPanel.add(studentLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(studentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        infoPanel.add(borrowDateLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(borrowDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        infoPanel.add(dueDateLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(dueDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        infoPanel.add(returnDateLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        infoPanel.add(returnDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(18, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        infoPanel.add(confirmReturnButton, gbc);

        JScrollPane borrowDetailScrollPane = new JScrollPane(borrowDetailTable);
        borrowDetailScrollPane.setBorder(BorderFactory.createTitledBorder("Chi tiết sách trong phiếu"));
        borrowDetailScrollPane.setPreferredSize(new Dimension(0, 250));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(infoPanel, BorderLayout.WEST);
        bottomPanel.add(borrowDetailScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    // Hiển thị danh sách phiếu mượn đang còn hiệu lực.
    public void showBorrowOrders(List<BorrowOrder> borrowOrders) {
        borrowOrderTableModel.setRowCount(0);
        for (BorrowOrder borrowOrder : borrowOrders) {
            borrowOrderTableModel.addRow(new Object[]{
                    borrowOrder.getId(),
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : "",
                    borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : "",
                    borrowOrder.getBorrowDate(),
                    borrowOrder.getDueDate(),
                    borrowOrder.getStatus()
            });
        }
    }

    // Hiển thị chi tiết các cuốn sách thuộc phiếu mượn đang chọn.
    public void showBorrowOrderDetails(BorrowOrder borrowOrder) {
        borrowDetailTableModel.setRowCount(0);

        if (borrowOrder == null) {
            orderIdField.setText("");
            studentField.setText("");
            borrowDateField.setText("");
            dueDateField.setText("");
            return;
        }

        orderIdField.setText(String.valueOf(borrowOrder.getId()));
        studentField.setText(borrowOrder.getStudent() == null
                ? ""
                : borrowOrder.getStudent().getFullName() + " - " + borrowOrder.getStudent().getStudentCode());
        borrowDateField.setText(borrowOrder.getBorrowDate() == null ? "" : String.valueOf(borrowOrder.getBorrowDate()));
        dueDateField.setText(borrowOrder.getDueDate() == null ? "" : String.valueOf(borrowOrder.getDueDate()));

        if (borrowOrder.getDetails() == null) {
            return;
        }

        for (BorrowDetail detail : borrowOrder.getDetails()) {
            Book book = detail.getBook();
            borrowDetailTableModel.addRow(new Object[]{
                    book != null ? book.getId() : "",
                    book != null && book.getBookTitle() != null ? book.getBookTitle().getTitle() : "",
                    book != null && book.getBookTitle() != null ? book.getBookTitle().getAuthor() : "",
                    book != null ? book.getLocation() : "",
                    book != null ? book.getStatus() : ""
            });
        }
    }

    public Integer getSelectedBorrowOrderId() {
        int selectedRow = borrowOrderTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = borrowOrderTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchOrderId() {
        return searchOrderIdField.getText().trim();
    }

    public String getSearchStudentCode() {
        return searchStudentCodeField.getText().trim();
    }

    public String getSearchStudentName() {
        return searchStudentNameField.getText().trim();
    }

    public LocalDate getReturnDate() {
        Date date = (Date) returnDateSpinner.getValue();
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // Xóa điều kiện tìm kiếm và phần thông tin đang hiển thị.
    public void clearReturnForm() {
        borrowOrderTable.clearSelection();
        showBorrowOrderDetails(null);
        returnDateSpinner.setValue(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    // Xóa điều kiện tìm phiếu mượn.
    public void clearSearchFields() {
        searchOrderIdField.setText("");
        searchStudentCodeField.setText("");
        searchStudentNameField.setText("");
    }

    // Gắn sự kiện tìm phiếu mượn.
    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Gắn sự kiện làm mới danh sách phiếu mượn.
    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    // Gắn sự kiện chọn phiếu mượn trên bảng.
    public void addBorrowOrderSelectionListener(ListSelectionListener listener) {
        borrowOrderTable.getSelectionModel().addListSelectionListener(listener);
    }

    // Gắn sự kiện xác nhận trả sách.
    public void addConfirmReturnListener(ActionListener listener) {
        confirmReturnButton.addActionListener(listener);
    }

    // Hiển thị thông báo lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    // Hỏi lại người dùng trước khi xác nhận trả sách.
    public boolean confirmReturn() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xác nhận trả sách cho phiếu này không?",
                "Xác nhận trả sách",
                JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }

    private JTextField createReadOnlyField() {
        JTextField textField = new JTextField(16);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        return textField;
    }

    private JSpinner createDateSpinner(LocalDate localDate) {
        Date initialDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initialDate, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd-MM-yyyy"));
        spinner.setPreferredSize(new Dimension(160, 32));
        return spinner;
    }
}
