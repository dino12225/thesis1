/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.SEreport;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Dino Alcala
 */

@MultipartConfig(maxFileSize = 16177215)
public class encodeSEReport3 extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            UserDAO UserDAO = new UserDAO();

            SEreport SEreport = new SEreport();
            SEreport = (SEreport) session.getAttribute("SEreport");

            SEreport.setMajorProblems(request.getParameter("problem"));
            SEreport.setOtherRecommendations(request.getParameter("recommendation"));

            InputStream inputStream1 = null;
            Part filePart1 = request.getPart("uploadphoto");

            InputStream inputStream2 = null;
            Part filePart2 = request.getPart("uploadbeneficiaries");

            InputStream inputStream3 = null;
            Part filePart3 = request.getPart("uploadparticipants");

            InputStream inputStream4 = null;
            Part filePart4 = request.getPart("uploadletters");

            if (filePart1 != null) {
                inputStream1 = filePart1.getInputStream();
            }

            if (filePart2 != null) {
                inputStream2 = filePart2.getInputStream();
            }

            if (filePart3 != null) {
                inputStream3 = filePart3.getInputStream();
            }

            if (filePart4 != null) {
                inputStream4 = filePart4.getInputStream();
            }

            SEreport.setAnnexes(inputStream1);
            SEreport.setAttendanceBeneficiaries(inputStream2);
            SEreport.setAttendanceDLSU(inputStream3);
            SEreport.setBeneficiariesLetters(inputStream4);

            session.setAttribute("SEreport", SEreport);

            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/DSA-encodeSEReport4.jsp");
            dispatcher.forward(request, response);
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
