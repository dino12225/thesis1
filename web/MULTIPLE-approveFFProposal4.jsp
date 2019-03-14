<%-- 
    Document   : MULTIPLE-approveCoscaSE
    Created on : 06 18, 18, 7:21:12 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<%@page import="entity.FF"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Approve FF Proposal</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/homepagestyle.css">
        <link rel="stylesheet" href="css/progressbar.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <style>
            p{
                margin-bottom: 0;
                font-size: 15px;
            }

            table, td, th {
                border: 1px solid black;
                border-collapse: collapse;
                text-align: center;
            }

            h4{
                color: white;
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 15px;
            }

            .panel-success > .panel-heading {
                background-color: #4CAF50;
                border-color: #ddd;
                border: 1px solid;
            }

            .panel-body{
                border: 1px solid;
            }

            .panel-upper{
                border: 3px solid #4CAF50;
            }

            h3{
                font-size: 22px;   
                border-bottom: 2px solid #4CAF50;
                padding-bottom: 5px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .card-header{
                background-color: darkgreen;
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 15px;
            }

            .card-body{
                font-family: "Arial", Helvetica, sans-serif;
                background-color: whitesmoke;
                border: 1px solid black;
            }

            .btn-success{
                background-color: darkgreen;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 10px 20px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .btn-warning{
                background-color: darkyellow;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 10px 20px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .btn-danger{
                background-color: red;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 10px 20px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .btn-audit{
                background-color: gray;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 10px 20px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .btn-list{
                background-color: dodgerblue;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 10px 20px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            th,tr,td{
                padding:15px;
            }

        </style>

        <script type="text/javascript">
            <%
                if (request.getAttribute("remarksFF") != null) {

            %>
            $("document").ready(function () {

                alert("<%=request.getAttribute("remarksFF")%>");
            });
            <% }%>
        </script>

    </head>

    <body>
        <!-- Bootstrap NavBar -->
        <nav class="navbar navbar-expand-md fixed-top" id="navbar">
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation" id="smallerscreenmenuButton">
                <span class="fa fa-align-justify"></span>
            </button>
            <a class="navbar-brand" href="#" id="navbar-unit">
                <img src="https://upload.wikimedia.org/wikipedia/en/thumb/c/c2/De_La_Salle_University_Seal.svg/1200px-De_La_Salle_University_Seal.svg.png" width="30" height="30" class="d-inline-block align-top" data-toggle="sidebar-colapse" id="collapse-icon">
                <span class="menu-collapsed"><%=session.getAttribute("unit")%></span>
            </a>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown d-sm-block d-md-none">
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Dashboard
                        </a>
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Proposals
                        </a>
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Units
                        </a>
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Key Result Areas
                        </a>
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Reports
                        </a>
                        <a class="nav-link" href="#" id="smallerscreenmenu">
                            Evaluation Forms
                        </a>
                    </li>
                </ul>
            </div>
            <ul class="navbar-nav mr auto">
                <div class="nav-button">
                    <div class="dropdown">
                        <button type="button" class="btn btn-info navbar-btn-profile" href="#" data-toggle="dropdown">
                            <i class="fa fa-user-circle"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <% UserDAO UserDAO = new UserDAO();%>
                            <div class="col-sm-12">
                                <legend style="font-size:14px;"><b>User ID:</b> <%=Integer.parseInt(session.getAttribute("userID").toString())%></legend>
                                <legend style="font-size:14px;"><b>Name:</b> <br><%=UserDAO.getFirstName(Integer.parseInt(session.getAttribute("userID").toString()))%> <%=UserDAO.getLastName(Integer.parseInt(session.getAttribute("userID").toString()))%></legend>
                                <legend style="font-size:14px;"><b>Unit/Position:</b> <br><%=session.getAttribute("position").toString()%></legend>
                            </div>
                        </ul>
                    </div>
                </div>
                <div class="nav-button">
                    <div class="dropdown">
                        <button type="button" class="btn btn-info navbar-btn-notifications" href="#" data-toggle="dropdown">
                            <span class="badge badge-pill badge-primary" style="background-color:red; color:white; float:right;margin-bottom:-20px;">!</span> 
                            <i class="fa fa-bell"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <div id="notifsScroll">
                                <form action="notifClick">
                                    <%
                                        ArrayList<Notification> n = new ArrayList();
                                        n = UserDAO.retrieveNotificationByUserID(Integer.parseInt(session.getAttribute("userID").toString()));

                                        for (int i = 0; i < n.size(); i++) {
                                    %>
                                    <button type="submit" value="<%=n.get(i).getId()%>" name="redirect" style="width:100%; background-color:white; text-align:left;"> 
                                        <li class="notification-box">
                                            <strong class="notificationBoxHeader"><%=n.get(i).getTitle()%></strong><br>
                                            <%=n.get(i).getBody()%>
                                        </li>
                                    </button>
                                    <%
                                        }
                                    %>
                                </form>
                            </div>
                        </ul>
                    </div>
                </div>
                <div class="nav-button">
                    <form action="logout">
                        <button class="btn btn-info navbar-btn-logout"><i class="fa fa-sign-out"></i></button>
                    </form>
                </div>
            </ul>
        </nav>


        <!-- Bootstrap row -->
        <div class="row" id="body-row">

            <!-- Sidebar -->
            <div class="sidebar-expanded d-none d-md-block">
                <ul id="sidebar-container" class="list-group sticky-top sticky-offset">
                    <script>
                        $("#sidebar-container").load("sidebarmultiple.jsp");
                    </script>
                </ul>
            </div>


            <!-- MAIN -->

            <div class="col py-3">
                <form action="approveFF3" method="post">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12">
                                <%
                                    FF FF = new FF();
                                    FF = UserDAO.retrieveFFByFFID(Integer.parseInt(request.getAttribute("ffID").toString()));
                                %>

                                <div class="panel panel-success ">

                                    <div class="panel panel-success ">

                                        <div class="card">
                                            <div class="card-body">
                                                <h3><%=FF.getProjectName()%></h3>
                                                <p><b>Unit: </b> <%=FF.getUnit()%></p>
                                                <p><b>Department: </b> <%=FF.getDepartment()%></p>
                                                <p><b>Program Head: </b> <%=FF.getProgramHead()%></p>
                                                <p><b>Program Classification: </b> <%=FF.getActivityClassification()%></p>
                                                <p><b>Total Amount Requested:</b> ₱<%=FF.getTotalAmount()%></p>
                                                <p><b>Actual Date of Implementation: </b> <%=FF.getActualDate()%></p>
                                                <p><b>Venue: </b> <%=FF.getVenue()%></p>
                                                <p><b>Speaker: </b> <%=FF.getSpeaker()%></p>
                                            </div>
                                        </div><br>

                                    </div>

                                </div>

                                <ul class="progress-tracker progress-tracker--center">

                                    <li class="progress-step is-complete">
                                        <span class="progress-marker"></span>
                                        <span class="progress-text">
                                            <h4 class="progress-title">Step 1</h4>
                                            Evaluation by LSPO
                                        </span>
                                    </li>

                                    <li class="progress-step is-active">
                                        <span class="progress-marker"></span>
                                        <span class="progress-text">
                                            <h4 class="progress-title">Step 2</h4>
                                            Approval by the Council
                                        </span>
                                    </li>

                                    <li class="progress-step">
                                        <span class="progress-marker"></span>
                                        <span class="progress-text">
                                            <h4 class="progress-title">Step 3</h4>
                                            Accomplish and Upload PRS for Endorsement
                                        </span>
                                    </li>

                                    <li class="progress-step">
                                        <span class="progress-marker"></span>
                                        <span class="progress-text">
                                            <h4 class="progress-title">Step 4</h4>
                                            Ready to Implement
                                        </span>
                                    </li>
                                </ul>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Objectives of the Project</h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><%=FF.getObjectives()%></p>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Breakdown of Expenses</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th>Item</th>
                                                <th>Unit Cost</th> 
                                                <th>Quantity</th>
                                                <th>Subtotal</th>
                                            </tr>
                                            <%
                                                double count = 0;
                                                for (int i = 0; i < FF.getExpenses().size(); i++) {
                                            %>
                                            <tr>
                                                <td><%=FF.getExpenses().get(i).getItem()%></td>
                                                <td><%=FF.getExpenses().get(i).getUnitcost()%></td>
                                                <td><%=FF.getExpenses().get(i).getQuantity()%></td>
                                                <td><%=FF.getExpenses().get(i).getUnitcost() * FF.getExpenses().get(i).getQuantity()%></td>
                                            </tr>
                                            <%
                                                    count += FF.getExpenses().get(i).getUnitcost() * FF.getExpenses().get(i).getQuantity();
                                                }
                                            %>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>Total: <%=count%></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Remarks</h4>
                                    </div>
                                </div>
                                <table style="width:100%">
                                    <tr>
                                        <th style="width:45%">Step</th>
                                        <th style="width:55%">Remarks</th> 
                                        <th>Remark Type</th>
                                    </tr>
                                    <tr>
                                        <td>Evaluation by LSPO Director</td>
                                        <td><%=FF.getLspoRemarks()%></td>
                                        <td><%if (FF.getRemarktype4() != null) {%><%=FF.getRemarktype4()%><%}%></td>
                                    </tr>
                                    <tr>
                                        <td>Enter Remarks:</td>
                                        <td>
                                            <%if (session.getAttribute("position").toString().equals("COSCA - Director")) {
                                            %>
                                            <b>Vice President for Lasallian Mission: </b> <%if (FF.getLmc1Remarks() != null) {%><%=FF.getLmc1Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>Dean of Student Affairs: </b> <%if (FF.getLmc2Remarks() != null) {%><%=FF.getLmc2Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>Laguna Campus Executive Director: </b> <%if (FF.getLmc3Remarks() != null) {%><%=FF.getLmc3Remarks()%><%}%>
                                            <br>
                                            <br>

                                            <%  } else if (session.getAttribute("position").equals("DSA - Dean")) {
                                            %>
                                            <b>Vice President for Lasallian Mission: </b> <%if (FF.getLmc1Remarks() != null) {%><%=FF.getLmc1Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>Laguna Campus Executive Director: </b> <%if (FF.getLmc3Remarks() != null) {%><%=FF.getLmc3Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>COSCA Director: </b> <%if (FF.getLmc5Remarks() != null) {%><%=FF.getLmc5Remarks()%><%}%>
                                            <br>

                                            <%  } else if (session.getAttribute("position").equals("OVPLM - Vice President for Lasallian Mission")) {
                                            %>
                                            <b>Dean of Student Affairs: </b> <%if (FF.getLmc2Remarks() != null) {%><%=FF.getLmc2Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>Laguna Campus Executive Director: </b> <%if (FF.getLmc3Remarks() != null) {%><%=FF.getLmc3Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>COSCA Director: </b> <%if (FF.getLmc5Remarks() != null) {%><%=FF.getLmc5Remarks()%><%}%>
                                            <br>

                                            <%  } else if (session.getAttribute("position").equals("LCLM - Executive Director")) {
                                            %>
                                            <b>Vice President for Lasallian Mission: </b> <%if (FF.getLmc1Remarks() != null) {%><%=FF.getLmc1Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>Dean of Student Affairs: </b> <%if (FF.getLmc2Remarks() != null) {%><%=FF.getLmc2Remarks()%><%}%>
                                            <br>
                                            <br>
                                            <b>COSCA Director: </b> <%if (FF.getLmc5Remarks() != null) {%><%=FF.getLmc5Remarks()%><%}%>
                                            <br>

                                            <%  }%>

                                        </td>
                                        <td>
                                            <%
                                                if (session.getAttribute("position").toString().equals("OVPLM - Vice President for Lasallian Mission")) {
                                            %>
                                            <%if (FF.getRemarktype2() != null) {%><%=FF.getRemarktype2()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype3() != null) {%><%=FF.getRemarktype3()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype5() != null) {%><%=FF.getRemarktype5()%><%}%>
                                            <br>

                                            <% } else if (session.getAttribute("position").toString().equals("COSCA - Director")) { %>

                                            <%if (FF.getRemarktype1() != null) {%><%=FF.getRemarktype1()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype2() != null) {%><%=FF.getRemarktype2()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype3() != null) {%><%=FF.getRemarktype3()%><%}%>
                                            <br>

                                            <% } else if (session.getAttribute("position").toString().equals("LSPO - Director")) { %>

                                            <%if (FF.getRemarktype1() != null) {%><%=FF.getRemarktype1()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype2() != null) {%><%=FF.getRemarktype2()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype3() != null) {%><%=FF.getRemarktype3()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype5() != null) {%><%=FF.getRemarktype5()%><%}%>
                                            <br>

                                            <% } else if (session.getAttribute("position").toString().equals("DSA - Dean")) { %>

                                            <%if (FF.getRemarktype1() != null) {%><%=FF.getRemarktype1()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype3() != null) {%><%=FF.getRemarktype3()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype5() != null) {%><%=FF.getRemarktype5()%><%}%>
                                            <br>

                                            <% } else if (session.getAttribute("position").toString().equals("LCLM - Executive Director")) { %>

                                            <%if (FF.getRemarktype1() != null) {%><%=FF.getRemarktype1()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype2() != null) {%><%=FF.getRemarktype2()%><%}%>
                                            <br>
                                            <br>
                                            <%if (FF.getRemarktype5() != null) {%><%=FF.getRemarktype5()%><%}%>
                                            <br>
                                            <% } %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Remarks by the Council</td>
                                        <%
                                            if (session.getAttribute("position").toString().equals("OVPLM - Vice President for Lasallian Mission")) {
                                        %>
                                        <td style="padding:0px"><textarea id="remarks4" rows="3" cols="110" style="margin-bottom:-0.5%" name="remarks1"><%if (FF.getLmc1Remarks() != null) {%><%=FF.getLmc1Remarks()%><%}%></textarea></td>
                                        <td><center>
                                        <select name="remarktype">
                                            <option value="--">Select</option>
                                            <option value="Comment">Comment</option>
                                            <option value="For Action">For Action</option>
                                        </select>
                                    </center></td>
                                    <%
                                        }
                                        if (session.getAttribute("position").toString().equals("DSA - Dean")) {
                                    %>
                                    <td style="padding:0px"><textarea id="remarks4" rows="3" cols="110" style="margin-bottom:-0.5%" name="remarks1"><%if (FF.getLmc2Remarks() != null) {%><%=FF.getLmc2Remarks()%><%}%></textarea></td>
                                    <td><center>
                                        <select name="remarktype">
                                            <option value="--">Select</option>
                                            <option value="Comment">Comment</option>
                                            <option value="For Action">For Action</option>
                                        </select>
                                    </center></td>
                                    <%
                                        }
                                        if (session.getAttribute("position").toString().equals("LCLM - Executive Director")) {
                                    %>
                                    <td style="padding:0px"><textarea id="remarks4" rows="3" cols="110" style="margin-bottom:-0.5%" name="remarks1"><%if (FF.getLmc3Remarks() != null) {%><%=FF.getLmc3Remarks()%><%}%></textarea></td>
                                    <td><center>
                                        <select name="remarktype">
                                            <option value="--">Select</option>
                                            <option value="Comment">Comment</option>
                                            <option value="For Action">For Action</option>
                                        </select>
                                    </center></td>
                                    <%
                                        }
                                        if (session.getAttribute("position").toString().equals("LSPO - Director")) {
                                    %>
                                    <td style="padding:0px"><textarea id="remarks4" rows="3" cols="110" style="margin-bottom:-0.5%" name="remarks1"><%if (FF.getLmc4Remarks() != null) {%><%=FF.getLmc4Remarks()%><%}%></textarea></td>
                                    <td><center>
                                        <select name="remarktype">
                                            <option value="--">Select</option>
                                            <option value="Comment">Comment</option>
                                            <option value="For Action">For Action</option>
                                        </select>
                                    </center></td>
                                    <%
                                        }
                                        if (session.getAttribute("position").toString().equals("COSCA - Director")) {
                                    %>
                                    <td style="padding:0px"><textarea id="remarks4" rows="3" cols="110" style="margin-bottom:-0.5%" name="remarks1"><%if (FF.getLmc5Remarks() != null) {%><%=FF.getLmc5Remarks()%><%}%></textarea></td>
                                    <td><center>
                                        <select name="remarktype">
                                            <option value="--">Select</option>
                                            <option value="Comment">Comment</option>
                                            <option value="For Action">For Action</option>
                                        </select>
                                    </center></td>
                                    <%
                                        }
                                    %>
                                    </tr>
                                </table>
                                <br/>
                                <input type="hidden" name="ffID" value="<%=FF.getId()%>">

                                <%
                                    if (FF.getStep() == 5) {
                                        if (session.getAttribute("position").toString().equals("OVPLM - Vice President for Lasallian Mission") && !UserDAO.hasMichaelVoted(Integer.parseInt(request.getAttribute("seID").toString()))
                                                || session.getAttribute("position").toString().equals("DSA - Dean") && !UserDAO.hasNelcaVoted(Integer.parseInt(request.getAttribute("seID").toString()))
                                                || session.getAttribute("position").toString().equals("LCLM - Executive Director") && !UserDAO.hasMargaritaVoted(Integer.parseInt(request.getAttribute("seID").toString()))
                                                || session.getAttribute("position").toString().equals("COSCA - Director") && !UserDAO.hasFritzieVoted(Integer.parseInt(request.getAttribute("seID").toString()))
                                                || session.getAttribute("position").toString().equals("LSPO - Director") && !UserDAO.hasJamesVoted(Integer.parseInt(request.getAttribute("seID").toString()))) {
                                %>

                                <center><button class="btn-audit" type="submit" name="auditFF" value="<%=request.getAttribute("ffID")%>">View Audit Trail</button> 
                                    <button class='btn-list' type="submit" name="viewAttendees" value="<%=FF.getId()%>">Attendees List</button>
                                    <button onclick="return window.confirm('Proceed?')" class="btn-success" type="submit" name="approve" value="<%=FF.getId()%>">Proceed</button></center>                  

                                <%} else {%>
                                <center><legend>This proposal is done with this step</legend></center>
                                    <%}
                                } else {%>
                                <center><legend>This proposal is done with this step</legend></center>
                                    <%}%>     
                            </div>
                        </div>
                    </div>
                </form>
            </div>



            <script>
                // sandbox disable popups
                if (window.self !== window.top && window.name != "view1") {
                    ;
                    window.alert = function () {/*disable alert*/
                    };
                    window.confirm = function () {/*disable confirm*/
                    };
                    window.prompt = function () {/*disable prompt*/
                    };
                    window.open = function () {/*disable open*/
                    };
                }

                // prevent href=# click jump
                document.addEventListener("DOMContentLoaded", function () {
                    var links = document.getElementsByTagName("A");
                    for (var i = 0; i < links.length; i++) {
                        if (links[i].href.indexOf('#') != -1) {
                            links[i].addEventListener("click", function (e) {
                                console.debug("prevent href=# click");
                                if (this.hash) {
                                    if (this.hash == "#") {
                                        e.preventDefault();
                                        return false;
                                    } else {
                                        /*
                                         var el = document.getElementById(this.hash.replace(/#/, ""));
                                         if (el) {
                                         el.scrollIntoView(true);
                                         }
                                         */
                                    }
                                }
                                return false;
                            })
                        }
                    }
                }, false);
            </script>
            <script>
                // Hide submenus
                $('#body-row .collapse').collapse('hide');
                // Collapse/Expand icon
                $('#collapse-icon').addClass('fa-angle-double-left');
                // Collapse click
                $('[data-toggle=sidebar-colapse]').click(function () {
                    SidebarCollapse();
                });
                function SidebarCollapse() {
                    $('.menu-collapsed').toggleClass('d-none');
                    $('.sidebar-submenu').toggleClass('d-none');
                    $('.submenu-icon').toggleClass('d-none');
                    $('#sidebar-container').toggleClass('sidebar-expanded sidebar-collapsed');
                    // Treating d-flex/d-none on separators with title
                    var SeparatorTitle = $('.sidebar-separator-title');
                    if (SeparatorTitle.hasClass('d-flex')) {
                        SeparatorTitle.removeClass('d-flex');
                    } else {
                        SeparatorTitle.addClass('d-flex');
                    }

                    // Collapse/Expand icon
                    $('#collapse-icon').toggleClass('fa-angle-double-left fa-angle-double-right');
                }
            </script>
    </body>
</html>