package com.example.chapter04_storage;

public class Book {
    private int bookNo;
    private String bookName;
    private int bookImg;
    public Book(){}

    public Book(int bookNo, String bookName, int bookImg) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.bookImg = bookImg;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookImg() {
        return bookImg;
    }

    public void setBookImg(int bookImg) {
        this.bookImg = bookImg;
    }
}
