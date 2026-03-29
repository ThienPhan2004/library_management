package com.laptrinhwindows.library_management.service;

import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.Student;
import com.laptrinhwindows.library_management.model.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface BorrowOrderService {
    BorrowOrder createBorrowOrder(User user, Student student, List<Book> books, LocalDate borrowDate, LocalDate dueDate);

    List<BorrowOrder> searchBorrowingOrders(Integer orderId, String studentCode, String studentName);

    List<BorrowOrder> searchTrackingOrders(String studentCode, String studentName, LocalDate fromDate, LocalDate toDate, String filterMode);

    List<BorrowOrder> getBorrowingReport();

    List<BorrowOrder> getOverdueReport();

    List<BorrowOrder> getStudentBorrowHistory(String studentCode, String studentName);

    List<BookTitleBorrowStatDTO> getBookTitleBorrowStatistics();

    BorrowOrder findBorrowOrderById(Integer id);

    BorrowOrder returnBooks(Integer orderId, LocalDate returnDate);
}
