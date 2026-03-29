package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BookService;
import com.laptrinhwindows.library_management.service.BookTitleService;
import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;

import java.util.List;
import java.util.stream.Collectors;

public class BookPanelController {
    private final ManagerContext context;
    private final BookService bookService;
    private final BookTitleService bookTitleService;
    private final BookManagementPanel panel;

    public BookPanelController(ManagerContext context) {
        this.context = context;
        this.bookService = context.getBookService();
        this.bookTitleService = context.getBookTitleService();
        this.panel = context.getBookManagementPanel();
    }

    public void init() {
        panel.addBookAddListener(event -> handleAdd());
        panel.addBookUpdateListener(event -> handleUpdate());
        panel.addBookTableSelectionListener(event -> handleSelection());
    }

    public void loadData() {
        List<Book> books = bookService.getAllBooks();
        List<BookTitle> bookTitles = bookTitleService.getAllBookTitles()
                .stream()
                .filter(bookTitle -> bookTitle.getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());
        panel.showBooks(books);
        panel.setBookTitleOptions(bookTitles);
        panel.clearBookForm();
    }

    private void handleAdd() {
        try {
            Book book = buildBookFromForm(null);
            bookService.addBook(book);
            loadData();
            context.refreshBorrowOrderOptions();
            context.setStatusMessage("Nhập cuốn sách thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể nhập cuốn sách. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleUpdate() {
        Integer selectedId = panel.getSelectedBookId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn cuốn sách cần cập nhật.");
            return;
        }

        try {
            Book book = buildBookFromForm(selectedId);
            bookService.updateBook(book);
            loadData();
            context.refreshBorrowOrderOptions();
            context.refreshReturnBorrowOrders();
            context.refreshTrackingOrders();
            context.refreshDefaultReport();
            context.setStatusMessage("Cập nhật cuốn sách thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật cuốn sách. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedBookId();
        if (selectedId == null) {
            return;
        }

        Book book = bookService.findBookById(selectedId);
        if (book != null) {
            if (book.getBookTitle() != null) {
                BookTitle fullBookTitle = bookTitleService.findBookTitleById(book.getBookTitle().getId());
                book.setBookTitle(fullBookTitle);
            }
            panel.fillBookForm(book);
        }
    }

    private Book buildBookFromForm(Integer id) {
        Integer bookTitleId = panel.getSelectedBookTitleId();
        String location = panel.getFormLocation();
        BookStatus status = panel.getFormStatus();

        if (bookTitleId == null) {
            throw new IllegalArgumentException("Vui lòng chọn đầu sách.");
        }
        if (location.isBlank()) {
            throw new IllegalArgumentException("Vị trí sách không được để trống.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Vui lòng chọn trạng thái sách.");
        }

        BookTitle bookTitle = bookTitleService.findBookTitleById(bookTitleId);
        if (bookTitle == null) {
            throw new IllegalArgumentException("Không tìm thấy đầu sách đã chọn.");
        }
        if (bookTitle.getStatus() != RecordStatus.ACTIVE) {
            throw new IllegalArgumentException("Đầu sách đã chọn đang ở trạng thái INACTIVE.");
        }

        Book book;
        if (id != null) {
            book = bookService.findBookById(id);
            if (book == null) {
                throw new IllegalArgumentException("Không tìm thấy cuốn sách cần cập nhật.");
            }
        } else {
            book = new Book();
        }

        book.setBookTitle(bookTitle);
        book.setLocation(location);
        book.setStatus(status);
        return book;
    }
}
