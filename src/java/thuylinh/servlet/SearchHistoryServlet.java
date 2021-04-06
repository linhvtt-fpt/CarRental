/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
import thuylinh.booking.BookingDAO;
import thuylinh.history.HistoryDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

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
        String searchName = request.getParameter("txtSearchName");
        String searchDate = request.getParameter("txtBookingDate");
        String url = "SearchHistory_PAGE";
        try {
            if(searchName==null){
                searchName ="";
            }
            if(searchDate==null){
                searchDate="";
            }
            HttpSession session = request.getSession();
            Map<String, List> listHistory = new HashMap<>();
            Map<String, String> totalPrice = new HashMap<>();
            Map<String, String> status = new HashMap<>();
            String email = (String) session.getAttribute("EMAIL");
            HistoryDAO historyDAO = new HistoryDAO();
            List<String> bookingID = historyDAO.findBookingID(email, searchName, searchDate);
            if(bookingID!=null){
                for (String str : bookingID) {
                    HistoryDAO dao = new HistoryDAO();
                    dao.searchHistory(str);
                    listHistory.put(str, dao.getListSearch());
                    BookingDAO bookingDAO = new BookingDAO();
                    String total = bookingDAO.getTotal(str);
                    totalPrice.put(str, total);
                    status.put(str, bookingDAO.getStatus(str));
                }
            }
            request.setAttribute("HISTORY", listHistory);
            request.setAttribute("TOTALPRICE", totalPrice);
            request.setAttribute("STATUS_BILL", status);
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
        } catch (SQLException e) {
            log("SearchHistoryServlet SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("SearchHistoryServlet NamingException " + e.getMessage());
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
