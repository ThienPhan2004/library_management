package com.laptrinhwindows.library_management.view.shared;

import com.laptrinhwindows.library_management.model.entity.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentManagementPanel extends JPanel {
    private final JTextField searchCodeField;
    private final JTextField searchNameField;
    private final JTextField searchClassField;
    private final JButton searchButton;
    private final JButton resetButton;

    private final JTable studentTable;
    private final DefaultTableModel studentTableModel;

    private final JTextField studentCodeField;
    private final JTextField studentNameField;
    private final JTextField studentClassField;
    private final JTextField studentPhoneField;
    private final JButton addStudentButton;
    private final JButton updateStudentButton;
    private final JButton deleteStudentButton;
    private JPanel buttonPanel;
    private boolean readOnlyMode;

    public StudentManagementPanel() {
        searchCodeField = new JTextField(12);
        searchNameField = new JTextField(14);
        searchClassField = new JTextField(10);
        searchButton = new JButton("Tìm kiếm");
        resetButton = new JButton("Làm mới");

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

        studentCodeField = new JTextField(15);
        studentNameField = new JTextField(20);
        studentClassField = new JTextField(15);
        studentPhoneField = new JTextField(15);
        addStudentButton = new JButton("Thêm");
        updateStudentButton = new JButton("Sửa");
        deleteStudentButton = new JButton("Xóa");

        initLayout();
    }

    // Tạo giao diện riêng cho chức năng quản lý học sinh.
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        Font formFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Mã học sinh:"));
        searchPanel.add(searchCodeField);
        searchPanel.add(new JLabel("Họ tên:"));
        searchPanel.add(searchNameField);
        searchPanel.add(new JLabel("Lớp:"));
        searchPanel.add(searchClassField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin học sinh"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel codeLabel = new JLabel("Mã học sinh:");
        JLabel nameLabel = new JLabel("Họ tên:");
        JLabel classLabel = new JLabel("Lớp:");
        JLabel phoneLabel = new JLabel("Số điện thoại:");

        codeLabel.setFont(formFont);
        nameLabel.setFont(formFont);
        classLabel.setFont(formFont);
        phoneLabel.setFont(formFont);
        studentCodeField.setFont(formFont);
        studentNameField.setFont(formFont);
        studentClassField.setFont(formFont);
        studentPhoneField.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(codeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(classLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentClassField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentPhoneField, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(updateStudentButton);
        buttonPanel.add(deleteStudentButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    public void setReadOnlyMode(boolean readOnlyMode) {
        this.readOnlyMode = readOnlyMode;
        studentCodeField.setEditable(!readOnlyMode);
        studentNameField.setEditable(!readOnlyMode);
        studentClassField.setEditable(!readOnlyMode);
        studentPhoneField.setEditable(!readOnlyMode);
        addStudentButton.setVisible(!readOnlyMode);
        updateStudentButton.setVisible(!readOnlyMode);
        deleteStudentButton.setVisible(!readOnlyMode);
        buttonPanel.setVisible(!readOnlyMode);
    }

    // Hiển thị danh sách học sinh lên bảng.
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
    }

    // Đổ dữ liệu của học sinh được chọn lên form.
    public void fillStudentForm(Student student) {
        studentCodeField.setText(student.getStudentCode());
        studentNameField.setText(student.getFullName());
        studentClassField.setText(student.getClassName());
        studentPhoneField.setText(student.getPhone() == null ? "" : student.getPhone());
    }

    // Xóa trắng toàn bộ ô nhập liệu của form học sinh.
    public void clearStudentForm() {
        studentTable.clearSelection();
        studentCodeField.setText("");
        studentNameField.setText("");
        studentClassField.setText("");
        studentPhoneField.setText("");
    }

    public boolean isReadOnlyMode() {
        return readOnlyMode;
    }

    // Xóa các điều kiện tìm kiếm học sinh.
    public void clearStudentSearchFields() {
        searchCodeField.setText("");
        searchNameField.setText("");
        searchClassField.setText("");
    }

    // Lấy mã định danh của học sinh đang được chọn trên bảng.
    public Integer getSelectedStudentId() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        Object value = studentTableModel.getValueAt(selectedRow, 0);
        return value instanceof Integer ? (Integer) value : null;
    }

    public String getSearchStudentCode() {
        return searchCodeField.getText().trim();
    }

    public String getSearchStudentName() {
        return searchNameField.getText().trim();
    }

    public String getSearchStudentClass() {
        return searchClassField.getText().trim();
    }

    public String getFormStudentCode() {
        return studentCodeField.getText().trim();
    }

    public String getFormStudentName() {
        return studentNameField.getText().trim();
    }

    public String getFormStudentClass() {
        return studentClassField.getText().trim();
    }

    public String getFormStudentPhone() {
        return studentPhoneField.getText().trim();
    }

    // Gắn sự kiện tìm kiếm học sinh.
    public void addStudentSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Gắn sự kiện làm mới danh sách học sinh.
    public void addStudentResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    // Gắn sự kiện thêm học sinh.
    public void addStudentAddListener(ActionListener listener) {
        addStudentButton.addActionListener(listener);
    }

    // Gắn sự kiện cập nhật học sinh.
    public void addStudentUpdateListener(ActionListener listener) {
        updateStudentButton.addActionListener(listener);
    }

    // Gắn sự kiện xóa học sinh.
    public void addStudentDeleteListener(ActionListener listener) {
        deleteStudentButton.addActionListener(listener);
    }

    // Gắn sự kiện chọn dòng trên bảng học sinh.
    public void addStudentTableSelectionListener(ListSelectionListener listener) {
        studentTable.getSelectionModel().addListSelectionListener(listener);
    }

    // Hiển thị hộp thoại báo lỗi cho người dùng.
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }

    // Hỏi lại người dùng trước khi xóa học sinh.
    public boolean confirmDeleteStudent() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa học sinh này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }
}
