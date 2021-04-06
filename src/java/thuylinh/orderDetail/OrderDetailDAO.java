/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.orderDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import thuylinh.Helper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class OrderDetailDAO implements Serializable {

    public boolean insertOrderDetail(orderDetailDTO dto) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Insert Into OrderDetail(OrderDetailID,BookingID,CarID,Quantity,Price,RentalDate,ReturnDate) "
                        + "Values(?,?,?,?,?,?,?) ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getOrderDetailID());
                stm.setString(2, dto.getBookingID());
                stm.setString(3, dto.getCarID());
                stm.setInt(4, dto.getQuantity());
                stm.setFloat(5, dto.getPrice());
                stm.setString(6, dto.getRentailDate());
                stm.setString(7, dto.getReturnDate());
                int row = stm.executeUpdate();
                if (row > 0) {
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
}
