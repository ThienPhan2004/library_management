package com.laptrinhwindows.library_management.controller.staff;

import com.laptrinhwindows.library_management.controller.common.LibraryContext;
import com.laptrinhwindows.library_management.dao.UserLookupDao;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.model.entity.User;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BorrowOrderService;
import com.laptrinhwindows.library_management.service.StudentService;
import com.laptrinhwindows.library_management.util.BorrowReceiptPdfExporter;
import com.laptrinhwindows.library_management.view.staff.BorrowOrderPanel;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowOrderPanelController {
    private final LibraryContext context;
    private final StudentService studentService;
    private final BookService bookService;
    private final BorrowOrderService borrowOrderService;
    private final UserLookupDao userLookupDao;
    private final BorrowOrderPanel panel;
    private BorrowOrder latestBorrowOrder;

    public BorrowOrderPanelController(LibraryContext context) {
        this.context = context;
        this.studentService = context.getStudentService();
        this.bookService = context.getBookService();
        this.borrowOrderService = context.getBorrowOrderService();
        this.userLookupDao = context.getUserLookupDao();
        this.panel = context.getBorrowOrderPanel();
    }

    public void init() {
        panel.addStudentSearchListener(event -> handleStudentSearch());
        panel.addStudentResetListener(event -> handleStudentReset());
        panel.addCreateBorrowOrderListener(event -> handleCreateBorrowOrder());
        panel.addExportPdfListener(event -> handleExportPdf());
        panel.setExportPdfEnabled(false);
    }

    public void loadOptions() {
        List<Student> students = studentService.getAllStudents();
        List<Book> availableBooks = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .filter(book -> book.getBookTitle() != null && book.getBookTitle().getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());

        panel.showStudents(students);
        panel.showAvailableBooks(availableBooks);
    }

    private void handleStudentSearch() {
        List<Student> students = studentService.searchStudents(
                panel.getSearchStudentCode(),
                panel.getSearchStudentName(),
                panel.getSearchStudentClass()
        );
        panel.showStudents(students);
        context.setStatusMessage("Đã lọc được " + students.size() + " học sinh để lập phiếu.");
    }

    private void handleStudentReset() {
        panel.clearStudentSearchFields();
        loadOptions();
        context.setStatusMessage("Đã làm mới danh sách học sinh trong phần lập phiếu mượn.");
    }

    private void handleCreateBorrowOrder() {
        try {
            Integer studentId = panel.getSelectedStudentId();
            List<Integer> bookIds = panel.getSelectedBookIds();
            LocalDate borrowDate = panel.getBorrowDate();
            LocalDate dueDate = panel.getDueDate();

            if (studentId == null) {
                throw new IllegalArgumentException("Vui lòng chọn học sinh.");
            }
            if (bookIds.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng chọn ít nhất một cuốn sách.");
            }
            if (dueDate.isBefore(borrowDate)) {
                throw new IllegalArgumentException("Hạn trả phải lớn hơn hoặc bằng ngày mượn.");
            }

            Student student = studentService.findStudentById(studentId);
            User user = userLookupDao.findById(context.getLoginUser().getUserId());
            List<Book> books = bookIds.stream()
                    .map(bookService::findBookById)
                    .collect(Collectors.toList());

            if (student == null || user == null || books.contains(null)) {
                throw new IllegalArgumentException("Dữ liệu phiếu mượn không hợp lệ.");
            }

            BorrowOrder borrowOrder = borrowOrderService.createBorrowOrder(user, student, books, borrowDate, dueDate);
            if (borrowOrder != null) {
                latestBorrowOrder = borrowOrderService.findBorrowOrderById(borrowOrder.getId());
                panel.setExportPdfEnabled(latestBorrowOrder != null);
                panel.clearBorrowOrderForm();
                context.refreshCirculationViews();
                context.setStatusMessage("Lưu phiếu mượn thành công. Bạn có thể xuất phiếu PDF cho sinh viên.");
            }
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể lưu phiếu mượn. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleExportPdf() {
        if (latestBorrowOrder == null) {
            panel.showErrorMessage("Chưa có phiếu mượn nào để xuất PDF.");
            return;
        }

        try {
            String studentCode = latestBorrowOrder.getStudent() == null ? "sinh-vien" : latestBorrowOrder.getStudent().getStudentCode();
            File outputFile = panel.choosePdfSaveLocation("phieu-muon-" + studentCode + "-" + latestBorrowOrder.getId() + ".pdf");
            if (outputFile == null) {
                return;
            }

            BorrowReceiptPdfExporter.export(latestBorrowOrder, outputFile);
            context.setStatusMessage("Xuất phiếu PDF thành công: " + outputFile.getName());

            if (panel.confirmOpenPdf(outputFile)) {
                BorrowReceiptPdfExporter.openFile(outputFile);
            }
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            panel.showErrorMessage(resolveErrorMessage(ex));
        }
    }

    private String resolveErrorMessage(Exception ex) {
        Throwable current = ex;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        String message = current.getMessage();
        if (message == null || message.isBlank()) {
            return "Không thể xuất phiếu mượn PDF.";
        }
        return "Không thể xuất phiếu mượn PDF: " + message;
    }
}
