/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.Measure;
import entity.SEreport;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
 * @author Dino Alcala
 */
public class encodeSEReport extends HttpServlet {

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

            SEreport SEreport = new SEreport();

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date javaDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

            SEreport.setProjectTitle(request.getParameter("name"));
            SEreport.setProgramHead(request.getParameter("programhead"));
            SEreport.setDate(sqlDate);
            SEreport.setTargetKRA(request.getParameter("kra"));
            SEreport.setTargetGoal(request.getParameter("goal"));
            SEreport.setProjectProponent(request.getParameter("proponents"));
            SEreport.setNumberOfBeneficiaries(Integer.parseInt(request.getParameter("number")));
            SEreport.setProjectBeneficiaries(request.getParameter("projben"));
            SEreport.setAddressBeneficiaries(request.getParameter("addressben"));
            SEreport.setImplementationdate(Date.valueOf(request.getParameter("implementationdate")));
            SEreport.setAddressOfProject(request.getParameter("addressproj"));
            SEreport.setCap(Integer.parseInt(request.getParameter("number0")));
            SEreport.setApsp(Integer.parseInt(request.getParameter("number1")));
            SEreport.setAsf(Integer.parseInt(request.getParameter("number2")));
            SEreport.setFaculty(Integer.parseInt(request.getParameter("number3")));
            SEreport.setAdmin(Integer.parseInt(request.getParameter("number4")));
            SEreport.setDirecthired(Integer.parseInt(request.getParameter("number5")));
            SEreport.setIndependent(Integer.parseInt(request.getParameter("number6")));
            SEreport.setExternal(Integer.parseInt(request.getParameter("number7")));
            SEreport.setGraduate(Integer.parseInt(request.getParameter("number8")));
            SEreport.setUndergraduate(Integer.parseInt(request.getParameter("number9")));
            SEreport.setAlumni(Integer.parseInt(request.getParameter("number10")));
            SEreport.setParents(Integer.parseInt(request.getParameter("number11")));
            SEreport.setAmountReceivedOVPLM(Double.parseDouble(request.getParameter("source")));
            SEreport.setSeproposalID(Integer.parseInt(request.getParameter("seID")));
            SEreport.setGsheets(request.getParameter("gsheets"));
            
            ArrayList<Measure> targetmeasures = new ArrayList();
            UserDAO UserDAO = new UserDAO();
            
            ArrayList<Integer> measuresid = new ArrayList();
            measuresid = UserDAO.GetMeasures(Integer.parseInt(request.getParameter("seID")));
            
            for(int x = 0 ; x < measuresid.size() ; x++){
                targetmeasures.add(UserDAO.GetMeasureObject(x));
            }
            
            SEreport.setTargetmeasures(targetmeasures);

            session.setAttribute("SEreport", SEreport);
            request.setAttribute("seID", request.getParameter("seID"));
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/DSA-encodeSEReport2.jsp");
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
