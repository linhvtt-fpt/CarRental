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
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

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
        String[] q = request.getParameterValues("txtQuantity");
        String[] key = request.getParameterValues("key");
        String url = "viewCart";
        try {
            HttpSession session = request.getSession(true);
            CartObject cart = (CartObject) session.getAttribute("CUSTCART");
            Integer[] quantity = new Integer[q.length];
            int i = 0;
            for (String str : q) {
                quantity[i] = Integer.parseInt(str.trim());
                i++;
            }
            for (int k = 0; k < quantity.length; k++) {
                CarDetailDAO carDAO = new CarDetailDAO();
                String rentalDate = cart.getItems().get(key[k]).getRentalDate();
                String returnDate = cart.getItems().get(key[k]).getReturnDate();
                float price = cart.getItems().get(key[k]).getPrice();
                CarDTO checkCarExist = carDAO.findByCarID(cart.getItems().get(key[k]).getCarID());
                if (checkCarExist != null) {
                    int quantityNotRental =  checkCarExist.getQuantity() - carDAO.getQuantityRentalByCarID(cart.getItems().get(key[k]).getCarID(), cart.getItems().get(key[k]).getRentalDate(), cart.getItems().get(key[k]).getReturnDate());
                    if (quantityNotRental < quantity[k]) {
                        String error = cart.getItems().get(key[k]).getCarName() + " remaining quantity " + quantityNotRental;
                        request.setAttribute("ERROR_UPDATE", error);
                    } else if ( quantityNotRental >= quantity[k]) {
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
                        float total = price * getDaysDiff;

                        cart.updateCart(key[k], quantity[k], total);
                    }
                } else {
                    request.setAttribute("ERROR_UPDATE", cart.getItems().get(key[k]).getCarName() + " has been discontinued for rent");
                    cart.removeItems(key[k]);
                }
            }
            session.setAttribute("CUSTCART", cart);
            int total = cart.total();
            session.setAttribute("TOTAL", total);
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
        } catch (SQLException e) {
            log("UpdateServlet SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("UpdateServlet NamingException " + e.getMessage());
        } catch (NumberFormatException e) {
            log("UpdateCartSevlet Exception " + e.getMessage());
        } catch(ParseException e){
            log("UpdateServlet ParseException "+e.getMessage());
        } 
        finally {
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
