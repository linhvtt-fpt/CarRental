/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

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
        String button = request.getParameter("btnAction");
        String url = "viewCart";
        try {
            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
            if (button.trim().equals("Remove")) {
                url = "remove";
                url = index.get(url);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            if (button.trim().equals("Check out")) {
                url = "checkOut";
                url = index.get(url);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            if (button.trim().equals("Update Cart")) {
                url = "update";
                url = index.get(url);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            if (button.trim().equals("Add Items To Your Cart")) {
                HttpSession session = request.getSession();
                String searchName = (String) session.getAttribute("SEARCHNAME");
                String searchCate = (String) session.getAttribute("SEARCH_CATE");
                String searchAmount = (String) session.getAttribute("SEARCH_AMOUNT");
                String rentalDate = (String) session.getAttribute("RENTAL_DATE");
                String returnDate = (String) session.getAttribute("RETURN_DATE");
                url = "search?"
                        + "&txtSearchName="+searchName
                        + "&cboCategory="+searchCate
                        + "&txtAmountOfCar="+searchAmount
                        +"&rentalDate="+rentalDate
                        +"&returnDate="+returnDate;
                response.sendRedirect(url);
            }
            

        } finally {
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
