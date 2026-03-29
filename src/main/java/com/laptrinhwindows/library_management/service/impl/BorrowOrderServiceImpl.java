package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.BorrowOrderDao;
import com.laptrinhwindows.library_management.dao.impl.BorrowOrderDaoImpl;
import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowDetail;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.model.entity.User;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.OrderStatus;
import com.laptrinhwindows.library_management.model.enumtype.RecordStatus;
import com.laptrinhwindows.library_management.service.BorrowOrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowOrderServiceImpl implements BorrowOrderService {
    private final BorrowOrderDao borrowOrderDao;

    public BorrowOrderServiceImpl() {
        this.borrowOrderDao = new BorrowOrderDaoImpl();
    }

    // Tạo phiếu mượn và cập nhật trạng thái các cuốn sách được chọn.
    @Override
    public BorrowOrder createBorrowOrder(User user, Student student, List<Book> books, LocalDate borrowDate, LocalDate dueDate) {
        if (user == null || student == null || books == null || books.isEmpty()) {
            throw new IllegalArgumentException("Dữ liệu phiếu mượn không hợp lệ.");
        }

        BorrowOrder borrowOrder = new BorrowOrder();
        borrowOrder.setUser(user);
        borrowOrder.setStudent(student);
        borrowOrder.setBorrowDate(borrowDate);
        borrowOrder.setDueDate(dueDate);
        borrowOrder.setStatus(OrderStatus.BORROWING);

        List<BorrowDetail> details = new ArrayList<>();
        for (Book book : books) {
            if (book == null) {
                throw new IllegalArgumentException("Tồn tại cuốn sách không hợp lệ.");
            }
            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new IllegalArgumentException("Chỉ được mượn sách đang ở trạng thái AVAILABLE.");
            }
            if (book.getBookTitle() == null || book.getBookTitle().getStatus() != RecordStatus.ACTIVE) {
                throw new IllegalArgumentException("Không thể mượn sách thuộc đầu sách INACTIVE.");
            }

            book.setStatus(BookStatus.BORROWED);

            BorrowDetail detail = new BorrowDetail();
            detail.setOrder(borrowOrder);
            detail.setBook(book);
            details.add(detail);
        }

        borrowOrder.setDetails(details);
        return borrowOrderDao.save(borrowOrder);
    }

    // Tìm các phiếu mượn đang còn hiệu lực để xử lý trả sách.
    @Override
    public List<BorrowOrder> searchBorrowingOrders(Integer orderId, String studentCode, String studentName) {
        return borrowOrderDao.findBorrowingOrders(orderId, studentCode, studentName);
    }

    // Tìm phiếu theo dõi mượn sách với các điều kiện lọc trên giao diện.
    @Override
    public List<BorrowOrder> searchTrackingOrders(String studentCode, String studentName, LocalDate fromDate, LocalDate toDate, String filterMode) {
        return borrowOrderDao.findTrackingOrders(studentCode, studentName, fromDate, toDate, filterMode);
    }

    // Lấy báo cáo danh sách sách đang được mượn.
    @Override
    public List<BorrowOrder> getBorrowingReport() {
        return borrowOrderDao.findBorrowingReport();
    }

    // Lấy báo cáo danh sách sách đang quá hạn.
    @Override
    public List<BorrowOrder> getOverdueReport() {
        return borrowOrderDao.findOverdueReport();
    }

    // Lấy lịch sử mượn theo học sinh.
    @Override
    public List<BorrowOrder> getStudentBorrowHistory(String studentCode, String studentName) {
        return borrowOrderDao.findStudentBorrowHistory(studentCode, studentName);
    }

    // Thống kê số lượt mượn theo đầu sách.
    @Override
    public List<BookTitleBorrowStatDTO> getBookTitleBorrowStatistics() {
        return borrowOrderDao.countBorrowByBookTitle();
    }

    // Tìm phiếu mượn theo mã để hiển thị chi tiết sách.
    @Override
    public BorrowOrder findBorrowOrderById(Integer id) {
        return borrowOrderDao.findById(id);
    }

    // Xác nhận trả sách, lưu ngày trả và cập nhật trạng thái phiếu mượn.
    @Override
    public BorrowOrder returnBooks(Integer orderId, LocalDate returnDate) {
        return borrowOrderDao.completeReturn(orderId, returnDate);
    }
}
