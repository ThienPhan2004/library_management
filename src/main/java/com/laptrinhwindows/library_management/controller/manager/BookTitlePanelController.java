package com.laptrinhwindows.library_management.controller.manager;

import com.laptrinhwindows.library_management.model.entity.BookTitle;
import com.laptrinhwindows.library_management.model.entity.Supplier;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BookTitleService;
import com.laptrinhwindows.library_management.service.SupplierService;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;

import java.util.List;
import java.util.stream.Collectors;

public class BookTitlePanelController {
    private final ManagerContext context;
    private final BookTitleService bookTitleService;
    private final SupplierService supplierService;
    private final BookTitleManagementPanel panel;

    public BookTitlePanelController(ManagerContext context) {
        this.context = context;
        this.bookTitleService = context.getBookTitleService();
        this.supplierService = context.getSupplierService();
        this.panel = context.getBookTitleManagementPanel();
    }

    public void init() {
        panel.addBookTitleSearchListener(event -> handleSearch());
        panel.addBookTitleResetListener(event -> handleReset());
        panel.addBookTitleAddListener(event -> handleAdd());
        panel.addBookTitleUpdateListener(event -> handleUpdate());
        panel.addBookTitleDeleteListener(event -> handleDelete());
        panel.addBookTitleTableSelectionListener(event -> handleSelection());
    }

    public void loadData() {
        List<BookTitle> bookTitles = bookTitleService.getAllBookTitles();
        List<Supplier> suppliers = supplierService.getAllSuppliers()
                .stream()
                .filter(supplier -> supplier.getStatus() == RecordStatus.ACTIVE)
                .collect(Collectors.toList());
        panel.setSupplierOptions(suppliers);
        panel.showBookTitles(bookTitles);
        panel.clearBookTitleForm();
    }

    private void handleSearch() {
        List<BookTitle> bookTitles = bookTitleService.searchBookTitles(
                panel.getSearchTitle(),
                panel.getSearchAuthor(),
                panel.getSearchCategory()
        );
        panel.showBookTitles(bookTitles);
        context.setStatusMessage("Đã tìm thấy " + bookTitles.size() + " đầu sách.");
    }

    private void handleReset() {
        panel.clearBookTitleSearchFields();
        loadData();
        context.setStatusMessage("Đã làm mới danh sách đầu sách.");
    }

    private void handleAdd() {
        try {
            BookTitle bookTitle = buildBookTitleFromForm(null);
            bookTitleService.addBookTitle(bookTitle);
            loadData();
            context.refreshBookTable();
            context.refreshBorrowOrderOptions();
            context.setStatusMessage("Thêm đầu sách thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể thêm đầu sách. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleUpdate() {
        Integer selectedId = panel.getSelectedBookTitleId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn đầu sách cần sửa.");
            return;
        }

        try {
            BookTitle bookTitle = buildBookTitleFromForm(selectedId);
            bookTitleService.updateBookTitle(bookTitle);
            loadData();
            context.refreshBookTable();
            context.refreshBorrowOrderOptions();
            context.setStatusMessage("Cập nhật đầu sách thành công.");
        } catch (IllegalArgumentException ex) {
            panel.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật đầu sách. Vui lòng kiểm tra dữ liệu.");
        }
    }

    private void handleDelete() {
        Integer selectedId = panel.getSelectedBookTitleId();
        if (selectedId == null) {
            panel.showErrorMessage("Vui lòng chọn đầu sách cần ngừng sử dụng.");
            return;
        }

        if (!panel.confirmDeleteBookTitle()) {
            return;
        }

        try {
            bookTitleService.deleteBookTitle(selectedId);
            loadData();
            context.refreshBookTable();
            context.refreshBorrowOrderOptions();
            context.setStatusMessage("Chuyển đầu sách sang INACTIVE thành công.");
        } catch (Exception ex) {
            panel.showErrorMessage("Không thể cập nhật trạng thái đầu sách. Có thể đầu sách này đã phát sinh dữ liệu sách.");
        }
    }

    private void handleSelection() {
        Integer selectedId = panel.getSelectedBookTitleId();
        if (selectedId == null) {
            return;
        }

        BookTitle bookTitle = bookTitleService.findBookTitleById(selectedId);
        if (bookTitle != null) {
            panel.fillBookTitleForm(bookTitle);
        }
    }

    private BookTitle buildBookTitleFromForm(Integer id) {
        String title = panel.getFormTitle();
        String author = panel.getFormAuthor();
        String category = panel.getFormCategory();
        String publishYearText = panel.getFormPublishYear();
        Integer supplierId = panel.getSelectedSupplierId();
        RecordStatus status = panel.getFormStatus();

        if (title.isBlank()) {
            throw new IllegalArgumentException("Tên sách không được để trống.");
        }
        if (author.isBlank()) {
            throw new IllegalArgumentException("Tác giả không được để trống.");
        }
        if (category.isBlank()) {
            throw new IllegalArgumentException("Thể loại không được để trống.");
        }
        if (supplierId == null) {
            throw new IllegalArgumentException("Vui lòng chọn nhà cung cấp.");
        }

        Integer publishYear = null;
        if (!publishYearText.isBlank()) {
            try {
                publishYear = Integer.parseInt(publishYearText);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Năm xuất bản phải là số nguyên.");
            }
        }

        Supplier supplier = supplierService.findSupplierById(supplierId);
        if (supplier == null || supplier.getStatus() != RecordStatus.ACTIVE) {
            throw new IllegalArgumentException("Nhà cung cấp đã chọn không hợp lệ.");
        }

        BookTitle bookTitle;
        if (id != null) {
            bookTitle = bookTitleService.findBookTitleById(id);
            if (bookTitle == null) {
                throw new IllegalArgumentException("Không tìm thấy đầu sách cần cập nhật.");
            }
        } else {
            bookTitle = new BookTitle();
        }

        bookTitle.setTitle(title);
        bookTitle.setAuthor(author);
        bookTitle.setCategory(category);
        bookTitle.setPublishYear(publishYear);
        bookTitle.setSupplier(supplier);
        bookTitle.setStatus(status == null ? RecordStatus.ACTIVE : status);
        return bookTitle;
    }
}
