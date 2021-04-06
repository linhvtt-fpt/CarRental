 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.discount;

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
public class DiscountDAO implements Serializable {

    private List<DiscountDTO> list;

    public List<DiscountDTO> getDiscount(String email, String currentDate) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select Discount.DiscountID, Discount.ExpiryDate, Discount.Value\n"
                        + "From Discount\n"
                        + "Where Discount.ExpiryDate >= ? AND  Discount.DiscountID NOT IN \n"
                        + "( Select Discount.DiscountID \n"
                        + "From Discount \n"
                        + "		JOIN Booking \n"
                        + "			ON Discount.DiscountID=Booking.DiscountID\n"
                        + "Where Booking.Email = ?)";
                stm = cn.prepareStatement(sql);
                stm.setString(1, currentDate);
                stm.setString(2, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String discountID = rs.getString("DiscountID");
                    String expiryDate = rs.getString("ExpiryDate");
                    int value = rs.getInt("Value");
                    DiscountDTO discount = new DiscountDTO(discountID, value, expiryDate);
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(discount);
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

    public int valueDiscount(String discountID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select Value "
                        + "From Discount "
                        + "Where DiscountID = ?";
                stm = cn.prepareStatement(sql);
                stm.setString(1, discountID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int value = rs.getInt("Value");
                    return value;
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
        return 0;
    }
}
