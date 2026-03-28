package com.laptrinhwindows.library_management.dao.impl;

import com.laptrinhwindows.library_management.dao.BorrowOrderDao;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;

public class BorrowOrderDaoImpl extends GenericDaoImpl<BorrowOrder> implements BorrowOrderDao {

    public BorrowOrderDaoImpl() {
        super(BorrowOrder.class);
    }
}
