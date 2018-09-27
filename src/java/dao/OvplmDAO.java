/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Community;
import entity.Department;
import entity.Goal;
import entity.KRA;
import entity.Measure;
import entity.Unit;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LA
 */
public class OvplmDAO {

    public void AddAcademicUnit(Unit u, ArrayList<Department> d) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs2 = null;
        try {
            String query = "INSERT INTO unit(unitName, unitHead, departments, unitType, unitDescription, userID) VALUES(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getHead());
            pstmt.setInt(3, u.getDepartments());
            pstmt.setString(4, u.getType());
            pstmt.setString(5, u.getDescription());
            pstmt.setInt(6, u.getUserID());

            int rs = pstmt.executeUpdate();

            query = "SELECT * FROM department ORDER by departmentID DESC LIMIT 1";

            int lastdepartmentID = 0;

            pstmt = conn.prepareStatement(query);

            rs2 = pstmt.executeQuery();

            while (rs2.next()) {
                lastdepartmentID = rs2.getInt("departmentID");
            }

            ArrayList<Integer> deptID = new ArrayList();
            for (int i = 0; i < d.size(); i++) {
                query = "INSERT INTO department (departmentID, department, numberOfFaculty, numberOfAdmin, numberOfAPSP, numberOfASF, numberOfCAP, numberOfDirectHired, numberOfIndependent, numberOfExternal) VALUES (?,?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(query);

                pstmt.setInt(1, lastdepartmentID + 1);
                pstmt.setString(2, d.get(i).getName());
                pstmt.setInt(3, d.get(i).getFaculty());
                pstmt.setInt(4, d.get(i).getAdmin());
                pstmt.setInt(5, d.get(i).getApsp());
                pstmt.setInt(6, d.get(i).getAsf());
                pstmt.setInt(7, d.get(i).getCap());
                pstmt.setInt(8, d.get(i).getDirecthired());
                pstmt.setInt(9, d.get(i).getIndependent());
                pstmt.setInt(10, d.get(i).getExternal());

                rs = pstmt.executeUpdate();

                deptID.add(lastdepartmentID + 1);
                lastdepartmentID++;
            }

            query = "SELECT * FROM unit ORDER by unitID DESC LIMIT 1";

            int lastunitID = 0;

            pstmt = conn.prepareStatement(query);

            rs2 = pstmt.executeQuery();

            while (rs2.next()) {
                lastunitID = rs2.getInt("unitID");
            }

            for (int i = 0; i < deptID.size(); i++) {
                query = "INSERT INTO unit_department (unitID, departmentID) VALUES (?,?)";
                pstmt = conn.prepareStatement(query);

                pstmt.setInt(1, lastunitID);
                pstmt.setInt(2, deptID.get(i));

                rs = pstmt.executeUpdate();

            }
        } catch (SQLException ex) {
            Logger.getLogger(OvplmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public void AddNonAcademicUnit(Unit u) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs2 = null;
        try {
            String query = "INSERT INTO unit(unitName, unitHead, departments, numberOfFaculty, numberOfAdmin, numberOfAPSP, numberOfASF, numberOfCAP, numberOfDirectHired, numberOfIndependent, numberOfExternal, unitDescription, userID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getHead());
            pstmt.setInt(3, u.getDepartments());
            pstmt.setInt(4, u.getFaculty());
            pstmt.setInt(5, u.getAdmin());
            pstmt.setInt(6, u.getApsp());
            pstmt.setInt(7, u.getAsf());
            pstmt.setInt(8, u.getCap());
            pstmt.setInt(9, u.getDirecthired());
            pstmt.setInt(10, u.getExternal());
            pstmt.setInt(11, u.getIndependent());
            pstmt.setString(12, u.getDescription());
            pstmt.setInt(13, u.getUserID());

            int rs = pstmt.executeUpdate();

            query = "SELECT * FROM unit ORDER by unitID DESC LIMIT 1";

            int lastunitID = 0;

            pstmt = conn.prepareStatement(query);

            rs2 = pstmt.executeQuery();

            while (rs2.next()) {
                lastunitID = rs2.getInt("unitID");
            }

            query = "INSERT INTO unit_department (unitID, departmentID) VALUES (?,?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, lastunitID);
            pstmt.setInt(2, 0);

            rs = pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OvplmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public void AddCommunity(Community c) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs2 = null;
        try {
            String query = "INSERT INTO community(name, contactPerson, contactNumber, unitNumber, street, barangay, city, description, userID) VALUES(?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getContactperson());
            pstmt.setString(3, c.getContactnumber());
            pstmt.setString(4, c.getUnitnumber());
            pstmt.setString(5, c.getStreet());
            pstmt.setString(6, c.getBarangay());
            pstmt.setString(7, c.getCity());
            pstmt.setString(8, c.getDescription());
            pstmt.setInt(9, c.getUserID());

            int rs = pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OvplmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public void AddKRA(KRA KRA) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs2 = null;
        try {
            String query = "INSERT INTO KRA(name, date, userID) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, KRA.getName());
            pstmt.setDate(2, KRA.getDate());
            pstmt.setInt(3, KRA.getUserID());

            int rs = pstmt.executeUpdate();

            query = "SELECT * FROM kra ORDER BY kraID DESC LIMIT 1";

            pstmt = conn.prepareStatement(query);

            rs2 = pstmt.executeQuery();

            int kraid = 0;

            while (rs2.next()) {
                kraid = rs2.getInt("kraID");
            }

            for (int i = 0; i < KRA.getGoals().size(); i++) {
                query = "INSERT INTO goal(goalNumber, name, kraID) VALUES(?,?,?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, KRA.getGoals().get(i).getGoal());
                pstmt.setString(2, KRA.getGoals().get(i).getName());
                pstmt.setInt(3, kraid);

                rs = pstmt.executeUpdate();

                int goalid = 0;

                query = "SELECT * FROM goal ORDER BY goalID DESC LIMIT 1";

                pstmt = conn.prepareStatement(query);

                rs2 = pstmt.executeQuery();

                while (rs2.next()) {
                    goalid = rs2.getInt("goalID");
                }

                for (int j = 0; j < KRA.getGoals().get(i).getMeasures().size(); j++) {
                    query = "INSERT into measure(measure, target, kraID, goalID) VALUES(?,?,?,?)";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, KRA.getGoals().get(i).getMeasures().get(j).getMeasure());
                    pstmt.setString(2, KRA.getGoals().get(i).getMeasures().get(j).getTarget());
                    pstmt.setInt(3, kraid);
                    pstmt.setInt(4, goalid);

                    rs = pstmt.executeUpdate();

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OvplmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public ArrayList<KRA> retrieveKRA() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();

        String query = "SELECT * FROM KRA";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<KRA> kra = new ArrayList();

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                KRA k = new KRA();
                k.setId(rs.getInt("kraID"));
                k.setName(rs.getString("name"));
                k.setDate(rs.getDate("date"));
                k.setUserID(rs.getInt("userID"));
                kra.add(k);
            }

            for (int i = 0; i < kra.size(); i++) {
                ArrayList<Goal> goals = new ArrayList();

                query = "SELECT * FROM goal WHERE kraID = ?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, kra.get(i).getId());
                rs = ps.executeQuery();

                while (rs.next()) {
                    Goal g = new Goal();
                    g.setGoalID(rs.getInt("goalID"));
                    g.setGoal(rs.getInt("goalNumber"));
                    g.setName(rs.getString("name"));
                    goals.add(g);
                }

                for (int j = 0; j < goals.size(); j++) {
                    ArrayList<Measure> measures = new ArrayList();

                    query = "SELECT * FROM measure WHERE goalID = ?";

                    ps = conn.prepareStatement(query);
                    ps.setInt(1, goals.get(j).getGoalID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        Measure m = new Measure();
                        m.setMeasureID(rs.getInt("measureID"));
                        m.setMeasure(rs.getString("measure"));
                        m.setTarget(rs.getString("target"));
                        measures.add(m);
                    }

                    goals.get(j).setMeasures(measures);
                }
                kra.get(i).setGoals(goals);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return kra;
    }

    public KRA retrieveKRAByID(int kraID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();

        String query = "SELECT * FROM KRA where kraID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        KRA kra = new KRA();

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, kraID);
            rs = ps.executeQuery();

            while (rs.next()) {
                kra.setId(rs.getInt("kraID"));
                kra.setName(rs.getString("name"));
                kra.setDate(rs.getDate("date"));
                kra.setUserID(rs.getInt("userID"));
            }

            ArrayList<Goal> goals = new ArrayList();

            query = "SELECT * FROM goal WHERE kraID = ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, kraID);
            rs = ps.executeQuery();

            while (rs.next()) {
                Goal g = new Goal();
                g.setGoalID(rs.getInt("goalID"));
                g.setGoal(rs.getInt("goalNumber"));
                g.setName(rs.getString("name"));
                goals.add(g);
            }

            for (int i = 0; i < goals.size(); i++) {

                ArrayList<Measure> measures = new ArrayList();

                query = "SELECT * FROM measure WHERE goalID = ?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, goals.get(i).getGoalID());
                rs = ps.executeQuery();

                while (rs.next()) {
                    Measure m = new Measure();
                    m.setMeasureID(rs.getInt("measureID"));
                    m.setMeasure(rs.getString("measure"));
                    m.setTarget(rs.getString("target"));
                    measures.add(m);
                }
                goals.get(i).setMeasures(measures);
            }

            kra.setGoals(goals);

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return kra;
    }

    public String getKRAnameByID(int kraID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection conn = myFactory.getConnection();

        String query = "SELECT name FROM kra WHERE kraID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String name = "";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, kraID);
            rs = ps.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return name;
    }
}
