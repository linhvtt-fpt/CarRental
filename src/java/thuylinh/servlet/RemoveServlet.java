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
import thuylinh.carDetail.CarDTO;
import thuylinh.cart.CartObject;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "RemoveServlet", urlPatterns = {"/RemoveServlet"})
public class RemoveServlet extends HttpServlet {

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
        String url = "viewCart";
        try {
             HttpSession session = request.getSession(false);
            if(session!=null){
                CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                if(cart!=null){
                    Map<String, CarDTO> items = cart.getItems();
                    if(items != null){
                     String[] removeListItems = request.getParameterValues("chkBox");
                     if(removeListItems != null){
                         for (String item :  removeListItems) {
                             cart.removeItems(item);
                         }
                         session.setAttribute("CUSTCART", cart);
                         int total = cart.total();
                         session.setAttribute("TOTAL", total);
                     }
                    }
                }
            }
            ServletContext context = request.getServletContext();
            Map<String,String> index = (Map<String,String>) context.getAttribute("MAP");
            if(index!=null){
                url = index.get(url);
            }
        }
       catch(Exception e){
            log("RemoveServlet Exception "+e.getMessage());
        }
        finally{
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
