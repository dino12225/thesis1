/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.Notification;
import entity.SE;
import entity.SEattendees;
import entity.SEreport;
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
public class createSEreport5 extends HttpServlet {

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

            UserDAO UserDAO = new UserDAO();
            HttpSession session = request.getSession();
            SEreport SEreport = new SEreport();
            SEreport = (SEreport) session.getAttribute("SEreport");
            SE SE = UserDAO.retrieveSEBySEID(SEreport.getSeproposalID());

            ArrayList<SEattendees> attendees = new ArrayList();

            for (int i = 0; i < Integer.parseInt(request.getParameter("countattendees")); i++) {
                SEattendees a = new SEattendees();
                a.setName(request.getParameter("attendee" + i));
                a.setEmail(request.getParameter("email" + i));
                a.setType(request.getParameter("type" + i));
                attendees.add(a);
            }

            SEreport.setAttendees(attendees);

            UserDAO.AddSEreport(SEreport);
            UserDAO.updateStep(9, SEreport.getSeproposalID());
            
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Notification n = new Notification();
            n.setBody("Program: " + UserDAO.getProgramName(SEreport.getSeproposalID()) + "\n" + sdf.format(dt));
            n.setTitle("Accomplishment Report Submitted");
            n.setDt(sdf2.format(dt));
            n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Vice President for Lasallian Mission"));
            n.setRedirect("/MULTIPLE-viewSEReport.jsp");
            n.setAttribute(SE.getId());
            UserDAO.AddNotification(n);
            
            n.setBody("Program: " + UserDAO.getProgramName(SEreport.getSeproposalID()) + "\n" + sdf.format(dt));
            n.setTitle("Accomplishment Report Submitted");
            n.setDt(sdf2.format(dt));
            n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Executive Officer"));
            n.setRedirect("/MULTIPLE-viewSEReport.jsp");
            n.setAttribute(SE.getId());
            UserDAO.AddNotification(n);
            
            n.setBody("Program: " + UserDAO.getProgramName(SEreport.getSeproposalID()) + "\n" + sdf.format(dt));
            n.setTitle("Accomplishment Report Submitted");
            n.setDt(sdf2.format(dt));
            n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Sir Jay Position"));
            n.setRedirect("/MULTIPLE-viewSEReport.jsp");
            n.setAttribute(SE.getId());
            UserDAO.AddNotification(n);

            request.setAttribute("SEreport", "You have successfully created the Accomplishment Report!");
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-socialEngagementProgramsList.jsp");
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
