package com.example.bookshop.Helpers;

public class FavoriteBook {

    private  String id;
    private  int image;
    private  String bookName;
    private  String bookDes;
    private  double before_discount;
    private  double after_discount;
    private  String addBasket;
    private  String favStatus;

    public FavoriteBook(String id, int image, String bookName, String bookDes, double before_discount, double after_discount, String addBasket, String favStatus) {
        this.id = id;
        this.image = image;
        this.bookName = bookName;
        this.bookDes = bookDes;
        this.before_discount = before_discount;
        this.after_discount = after_discount;
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
}
