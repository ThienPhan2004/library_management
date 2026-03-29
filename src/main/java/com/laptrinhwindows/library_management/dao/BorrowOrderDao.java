package com.laptrinhwindows.library_management.dao;

import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;

import java.time.LocalDate;
import java.util.List;

public interface BorrowOrderDao {
    BorrowOrder save(BorrowOrder borrowOrder);

    List<BorrowOrder> findBorrowingOrders(Integer orderId, String studentCode, String studentName);

    List<BorrowOrder> findTrackingOrders(String studentCode, String studentName, LocalDate fromDate, LocalDate toDate, String filterMode);

    List<BorrowOrder> findBorrowingReport();

    List<BorrowOrder> findOverdueReport();

    List<BorrowOrder> findStudentBorrowHistory(String studentCode, String studentName);

    List<BookTitleBorrowStatDTO> countBorrowByBookTitle();

    BorrowOrder findById(Integer id);

    BorrowOrder completeReturn(Integer orderId, LocalDate returnDate);
}
