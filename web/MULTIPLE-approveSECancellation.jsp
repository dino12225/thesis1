<%-- 
    Document   : MULTIPLE-approveCoscaSE
    Created on : 06 18, 18, 7:21:12 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.SE"%>
<%@page import="entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>SE Proposal Cancellation</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/homepagestyle.css">
        <link rel="stylesheet" href="css/progressbar.css">
        <link rel="stylesheet" href="css/formstyle5.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <style>
            #myInput {
                background-image: url('/css/searchicon.png'); /* Add a search icon to input */
                background-position: 10px 12px; /* Position the search icon */
                background-repeat: no-repeat; /* Do not repeat the icon image */
                width: 100%; /* Full-width */
                padding: 12px 20px 12px 40px; /* Add some padding */
                border: 1px solid #ddd; /* Add a grey border */
                margin-bottom: 12px; /* Add some space below the input */
                margin-top: 20px; 
            }

            #myTable {
                border-collapse: collapse; /* Collapse borders */
                width: 100%; /* Full-width */
                border: 1px solid #ddd; /* Add a grey border */

            }

            #myTable th, #myTable td {
                text-align: left; /* Left-align text */
                padding: 12px; /* Add padding */
            }

            #myTable tr {
                border-bottom: 1px solid #ddd; 
            }

            #myTable tr.header, #myTable tr:hover {
                background-color: #4CAF50;
            }

            .panel-title{
                font-size: 40px;
                text-align: left;
                margin-top: 20px;
                padding-bottom: 10px;
            }

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
                border: 1px solid #4CAF50;
            }

            @-webkit-keyframes scale-up {
                from {
                    opacity: 1;
                    -webkit-transform: translate(-50%, -50%) scale(0);
                    transform: translate(-50%, -50%) scale(0);
                }
                to {
                    opacity: 0;
                    -webkit-transform: translate(-50%, -50%) scale(1);
                    transform: translate(-50%, -50%) scale(1);
                }
            }

            @keyframes scale-up {
                from {
                    opacity: 1;
                    -webkit-transform: translate(-50%, -50%) scale(0);
                    transform: translate(-50%, -50%) scale(0);
                }
                to {
                    opacity: 0;
                    -webkit-transform: translate(-50%, -50%) scale(1);
                    transform: translate(-50%, -50%) scale(1);
                }
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

            th,tr,td{
                padding:15px;
            }

            textarea{
                resize: none;
            }

            .button{
                background-color: mediumseagreen;
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

            legend, h3, #inputText, #classification, option, select, value{
                font-family: "Arial", Helvetica, sans-serif;
            }
        </style>

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


            <%
                SE SE = new SE();
                SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getAttribute("seID").toString()));
            %>
            <!-- MAIN -->
            <div class="col py-3">
                <form action="approveSECancellation" method="post">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-body">
                                        <h3><%=SE.getName()%></h3>
                                        <p><b>Unit: </b> <%=SE.getUnit()%></p>
                                        <p><b>Department: </b> <%=SE.getDepartment()%></p>
                                        <p><b>Target Date of Implementation: </b> <%=SE.getActualDate()%></p>
                                        <p><b>Implementation Address: </b> <%=SE.getImplementationaddress()%></p>
                                        <br>
                                        <p><b>Program Head: </b> <%=SE.getProgramHead()%></p>
                                        <p><b>Program Classification: </b> <%=SE.getActivityClassification()%></p>
                                        <p><b>Total Amount Requested:</b> ₱<%=SE.getTotalAmount()%></p>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Targets</h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><b>KRA:</b> <%=UserDAO.getKRAnameByID(SE.getTargetKRA())%></p><br>
                                        <p><b>Goal:</b> <%=UserDAO.getGoalnameByID(SE.getTargetGoal())%></p><br>
                                        <p><b>Measure/s:</b> 
                                            <%
                                                ArrayList<Integer> measures = new ArrayList();
                                                measures = UserDAO.GetMeasures(SE.getId());

                                                for (int x = 0; x < measures.size(); x++) {
                                            %>    
                                        <p><%=UserDAO.GetMeasureObject(measures.get(x)).getMeasure()%> - <%=UserDAO.GetMeasureObject(measures.get(x)).getDescription()%></p>

                                        <%
                                            }
                                        %>
                                        <br>
                                        <p><br><b>Community: </b> <%=UserDAO.getCommunitynameByID(SE.getTargetCommunity())%></p>
                                    </div>  
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Social/Community Problem being Addressed</h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><%=SE.getSocialCommunityProblem()%></p>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Social Engagement Partner(s)/Beneficiaries</h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><b>Name of Partner: </b><%=SE.getNameSEbeneficiaries()%></p>
                                        <p><b>Address: </b><%=SE.getAddressSEbeneficiaries()%></p>
                                        <p><b>Contact Person: </b><%=SE.getContactPersonSEbeneficiaries()%></p>
                                        <p><b>Mobile Number: </b><%=SE.getMobileSEbeneficiaries()%></p>
                                        <p><b>Email: </b><%=SE.getEmailSEbeneficiaries()%> </p><br>
                                        <p><b>Brief Description of Partner: </b><%=SE.getDescriptionSEbeneficiaries()%></p>
                                    </div>
                                </div>
                                <br>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Measurable Outcomes/Objectives of the Project</h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><%=SE.getObjectives()%></p>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Sustainability Component</h4>
                                    </div>
                                    <div class="card-body">
                                        <%
                                            for (int i = 0; i < SE.getComponent().size(); i++) {
                                        %>
                                        <p><%=SE.getComponent().get(i)%></p>

                                        <%
                                            }
                                        %>
                                        <br>
                                        <p>Explanation:</p>
                                        <p><%if (SE.getExplanation() != null) {%> <%=SE.getExplanation()%><% } else { %> None <% } %></p>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Work Plan</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th>Date</th>
                                                <th>Activity</th> 
                                                <th>Time Start</th>
                                                <th>Time End</th>
                                                <th>Venue</th>
                                            </tr>
                                            <%
                                                for (int i = 0; i < SE.getWorkplan().size(); i++) {
                                            %>
                                            <tr>
                                                <td><%=SE.getWorkplan().get(i).getDate()%></td>
                                                <td><%=SE.getWorkplan().get(i).getActivity()%></td>
                                                <td><%=SE.getWorkplan().get(i).getTimestarttimeend()%></td>
                                                <td><%=SE.getWorkplan().get(i).getTimestarttimeend2()%></td>
                                                <td><%=SE.getWorkplan().get(i).getVenue()%></td>
                                            </tr>

                                            <%
                                                }
                                            %>

                                        </table>
                                    </div>
                                </div>

                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Breakdown of Expenses (Requested: ₱<%=SE.getTotalAmount()%>)</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th>Item</th>
                                                <th>Unit Cost</th> 
                                                <th>Quantity</th>
                                                <th>Subtotal</th>
                                                <th>Amount Used</th>
                                                <th>Date</th>
                                            </tr>
                                            <%
                                                double count = 0;
                                                double total = 0;
                                                for (int i = 0; i < SE.getExpenses().size(); i++) {
                                            %>
                                            <tr>
                                                <td><%=SE.getExpenses().get(i).getItem()%></td>
                                                <td><%=SE.getExpenses().get(i).getUnitcost()%></td>
                                                <td><%=SE.getExpenses().get(i).getQuantity()%></td>
                                                <td><%=SE.getExpenses().get(i).getUnitcost() * SE.getExpenses().get(i).getQuantity()%></td>
                                                <td><%=SE.getExpenses().get(i).getAmountUsed()%></td>
                                                <td><%if (SE.getExpenses().get(i).getDatetime() != null) {%> <%=SE.getExpenses().get(i).getDatetime()%><% } else { %> None <% } %></td>
                                            </tr>
                                            <%
                                                    count += SE.getExpenses().get(i).getUnitcost() * SE.getExpenses().get(i).getQuantity();
                                                    total += SE.getExpenses().get(i).getAmountUsed();
                                                }
                                            %>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>Total: <%=count%></td>
                                                <td>Total: <%=total%></td>
                                                <td></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Expected Participants vs. Total Population of the Unit</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th>Sector</th>
                                                <th>Total Population</th>
                                                <th>Expected Number of Participants</th>
                                            </tr>
                                            <tr>
                                                <td>Academic Staff from Unit</td>
                                                <td><%=SE.getTotalpopulationAcademicStaff()%></td>
                                                <td><%=SE.getExpectedAcademicStaff()%></td>
                                            </tr>
                                            <tr>
                                                <td>Support Staff from Unit</td>
                                                <td><%=SE.getTotalpopulationSupportStaff()%></td>
                                                <td><%=SE.getExpectedSupportStaff()%></td>
                                            </tr>
                                            <tr>
                                                <td>Undergraduate Students</td>
                                                <td><%=SE.getTotalpopulationUndergraduate()%></td>
                                                <td><%=SE.getExpectedUndergraduate()%></td>
                                            </tr>
                                            <tr>
                                                <td>Graduate Students</td>
                                                <td><%=SE.getTotalPopulationGraduate()%></td>
                                                <td><%=SE.getExpectedGraduate()%></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Persons Responsible</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th>Name</th>
                                                <th>Email</th>
                                            </tr>
                                            <%
                                                for (int i = 0; i < SE.getResponsible().size(); i++) {
                                            %>
                                            <tr>
                                                <td><%=SE.getResponsible().get(i).getName()%></td>
                                                <td><%=SE.getResponsible().get(i).getEmail()%></td>
                                            </tr>

                                            <%
                                                }
                                            %>
                                        </table>
                                    </div>
                                </div>
                                <br/>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Source of Funds </h4>
                                    </div>
                                    <div class="card-body">   
                                        <p><%=SE.getSourceOfFunds()%></p>
                                    </div>
                                </div>
                                <br/>

                                <%
                                    if (SE.getStudentorg() != 1) {
                                %>
                                <div class="card">
                                    <div class="card-header">
                                        <h4>Remarks</h4>
                                    </div>
                                    <div class="card-body">
                                        <table style="width:100%">
                                            <tr>
                                                <th style="width:35%">Step</th>
                                                <th style="width:65%">Remarks</th> 
                                            </tr>
                                            <tr>
                                                <td>Department/Unit Chair</td>
                                                <td><%if (SE.getDeptunitRemarks() != null) {%><%=SE.getDeptunitRemarks()%><%}%><%if (SE.getUnitChairRemarks() != null) {%><%=SE.getUnitChairRemarks()%><%}%></td>
                                            </tr>
                                            <tr>
                                                <td>College ADEALM/SE Director</td>
                                                <td><%if (SE.getExternaldirectorRemarks() != null) {%><%=SE.getExternaldirectorRemarks()%><%}%><%if (SE.getSeDirectorRemarks() != null) {%><%=SE.getSeDirectorRemarks()%><%}%></td>
                                            </tr>
                                            <tr>
                                                <td>College Dean/VP/VC</td>
                                                <td><%if (SE.getDeanRemarks() != null) {%><%=SE.getDeanRemarks()%><%}%><%if (SE.getVpVcRemarks() != null) {%><%=SE.getVpVcRemarks()%><%}%></td>
                                            </tr>
                                            <tr>
                                                <td>Evaluation by COSCA</td>
                                                <td><%if (SE.getCoscaRemarks() != null) {%><%=SE.getCoscaRemarks()%><%}%></td>
                                            </tr>
                                            <tr>
                                                <td>Approval by the Council</td>
                                                <td>
                                                    <b>Br. Michael Broughton: </b> <%if (SE.getLmc1Remarks() != null) {%><%=SE.getLmc1Remarks()%><%}%>
                                                    <br>
                                                    <br>
                                                    <b>Ms. Nelca Villarin: </b> <%if (SE.getLmc2Remarks() != null) {%><%=SE.getLmc2Remarks()%><%}%>
                                                    <br>
                                                    <br>
                                                    <b>Ms. Margarita Perdido: </b> <%if (SE.getLmc3Remarks() != null) {%><%=SE.getLmc3Remarks()%><%}%>
                                                    <br>
                                                    <br>
                                                    <b>Mr. James Laxa: </b> <%if (SE.getLmc4Remarks() != null) {%><%=SE.getLmc4Remarks()%><%}%>
                                                    <br>
                                                    <br>
                                                    <b>Ms. Fritzie De Vera: </b> <%if (SE.getLmc5Remarks() != null) {%><%=SE.getLmc5Remarks()%><%}%>
                                                    <br>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <% }%>
                                <br>

                                <div class="card">
                                    <div class="card-header">
                                        <h4>Cancel SE Program</h4>
                                    </div>
                                    <div class="card-body">
                                        <center>
                                            <b>Reason for Cancellation of SE Program:</b><br>
                                            <%=SE.getReasonforcancel()%>
                                        </center>
                                    </div>
                                </div>
                                <br/>

                                <center>
                                    <button onclick="return window.confirm('Cancel Program?')" class="btn-success" name="cancel" value="<%=SE.getId()%>">Cancel Program</button>
                                </center>
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