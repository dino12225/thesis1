/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FF;
import entity.Goal;
import entity.KRA;
import entity.Measure;
import entity.SE;
import entity.Unit;
import entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dino Alcala
 */
public class TargetDAO {

    public double calculateTarget(Measure m, ArrayList<Integer> totals) {
        UserDAO UserDAO = new UserDAO();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "";

        int totalunits = totals.get(0);
        int totalstudentorgs = totals.get(1);
        int totalfacultydept = totals.get(2);
        int totalstaff = totals.get(3);
        int totalcap = totals.get(4);
        int totalapsp = totals.get(5);
        int totalasf = totals.get(6);
        int totalfaculty = totals.get(7);
        int totaladmin = totals.get(8);
        int totalgrad = totals.get(9);
        int totalundergrad = totals.get(10);
        int totalinternationalstudents = totals.get(11);
        int totaldepts = totals.get(12);
        int totalalumni = totals.get(13);
        double count = 0;
        double percent = 0;

        if (m.getTypetarget().equals("Faith Formation")) {
            //for ff
            //se programs per units - n/a
            //no specified unit - yes
            //student orgs - yes
            //depts - yes
            //faculty depts - yes
            //staff - yes
            //cap - yes
            //apsp
            //asf
            //faculty
            //admin
            //grad
            //undergrad
            //international - yes
            //alumni
            //social engagement - n/a

            //
            //FOR FAITH FORMATION
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<FF> FF = UserDAO.retrieveALLFFProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(f.id) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Alumni') AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Parent') AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('International') AND f.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION 
            //UNIT TARGET: FACULTY DEPT
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }

            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
            //
            //START OF
            //SOCIAL ENGAGEMENT
            //
        } else if (m.getTypetarget().equals("Social Engagement")) {
            //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }
                    
                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND a.type IN ('International') AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.classification = 1 AND s.step = 9 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                return -1;
            }
        } //
        //START OF
        //INTERDISCIPLINARY FORA
        //
        else if (m.getTypetarget().equals("Interdisciplinary Fora")) {
            //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) FROM seproposal s WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ? AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ? AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ? AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    for (int i = 0; i < units.size(); i++) {
                        double temppercent = 0;

                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, units.get(i).getName());
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            count = rs.getInt("count(id)");
                        }

                        if (count != 0) {
                            temppercent = ((double) count / (double) UserDAO.countTotalProposalByUnit(units.get(i).getName()) * 100);
                        }

                        if (temppercent >= m.getNumtarget()) {
                            overallCount += 1;
                        }
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = (double) overallCount / (double) totalunits * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = overallCount * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.id = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) UserDAO.retrieveALLSEProposal().size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF 
        //SUSTAINABLE SE
        //
        else if (m.getTypetarget().equals("Sustainable SE")) {
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) FROM seproposal s WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ? AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ? AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ? AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    for (int i = 0; i < units.size(); i++) {
                        double temppercent = 0;

                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, units.get(i).getName());
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            count = rs.getInt("count(id)");
                        }

                        if (count >= 1) {
                            temppercent = (count / UserDAO.countTotalProposalByUnit(units.get(i).getName())) * 100;
                        }

                        if (temppercent >= m.getNumtarget()) {
                            overallCount += 1;
                        }
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = (double) overallCount / (double) totalunits * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = overallCount * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.sustainable = 1 AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: FACULTY 
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) as count FROM seproposal s WHERE s.step = 9 AND s.sustainable = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Alumni')";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) SE.size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SDGLP
        //
        else if (m.getTypetarget().equals("Sustainable Development Goals Localization Project")) {
            //
            //FOR SDGLP
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ? AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ? AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ? AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    for (int i = 0; i < units.size(); i++) {
                        double temppercent = 0;

                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, units.get(i).getName());
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            count = rs.getInt("count(id)");
                        }

                        if (count != 0) {
                            temppercent = ((double) count / (double) UserDAO.countTotalProposalByUnit(units.get(i).getName()) * 100);
                        }

                        if (temppercent >= m.getNumtarget()) {
                            overallCount += 1;
                        }
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = (double) overallCount / (double) totalunits * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = overallCount * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return -1;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) as count FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Alumni')";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None'";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) SE.size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SERVICE-LEARNING
        //
        else if (m.getTypetarget().equals("Service-Learning")) {
            //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ? AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ? AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ? AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    for (int i = 0; i < units.size(); i++) {
                        double temppercent = 0;

                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, units.get(i).getName());
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            count = rs.getInt("count(id)");
                        }

                        if (count != 0) {
                            temppercent = ((double) count / (double) UserDAO.countTotalProposalByUnit(units.get(i).getName()) * 100);
                        }

                        if (temppercent >= m.getNumtarget()) {
                            overallCount += 1;
                        }
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = (double) overallCount / (double) totalunits * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = overallCount * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal WHERE s.step = 9 AND s.activityClassification = 'Service-Learning'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.id = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) UserDAO.retrieveALLSEProposal().size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        }
        if (query.equals("")) {
            return -1;
        }
        return -1;
    }
    
    public double calculateTargetCollege(Measure m, ArrayList<Integer> totals, String unit) {
        UserDAO UserDAO = new UserDAO();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "";

        int totalunits = totals.get(0);
        int totalstudentorgs = totals.get(1);
        int totalfacultydept = totals.get(2);
        int totalstaff = totals.get(3);
        int totalcap = totals.get(4);
        int totalapsp = totals.get(5);
        int totalasf = totals.get(6);
        int totalfaculty = totals.get(7);
        int totaladmin = totals.get(8);
        int totalgrad = totals.get(9);
        int totalundergrad = totals.get(10);
        int totalinternationalstudents = totals.get(11);
        int totaldepts = totals.get(12);
        int totalalumni = totals.get(13);
        double count = 0;
        double percent = 0;

        if (m.getTypetarget().equals("Faith Formation")) {
            //for ff
            //se programs per units - n/a
            //no specified unit - yes
            //student orgs - yes
            //depts - yes
            //faculty depts - yes
            //staff - yes
            //cap - yes
            //apsp
            //asf
            //faculty
            //admin
            //grad
            //undergrad
            //international - yes
            //alumni
            //social engagement - n/a

            //
            //FOR FAITH FORMATION
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<FF> FF = UserDAO.retrieveALLFFProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(f.id) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(f.id)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg = 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Alumni') AND f.studentorg = 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Parent') AND f.studentorg = 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('International') AND f.studentorg = 1 AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1 AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION 
            //UNIT TARGET: FACULTY DEPT
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1 AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1 AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }

            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'APSP' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'ASF' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Faculty' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Admin' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Grad' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Undergrad' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'International' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Alumni') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Alumni' AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'Parent') AND f.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'International') AND f.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
            //
            //START OF
            //SOCIAL ENGAGEMENT
            //
        } else if (m.getTypetarget().equals("Social Engagement")) {
            //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            } 
            //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }
                    
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'APSP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'APSP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'ASF' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'ASF' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Faculty' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Faculty' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Admin' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Admin' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Grad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Grad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Undergrad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Undergrad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'International' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'International' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Alumni' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.classification = 1 AND s.step = 9 AND a.type = 'Alumni' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                return -1;
            }
        } //
        //START OF
        //INTERDISCIPLINARY FORA
        //
        else if (m.getTypetarget().equals("Interdisciplinary Fora")) {
            //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'CAP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'APSP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'APSP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'ASF' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'ASF' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Faculty' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Faculty' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Admin' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Admin' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Grad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Grad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Undergrad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Undergrad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'International' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.id = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'International' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Alumni' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Alumni' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) UserDAO.retrieveALLSEProposal().size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF 
        //SUSTAINABLE SE
        //
        else if (m.getTypetarget().equals("Sustainable SE")) {
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            }
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.sustainable = 1 AND s.step = 9 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'CAP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'APSP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'APSP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'ASF' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'ASF' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: FACULTY 
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Faculty' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Faculty', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Faculty' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Admin' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Admin' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Grad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Parents') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Grad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Undergrad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Undergrad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'International' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'International' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Alumni' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Alumni' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) as count FROM seproposal s WHERE s.step = 9 AND s.sustainable = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) SE.size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SDGLP
        //
        else if (m.getTypetarget().equals("Sustainable Development Goals Localization Project")) {
            //
            //FOR SDGLP
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            } //
            //FOR SDGLP
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return -1;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'CAP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'APSP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'APSP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'ASF' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'ASF' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Faculty' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Faculty' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Admin' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Admin' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaladmin) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Grad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Grad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Undergrad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Undergrad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'International' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'International' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Alumni' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Alumni' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(id) as count FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(id) as count FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.unit = ?";
                }
                try {

                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) SE.size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SERVICE-LEARNING
        //
        else if (m.getTypetarget().equals("Service-Learning")) {
            //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return -1;
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(s.id)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    return percent = count * 100 / m.getNumtarget();

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg = 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstudentorgs) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totaldepts) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(department)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1 AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfacultydept) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalstaff) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'CAP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'APSP' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'APSP' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalapsp) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'ASF' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'ASF' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalasf) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Faculty' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Faculty' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalfaculty) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Admin' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Admin' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalcap) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Grad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Grad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalgrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Undergrad' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Undergrad' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalundergrad) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'International' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.id = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'International' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalinternationalstudents) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Alumni' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return -1;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Alumni' AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) totalalumni) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.unit = ?";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT count(distinct(a.name)) as count FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND s.unit = ?";
                }
                try {
                    if (query.equals("")) {
                        return -1;
                    }

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, unit);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        count = rs.getInt("count");
                    }

                    if (m.getNumtypetarget().equals("Percent")) {
                        return percent = ((double) count / (double) UserDAO.retrieveALLSEProposal().size()) * 100;
                    } else if (m.getNumtypetarget().equals("Count")) {
                        return percent = count * 100 / m.getNumtarget();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        }
        if (query.equals("")) {
            return -1;
        }
        return -1;
    }

    public ArrayList<Integer> getPrograms(Measure m) {
        UserDAO UserDAO = new UserDAO();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "";
        ArrayList<Integer> ids = new ArrayList();

        if (m.getTypetarget().equals("Faith Formation")) {
            //for ff
            //se programs per units - n/a
            //no specified unit - yes
            //student orgs - yes
            //depts - yes
            //faculty depts - yes
            //staff - yes
            //cap - yes
            //apsp
            //asf
            //faculty
            //admin
            //grad
            //undergrad
            //international - yes
            //alumni
            //social engagement - n/a

            //
            //FOR FAITH FORMATION
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<FF> FF = UserDAO.retrieveALLFFProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT f.id FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Alumni') AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('Parent') AND f.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE a.type IN ('International') AND f.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION 
            //UNIT TARGET: FACULTY DEPT
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID WHERE f.step = 9 AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Alumni') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('Parent') AND f.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN ('International') AND f.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }

            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('CAP', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('APSP', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('ASF', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Faculty', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Admin', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Grad', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Undergrad', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR FAITH FORMATION
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(f.id) FROM ffproposal f JOIN ffreport ff ON f.id = ff.ffproposalID JOIN ffreport_attendees a ON ff.id = a.ffreportID WHERE f.step = 9 AND a.type IN('Alumni', 'International')";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("f.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
            //
            //START OF
            //SOCIAL ENGAGEMENT
            //
        } else if (m.getTypetarget().equals("Social Engagement")) {
            //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                return ids;
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND a.type IN ('International') AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE s.step = 9 AND c.international = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: FACULTY
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SOCIAL ENGAGEMENT
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                return ids;
            }
        } //
        //START OF
        //INTERDISCIPLINARY FORA
        //
        else if (m.getTypetarget().equals("Interdisciplinary Fora")) {
            //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                }
                try {

                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.activityClassification = 'Interdisciplinary Fora' AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: DEPTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;

                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International') AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'Parents')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR INTERDISCIPLINARY FORA
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Interdisciplinary Fora' AND c.international = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF 
        //SUSTAINABLE SE
        //
        else if (m.getTypetarget().equals("Sustainable SE")) {
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.sustainable = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
            //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.sustainable = 1 AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.sustainable = 1 AND s.step = 9";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.sustainable = 1 AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SUSTAINABLE SE
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.sustainable = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Alumni')";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.sustainable = 1 AND a.type IN('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.sustainable = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SDGLP
        //
        else if (m.getTypetarget().equals("Sustainable Development Goals Localization Project")) {
            //
            //FOR SDGLP
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None'AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON se.seproposalID = s.id JOIN sereport_attendees a ON a.sereportID = se.id WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND s.studentorg != 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: STAFF
            //
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.classificationforkra != 'None' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SDGLP
            //UNIT TARGET: SOCIAL ENGAGEMENT PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                int overallCount = 0;
                double total;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Alumni')";
                } else if (m.getEngagingtarget().equals("Parent")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.classificationforkra != 'None' AND a.type IN('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.classificationforkra != 'None'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        } //
        //START OF
        //SERVICE-LEARNING
        //
        else if (m.getTypetarget().equals("Service-Learning")) {
            //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS PER UNIT
            //
            if (m.getUnittarget().equals("SE Programs Per Unit")) {
                ArrayList<Unit> units = UserDAO.retrieveUnits();
                int overallCount = 0;

                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s WHERE s.step = 9 AND s.classificationforkra != 'None'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: NO SPECIFIED UNIT
            //
            else if (m.getUnittarget().equals("No Specified Unit") && m.getNumtypetarget().equals("Count")) {
                ArrayList<SE> SE = UserDAO.retrieveALLSEProposal();
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal WHERE s.step = 9 AND s.activityClassification = 'Service-Learning'";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.activityClassification = 'Service-Learning' AND s.step = 9 AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN community c ON s.targetCommunity = c.communityID WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STUDENT ORGS
            //
            else if (m.getUnittarget().equals("Student Organizations")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg = 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: DEPARTMENTS
            //
            else if (m.getUnittarget().equals("Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY DEPTS
            //
            else if (m.getUnittarget().equals("Faculty Departments")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International') AND s.studentorg != 1";
                } else if (m.getEngagingtarget().equals("International Communities")) {

                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: STAFF
            //dito
            else if (m.getUnittarget().equals("Staff")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Parent') OR a.type IN('APSP', Parent') OR a.type IN('ASF', Parent') OR a.type IN('Faculty', Parent') OR a.type IN('Admin', Parent') OR a.type IN('Directhired', Parent') OR a.type IN('Independent', Parent') OR a.type IN('External', Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', Alumni') OR a.type IN('APSP', Alumni') OR a.type IN('ASF', Alumni') OR a.type IN('Faculty', Alumni') OR a.type IN('Admin', Alumni') OR a.type IN('Directhired', Alumni') OR a.type IN('Independent', Alumni') OR a.type IN('External', Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN('CAP', International') OR a.type IN('APSP', International') OR a.type IN('ASF', International') OR a.type IN('Faculty', International') OR a.type IN('Admin', International') OR a.type IN('Directhired', International') OR a.type IN('Independent', International') OR a.type IN('External', International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c ON c.communityID = s.targetCommunity WHERE c.international = 1 AND s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP' OR a.type = 'APSP' OR a.type = 'ASF' OR a.type = 'Faculty' OR a.type = 'Admin' OR a.type = 'Directhired' OR a.type = 'Independent' OR a.type = 'External'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: CAP
            //
            else if (m.getUnittarget().equals("CAP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'CAP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('CAP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'CAP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: APSP
            //
            else if (m.getUnittarget().equals("APSP Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'APSP'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('APSP', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'APSP'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ASF
            //
            else if (m.getUnittarget().equals("ASF Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'ASF'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('ASF', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'ASF'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: FACULTY EMPLOYEES
            //
            else if (m.getUnittarget().equals("Faculty Employees")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Faculty'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Faculty', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Faculty'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ADMINISTRATORS
            //
            else if (m.getUnittarget().equals("Administrators")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Admin'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Admin', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Admin'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: GRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Graduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Grad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Grad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Grad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: UNDERGRADUATE STUDENTS
            //
            else if (m.getUnittarget().equals("Undergraduate Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Undergrad'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Undergrad', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Undergrad'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: INTERNATIONAL STUDENTS
            //
            else if (m.getUnittarget().equals("International Students")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'International'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International', 'Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'International'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: ALUMNI
            //
            else if (m.getUnittarget().equals("Alumni")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type = 'Alumni'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    return ids;
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni', 'International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1 AND a.type = 'Alumni'";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            } //
            //FOR SERVICE-LEARNING
            //UNIT TARGET: SE PROGRAMS
            //
            else if (m.getUnittarget().equals("Social Engagement Programs")) {
                if (m.getEngagingtarget().equals("N/A")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning'";
                } else if (m.getEngagingtarget().equals("Parents")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Parent')";
                } else if (m.getEngagingtarget().equals("Alumni")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('Alumni')";
                } else if (m.getEngagingtarget().equals("International Students")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND a.type IN ('International')";
                } else if (m.getEngagingtarget().equals("International Communities")) {
                    query = "SELECT distinct(s.id) FROM seproposal s JOIN sereport se ON s.id = se.seproposalID JOIN sereport_attendees a ON se.id = a.sereportID JOIN community c on c.communityID = s.targetCommunity WHERE s.step = 9 AND s.activityClassification = 'Service-Learning' AND c.international = 1";
                }
                try {
                    if (query.equals("")) {
                        return ids;
                    }

                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        ids.add(rs.getInt("s.id"));
                    }

                    return ids;
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                    } catch (Exception e) {
                        /* ignored */ }
                }
            }
        }
        if (query.equals("")) {
            return ids;
        }
        return ids;
    }
    
    public ArrayList<Integer> getProgramsCollege(ArrayList<Integer> id, String unit){
        ArrayList<Integer> ids = new ArrayList();
        UserDAO UserDAO = new UserDAO();
        SE SE;
        
        for(int x = 0 ; x < id.size() ; x++){
            SE = UserDAO.retrieveSEBySEID(id.get(x));
            if(SE.getUnit().equals(unit)){
                ids.add(id.get(x));
            }
        }
        
        return ids;
    }
    
    public ArrayList<Integer> getTotals() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Integer> totals = new ArrayList();

        String query = "SELECT * FROM totals WHERE id = 1";

        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            rs.next();
            int a = rs.getInt("totalunits");
            int b = rs.getInt("totalstudentorgs");
            int c = rs.getInt("totalfacultydept");
            int d = rs.getInt("totalstaff");
            int e = rs.getInt("totalcap");
            int f = rs.getInt("totalapsp");
            int g = rs.getInt("totalasf");
            int h = rs.getInt("totalfaculty");
            int i = rs.getInt("totaladmin");
            int j = rs.getInt("totalgrad");
            int k = rs.getInt("totalundergrad");
            int l = rs.getInt("totalinternationalstudents");
            int m = rs.getInt("totaldepts");
            int n = rs.getInt("totalalumni");

            totals.add(a);
            totals.add(b);
            totals.add(c);
            totals.add(d);
            totals.add(e);
            totals.add(f);
            totals.add(g);
            totals.add(h);
            totals.add(i);
            totals.add(j);
            totals.add(k);
            totals.add(l);
            totals.add(m);
            totals.add(n);

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                pstmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return totals;
    }

    public void editTotals(ArrayList<Integer> totals) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;

        String query = "UPDATE totals SET totalunits = ?, totalstudentorgs = ?, totalfacultydept = ?, totalstaff = ?, totalcap = ?, totalapsp = ?, totalasf = ?, totalfaculty = ?, totaladmin = ?, totalgrad = ?, totalundergrad = ?, totalinternationalstudents = ?, totaldepts = ?, totalalumni = ? WHERE id = 1";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, totals.get(0));
            pstmt.setInt(2, totals.get(1));
            pstmt.setInt(3, totals.get(2));
            pstmt.setInt(4, totals.get(3));
            pstmt.setInt(5, totals.get(4));
            pstmt.setInt(6, totals.get(5));
            pstmt.setInt(7, totals.get(6));
            pstmt.setInt(8, totals.get(7));
            pstmt.setInt(9, totals.get(8));
            pstmt.setInt(10, totals.get(9));
            pstmt.setInt(11, totals.get(10));
            pstmt.setInt(12, totals.get(11));
            pstmt.setInt(13, totals.get(12));
            pstmt.setInt(14, totals.get(13));
            int rs = pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                pstmt.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
    }

    public ArrayList<KRA> retrieveTermEndSortedKRA(Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ResultSet rs2 = null;

        ArrayList<KRA> kra = new ArrayList();
        ArrayList<KRA> kra2 = new ArrayList();
        kra = OvplmDAO.retrieveKRA();
        int count = 0;

        for (int x = 0; x < kra.size(); x++) {
            KRA k = kra.get(x);

            for (int y = 0; y < k.getGoals().size(); y++) {
                for (int z = 0; z < k.getGoals().get(y).getMeasures().size(); z++) {
                    for (int a = 0; a < this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).size(); a++) {
                        if (k.getGoals().get(y).getMeasures().get(z).getTypetarget().equals("Faith Formation")) {
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        }
                    }
                }
            }

            k.setTotal(count);
            count = 0;

            kra2.add(k);
        }

        return kra2;
    }
    
    public ArrayList<KRA> retrieveTermEndSortedKRA2(Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ArrayList<KRA> kra = OvplmDAO.retrieveKRA();
        ArrayList<KRA> kra2 = new ArrayList();
        ArrayList<Goal> goals = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        
        for(int a = 0 ; a < kra.size() ; a++){// kra = a
            ArrayList<SE> seprograms = new ArrayList();
            ArrayList<FF> ffprograms = new ArrayList();
            int count = 0;

            goals = kra.get(a).getGoals();
            System.out.println("KRA: " + kra.get(a).getName());
            for (int b = 0; b < goals.size(); b++) {// goals = b
                measures = goals.get(b).getMeasures();
                for (int c = 0; c < measures.size(); c++) { //measures = c
                    for (int d = 0; d < this.getPrograms(measures.get(c)).size(); d++) {
                        if (measures.get(c).getTypetarget().equals("Faith Formation")) {
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                ffprograms.add(UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d)));
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                seprograms.add(UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d)));
                            }
                        }
                    }
                }
            }

            ArrayList<Integer> seprogramsid = new ArrayList();
            ArrayList<Integer> ffprogramsid = new ArrayList();
            ArrayList<Integer> seprogramsid2 = new ArrayList();
            ArrayList<Integer> ffprogramsid2 = new ArrayList();
            
            if(!ffprograms.isEmpty()){
                for (int x = 0; x < ffprograms.size(); x++) {
                    ffprogramsid.add(ffprograms.get(x).getId());
                }

                for (int x = 0; x < ffprogramsid.size(); x++) {
                    if (!ffprogramsid2.contains(ffprogramsid.get(x))) {
                        ffprogramsid2.add(ffprogramsid.get(x));
                    }
                }
            }
            
            if(!seprograms.isEmpty()){
                for (int x = 0; x < seprograms.size(); x++) {
                    seprogramsid.add(seprograms.get(x).getId());
                }

                for (int x = 0; x < seprogramsid.size(); x++) {
                    if (!seprogramsid2.contains(seprogramsid.get(x))) {
                        seprogramsid2.add(seprogramsid.get(x));
                    }
                }
            }
                
            KRA newkra = kra.get(a);
            count = ffprogramsid2.size() + seprogramsid2.size();
            newkra.setTotal(count);
            kra2.add(newkra);
        }

        return kra2;
    }

    public ArrayList<Measure> retrieveMeasuresImplemented(Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ArrayList<KRA> kra = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        ArrayList<Measure> measures2 = new ArrayList();

        kra = OvplmDAO.retrieveKRA();
        int count = 0;

        for (int x = 0; x < kra.size(); x++) {
            KRA k = kra.get(x);

            for (int y = 0; y < kra.get(x).getGoals().size(); y++) {
                for (int z = 0; z < kra.get(x).getGoals().get(y).getMeasures().size(); z++) {
                    Measure m = kra.get(x).getGoals().get(y).getMeasures().get(z);

                    for (int a = 0; a < this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).size(); a++) {
                        if (k.getGoals().get(y).getMeasures().get(z).getTypetarget().equals("Faith Formation")) {
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        }
                    }
                    m.setMeasureID(count);
                    count = 0;
                    if (m.getUntrackable() == 0) {
                        measures2.add(m);

                    }
                }
            }
        }

        return measures2;
    }

    public ArrayList<Measure> retrieveMeasuresImplementedOfSelectedKRA(int id, Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ArrayList<KRA> kra = new ArrayList();
        ArrayList<KRA> kra2 = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        ArrayList<Measure> measures2 = new ArrayList();

        kra2 = OvplmDAO.retrieveKRA();
        for (int x = 0; x < kra2.size(); x++) {
            if (kra2.get(x).getId() == id) {
                kra.add(kra2.get(x));
            }
        }

        int count = 0;

        for (int x = 0; x < kra.size(); x++) {
            KRA k = kra.get(x);

            for (int y = 0; y < kra.get(x).getGoals().size(); y++) {
                for (int z = 0; z < kra.get(x).getGoals().get(y).getMeasures().size(); z++) {
                    Measure m = kra.get(x).getGoals().get(y).getMeasures().get(z);

                    for (int a = 0; a < this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).size(); a++) {
                        if (k.getGoals().get(y).getMeasures().get(z).getTypetarget().equals("Faith Formation")) {
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                count += 1;
                            }
                        }
                    }
                    m.setMeasureID(count);
                    count = 0;
                    if (m.getUntrackable() == 0) {
                        measures2.add(m);

                    }
                }
            }
        }

        return measures2;
    }

    public ArrayList<KRA> retrieveUnitSortedKRA(String unit, Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ResultSet rs2 = null;

        ArrayList<KRA> kra = new ArrayList();
        ArrayList<KRA> kra2 = new ArrayList();
        kra = OvplmDAO.retrieveKRA();
        int count = 0;

        for (int x = 0; x < kra.size(); x++) {
            KRA k = kra.get(x);

            for (int y = 0; y < k.getGoals().size(); y++) {
                for (int z = 0; z < k.getGoals().get(y).getMeasures().size(); z++) {
                    for (int a = 0; a < this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).size(); a++) {
                        if (k.getGoals().get(y).getMeasures().get(z).getTypetarget().equals("Faith Formation")) {
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                if (proposal.getUnit().equals(unit)) {
                                    count += 1;
                                }
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(k.getGoals().get(y).getMeasures().get(z)).get(a));
                            if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                if (proposal.getUnit().equals(unit)) {
                                    count += 1;
                                }

                            }
                        }
                    }
                }
            }

            k.setTotal(count);
            count = 0;

            if (!kra2.contains(k)) {
                kra2.add(k);
            }
        }

        return kra2;
    }
    
    public ArrayList<KRA> retrieveUnitSortedKRA2(String unit, Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ArrayList<KRA> kra = OvplmDAO.retrieveKRA();
        ArrayList<KRA> kra2 = new ArrayList();
        ArrayList<Goal> goals = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        
        System.out.println("START HERE");
        for(int a = 0 ; a < kra.size() ; a++){// kra = a
            ArrayList<SE> seprograms = new ArrayList();
            ArrayList<FF> ffprograms = new ArrayList();
            int count = 0;
            
            goals = kra.get(a).getGoals();
            System.out.println("KRA: " + kra.get(a).getName());
            for(int b = 0 ; b < goals.size() ; b++){// goals = b
                measures = goals.get(b).getMeasures();
                for(int c = 0 ; c < measures.size() ; c++){ //measures = c
                    for(int d = 0 ; d < this.getPrograms(measures.get(c)).size() ; d++){
                        if(measures.get(c).getTypetarget().equals("Faith Formation")){
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    ffprograms.add(UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    seprograms.add(UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        }
                    }
                }
            }
    
            ArrayList<Integer> seprogramsid = new ArrayList();
            ArrayList<Integer> ffprogramsid = new ArrayList();
            ArrayList<Integer> seprogramsid2 = new ArrayList();
            ArrayList<Integer> ffprogramsid2 = new ArrayList();
            
            if(!ffprograms.isEmpty()){
                for (int x = 0; x < ffprograms.size(); x++) {
                    ffprogramsid.add(ffprograms.get(x).getId());
                }

                for (int x = 0; x < ffprogramsid.size(); x++) {
                    if (!ffprogramsid2.contains(ffprogramsid.get(x))) {
                        ffprogramsid2.add(ffprogramsid.get(x));
                    }
                }
            }
            
            if(!seprograms.isEmpty()){
                for (int x = 0; x < seprograms.size(); x++) {
                    seprogramsid.add(seprograms.get(x).getId());
                }

                for (int x = 0; x < seprogramsid.size(); x++) {
                    if (!seprogramsid2.contains(seprogramsid.get(x))) {
                        seprogramsid2.add(seprogramsid.get(x));
                    }
                }
            }
                
            KRA newkra = kra.get(a);
            count = ffprogramsid2.size() + seprogramsid2.size();
            newkra.setTotal(count);
            kra2.add(newkra);
            System.out.println("FOR " + kra.get(a).getName() + "    COUNT = " + count);
        }

        return kra2;
    }

    public ArrayList<KRA> retrieveProgramsUnitMeasure(String unit, Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();
        
        ArrayList<KRA> kra = OvplmDAO.retrieveKRA();
        ArrayList<KRA> kra2 = new ArrayList();
        ArrayList<Goal> goals = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        
        System.out.println("START HERE");
        for(int a = 0 ; a < kra.size() ; a++){// kra = a
            ArrayList<SE> seprograms = new ArrayList();
            ArrayList<FF> ffprograms = new ArrayList();
            int count = 0;
            
            goals = kra.get(a).getGoals();
            System.out.println("KRA: " + kra.get(a).getName());
            for(int b = 0 ; b < goals.size() ; b++){// goals = b
                measures = goals.get(b).getMeasures();
                for(int c = 0 ; c < measures.size() ; c++){ //measures = c
                    for(int d = 0 ; d < this.getPrograms(measures.get(c)).size() ; d++){
                        if(measures.get(c).getTypetarget().equals("Faith Formation")){
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    ffprograms.add(UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    seprograms.add(UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        }
                    }
                }
            }
    
            ArrayList<Integer> seprogramsid = new ArrayList();
            ArrayList<Integer> ffprogramsid = new ArrayList();
            ArrayList<Integer> seprogramsid2 = new ArrayList();
            ArrayList<Integer> ffprogramsid2 = new ArrayList();
            
            if(!ffprograms.isEmpty()){
                for (int x = 0; x < ffprograms.size(); x++) {
                    ffprogramsid.add(ffprograms.get(x).getId());
                }

                for (int x = 0; x < ffprogramsid.size(); x++) {
                    if (!ffprogramsid2.contains(ffprogramsid.get(x))) {
                        ffprogramsid2.add(ffprogramsid.get(x));
                    }
                }
            }
            
            if(!seprograms.isEmpty()){
                for (int x = 0; x < seprograms.size(); x++) {
                    seprogramsid.add(seprograms.get(x).getId());
                }

                for (int x = 0; x < seprogramsid.size(); x++) {
                    if (!seprogramsid2.contains(seprogramsid.get(x))) {
                        seprogramsid2.add(seprogramsid.get(x));
                    }
                }
            }
            
            for(int x = 0 ; x < seprogramsid2.size() ; x++){
                KRA newkra = new KRA();
                newkra.setDate(UserDAO.retrieveSEBySEID(seprogramsid2.get(x)).getActualDate());
                newkra.setName(kra.get(a).getName());
                newkra.setProgramName(UserDAO.retrieveSEBySEID(seprogramsid2.get(x)).getName());
                kra2.add(newkra);
            }
            
            for(int x = 0 ; x < ffprogramsid2.size() ; x++){
                KRA newkra = new KRA();
                newkra.setDate(UserDAO.retrieveFFByFFID(ffprogramsid2.get(x)).getActualDate());
                newkra.setName(kra.get(a).getName());
                newkra.setProgramName(UserDAO.retrieveFFByFFID(ffprogramsid2.get(x)).getProjectName());
                kra2.add(newkra);
            }
        }

        return kra2;
    }

    public ArrayList<KRA> retrieveProgramsUnitMeasureOfSelectedKRA(int id, String unit, Date start, Date end) {
        OvplmDAO OvplmDAO = new OvplmDAO();
        UserDAO UserDAO = new UserDAO();

        ArrayList<KRA> kra = new ArrayList();
        ArrayList<KRA> kra2 = OvplmDAO.retrieveKRA();
        ArrayList<KRA> kra3 = new ArrayList();
        ArrayList<Goal> goals = new ArrayList();
        ArrayList<Measure> measures = new ArrayList();
        
        for(int x = 0 ; x < kra2.size() ; x++){
            if(kra2.get(x).getId() == id){
                kra.add(kra2.get(x));
            }
        }
        
        for(int a = 0 ; a < kra.size() ; a++){// kra = a
            ArrayList<SE> seprograms = new ArrayList();
            ArrayList<FF> ffprograms = new ArrayList();
            int count = 0;
            
            goals = kra.get(a).getGoals();
            System.out.println("KRA: " + kra.get(a).getName());
            for(int b = 0 ; b < goals.size() ; b++){// goals = b
                measures = goals.get(b).getMeasures();
                for(int c = 0 ; c < measures.size() ; c++){ //measures = c
                    for(int d = 0 ; d < this.getPrograms(measures.get(c)).size() ; d++){
                        if(measures.get(c).getTypetarget().equals("Faith Formation")){
                            FF proposal = UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    ffprograms.add(UserDAO.retrieveFFByFFID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        } else {
                            SE proposal = UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d));
                            if(proposal.getUnit().equals(unit)){
                                if (proposal.getActualDate().before(end) && proposal.getActualDate().after(start) || proposal.getActualDate().equals(end) && proposal.getActualDate().equals(start)
                                    || proposal.getActualDate().equals(end) && proposal.getActualDate().after(start) || proposal.getActualDate().before(end) && proposal.getActualDate().equals(start)) {
                                    seprograms.add(UserDAO.retrieveSEBySEID(this.getPrograms(measures.get(c)).get(d)));
                                }
                            }
                        }
                    }
                }
            }
    
            ArrayList<Integer> seprogramsid = new ArrayList();
            ArrayList<Integer> ffprogramsid = new ArrayList();
            ArrayList<Integer> seprogramsid2 = new ArrayList();
            ArrayList<Integer> ffprogramsid2 = new ArrayList();
            
            if(!ffprograms.isEmpty()){
                for (int x = 0; x < ffprograms.size(); x++) {
                    ffprogramsid.add(ffprograms.get(x).getId());
                }

                for (int x = 0; x < ffprogramsid.size(); x++) {
                    if (!ffprogramsid2.contains(ffprogramsid.get(x))) {
                        ffprogramsid2.add(ffprogramsid.get(x));
                    }
                }
            }
            
            if(!seprograms.isEmpty()){
                for (int x = 0; x < seprograms.size(); x++) {
                    seprogramsid.add(seprograms.get(x).getId());
                }

                for (int x = 0; x < seprogramsid.size(); x++) {
                    if (!seprogramsid2.contains(seprogramsid.get(x))) {
                        seprogramsid2.add(seprogramsid.get(x));
                    }
                }
            }
            
            for(int x = 0 ; x < seprogramsid2.size() ; x++){
                KRA newkra = new KRA();
                newkra.setDate(UserDAO.retrieveSEBySEID(seprogramsid2.get(x)).getActualDate());
                newkra.setName(kra.get(a).getName());
                newkra.setProgramName(UserDAO.retrieveSEBySEID(seprogramsid2.get(x)).getName());
                kra3.add(newkra);
            }
            
            for(int x = 0 ; x < ffprogramsid2.size() ; x++){
                KRA newkra = new KRA();
                newkra.setDate(UserDAO.retrieveFFByFFID(ffprogramsid2.get(x)).getActualDate());
                newkra.setName(kra.get(a).getName());
                newkra.setProgramName(UserDAO.retrieveFFByFFID(ffprogramsid2.get(x)).getProjectName());
                kra3.add(newkra);
            }
        }

        return kra3;
    }
}
