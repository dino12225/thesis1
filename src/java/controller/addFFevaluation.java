/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.FFevaluation;
import entity.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LA
 */
public class addFFevaluation extends HttpServlet {

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
            FFevaluation FFevaluation = new FFevaluation();

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date javaDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

            FFevaluation.setDate(sqlDate);
            FFevaluation.setName(request.getParameter("name"));
            FFevaluation.setQ1(Integer.parseInt(request.getParameter("q1")));
            FFevaluation.setQ2(Integer.parseInt(request.getParameter("q2")));
            FFevaluation.setQ3(Integer.parseInt(request.getParameter("q3")));
            FFevaluation.setQ4(Integer.parseInt(request.getParameter("q4")));
            FFevaluation.setQ5(Integer.parseInt(request.getParameter("q5")));
            FFevaluation.setQ6(Integer.parseInt(request.getParameter("q6")));
            FFevaluation.setQ7(Integer.parseInt(request.getParameter("q7")));
            if (request.getParameter("q8") != null) {
                FFevaluation.setQ8(Integer.parseInt(request.getParameter("q8")));
            }
            if (request.getParameter("q9") != null) {
                FFevaluation.setQ9(Integer.parseInt(request.getParameter("q9")));
            }
            FFevaluation.setFfproposalID(Integer.parseInt(request.getParameter("ffID")));
            FFevaluation.setLearning(request.getParameter("learning"));
            FFevaluation.setMemorable(request.getParameter("memorable"));
            FFevaluation.setFeedback(request.getParameter("feedback"));

            UserDAO.AddFFevaluation(FFevaluation);
            
            if(FFevaluation.isEvaluationnotified() == false && UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100 >= 50){
                
                FFevaluation.setEvaluationnotified(true);
                DecimalFormat df = new DecimalFormat("##.##");
                
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                Notification n2 = new Notification();
                n2.setTitle("Evaluation Received");
                n2.setBody(UserDAO.getFFNameByFFID(FFevaluation.getFfproposalID()) + ": " + df.format(UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100) + "% of Attendees have now Evaluated!" + "\n"  + sdf.format(dt));
                n2.setDt(sdf2.format(dt));
                n2.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Vice President for Lasallian Mission"));
                n2.setRedirect("/MULTIPLE-viewFFResponses.jsp");
                n2.setAttribute(FFevaluation.getFfproposalID());
                UserDAO.AddNotification(n2);
                
                n2.setTitle("Evaluation Received");
                n2.setBody(UserDAO.getFFNameByFFID(FFevaluation.getFfproposalID()) + ": " + df.format(UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100) + "% of Attendees have now Evaluated!" + "\n"  + sdf.format(dt));
                n2.setDt(sdf2.format(dt));
                n2.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Executive Officer"));
                n2.setRedirect("/MULTIPLE-viewFFResponses.jsp");
                n2.setAttribute(FFevaluation.getFfproposalID());
                UserDAO.AddNotification(n2);
                
                n2.setTitle("Evaluation Received");
                n2.setBody(UserDAO.getFFNameByFFID(FFevaluation.getFfproposalID()) + ": " + df.format(UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100) + "% of Attendees have now Evaluated!" + "\n"  + sdf.format(dt));
                n2.setDt(sdf2.format(dt));
                n2.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Sir Jay Position"));
                n2.setRedirect("/MULTIPLE-viewFFResponses.jsp");
                n2.setAttribute(FFevaluation.getFfproposalID());
                UserDAO.AddNotification(n2);   
                
                n2.setTitle("Evaluation Received");
                n2.setBody(UserDAO.getFFNameByFFID(FFevaluation.getFfproposalID()) + ": " + df.format(UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100) + "% of Attendees have now Evaluated!" + "\n"  + sdf.format(dt));
                n2.setDt(sdf2.format(dt));
                n2.setUserID(UserDAO.getFFOwner(FFevaluation.getFfproposalID()));
                n2.setRedirect("/MULTIPLE-viewFFResponses.jsp");
                n2.setAttribute(FFevaluation.getFfproposalID());
                UserDAO.AddNotification(n2);   
                
                UserDAO.updateFFEvaluationPercent(FFevaluation.getFfproposalID(), UserDAO.getNumberEvaluatorsFF(FFevaluation.getFfproposalID()) / UserDAO.getNumberParticipantsFF(FFevaluation.getFfproposalID()) * 100);
            }

            request.setAttribute("successEvaluation", "You have successfully submitted the evaluation!");
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-evaluationLogIn.jsp");
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
