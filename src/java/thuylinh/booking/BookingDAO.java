/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import thuylinh.Helper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class BookingDAO implements Serializable{
    public boolean insertBooking(BookingDTO dto) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql ="Insert Into Booking(BookingID,Email,TotalPrice,Status,BookingDate,DiscountID) "
                        + "Values(?,?,?,?,?,?)";
                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getBookingID());
                stm.setString(2, dto.getEmail());
                stm.setFloat(3, dto.getTotalPrice());
                stm.setString(4, dto.getStatus());
                stm.setTimestamp(5, new Timestamp(dto.getBookingDate().getTime()));
                stm.setString(6, dto.getDiscountID());
                int row = stm.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
    public boolean cancelBill(String bookingID) throws SQLException, NamingException{
         Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Update Booking SET Status = ? "
                        + "Where BookingID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, "Inactive");
                stm.setString(2, bookingID);
                int row = stm.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
    public String getStatus(String bookingID) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select Status "
                        + "From Booking "
                        + "Where BookingID = ?";
                stm = cn.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String status = rs.getString("Status");        
                    return status;
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
     public String getTotal(String bookingID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select TotalPrice "
                        + "From Booking "
                        + "Where BookingID = ?";
                stm = cn.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String total = rs.getString("TotalPrice");        
                    return total;
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
}
