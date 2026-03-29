package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.BorrowOrderDao;
import com.laptrinhwindows.library_management.dto.BookTitleBorrowStatDTO;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.laptrinhwindows.library_management.model.entity.ReturnRecord;
import com.laptrinhwindows.library_management.model.enumtype.BookStatus;
import com.laptrinhwindows.library_management.model.enumtype.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class BorrowOrderDaoImpl extends GenericDaoImpl<BorrowOrder> implements BorrowOrderDao {

    public BorrowOrderDaoImpl() {
        super(BorrowOrder.class);
    }

    // Lưu phiếu mượn cùng danh sách chi tiết mượn.
    @Override
    public BorrowOrder save(BorrowOrder borrowOrder) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(borrowOrder);
            em.getTransaction().commit();
            return borrowOrder;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Tìm các phiếu đang ở trạng thái mượn để phục vụ màn hình trả sách.
    @Override
    public List<BorrowOrder> findBorrowingOrders(Integer orderId, String studentCode, String studentName) {
        EntityManager em = createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "select o from BorrowOrder o join o.student s where o.status = :status"
            );

            if (orderId != null) {
                jpql.append(" and o.id = :orderId");
            }
            if (studentCode != null && !studentCode.isBlank()) {
                jpql.append(" and lower(s.studentCode) like :studentCode");
            }
            if (studentName != null && !studentName.isBlank()) {
                jpql.append(" and lower(s.fullName) like :studentName");
            }

            jpql.append(" order by o.id desc");

            TypedQuery<BorrowOrder> query = em.createQuery(jpql.toString(), BorrowOrder.class);
            query.setParameter("status", OrderStatus.BORROWING);

            if (orderId != null) {
                query.setParameter("orderId", orderId);
            }
            if (studentCode != null && !studentCode.isBlank()) {
                query.setParameter("studentCode", "%" + studentCode.toLowerCase() + "%");
            }
            if (studentName != null && !studentName.isBlank()) {
                query.setParameter("studentName", "%" + studentName.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm danh sách phiếu đang mượn hoặc quá hạn cho màn hình theo dõi.
    @Override
    public List<BorrowOrder> findTrackingOrders(String studentCode, String studentName, LocalDate fromDate, LocalDate toDate, String filterMode) {
        EntityManager em = createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "select o from BorrowOrder o join o.student s where 1 = 1"
            );

            if (studentCode != null && !studentCode.isBlank()) {
                jpql.append(" and lower(s.studentCode) like :studentCode");
            }
            if (studentName != null && !studentName.isBlank()) {
                jpql.append(" and lower(s.fullName) like :studentName");
            }
            if (fromDate != null) {
                jpql.append(" and o.borrowDate >= :fromDate");
            }
            if (toDate != null) {
                jpql.append(" and o.borrowDate <= :toDate");
            }
            if ("BORROWING".equals(filterMode)) {
                jpql.append(" and o.status = :status");
            }
            if ("OVERDUE".equals(filterMode)) {
                jpql.append(" and o.status = :status and o.dueDate < :today");
            }

            jpql.append(" order by o.borrowDate desc, o.id desc");

            TypedQuery<BorrowOrder> query = em.createQuery(jpql.toString(), BorrowOrder.class);

            if (studentCode != null && !studentCode.isBlank()) {
                query.setParameter("studentCode", "%" + studentCode.toLowerCase() + "%");
            }
            if (studentName != null && !studentName.isBlank()) {
                query.setParameter("studentName", "%" + studentName.toLowerCase() + "%");
            }
            if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null) {
                query.setParameter("toDate", toDate);
            }
            if ("BORROWING".equals(filterMode) || "OVERDUE".equals(filterMode)) {
                query.setParameter("status", OrderStatus.BORROWING);
            }
            if ("OVERDUE".equals(filterMode)) {
                query.setParameter("today", LocalDate.now());
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Lấy danh sách phiếu đang mượn kèm chi tiết sách để làm báo cáo.
    @Override
    public List<BorrowOrder> findBorrowingReport() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                            "select distinct o from BorrowOrder o " +
                                    "left join fetch o.student " +
                                    "left join fetch o.details d " +
                                    "left join fetch d.book b " +
                                    "left join fetch b.bookTitle " +
                                    "where o.status = :status " +
                                    "order by o.borrowDate desc, o.id desc",
                            BorrowOrder.class
                    )
                    .setParameter("status", OrderStatus.BORROWING)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Lấy danh sách phiếu quá hạn kèm chi tiết sách để làm báo cáo.
    @Override
    public List<BorrowOrder> findOverdueReport() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                            "select distinct o from BorrowOrder o " +
                                    "left join fetch o.student " +
                                    "left join fetch o.details d " +
                                    "left join fetch d.book b " +
                                    "left join fetch b.bookTitle " +
                                    "where o.status = :status and o.dueDate < :today " +
                                    "order by o.dueDate asc, o.id desc",
                            BorrowOrder.class
                    )
                    .setParameter("status", OrderStatus.BORROWING)
                    .setParameter("today", LocalDate.now())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Lấy lịch sử mượn theo học sinh.
    @Override
    public List<BorrowOrder> findStudentBorrowHistory(String studentCode, String studentName) {
        EntityManager em = createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "select distinct o from BorrowOrder o " +
                            "left join fetch o.student s " +
                            "left join fetch o.returnRecord " +
                            "left join fetch o.details d " +
                            "left join fetch d.book b " +
                            "left join fetch b.bookTitle " +
                            "where 1 = 1"
            );

            if (studentCode != null && !studentCode.isBlank()) {
                jpql.append(" and lower(s.studentCode) like :studentCode");
            }
            if (studentName != null && !studentName.isBlank()) {
                jpql.append(" and lower(s.fullName) like :studentName");
            }

            jpql.append(" order by o.borrowDate desc, o.id desc");

            TypedQuery<BorrowOrder> query = em.createQuery(jpql.toString(), BorrowOrder.class);
            if (studentCode != null && !studentCode.isBlank()) {
                query.setParameter("studentCode", "%" + studentCode.toLowerCase() + "%");
            }
            if (studentName != null && !studentName.isBlank()) {
                query.setParameter("studentName", "%" + studentName.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Thống kê số lượt mượn theo đầu sách.
    @Override
    public List<BookTitleBorrowStatDTO> countBorrowByBookTitle() {
        EntityManager em = createEntityManager();
        try {
            List<Object[]> rows = em.createQuery(
                            "select bt.id, bt.title, bt.author, count(d.id) " +
                                    "from BorrowDetail d " +
                                    "join d.book b " +
                                    "join b.bookTitle bt " +
                                    "group by bt.id, bt.title, bt.author " +
                                    "order by count(d.id) desc, bt.title asc",
                            Object[].class
                    )
                    .getResultList();

            return rows.stream()
                    .map(row -> new BookTitleBorrowStatDTO(
                            (Integer) row[0],
                            (String) row[1],
                            (String) row[2],
                            (Long) row[3]
                    ))
                    .toList();
        } finally {
            em.close();
        }
    }

    // Tìm phiếu mượn kèm chi tiết sách để hiển thị lên giao diện.
    @Override
    public BorrowOrder findById(Integer id) {
        EntityManager em = createEntityManager();
        try {
            List<BorrowOrder> results = em.createQuery(
                            "select distinct o from BorrowOrder o " +
                                    "left join fetch o.student " +
                                    "left join fetch o.details d " +
                                    "left join fetch d.book b " +
                                    "left join fetch b.bookTitle " +
                                    "left join fetch o.returnRecord " +
                                    "where o.id = :id",
                            BorrowOrder.class
                    )
                    .setParameter("id", id)
                    .getResultList();

            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    // Xác nhận trả sách, cập nhật trạng thái sách và lưu ngày trả.
    @Override
    public BorrowOrder completeReturn(Integer orderId, LocalDate returnDate) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();

            List<BorrowOrder> results = em.createQuery(
                            "select distinct o from BorrowOrder o " +
                                    "left join fetch o.details d " +
                                    "left join fetch d.book " +
                                    "left join fetch o.returnRecord " +
                                    "left join fetch o.student " +
                                    "where o.id = :id",
                            BorrowOrder.class
                    )
                    .setParameter("id", orderId)
                    .getResultList();

            BorrowOrder borrowOrder = results.isEmpty() ? null : results.get(0);
            if (borrowOrder == null) {
                throw new IllegalArgumentException("Không tìm thấy phiếu mượn.");
            }
            if (borrowOrder.getStatus() == OrderStatus.COMPLETED) {
                throw new IllegalArgumentException("Phiếu mượn này đã được trả trước đó.");
            }

            borrowOrder.setStatus(OrderStatus.COMPLETED);

            if (borrowOrder.getDetails() != null) {
                borrowOrder.getDetails().forEach(detail -> {
                    if (detail.getBook() != null) {
                        detail.getBook().setStatus(BookStatus.AVAILABLE);
                    }
                });
            }

            ReturnRecord returnRecord = borrowOrder.getReturnRecord();
            if (returnRecord == null) {
                returnRecord = new ReturnRecord();
                returnRecord.setOrder(borrowOrder);
                borrowOrder.setReturnRecord(returnRecord);
                em.persist(returnRecord);
            }
            returnRecord.setReturnDate(returnDate);

            em.merge(borrowOrder);
            em.getTransaction().commit();
            return borrowOrder;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
