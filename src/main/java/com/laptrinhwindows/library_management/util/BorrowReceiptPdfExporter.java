package com.laptrinhwindows.library_management.util;

import com.laptrinhwindows.library_management.model.entity.Book;
import com.laptrinhwindows.library_management.model.entity.BorrowDetail;
import com.laptrinhwindows.library_management.model.entity.BorrowOrder;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class BorrowReceiptPdfExporter {
    // Dùng lại font Unicode để khi xuất nhiều file sẽ nhanh hơn.
    private static BaseFont cachedUnicodeBaseFont;

    private BorrowReceiptPdfExporter() {
    }

    public static void export(BorrowOrder borrowOrder, File outputFile) {
        if (borrowOrder == null) {
            throw new IllegalArgumentException("Không có phiếu mượn để xuất.");
        }
        if (outputFile == null) {
            throw new IllegalArgumentException("Đường dẫn file PDF không hợp lệ.");
        }

        File parent = outputFile.getParentFile();
        // Nếu thư mục chưa tồn tại thì tạo mới trước khi ghi file.
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Không tạo được thư mục lưu file PDF.");
        }

        Document document = new Document();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(loadUnicodeBaseFont(), 18, Font.BOLD);
            Font sectionFont = new Font(loadUnicodeBaseFont(), 12, Font.BOLD);
            Font normalFont = new Font(loadUnicodeBaseFont(), 11, Font.NORMAL);

            Paragraph title = new Paragraph("PHIẾU MƯỢN SÁCH", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Mã phiếu: " + safeValue(borrowOrder.getId()), sectionFont));
            document.add(new Paragraph("Ngày mượn: " + safeValue(borrowOrder.getBorrowDate()), normalFont));
            document.add(new Paragraph("Hạn trả: " + safeValue(borrowOrder.getDueDate()), normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Thông tin sinh viên", sectionFont));
            document.add(new Paragraph(
                    "Sinh viên: " + safeValue(borrowOrder.getStudent() != null ? borrowOrder.getStudent().getFullName() : ""),
                    normalFont
            ));
            document.add(new Paragraph(
                    "Mã sinh viên: " + safeValue(borrowOrder.getStudent() != null ? borrowOrder.getStudent().getStudentCode() : ""),
                    normalFont
            ));
            document.add(new Paragraph(
                    "Lớp: " + safeValue(borrowOrder.getStudent() != null ? borrowOrder.getStudent().getClassName() : ""),
                    normalFont
            ));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Thông tin nhân viên lập phiếu", sectionFont));
            document.add(new Paragraph(
                    "Tài khoản: " + safeValue(borrowOrder.getUser() != null ? borrowOrder.getUser().getUsername() : ""),
                    normalFont
            ));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 3.4f, 2.8f, 2.2f, 2.0f});
            addHeaderCell(table, "STT");
            addHeaderCell(table, "Đầu sách");
            addHeaderCell(table, "Tác giả");
            addHeaderCell(table, "Vị trí");
            addHeaderCell(table, "Mã sách");

            int index = 1;
            // Ghi từng cuốn sách trong phiếu mượn ra bảng PDF.
            if (borrowOrder.getDetails() != null) {
                for (BorrowDetail detail : borrowOrder.getDetails()) {
                    Book book = detail.getBook();
                    String titleText = book != null && book.getBookTitle() != null ? safeValue(book.getBookTitle().getTitle()) : "";
                    String authorText = book != null && book.getBookTitle() != null ? safeValue(book.getBookTitle().getAuthor()) : "";
                    String locationText = book != null ? safeValue(book.getLocation()) : "";
                    String bookIdText = book != null ? safeValue(book.getId()) : "";

                    addBodyCell(table, String.valueOf(index++));
                    addBodyCell(table, titleText);
                    addBodyCell(table, authorText);
                    addBodyCell(table, locationText);
                    addBodyCell(table, bookIdText);
                }
            }

            document.add(new Paragraph("Danh sách sách mượn", sectionFont));
            document.add(new Paragraph(" "));
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Sinh viên vui lòng giữ phiếu này để đối chiếu khi trả sách.", normalFont));
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể xuất phiếu mượn PDF. " + resolveRootCauseMessage(ex), ex);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void openFile(File file) {
        try {
            if (file != null && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException ignored) {
        }
    }

    private static void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(loadUnicodeBaseFont(), 11, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        cell.setBackgroundColor(new java.awt.Color(230, 230, 230));
        table.addCell(cell);
    }

    private static void addBodyCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(loadUnicodeBaseFont(), 10, Font.NORMAL)));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        cell.setBorder(Rectangle.BOX);
        table.addCell(cell);
    }

    private static BaseFont loadUnicodeBaseFont() {
        if (cachedUnicodeBaseFont != null) {
            return cachedUnicodeBaseFont;
        }

        // Thử các font phổ biến trên Windows để hỗ trợ tiếng Việt có dấu.
        String[] fontCandidates = {
                "C:/Windows/Fonts/arial.ttf",
                "C:/Windows/Fonts/tahoma.ttf",
                "C:/Windows/Fonts/Times.ttf"
        };

        for (String fontPath : fontCandidates) {
            try {
                cachedUnicodeBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                return cachedUnicodeBaseFont;
            } catch (Exception ignored) {
            }
        }

        throw new IllegalStateException("Không tải được font Unicode để xuất PDF.");
    }

    private static String safeValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String resolveRootCauseMessage(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        String message = current.getMessage();
        return message == null || message.isBlank() ? "Lỗi không xác định." : message;
    }
}
