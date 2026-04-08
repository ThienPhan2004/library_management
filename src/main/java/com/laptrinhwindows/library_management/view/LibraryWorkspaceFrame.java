package com.laptrinhwindows.library_management.view;

import com.laptrinhwindows.library_management.view.manager.BookManagementPanel;
import com.laptrinhwindows.library_management.view.manager.BookTitleManagementPanel;
import com.laptrinhwindows.library_management.view.manager.ReportPanel;
import com.laptrinhwindows.library_management.view.manager.SupplierManagementPanel;
import com.laptrinhwindows.library_management.view.shared.StudentManagementPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowOrderPanel;
import com.laptrinhwindows.library_management.view.staff.BorrowTrackingPanel;
import com.laptrinhwindows.library_management.view.staff.ReturnBookPanel;

public interface LibraryWorkspaceFrame {
    StudentManagementPanel getStudentManagementPanel();

    BookTitleManagementPanel getBookTitleManagementPanel();

    BookManagementPanel getBookManagementPanel();

    BorrowOrderPanel getBorrowOrderPanel();

    ReturnBookPanel getReturnBookPanel();

    BorrowTrackingPanel getBorrowTrackingPanel();

    SupplierManagementPanel getSupplierManagementPanel();

    ReportPanel getReportPanel();

    void setStatusMessage(String message);

    void setCurrentUserInfo(String username, Integer roleId, String roleName);
}
