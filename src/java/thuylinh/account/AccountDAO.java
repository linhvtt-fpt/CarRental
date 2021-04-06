/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.account;

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
public class AccountDAO implements Serializable{
    public boolean checkLogin(String email, String password)throws SQLException,NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            cn = DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Select Email "
                        + "From Account "
                        + "Where Email = ? AND Password = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        }
        finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return false;
    }
     public String getStatus(String email)throws SQLException,NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            cn = DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Select Status "
                        + "From Account "
                        + "Where Email = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if(rs.next()){
                    String status = rs.getString("Status");
                    return status;
                }
            }
        }
        finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return null;
    }
    public String getFullName(String email) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            cn = DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Select Name "
                        + "From Account "
                        + "Where Email = ?";
                stm = cn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if(rs.next()){
                    String fullName = rs.getString("Name").trim();
                    return fullName;
                }
            }
        }
        finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return null;
    }
    public boolean createNewAccount(AccountDTO dto) throws SQLException, NamingException{
         Connection cn = null;
        PreparedStatement stm = null;
        try{
            cn = DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Insert Into Account(Email,Phone,Name,Address,CreateDate,Password,Status) "
                        + "Values(?,?,?,?,?,?,?) ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getEmail());
                stm.setInt(2, dto.getPhone());
                stm.setString(3, dto.getName());
                stm.setString(4, dto.getAddress());
                stm.setTimestamp(5, new Timestamp(dto.getCreateDate().getTime()));
                stm.setString(6, dto.getPassword());
                stm.setString(7, dto.getStatus());
                int row = stm.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        }
        finally{
            
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return false;
    }
    public boolean updateStatus(String email) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        try{
            cn = DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Update Account "
                        + "SET Status = ? "
                        + "Where Email = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, "Active");
                stm.setString(2, email);
                int row = stm.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        }
        finally{
            
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return false;
    }
}
