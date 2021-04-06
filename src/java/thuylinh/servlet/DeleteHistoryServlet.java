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
import thuylinh.history.HistoryDTO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "DeleteHistoryServlet", urlPatterns = {"/DeleteHistoryServlet"})
public class DeleteHistoryServlet extends HttpServlet {

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
        String bookingID = request.getParameter("bookingID");
        String url = "searchHistory";
        String searchName = request.getParameter("txtSearchName");
        String searchDate = request.getParameter("txtBookingDate");
        try {
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
            HttpSession session = request.getSession();
            HistoryDAO historyDAO = new HistoryDAO();
            historyDAO.searchHistory(bookingID);
            List<HistoryDTO> historyDTO = historyDAO.getListSearch();
            int count = 0;
            for (HistoryDTO billDetail : historyDTO) {
                Date currentDate = new Date();
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null;
                Date date2 = null;
                String rentalDate = billDetail.getRentalDate();
                String current = simpleDateFormat.format(currentDate);
                date1 = simpleDateFormat.parse(rentalDate);
                date2 = simpleDateFormat.parse(current);
                if (date1.compareTo(date2) > 0) {
                    count++;
                } else {
                    request.setAttribute("STATUS_DELETE", "You cannot cancel "+bookingID);
                }
            }
            if (count == historyDTO.size()) {
                BookingDAO bookingDAO = new BookingDAO();
                boolean result = bookingDAO.cancelBill(bookingID);
                if (result) {
                    request.setAttribute("STATUS_DELETE", "You cancels "+bookingID+" successfully.");

                }
            }

        } catch (SQLException e) {
            log("DeleteHistoryServlet SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("DeleteHistoryServlet NamingException " + e.getMessage());
        } catch (ParseException e) {
            log("DeleteHistoryServlet ParseException " + e.getMessage());
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
