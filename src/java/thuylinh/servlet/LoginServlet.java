/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuylinh.account.AccountDAO;
import thuylinh.carDetail.CarDetailDAO;
import thuylinh.discount.DiscountDAO;
import thuylinh.discount.DiscountDTO;
import thuylinh.recaptcha.VerifyUtils;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {


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
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String url = "Login_Page";
        boolean valid = true;
        String errorString = "";
        try {
            AccountDAO dao = new AccountDAO();
            boolean checkLogin = dao.checkLogin(email, password);
            if (!checkLogin) {
                valid = false;
                errorString = "UserName or Password invalid!";
                url ="invalid_PAGE";
            } else {
                if (valid) {
                    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                    // Verify CAPTCHA.
                    valid = VerifyUtils.verify(gRecaptchaResponse);
                    if (!valid) {
                        errorString = "Captcha invalid!";
                        url = "invalidReCaptcha_PAGE";
                    } else {
                        String status = dao.getStatus(email).trim();
                        String fullName = dao.getFullName(email);
                        CarDetailDAO carDAO = new CarDetailDAO();
                        List<String> category = carDAO.getCategory();
                        HttpSession session = request.getSession();
                        session.setAttribute("CATEGORY", category);
                        session.setAttribute("FULLNAME", fullName);
                        session.setAttribute("EMAIL", email);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String rentalDate = formatter.format(date);
                        session.setAttribute("SEARCHNAME", "");
                        session.setAttribute("SEARCH_CATE", "");
                        session.setAttribute("SEARCH_AMOUNT", "");
                        session.setAttribute("RENTAL_DATE", rentalDate);
                        session.setAttribute("RETURN_DATE", rentalDate);
                        DiscountDAO discountDAO = new DiscountDAO();
                        String currentDate = formatter.format(date);
                        List<DiscountDTO> listDiscount = discountDAO.getDiscount(email, currentDate);
                        session.setAttribute("DISCOUNT_LIST", listDiscount);
                        if (status.equals("Active")) {                           
                            url = "";
                        } else if (status.equals("NEW")) {
                            url = "sendEmail";
                        }
                    }
                }
            }
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }

        } catch (SQLException e) {
            log("LoginServlet SQLEXPRESS " + e.getMessage());
        } catch (NamingException e) {
            log("LoginServlet NamingException " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
