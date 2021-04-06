/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.booking;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Thuy Linh
 */
public class BookingDTO implements Serializable{
    String bookingID, email, status, discountID;
    Date bookingDate;
    float totalPrice;

    public BookingDTO(String bookingID, String email, String status, String discountID, Date bookingDate, float totalPrice) {
        this.bookingID = bookingID;
        this.email = email;
        this.status = status;
        this.discountID = discountID;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
    }

    public BookingDTO() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
