/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.carDetail;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class CarDTO implements Serializable{
    String carID, carName, color, category;
    String rentalDate, returnDate;
    int year, quantity, quantityAvailable;
    float price, total;

    public CarDTO(String carID, String carName, String color, String category, int year, int quantity, float price) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.category = category;
        this.year = year;
        this.quantity = quantity;
        this.price = price;
    }    

    public CarDTO(String carID, String carName, String color, String category, String rentalDate, String returnDate, int year, int quantity, float price,float total) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.category = category;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.year = year;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

  

  

    public CarDTO() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
