package com.example.bookshop.Helpers;

public class BasketBook {

    private String id;
    private int image;
    private String bookName;
    private String bookDes;
    private double before_discount;
    private double after_discount;
    private String countBook;
    private String favorite_Status;
    private String add_basket;

    public BasketBook(){}

    public BasketBook(String id, int image, String bookName, String bookDes, double before_discount, double after_discount, String favorite_Status,String add_basket) {
        this.id = id;
        this.image = image;
        this.bookName = bookName;
        this.bookDes = bookDes;
        this.before_discount = before_discount;
        this.after_discount = after_discount;
        this.favorite_Status = favorite_Status;
        this.add_basket = add_basket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDes() {
        return bookDes;
    }

    public void setBookDes(String bookDes) {
        this.bookDes = bookDes;
    }

    public double getBefore_discount() {
        return before_discount;
    }

    public void setBefore_discount(double before_discount) {
        this.before_discount = before_discount;
    }

    public double getAfter_discount() {
        return after_discount;
    }

    public void setAfter_discount(double after_discount) {
        this.after_discount = after_discount;
    }

    public String getCountBook() {
        return countBook;
    }

    public void setCountBook(String countBook) {
        this.countBook = countBook;
    }

    public String getFavorite_Status() {
        return favorite_Status;
    }

    public void setFavorite_Status(String favorite_Status) {
        this.favorite_Status = favorite_Status;
    }

    public String getAdd_basket() {
        return add_basket;
    }

    public void setAdd_basket(String add_basket) {
        this.add_basket = add_basket;
    }
}
