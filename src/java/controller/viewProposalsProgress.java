/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.FF;
import entity.SE;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dang
 */
public class viewProposalsProgress extends HttpServlet {

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
            ArrayList<SE> s = new ArrayList();
            s = UserDAO.retrieveALLSEProposal();

            for (int i = 0; i < s.size(); i++) {
                if (request.getParameter("viewSE" + i) != null) {
                    request.setAttribute("seID", request.getParameter("viewSE" + i));

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    dispatcher.forward(request, response);
                }
            }

            ArrayList<FF> f = new ArrayList();
            f = UserDAO.retrieveFFbyUnit(session.getAttribute("unit").toString());

            for (int i = 0; i < f.size(); i++) {
                if (request.getParameter("viewFF" + i) != null) {
                    request.setAttribute("ffID", request.getParameter("viewFF" + i));

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-viewPendingFFProgramDetails.jsp");
                    dispatcher.forward(request, response);
                }
            }
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
