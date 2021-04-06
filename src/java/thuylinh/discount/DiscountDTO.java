/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.discount;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class DiscountDTO implements Serializable{
    String discountID;
    int value;
    String expiryDate;

    public DiscountDTO() {
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DiscountDTO(String discountID, int value, String expiryDate) {
        this.discountID = discountID;
        this.value = value;
        this.expiryDate = expiryDate;
    }

   
}
