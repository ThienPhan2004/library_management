package com.laptrinhwindows.library_management.view.manager;

import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.Student;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowOrderPanel extends JPanel {
    private final JTextField searchStudentCodeField;
    private final JTextField searchStudentNameField;
    private final JTextField searchStudentClassField;
    private final JButton searchStudentButton;
    private final JButton resetStudentButton;

    private final JTable studentTable;
    private final DefaultTableModel studentTableModel;

    private final JTextField selectedStudentField;
    private final JTextArea selectedBooksArea;
    private final JSpinner borrowDateSpinner;
    private final JSpinner dueDateSpinner;
    private final JTable availableBooksTable;
    private final DefaultTableModel availableBooksTableModel;
    private final JButton createBorrowOrderButton;

    public BorrowOrderPanel() {
        searchStudentCodeField = new JTextField(10);
        searchStudentNameField = new JTextField(14);
        searchStudentClassField = new JTextField(10);
        searchStudentButton = new JButton("Tìm học sinh");
        resetStudentButton = new JButton("Làm mới");

        studentTableModel = new DefaultTableModel(
                new Object[]{"ID", "Mã học sinh", "Họ tên", "Lớp", "Số điện thoại"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(studentTableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(24);

        selectedStudentField = new JTextField(18);
        selectedStudentField.setEditable(false);
        selectedStudentField.setBackground(Color.WHITE);

        selectedBooksArea = new JTextArea(4, 18);
        selectedBooksArea.setEditable(false);
        selectedBooksArea.setLineWrap(true);
        selectedBooksArea.setWrapStyleWord(true);
        selectedBooksArea.setBackground(Color.WHITE);

        borrowDateSpinner = createDateSpinner(LocalDate.now());
        dueDateSpinner = createDateSpinner(LocalDate.now().plusDays(7));
        createBorrowOrderButton = new JButton("Lưu phiếu mượn");

        availableBooksTableModel = new DefaultTableModel(
                new Object[]{"Chọn", "ID", "Đầu sách", "Tác giả", "Vị trí", "Trạng thái"}, 0
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        availableBooksTable = new JTable(availableBooksTableModel);
        availableBooksTable.setRowHeight(24);

        initLayout();
        registerDisplayEvents();
        updateSelectedStudentDisplay();
        updateSelectedBooksDisplay();
    }

    // Tạo giao diện cho chức năng lập phiếu mượn theo bố cục rõ ràng hơn.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel studentPanel = new JPanel(new BorderLayout(10, 10));
        studentPanel.setBackground(Color.WHITE);

        JPanel studentSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        studentSearchPanel.setBackground(Color.WHITE);
        studentSearchPanel.add(new JLabel("Mã học sinh:"));
        studentSearchPanel.add(searchStudentCodeField);
        studentSearchPanel.add(new JLabel("Họ tên:"));
        studentSearchPanel.add(searchStudentNameField);
        studentSearchPanel.add(new JLabel("Lớp:"));
        studentSearchPanel.add(searchStudentClassField);
        studentSearchPanel.add(searchStudentButton);
        studentSearchPanel.add(resetStudentButton);

        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createTitledBorder("Chọn học sinh"));
        studentScrollPane.setPreferredSize(new Dimension(0, 190));

        studentPanel.add(studentSearchPanel, BorderLayout.NORTH);
        studentPanel.add(studentScrollPane, BorderLayout.CENTER);

        JPanel borrowInfoPanel = new JPanel(new GridBagLayout());
        borrowInfoPanel.setBackground(Color.WHITE);
        borrowInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));
        borrowInfoPanel.setPreferredSize(new Dimension(360, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel selectedStudentLabel = new JLabel("Học sinh mượn:");
        JLabel selectedBooksLabel = new JLabel("Sách đã chọn:");
        JLabel borrowDateLabel = new JLabel("Ngày mượn:");
        JLabel dueDateLabel = new JLabel("Hạn trả:");

        selectedStudentLabel.setFont(formFont);
        selectedBooksLabel.setFont(formFont);
        borrowDateLabel.setFont(formFont);
        dueDateLabel.setFont(formFont);
        selectedStudentField.setFont(formFont);
        selectedBooksArea.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        borrowInfoPanel.add(selectedStudentLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        borrowInfoPanel.add(selectedStudentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        borrowInfoPanel.add(selectedBooksLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JScrollPane selectedBooksScrollPane = new JScrollPane(selectedBooksArea);
        selectedBooksScrollPane.setPreferredSize(new Dimension(210, 90));
        borrowInfoPanel.add(selectedBooksScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        borrowInfoPanel.add(borrowDateLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        borrowInfoPanel.add(borrowDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        borrowInfoPanel.add(dueDateLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        borrowInfoPanel.add(dueDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(18, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        borrowInfoPanel.add(createBorrowOrderButton, gbc);

        JScrollPane booksScrollPane = new JScrollPane(availableBooksTable);
        booksScrollPane.setBorder(BorderFactory.createTitledBorder("Chọn một hoặc nhiều cuốn sách"));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(borrowInfoPanel, BorderLayout.WEST);
        bottomPanel.add(booksScrollPane, BorderLayout.CENTER);

        add(studentPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    // Gắn các sự kiện nội bộ để tự cập nhật thông tin đang chọn trên form.
    private void registerDisplayEvents() {
        studentTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                updateSelectedStudentDisplay();
            }
        });

        availableBooksTableModel.addTableModelListener(event -> {
            if (event.getType() == TableModelEvent.UPDATE || event.getType() == TableModelEvent.INSERT || event.getType() == TableModelEvent.DELETE) {
                updateSelectedBooksDisplay();
            }
        });
    }

    // Tạo ô chọn ngày để người dùng đỡ phải gõ tay.
    private JSpinner createDateSpinner(LocalDate localDate) {
        Date initialDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initialDate, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd-MM-yyyy");
        spinner.setEditor(editor);
        spinner.setPreferredSize(new Dimension(160, 32));
        return spinner;
    }

    // Cập nhật tên học sinh đã chọn để người dùng dễ quan sát.
    private void updateSelectedStudentDisplay() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            selectedStudentField.setText("Chưa chọn học sinh");
            return;
        }

        Object studentCode = studentTableModel.getValueAt(selectedRow, 1);
        Object fullName = studentTableModel.getValueAt(selectedRow, 2);
        Object className = studentTableModel.getValueAt(selectedRow, 3);
        selectedStudentField.setText(fullName + " - " + studentCode + " - " + className);
    }

    // Cập nhật danh sách sách đã tích chọn trên bảng.
    private void updateSelectedBooksDisplay() {
        List<String> selectedBooks = new ArrayList<>();
        for (int i = 0; i < availableBooksTableModel.getRowCount(); i++) {
            Object selectedValue = availableBooksTableModel.getValueAt(i, 0);
            if (selectedValue instanceof Boolean && (Boolean) selectedValue) {
                Object title = availableBooksTableModel.getValueAt(i, 2);
                Object location = availableBooksTableModel.getValueAt(i, 4);
                selectedBooks.add(title + " (" + location + ")");
            }
        }

        if (selectedBooks.isEmpty()) {
            selectedBooksArea.setText("Chưa chọn sách");
        } else {
            selectedBooksArea.setText(String.join("\n", selectedBooks));
        }
    }

    // Hiển thị danh sách học sinh để người dùng tìm và chọn.
    public void showStudents(List<Student> students) {
        studentTableModel.setRowCount(0);
        for (Student student : students) {
            studentTableModel.addRow(new Object[]{
                    student.getId(),
                    student.getStudentCode(),
                    student.getFullName(),
                    student.getClassName(),
                    student.getPhone()
            });
        }
        updateSelectedStudentDisplay();
    }

    // Hiển thị danh sách các cuốn sách còn khả dụng để chọn mượn.
    public void showAvailableBooks(List<Book> books) {
        availableBooksTableModel.setRowCount(0);
        for (Book book : books) {
            String title = book.getBookTitle() != null ? book.getBookTitle().getTitle() : "";
            String author = book.getBookTitle() != null ? book.getBookTitle().getAuthor() : "";
            availableBooksTableModel.addRow(new Object[]{
                    false,
                    book.getId(),
                    title,
                    author,
                    book.getLocation(),
                    book.getStatus()
            });
        }
        updateSelectedBooksDisplay();
    }

    // Lấy danh sách mã cuốn sách được chọn trên bảng.
    public List<Integer> getSelectedBookIds() {
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = 0; i < availableBooksTableModel.getRowCount(); i++) {
            Object selectedValue = availableBooksTableModel.getValueAt(i, 0);
            if (selectedValue instanceof Boolean && (Boolean) selectedValue) {
                selectedIds.add((Integer) availableBooksTableModel.getValueAt(i, 1));
            }
        }
        return selectedIds;
    }

    public Integer getSelectedStudentId() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = studentTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchStudentCode() {
        return searchStudentCodeField.getText().trim();
    }

    public String getSearchStudentName() {
        return searchStudentNameField.getText().trim();
    }

    public String getSearchStudentClass() {
        return searchStudentClassField.getText().trim();
    }

    public LocalDate getBorrowDate() {
        return convertToLocalDate((Date) borrowDateSpinner.getValue());
    }

    public LocalDate getDueDate() {
        return convertToLocalDate((Date) dueDateSpinner.getValue());
    }

    // Xóa các lựa chọn cũ sau khi lưu phiếu mượn thành công.
    public void clearBorrowOrderForm() {
        borrowDateSpinner.setValue(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dueDateSpinner.setValue(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        studentTable.clearSelection();
        for (int i = 0; i < availableBooksTableModel.getRowCount(); i++) {
            availableBooksTableModel.setValueAt(false, i, 0);
        }
        updateSelectedStudentDisplay();
        updateSelectedBooksDisplay();
    }

    // Xóa điều kiện tìm học sinh.
    public void clearStudentSearchFields() {
        searchStudentCodeField.setText("");
        searchStudentNameField.setText("");
        searchStudentClassField.setText("");
    }

    // Gắn sự kiện tìm học sinh.
    public void addStudentSearchListener(ActionListener listener) {
        searchStudentButton.addActionListener(listener);
    }

    // Gắn sự kiện làm mới danh sách học sinh.
    public void addStudentResetListener(ActionListener listener) {
        resetStudentButton.addActionListener(listener);
    }

    // Gắn sự kiện lưu phiếu mượn.
    public void addCreateBorrowOrderListener(ActionListener listener) {
        createBorrowOrderButton.addActionListener(listener);
    }

    // Hiển thị hộp thoại báo lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    private LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
