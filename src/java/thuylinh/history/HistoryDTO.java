/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.history;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class HistoryDTO implements Serializable {
    String bookingID,orderDetailID, bookingDate, rentalDate, returnDate, carName;
    int quantity;
    float price;

    public HistoryDTO(String bookingID,String orderDetailID, String bookingDate, String rentalDate, String returnDate, int quantity, float price,String carName) {
        this.bookingID = bookingID;
        this.orderDetailID = orderDetailID;
        this.bookingDate = bookingDate;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.quantity = quantity;
        this.price = price;
        this.carName = carName;
    }

    public HistoryDTO() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }


    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    
}
