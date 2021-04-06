/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.orderDetail;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class orderDetailDTO implements  Serializable{
    String orderDetailID,bookingID, carID;
    int quantity;
    float price;
    String rentailDate, returnDate;

    public orderDetailDTO(String orderDetailID, String bookingID, String carID, int quantity, float price, String rentailDate, String returnDate) {
        this.orderDetailID = orderDetailID;
        this.bookingID = bookingID;
        this.carID = carID;
        this.quantity = quantity;
        this.price = price;
        this.rentailDate = rentailDate;
        this.returnDate = returnDate;
    }

    public orderDetailDTO() {
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
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

    public String getRentailDate() {
        return rentailDate;
    }

    public void setRentailDate(String rentailDate) {
        this.rentailDate = rentailDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
}
