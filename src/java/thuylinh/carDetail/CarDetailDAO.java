/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.carDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thuylinh.Helper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class CarDetailDAO implements Serializable {

    private List<String> result;

    public List<String> getCategory() throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select Distinct Category "
                        + "From CarDetail ";
                stm = cn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("Category");
                    if (this.result == null) {
                        this.result = new ArrayList<>();
                    }
                    this.result.add(category);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return this.result;
    }
    private List<CarDTO> list;

    public List<CarDTO> searchCar(String name, String category, String quantity, String rentalDate, String returnDate, int offset, int recordOnPage) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                if (quantity.equals("")) {
                    quantity = "0";
                }
                String sql = "SELECT CarDetail.CarID, CarDetail.CarName, CarDetail.Color, CarDetail.Category,CarDetail.Year, CarDetail.Price, CarDetail.Quantity\n"
                        + "FROM CarDetail\n"
                        + "WHERE CarDetail.Quantity >= ? AND CarDetail.CarName LIKE ? AND CarDetail.Category LIKE ? AND CarDetail.CarID NOT IN \n"
                        + "(\n"
                        + "    SELECT CarDetail.CarID \n"
                        + "    FROM    CarDetail \n"
                        + "            JOIN OrderDetail\n"
                        + "               ON CarDetail.CarID=OrderDetail.CarID \n"
                        + " JOIN Booking ON Booking.BookingID=OrderDetail.BookingID "
                        + "    WHERE  \n "
                        + " CarDetail.Quantity - OrderDetail.Quantity <= 0  AND "
                        + " Booking.Status = 'Active' AND \n" 
                        + "  (( OrderDetail.RentalDate  <= ? AND OrderDetail.ReturnDate >= ?) \n"
                        + "           OR (OrderDetail.RentalDate < ? AND OrderDetail.ReturnDate >= ? ) \n"
                        + "           OR (? <= OrderDetail.RentalDate AND ? >= OrderDetail.ReturnDate)  \n"
                        + "))"
                        + "Order by CarDetail.Year\n"
                        + "OFFSET ? rows\n"
                        + "FETCH next ? rows only";
                stm = cn.prepareStatement(sql);
                stm.setString(1, quantity);
                stm.setString(2, "%" + name + "%");
                stm.setString(3, "%" + category + "%");
                stm.setString(4, rentalDate);
                stm.setString(5, rentalDate);
                stm.setString(6, returnDate);
                stm.setString(7, returnDate);
                stm.setString(8, rentalDate);
                stm.setString(9, returnDate);
                stm.setInt(10, offset);
                stm.setInt(11, recordOnPage);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString("CarID");
                    String carName = rs.getString("CarName");
                    String color = rs.getString("Color");
                    String Category = rs.getString("Category");
                    int year = rs.getInt("Year");
                    float price = rs.getFloat("Price");
                    int Quantity = rs.getInt("Quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, Category, year, Quantity, price);
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return this.list;
    }

    public int getRecord(String name, String category, String quantity, String rentalDate, String returnDate) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                if (quantity.equals("")) {
                    quantity = "0";
                }
                String sql = "SELECT CarDetail.CarID, CarDetail.CarName, CarDetail.Color, CarDetail.Category,CarDetail.Year, CarDetail.Price, CarDetail.Quantity\n"
                        + "FROM CarDetail\n"
                        + "WHERE CarDetail.Quantity >= ? AND CarDetail.CarName LIKE ? AND CarDetail.Category LIKE ? AND CarDetail.CarID NOT IN \n"
                        + "(\n"
                        + "    SELECT CarDetail.CarID \n"
                        + "    FROM    CarDetail \n"
                        + "            JOIN OrderDetail\n"
                        + "               ON CarDetail.CarID=OrderDetail.CarID\n "
                        + " JOIN Booking ON Booking.BookingID=OrderDetail.BookingID "
                        + "    WHERE  \n"
                        + " CarDetail.Quantity - OrderDetail.Quantity <= 0  AND "
                        + " Booking.Status ='Active' \n"
                        + "    AND( ( OrderDetail.RentalDate  <= ? AND OrderDetail.ReturnDate >= ?) \n"
                        + "   OR (OrderDetail.RentalDate < ? AND OrderDetail.ReturnDate >= ? ) \n"
                        + "   OR (? <= OrderDetail.RentalDate AND ? >= OrderDetail.ReturnDate) ) \n"
                        + ")"
                        + "Order by CarDetail.Year\n";
                stm = cn.prepareStatement(sql);
                stm.setString(1, quantity);
                stm.setString(2, "%" + name + "%");
                stm.setString(3, "%" + category + "%");
                stm.setString(4, rentalDate);
                stm.setString(5, rentalDate);
                stm.setString(6, returnDate);
                stm.setString(7, returnDate);
                stm.setString(8, rentalDate);
                stm.setString(9, returnDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    count++;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return count;
    }

    public CarDTO findByCarID(String carID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select CarID, CarName, Color, Year, Price, Category, Quantity "
                        + "From CarDetail "
                        + "Where CarID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, carID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String carName = rs.getString("CarName");
                    String color = rs.getString("Color");
                    String Category = rs.getString("Category");
                    int year = rs.getInt("Year");
                    float price = rs.getFloat("Price");
                    int Quantity = rs.getInt("Quantity");
                    CarDTO dto = new CarDTO(carID, carName, color, Category, year, Quantity, price);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }


    public int getQuantityRentalByCarID(String carID, String rentalDate, String returnDate) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int rentalQuantity = 0;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "SELECT OrderDetail.Quantity, CarDetail.CarID\n"
                        + "FROM    CarDetail\n"
                        + "     JOIN OrderDetail\n"
                        + "     ON CarDetail.CarID=OrderDetail.CarID\n "
                        + " JOIN Booking ON Booking.BookingID = OrderDetail.BookingID "
                        + "WHERE "
                        + "   CarDetail.CarID = ? AND Booking.Status='Active' \n"
                        + "    AND ( ( OrderDetail.RentalDate  <= ? AND OrderDetail.ReturnDate >= ?) \n"
                        + "    OR (OrderDetail.RentalDate < ? AND OrderDetail.ReturnDate >= ? ) \n"
                        + "    OR (? <= OrderDetail.RentalDate AND ? >= OrderDetail.ReturnDate) ) ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, carID);
                stm.setString(2, rentalDate);
                stm.setString(3, rentalDate);
                stm.setString(4, returnDate);
                stm.setString(5, returnDate);
                stm.setString(6, rentalDate);
                stm.setString(7, returnDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int quantity = rs.getInt("Quantity");
                    rentalQuantity = rentalQuantity + quantity;                   
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return rentalQuantity;
    }
    public int getQuantityByCarID(String carID)throws SQLException, NamingException{
          Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
         int quantity = 0;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select CarID, Quantity "
                        + "From CarDetail "
                        + "Where CarID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, carID);
                rs = stm.executeQuery();
               if (rs.next()) {
                    quantity = rs.getInt("Quantity");
                                
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return quantity  ;
    }
}
