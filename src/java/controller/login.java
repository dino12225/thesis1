/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.Notification;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
public class login extends HttpServlet {

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

            User u = new User();

            u.setUsername(request.getParameter("username"));
            u.setPassword(request.getParameter("password"));

            HttpSession session = request.getSession();
         
            if (UserDAO.Login(u)) {
                if (UserDAO.isAdmin(u.getUsername())) {
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Admin");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/ADMIN-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isLMD(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Lasallian Mission Director (LMD)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/LMD-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isLCLM(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Laguna Campus Lasallian Mission (LCLM)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/LCLM-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isDSA(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Dean of Student Affairs (DSA)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/DSA-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isOPMD(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Office of Personnel Management Director (OPMD)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/OPMD-home.jsp");
                    dispatcher.forward(request, response);
                }  else if (UserDAO.isLMC(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Lasallian Mission Council (LMC)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/LMC-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isADEALM(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/ADEALM-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isDeptChair(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isUnitChair(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home.jsp");
                    dispatcher.forward(request, response);
                    
                } else if (UserDAO.isSEDirector(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home.jsp");
                    dispatcher.forward(request, response);
                    
                } else if (UserDAO.isDean(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isVpVc(u.getUsername())){
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home.jsp");
                    dispatcher.forward(request, response);
                    
                } else if (UserDAO.isAssistantDeanforLM(u.getUsername())) {
                    
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home2.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isChairperson(u.getUsername())) {
                    
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home2.jsp");
                    dispatcher.forward(request, response);
                }  else if (UserDAO.isUnitHead(u.getUsername())) {
                    
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home2.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isDirector(u.getUsername())) {
                    
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/SIGNATORIES-home2.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isCOSCA(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Center for Social Concern and Action (COSCA)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/COSCA-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isLSPO(u.getUsername())) {

                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Lasallian Pastoral Office (LSPO)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/LSPO-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isOVPLM(u.getUsername())) {
                    if(UserDAO.getLatestBudget().getRemainingBudget() <= 400000 && UserDAO.getLatestBudget().getRemainingBudget() <= 300000){
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        Notification n = new Notification();
                        n.setTitle("Budget Low");
                        n.setBody("Overall Remaining Budget is now at: " + UserDAO.getLatestBudget().getRemainingBudget());
                        n.setDt(sdf2.format(dt));
                        n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Vice President for Lasallian Mission"));
                        n.setRedirect("/MULTIPLE-viewBudget.jsp");
                        n.setAttribute(0);
                        UserDAO.AddNotification(n);
                        
                        n.setTitle("Budget Low");
                        n.setBody("Overall Remaining Budget is now at: " + UserDAO.getLatestBudget().getRemainingBudget());
                        n.setDt(sdf2.format(dt));
                        n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Executive Officer"));
                        n.setRedirect("/MULTIPLE-viewBudget.jsp");
                        n.setAttribute(0);
                        UserDAO.AddNotification(n);
                        
                        n.setTitle("Budget Low");
                        n.setBody("Overall Remaining Budget is now at: " + UserDAO.getLatestBudget().getRemainingBudget());
                        n.setDt(sdf2.format(dt));
                        n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Sir Jay Position"));
                        n.setRedirect("/MULTIPLE-viewBudget.jsp");
                        n.setAttribute(0);
                        UserDAO.AddNotification(n);
                    }
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", "Office of the Vice President for Lasallian Mission (OVPLM)");
                    session.setAttribute("position", position);

                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/OVPLM-home.jsp");
                    dispatcher.forward(request, response);
                } else if (UserDAO.isUnit(u.getUsername(), u.getPassword())) {
                    
                    int id = UserDAO.getIDbyUsername(u.getUsername());
                    String position = UserDAO.getPosition(u.getUsername());
                    session.setAttribute("userID", id);
                    session.setAttribute("unit", UserDAO.getUnitByUserID(id));
                    session.setAttribute("position", position);
                    
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/UR-home.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
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
