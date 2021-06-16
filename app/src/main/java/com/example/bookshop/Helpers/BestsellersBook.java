package com.example.bookshop.Helpers;

public class BestsellersBook {

    private String id;
    private int image;
    private String bookName;
    private String bookDes;
    private double salary;
    private double before;
    private String addBasket;
    private String favStatus;


    public BestsellersBook(String id, int image, String bookName, String bookDes, double salary,double before, String addBasket, String favStatus) {
        this.id = id;
        this.image = image;
        this.bookName = bookName;
        this.bookDes = bookDes;
        this.salary = salary;
        this.before = before;
        this.addBasket = addBasket;
        this.favStatus = favStatus;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAddBasket() {
        return addBasket;
    }

    public void setAddBasket(String addBasket) {
        this.addBasket = addBasket;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public double getBefore() {
        return before;
    }

    public void setBefore(double before) {
        this.before = before;
    }
}
