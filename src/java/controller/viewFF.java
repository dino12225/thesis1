/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.FF;
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
 * @author LA
 */
public class viewFF extends HttpServlet {

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

            ArrayList<FF> proposals = new ArrayList();

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("ADLM")) {
                proposals = UserDAO.retrieveFFProposalByUnit(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())));
            }

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Chairperson")) {
                proposals = UserDAO.retrieveFFProposalByDepartment(UserDAO.getDepartmentByUserID(Integer.parseInt(session.getAttribute("userID").toString())));
            }

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Unit Head")) {
                proposals = UserDAO.retrieveFFProposalByStepUnit(2,UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())) );
            }
            
            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Dean")) {
                proposals = UserDAO.retrieveFFProposalByStepUnit(3,UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())) );
            }
            
            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Director")) {
                proposals = UserDAO.retrieveFFProposalByStepUnit(3,UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())) );
            }

            for (int i = 0; i < proposals.size(); i++) {
                if (request.getParameter("ffID" + i) != null) {
                    request.setAttribute("ffID", proposals.get(i).getId());
                }
            }

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("ADLM")) {

                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-approveFFProposal.jsp");
                dispatcher.forward(request, response);
            }

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Chairperson")) {

                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-approveFFProposal2.jsp");
                dispatcher.forward(request, response);

            }
            
            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Unit Head")) {

                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-approveFFProposal2.jsp");
                dispatcher.forward(request, response);

            }

            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Dean")) {

                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-approveFFProposal3.jsp");
                dispatcher.forward(request, response);

            }
            
            if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString()))) && session.getAttribute("position").toString().contains("Director")) {

                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-approveFFProposal3.jsp");
                dispatcher.forward(request, response);

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
