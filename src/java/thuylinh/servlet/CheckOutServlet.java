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
import java.util.Random;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuylinh.booking.BookingDAO;
import thuylinh.booking.BookingDTO;
import thuylinh.carDetail.CarDTO;
import thuylinh.carDetail.CarDetailDAO;
import thuylinh.cart.CartObject;
import thuylinh.discount.DiscountDAO;
import thuylinh.discount.DiscountDTO;
import thuylinh.orderDetail.OrderDetailDAO;
import thuylinh.orderDetail.orderDetailDTO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

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
        String discountID = request.getParameter("cboDiscount");
        String url = "viewCart";
        int count = 0;
        int valueDiscount = 0;
        try {
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
            HttpSession session = request.getSession(true);
            CartObject cart = (CartObject) session.getAttribute("CUSTCART");
            String email = (String) session.getAttribute("EMAIL");
            Random random = new Random();
            int number = random.nextInt(40000);
            String bookingID = "B" + number;
            Date bookingDate = new Date();
            if (discountID.trim().equals("Choose discount code")) {
                discountID = null;
            }
            DiscountDAO discountDAO = new DiscountDAO();
            valueDiscount = discountDAO.valueDiscount(discountID);
            float total = cart.total() - ((valueDiscount * cart.total()) / 100);          
           for (CarDTO car : cart.getItems().values()) {
                CarDetailDAO carDAO = new CarDetailDAO();
                CarDTO checkCarExist = carDAO.findByCarID(car.getCarID());
                if (checkCarExist != null) {
                    int quantityNotRental = checkCarExist.getQuantity() - carDAO.getQuantityRentalByCarID(car.getCarID(), car.getRentalDate(), car.getReturnDate());    
                    if(quantityNotRental < car.getQuantity()){
                         String error = car.getCarName() + " remaining quantity " + quantityNotRental;
                        request.setAttribute("ERROR_UPDATE", error);
                        return;
                    }
                    else if(quantityNotRental >= car.getQuantity()){
                        count++;
                    }
                }
                else {
                    request.setAttribute("ERROR_UPDATE", car.getCarName() + " has been discontinued for rent");
                    cart.removeItems(car.getCarID());
                    return;
                }
           
           }
            if (count == cart.getItems().size()) {
                BookingDAO bookingDao = new BookingDAO();
                BookingDTO booking = new BookingDTO(bookingID, email, "Active", discountID, bookingDate, total);
                if (bookingDao.insertBooking(booking)) {
                    count = 1;
                    for (CarDTO car : cart.getItems().values()) {
                        String orderDetailID = bookingID + "-" + count++;
                        orderDetailDTO orderDetail = new orderDetailDTO(orderDetailID, bookingID, car.getCarID(), car.getQuantity(), car.getPrice(), car.getRentalDate(), car.getReturnDate());
                        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                        boolean result = orderDetailDAO.insertOrderDetail(orderDetail);
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String currentDate = formatter.format(date);
                    session.removeAttribute("CUSTCART");
                    List<DiscountDTO> listDiscount = discountDAO.getDiscount(email, currentDate);
                    session.setAttribute("DISCOUNT_LIST", listDiscount);
                } else {
                    request.setAttribute("ERROR_UPDATE", "Create bill failed");
                }
            }

        } catch (SQLException e) {
            log("CheckOutServlet SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("CheckOutServlet NamingException " + e.getMessage());
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
