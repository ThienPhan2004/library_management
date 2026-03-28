package com.laptrinhwindows.library_management.service.impl;

import com.laptrinhwindows.library_management.dao.BorrowOrderDao;
import com.laptrinhwindows.library_management.dao.impl.BorrowOrderDaoImpl;
import com.laptrinhwindows.library_management.service.BorrowOrderService;

public class BorrowOrderServiceImpl implements BorrowOrderService {
    private final BorrowOrderDao borrowOrderDao;

    public BorrowOrderServiceImpl() {
        this.borrowOrderDao = new BorrowOrderDaoImpl();
    }

    public BorrowOrderDao getBorrowOrderDao() {
        return borrowOrderDao;
    }
}
