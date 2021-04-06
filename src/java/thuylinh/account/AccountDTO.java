/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.account;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Thuy Linh
 */
public class AccountDTO implements Serializable{
    private String email,name,address,password,status;
    private int phone;
    private Date createDate;

    public AccountDTO() {
    }

    public AccountDTO(String email, String name, String address, String password, String status, int phone, Date createDate) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
        this.status = status;
        this.phone = phone;
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    
}
