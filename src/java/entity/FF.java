/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author LA
 */
public class FF {

    protected int id;
    protected String unit;
    protected String department;
    protected Date datecreated;
    protected String programHead;
    protected String activityClassification;
    protected String projectName;
    protected String venue;
    protected String speaker;
    protected String objectives;
    protected Date actualDate;
    protected Double totalAmount;
    protected String sourceOfFunds;
    protected ArrayList<FFexpenses> expenses = new ArrayList();
    protected ArrayList<FFattendees> attendees = new ArrayList();
    protected int step;
    protected int userID;
    protected String chairdirectorRemarks;
    protected String vplmRemarks;
    protected String deanunitRemarks;
    protected String assistantdeanRemarks;
    protected String unitheadremarks;
    protected String directorremarks;
    protected String jayRemarks;
    protected String carmelRemarks;
    protected String lspoRemarks;
    protected String lmc1Remarks;
    protected String lmc2Remarks;
    protected String lmc3Remarks;
    protected String lmc4Remarks;
    protected String lmc5Remarks;
    protected String unittype;
    protected String revisionTime;
    protected String chairdirectordatetime;
    protected String vplmdatetime;
    protected String deanunitdatetime;
    protected String lspodatetime;
    protected String unitheaddatetime;
    protected String directordatetime;
    protected int approve1;
    protected int approve2;
    protected int approve3;
    protected int approve4;
    protected int revise1;
    protected int revise2;
    protected int revise3;
    protected int revise4;
    protected int reject1;
    protected int reject2;
    protected int reject3;
    protected int reject4;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getProgramHead() {
        return programHead;
    }

    public void setProgramHead(String programHead) {
        this.programHead = programHead;
    }

    public String getActivityClassification() {
        return activityClassification;
    }

    public void setActivityClassification(String activityClassification) {
        this.activityClassification = activityClassification;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSourceOfFunds() {
        return sourceOfFunds;
    }

    public void setSourceOfFunds(String sourceOfFunds) {
        this.sourceOfFunds = sourceOfFunds;
    }

    public ArrayList<FFexpenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<FFexpenses> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<FFattendees> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<FFattendees> attendees) {
        this.attendees = attendees;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getChairdirectorRemarks() {
        return chairdirectorRemarks;
    }

    public void setChairdirectorRemarks(String chairdirectorRemarks) {
        this.chairdirectorRemarks = chairdirectorRemarks;
    }

    public String getVplmRemarks() {
        return vplmRemarks;
    }

    public void setVplmRemarks(String vplmRemarks) {
        this.vplmRemarks = vplmRemarks;
    }

    public String getDeanunitRemarks() {
        return deanunitRemarks;
    }

    public void setDeanunitRemarks(String deanunitRemarks) {
        this.deanunitRemarks = deanunitRemarks;
    }

    public String getAssistantdeanRemarks() {
        return assistantdeanRemarks;
    }

    public void setAssistantdeanRemarks(String assistantdeanRemarks) {
        this.assistantdeanRemarks = assistantdeanRemarks;
    }

    public String getJayRemarks() {
        return jayRemarks;
    }

    public void setJayRemarks(String jayRemarks) {
        this.jayRemarks = jayRemarks;
    }

    public String getCarmelRemarks() {
        return carmelRemarks;
    }

    public void setCarmelRemarks(String carmelRemarks) {
        this.carmelRemarks = carmelRemarks;
    }

    public String getLspoRemarks() {
        return lspoRemarks;
    }

    public void setLspoRemarks(String lspoRemarks) {
        this.lspoRemarks = lspoRemarks;
    }

    public String getLmc1Remarks() {
        return lmc1Remarks;
    }

    public void setLmc1Remarks(String lmc1Remarks) {
        this.lmc1Remarks = lmc1Remarks;
    }

    public String getLmc2Remarks() {
        return lmc2Remarks;
    }

    public void setLmc2Remarks(String lmc2Remarks) {
        this.lmc2Remarks = lmc2Remarks;
    }

    public String getLmc3Remarks() {
        return lmc3Remarks;
    }

    public void setLmc3Remarks(String lmc3Remarks) {
        this.lmc3Remarks = lmc3Remarks;
    }

    public String getLmc4Remarks() {
        return lmc4Remarks;
    }

    public void setLmc4Remarks(String lmc4Remarks) {
        this.lmc4Remarks = lmc4Remarks;
    }

    public String getLmc5Remarks() {
        return lmc5Remarks;
    }

    public void setLmc5Remarks(String lmc5Remarks) {
        this.lmc5Remarks = lmc5Remarks;
    }

    public String getUnitheadremarks() {
        return unitheadremarks;
    }

    public void setUnitheadremarks(String unitheadremarks) {
        this.unitheadremarks = unitheadremarks;
    }

    public String getDirectorremarks() {
        return directorremarks;
    }

    public void setDirectorremarks(String directorremarks) {
        this.directorremarks = directorremarks;
    }

    public String getUnittype() {
        return unittype;
    }

    public void setUnittype(String unittype) {
        this.unittype = unittype;
    }

    public String getRevisionTime() {
        return revisionTime;
    }

    public void setRevisionTime(String revisionTime) {
        this.revisionTime = revisionTime;
    }

    public String getChairdirectordatetime() {
        return chairdirectordatetime;
    }

    public void setChairdirectordatetime(String chairdirectordatetime) {
        this.chairdirectordatetime = chairdirectordatetime;
    }

    public String getVplmdatetime() {
        return vplmdatetime;
    }

    public void setVplmdatetime(String vplmdatetime) {
        this.vplmdatetime = vplmdatetime;
    }

    public String getDeanunitdatetime() {
        return deanunitdatetime;
    }

    public void setDeanunitdatetime(String deanunitdatetime) {
        this.deanunitdatetime = deanunitdatetime;
    }

    public String getLspodatetime() {
        return lspodatetime;
    }

    public void setLspodatetime(String lspodatetime) {
        this.lspodatetime = lspodatetime;
    }

    public String getUnitheaddatetime() {
        return unitheaddatetime;
    }

    public void setUnitheaddatetime(String unitheaddatetime) {
        this.unitheaddatetime = unitheaddatetime;
    }

    public String getDirectordatetime() {
        return directordatetime;
    }

    public void setDirectordatetime(String directordatetime) {
        this.directordatetime = directordatetime;
    }

    public int getApprove1() {
        return approve1;
    }

    public void setApprove1(int approve1) {
        this.approve1 = approve1;
    }

    public int getApprove2() {
        return approve2;
    }

    public void setApprove2(int approve2) {
        this.approve2 = approve2;
    }

    public int getApprove3() {
        return approve3;
    }

    public void setApprove3(int approve3) {
        this.approve3 = approve3;
    }

    public int getApprove4() {
        return approve4;
    }

    public void setApprove4(int approve4) {
        this.approve4 = approve4;
    }

    public int getRevise1() {
        return revise1;
    }

    public void setRevise1(int revise1) {
        this.revise1 = revise1;
    }

    public int getRevise2() {
        return revise2;
    }

    public void setRevise2(int revise2) {
        this.revise2 = revise2;
    }

    public int getRevise3() {
        return revise3;
    }

    public void setRevise3(int revise3) {
        this.revise3 = revise3;
    }

    public int getRevise4() {
        return revise4;
    }

    public void setRevise4(int revise4) {
        this.revise4 = revise4;
    }

    public int getReject1() {
        return reject1;
    }

    public void setReject1(int reject1) {
        this.reject1 = reject1;
    }

    public int getReject2() {
        return reject2;
    }

    public void setReject2(int reject2) {
        this.reject2 = reject2;
    }

    public int getReject3() {
        return reject3;
    }

    public void setReject3(int reject3) {
        this.reject3 = reject3;
    }

    public int getReject4() {
        return reject4;
    }

    public void setReject4(int reject4) {
        this.reject4 = reject4;
    }
    
    
}
