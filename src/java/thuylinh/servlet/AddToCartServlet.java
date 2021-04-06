/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuylinh.carDetail.CarDTO;
import thuylinh.carDetail.CarDetailDAO;
import thuylinh.cart.CartObject;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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
        String url = "CAR_SEARCH";
        try {
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
            HttpSession session = request.getSession(true);
            String rentalDate = (String) session.getAttribute("RENTAL_DATE");
            String returnDate = (String) session.getAttribute("RETURN_DATE");
            if (session.getAttribute("FULLNAME") == null) {
                url = "Login_Page";
                url = index.get(url);
            }
           
                CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                if (cart == null) {
                    cart = new CartObject();
                }
                String carID = request.getParameter("txtCarID").trim();
                CarDetailDAO dao = new CarDetailDAO();
                CarDTO item = dao.findByCarID(carID);

                // tinh so ngay 
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null;
                Date date2 = null;
                date1 = simpleDateFormat.parse(rentalDate);
                date2 = simpleDateFormat.parse(returnDate);
                long getDiff = date2.getTime() - date1.getTime();
                long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
                if (date1.compareTo(date2) == 0) {
                    getDaysDiff = 1;
                }
                float total = item.getPrice() * getDaysDiff;
                CarDTO car = new CarDTO(item.getCarID(), item.getCarName(), item.getColor(), item.getCategory(), rentalDate, returnDate, item.getYear(), item.getQuantity(), item.getPrice(), total);
                cart.addItemsToCart(car);
                session.setAttribute("CUSTCART", cart);
                int totalBill = cart.total();
                session.setAttribute("TOTAL", totalBill);
                String searchName = (String) session.getAttribute("SEARCHNAME");
                String searchCate = (String) session.getAttribute("SEARCH_CATE");
                String searchAmount = (String) session.getAttribute("SEARCH_AMOUNT");
                if(session.getAttribute("FULLNAME") != null){
                url = "search?"
                        + "&txtSearchName=" + searchName
                        + "&cboCategory=" + searchCate
                        + "&txtAmountOfCar=" + searchAmount
                        + "&rentalDate=" + rentalDate
                        + "&returnDate=" + returnDate;
                        }
        } catch (SQLException e) {
            log("AddToCartServlet SQL " + e.getMessage());
        } catch (NamingException e) {
            log("AddToCartServlet Naming " + e.getMessage());
        } catch (ParseException e) {
            log("AddToCartServlet ParseException " + e.getMessage());
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
