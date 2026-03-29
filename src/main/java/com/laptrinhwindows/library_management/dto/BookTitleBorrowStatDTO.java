package com.laptrinhwindows.library_management.dto;

public class BookTitleBorrowStatDTO {
    private final Integer bookTitleId;
    private final String title;
    private final String author;
    private final Long borrowCount;

    public BookTitleBorrowStatDTO(Integer bookTitleId, String title, String author, Long borrowCount) {
        this.bookTitleId = bookTitleId;
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
    }

    public Integer getBookTitleId() {
        return bookTitleId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Long getBorrowCount() {
        return borrowCount;
    }
}
