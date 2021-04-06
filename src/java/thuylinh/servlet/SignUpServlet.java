/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuylinh.account.AccountDAO;
import thuylinh.account.AccountDTO;
import thuylinh.account.CreateAccountErr;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail").trim();
        String p = request.getParameter("txtPhone").trim();
        String name = request.getParameter("txtName").trim();
        String address = request.getParameter("txtAddress").trim();
        String password = request.getParameter("txtPassword").trim();
        String confirm = request.getParameter("txtConfirm").trim();
        int phone = 0;
        String url = "SIGNUP_PAGE";
        CreateAccountErr err = new CreateAccountErr();
        boolean foundErr = false;
        try {

            if (!p.equals("")) {
                phone = Integer.parseInt(p);
            }
            String format = "\\w+@\\w+[.]\\w+([.]\\w+)?";
            if (!email.trim().matches(format)) {
                err.setEmailFormatError("Email format: linh244@fpt.edu.vn");
                foundErr = true;
            }
            if (email.trim().length() < 5 || email.trim().length() > 50) {
                err.setEmailLengError("Email requires typing from 5 to 50 chars");
                foundErr = true;
            }
            if (name.trim().length() < 3 || name.trim().length() > 50) {
                err.setNameLengError("Name requires typing from 3 to 50 chars");
                foundErr = true;
            }
            if(address.trim().length()<5){
                err.setAddressLengError("Address requires typing from 5 chars");
                foundErr = true;
            }
            if (password.trim().length() < 8 || password.trim().length() > 15) {
                err.setPasswordLengError("Password requires typing from 8 to 15 chars");
                foundErr = true;
            } else if (!password.trim().equals(confirm)) {
                err.setConfirmNotMatched("Confirm must match password");
                foundErr = true;
            }
            if (foundErr) {
                request.setAttribute("CREATE_ERROR", err);
            } else {
                Date createDate = new Date();
                AccountDAO dao = new AccountDAO();
                AccountDTO account = new AccountDTO(email, name, address, password, "NEW", phone, createDate);
                boolean result = dao.createNewAccount(account);
                if (result) {
                    HttpSession session = request.getSession();
                    session.setAttribute("EMAIL", email);
                    url = "sendEmail";
                }
            }
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }

        } catch (SQLException e) {
            log("SignUpServlet SQLException " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                err.setEmailIsExisted("This Email is existed");
                request.setAttribute("CREATE_ERROR", err);
            }
        } catch (NumberFormatException e) {
            log("SignUpServlet NumberFormatException " + e.getMessage());
            err.setPhoneIsNotNumber("Phone is number.");
            request.setAttribute("CREATE_ERROR", err);
        } catch (NamingException e) {
            log("SignUpServlet NamingException " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
