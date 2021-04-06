/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.history;

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
public class HistoryDAO implements Serializable {

    private List<String> result;

    public List<String> findBookingID(String email, String searchName, String date) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select DISTINCT Booking.BookingID \n"
                        + "From OrderDetail , Booking, CarDetail, Account \n"
                        + "Where OrderDetail.BookingID=Booking.BookingID AND Booking.Email= Account.Email AND OrderDetail.CarID=CarDetail.CarID \n"
                        + " AND Booking.Email = ? AND CarDetail.CarName LIKE ? AND Booking.BookingDate LIKE ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, "%" + searchName + "%");
                stm.setString(3, "%" + date + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    String bookingID = rs.getString("BookingID");
                    if (this.result == null) {
                        this.result = new ArrayList<>();
                    }
                    this.result.add(bookingID);
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
    private List<HistoryDTO> list;

    public List<HistoryDTO> getListSearch() {
        return list;
    }

    public void searchHistory(String bookingID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select DISTINCT OrderDetail.BookingID, OrderDetail.CarID, OrderDetail.OrderDetailID, OrderDetail.Price, OrderDetail.Quantity, OrderDetail.RentalDate, OrderDetail.ReturnDate, Booking.BookingDate, CarDetail.CarName \n"
                        + "From OrderDetail, Booking, CarDetail \n"
                        + "Where OrderDetail.BookingID = ? AND OrderDetail.BookingID=Booking.BookingID AND OrderDetail.CarID=CarDetail.CarID";
                stm = cn.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String bookingid = rs.getString("BookingID");
                    String carName = rs.getString("CarName");
                    String orderDetailID = rs.getString("OrderDetailID");
                    String bookingDate = rs.getString("BookingDate");
                    String rentalDate = rs.getString("RentalDate");
                    String returnDate = rs.getString("ReturnDate");
                    int quantity = rs.getInt("Quantity");
                    float price = rs.getFloat("Price");
                    HistoryDTO history = new HistoryDTO(bookingid, orderDetailID, bookingDate, rentalDate, returnDate, quantity, price, carName);
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(history);
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
    }

   
}
