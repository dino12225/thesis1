/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.Notification;
import entity.SE;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class approveSE2 extends HttpServlet {

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

            if (request.getParameter("auditSE") != null) {

                session.setAttribute("auditSE", request.getParameter("auditSE"));
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-auditTrailSE.jsp");
                dispatcher.forward(request, response);
            }
            
            if (request.getParameter("approve") != null) {
                if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())))) {
                    UserDAO.updateStep(5, Integer.parseInt(request.getParameter("approve")));
                    UserDAO.approveCOSCA(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatecoscaRemarks(request.getParameter("remarks1"), Integer.parseInt(request.getParameter("approve")));
                    UserDAO.setClassificationforKRA(Integer.parseInt(request.getParameter("approve")), request.getParameter("classificationforkra"));
                    
                    String[] component;
                    ArrayList<String> components = new ArrayList();
                    SE SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getParameter("approve")));

                    component = request.getParameterValues("component");

                    if (component != null) {
                        for (int i = 0; i < component.length; i++) {
                            if (component[i].equals("Others")) {
                                components.add(request.getParameter("otherscomponent"));
                            } else {
                                components.add(component[i]);
                            }
                        }
                        SE.setSustainable(1);
                    } else {
                        SE.setSustainable(0);
                    }
                    
                    SE.setExplanation(request.getParameter("sustainabilityexplanation"));
                    SE.setComponent(components);
                    UserDAO.AddSEComponent(components, Integer.parseInt(request.getParameter("approve")), SE.getSustainable(), request.getParameter("sustainabilityexplanation"));
                    
                }

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                Notification n = new Notification();
                n.setBody("Program: " + UserDAO.getProgramName(Integer.parseInt(request.getParameter("approve"))) + "\n"  + sdf.format(dt));
                
                SE SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getParameter("approve")));
                
                //to check if submitter is part of council
                String notiftitle = "";
                boolean checkproponent1 = false;
                boolean checkproponent2 = false;
                boolean checkproponent3 = false;
                boolean checkproponent4 = false;
                boolean checkproponent5 = false;
                if(SE.getUnit().equals("Office of the Vice President for Lasallian Mission (OVPLM)")){
                    UserDAO.voteMichael(Integer.parseInt(request.getParameter("approve")), 1);
                    UserDAO.addVote(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatelmc1Remarks("N/A", "Comment", Integer.parseInt(request.getParameter("approve")));
                    SE.setRemarktype1(request.getParameter("Comment"));
                    notiftitle = "Proposal being approved by the LMC";
                    checkproponent1 = true;
                } else if(SE.getUnit().equals("Dean of Student Affairs (DSA)")){
                    UserDAO.voteNelca(Integer.parseInt(request.getParameter("approve")), 1);
                    UserDAO.addVote(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatelmc2Remarks("N/A", "Comment", Integer.parseInt(request.getParameter("approve")));
                    SE.setRemarktype2("Comment");
                    notiftitle = "Proposal being approved by the LMC";
                    checkproponent2 = true;
                } else if(SE.getUnit().equals("Laguna Campus Lasallian Mission (LCLM)")){
                    UserDAO.voteMargarita(Integer.parseInt(request.getParameter("approve")), 1);
                    UserDAO.addVote(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatelmc3Remarks("N/A", "Comment", Integer.parseInt(request.getParameter("approve")));
                    SE.setRemarktype3(request.getParameter("Comment"));
                    notiftitle = "Proposal being approved by the LMC";
                    checkproponent3 = true;
                } else if(SE.getUnit().equals("Lasallian Pastoral Office (LSPO)")){
                    UserDAO.voteMargarita(Integer.parseInt(request.getParameter("approve")), 1);
                    UserDAO.addVote(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatelmc3Remarks("N/A", "Comment", Integer.parseInt(request.getParameter("approve")));
                    SE.setRemarktype3(request.getParameter("Comment"));
                    notiftitle = "Proposal being approved by the LMC";
                    checkproponent4 = true;
                } else if(SE.getUnit().equals("Center for Social Concern and Action (COSCA)")){
                    UserDAO.voteFritzie(Integer.parseInt(request.getParameter("approve")), 1);
                    UserDAO.addVote(Integer.parseInt(request.getParameter("approve")));
                    UserDAO.updatelmc5Remarks("N/A", "Comment", Integer.parseInt(request.getParameter("approve")));
                    SE.setRemarktype5(request.getParameter("Comment"));
                    notiftitle = "Proposal being approved by the LMC";
                    checkproponent5 = true;
                }
                
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date javaDate = new java.util.Date();
                String input1 = new java.sql.Date(javaDate.getTime()).toString();
                String input2 = SE.getActualDate().toString();
                
                try {
                    java.util.Date date1 = myFormat.parse(input1);
                    java.util.Date date2 = myFormat.parse(input2);
                    long diff = date2.getTime() - date1.getTime();
                    long days = (diff / (1000*60*60*24));

                    if (days <= 14) {
                        n.setTitle("Urgent SE Proposal ready for Approval");
                    } else if (days >= 15) {
                        n.setTitle("Urgent SE Proposal ready for Approval");
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(addSE2.class.getName()).log(Level.SEVERE, null, ex);
                }

                n.setDt(sdf2.format(dt));

                if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())))) {
                    n.setUserID(UserDAO.getUserIDforNotifsPosition("LSPO - Director"));
                    if(checkproponent4 == true){
                        n.setTitle("Proposal being approved by the LMC");
                        n.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    } else {
                        n.setRedirect("/MULTIPLE-approveSEProposal4.jsp");
                    }
                    n.setAttribute(SE.getId());
                    UserDAO.AddNotification(n);
                    
                    n.setUserID(UserDAO.getUserIDforNotifsPosition("LCLM - Executive Director"));
                    if(checkproponent3 == true){
                        n.setTitle("Proposal being approved by the LMC");
                        n.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    } else {
                        n.setRedirect("/MULTIPLE-approveSEProposal4.jsp");
                    }
                    n.setAttribute(SE.getId());
                    UserDAO.AddNotification(n);
                    
                    n.setUserID(UserDAO.getUserIDforNotifsPosition("COSCA - Director"));
                    if(checkproponent5 == true){
                        n.setTitle("Proposal being approved by the LMC");
                        n.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    } else {
                        n.setRedirect("/MULTIPLE-approveSEProposal4.jsp");
                    }
                    n.setAttribute(SE.getId());
                    UserDAO.AddNotification(n);
                    
                    n.setUserID(UserDAO.getUserIDforNotifsPosition("DSA - Dean"));
                    if(checkproponent2 == true){
                        n.setTitle("Proposal being approved by the LMC");
                        n.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    } else {
                        n.setRedirect("/MULTIPLE-approveSEProposal4.jsp");
                    }
                    n.setAttribute(SE.getId());
                    UserDAO.AddNotification(n);
                    
                    n.setUserID(UserDAO.getUserIDforNotifsPosition("OVPLM - Vice President for Lasallian Mission"));
                    if(checkproponent1 == true){
                        n.setTitle("Proposal being approved by the LMC");
                        n.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    } else {
                        n.setRedirect("/MULTIPLE-approveSEProposal4.jsp");
                    }
                    n.setAttribute(SE.getId());
                    UserDAO.AddNotification(n);
                }

                Notification n2 = new Notification();
                n2.setTitle(UserDAO.getProgramName(Integer.parseInt(request.getParameter("approve"))));

                if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())))) {
                    n2.setBody(UserDAO.getProgramName(Integer.parseInt(request.getParameter("approve"))) + " has been approved by COSCA. It will now be taken to the LMC Council." + "\n"  + sdf2.format(dt));
                }

                n2.setDt(sdf2.format(dt));
                n2.setUserID(UserDAO.getSEOwner(Integer.parseInt(request.getParameter("approve"))));
                n2.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                n2.setAttribute(SE.getId());

                UserDAO.AddNotification(n2);

                request.setAttribute("successSE1", "You have successfully approved the SE Proposal!");
                
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-seProgramsForApproval.jsp");
                dispatcher.forward(request, response);
            }

            if (request.getParameter("revise") != null) {
                SE SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getParameter("revise")));
                
                Notification n3 = new Notification();
                n3.setTitle("SE Proposal for Revision");
                
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())))) {
                    UserDAO.reviseSE(Integer.parseInt(request.getParameter("revise")));
                    UserDAO.updatecoscaRemarks(request.getParameter("remarks1"), Integer.parseInt(request.getParameter("revise")));
                    UserDAO.reviseCOSCA(Integer.parseInt(request.getParameter("revise")));
                    n3.setBody(UserDAO.getProgramName(Integer.parseInt(request.getParameter("revise"))) + " has some revisions before it is approved by COSCA." + "\n"  + sdf.format(dt));
                    n3.setDt(sdf2.format(dt));
                    n3.setUserID(UserDAO.getSEOwner(Integer.parseInt(request.getParameter("revise"))));
                    n3.setRedirect("/MULTIPLE-viewPendingSEProgramDetails.jsp");
                    n3.setAttribute(SE.getId());
                }

                UserDAO.AddNotification(n3);
                request.setAttribute("reviseSE1", "Revisions has been sent to the requesting unit.");
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-seProgramsForApproval.jsp");
                dispatcher.forward(request, response);
            }

            if (request.getParameter("reject") != null) {
                SE SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getParameter("reject")));
                
                Notification n3 = new Notification();
                n3.setTitle("SE Proposal Rejected");
                
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (session.getAttribute("unit").toString().equals(UserDAO.getUnitByUserID(Integer.parseInt(session.getAttribute("userID").toString())))) {
                    UserDAO.updatecoscaRemarks(request.getParameter("remarks1"), Integer.parseInt(request.getParameter("reject")));
                    UserDAO.rejectCOSCA(Integer.parseInt(request.getParameter("reject")));
                    n3.setBody(UserDAO.getProgramName(Integer.parseInt(request.getParameter("reject"))) + " has been rejected by COSCA. Reason: " + request.getParameter("remarks1") + "\n" + sdf.format(dt));
                    n3.setDt(sdf2.format(dt));
                    n3.setUserID(UserDAO.getSEOwner(Integer.parseInt(request.getParameter("reject"))));
                    n3.setRedirect("/MULTIPLE-viewSEProgramDetails.jsp");
                    n3.setAttribute(SE.getId());
                }

                UserDAO.rejectSE(Integer.parseInt(request.getParameter("reject")));
                UserDAO.AddNotification(n3);

                request.setAttribute("rejectSE1", "You have rejected the proposal.");
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/MULTIPLE-seProgramsForApproval.jsp");
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
