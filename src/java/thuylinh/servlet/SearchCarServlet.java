/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import thuylinh.carDetail.CarDTO;
import thuylinh.carDetail.CarDetailDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "SearchCarServlet", urlPatterns = {"/SearchCarServlet"})
public class SearchCarServlet extends HttpServlet {

    private final int RECORD_ON_PAGE = 20;

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
        String searchCategory = request.getParameter("cboCategory");
        String searchAmount = request.getParameter("txtAmountOfCar");
        String rentalDate = request.getParameter("rentalDate");
        String returnDate = request.getParameter("returnDate");
        String url = "CAR_SEARCH";
        String p = request.getParameter("page");
        int numberOfRecord = 0;
        int pageSize;
        int offset;
        boolean flag = false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (searchCategory.trim().equals("ALL")) {
                searchCategory = "";
            }
            if (rentalDate.equals("") && returnDate.equals("")) {
                Date rental = new Date();
                rentalDate = formatter.format(rental);
                Date returndate = new Date();
                returnDate = formatter.format(returndate);
                flag = true;
            }
            if(rentalDate.equals("")||returnDate.equals("")){
                if(rentalDate.equals("")){
                request.setAttribute("DATE_ERROR", "Please choose rental date");
                }
                if(returnDate.equals("")){
                request.setAttribute("DATE_ERROR", "Please choose return date");
                }
            }
            if (!rentalDate.equals("") && !returnDate.equals("")) {
                Date date1 = formatter.parse(rentalDate);
                Date date2 = formatter.parse(returnDate);
                if (date1.compareTo(date2) > 0) {
                    request.setAttribute("DATE_ERROR", "The rental date must be greater than the return date.");
                } else {
                    int pageID = 1;
                    if (p != null) {
                        pageID = Integer.parseInt(p);
                    }
                    offset = (pageID - 1) * RECORD_ON_PAGE;
                    CarDetailDAO dao = new CarDetailDAO();
                    List<CarDTO> list = dao.searchCar(searchName, searchCategory, searchAmount, rentalDate, returnDate, offset, RECORD_ON_PAGE);
                    if (list != null) {
                        numberOfRecord = dao.getRecord(searchName, searchCategory, searchAmount, rentalDate, returnDate);
                        pageSize = (int) Math.ceil((double) numberOfRecord / RECORD_ON_PAGE);
                        request.setAttribute("PAGESIZE", pageSize);
                        request.setAttribute("currentPage", pageID);
                        request.setAttribute("SEARCH_LIST", list);
                        Map<String, Integer> quantityAvailable = new HashMap<String, Integer>();
                        for (CarDTO carDTO : list) {
                            if (!flag) {
                                CarDetailDAO carDao = new CarDetailDAO();
                                int quantityNotRental = carDTO.getQuantity() - carDao.getQuantityRentalByCarID(carDTO.getCarID(), rentalDate, rentalDate);
                                quantityAvailable.put(carDTO.getCarID(), quantityNotRental);
                            } else {
                                quantityAvailable.put(carDTO.getCarID(), carDTO.getQuantity());
                            }
                        }
                        request.setAttribute("QuantityAvailable", quantityAvailable);
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("SEARCHNAME", searchName);
                    session.setAttribute("SEARCH_CATE", searchCategory);
                    session.setAttribute("SEARCH_AMOUNT", searchAmount);
                    session.setAttribute("RENTAL_DATE", rentalDate);
                    session.setAttribute("RETURN_DATE", returnDate);
                }
            }

            ServletContext context = request.getServletContext();
            Map<String, String> index = (Map<String, String>) context.getAttribute("MAP");
            if (index != null) {
                url = index.get(url);
            }
        } catch (SQLException e) {
            log("SearchCarServlet SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("SearchCarServlet NamingException " + e.getMessage());
        } catch (ParseException e) {
            log("SearchCarServlet ParseException " + e.getMessage());
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
