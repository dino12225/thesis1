<%-- 
    Document   : UR-home
    Created on : 06 27, 18, 1:25:59 PM
    Author     : Karl Madrid
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="entity.Unit"%>
<%@page import="entity.FF"%>
<%@page import="entity.SE"%>
<%@page import="java.util.Collections"%>
<%@page import="entity.KRA"%>
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

        <title>Create SE 2</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar2.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <style>
            #notifsScroll {
                overflow-y: auto; 
                overflow-x: hidden;
                height: 250px;
            }

            .navbar-btn-profile {
                padding-right: 20px;
                padding-left: 20px;
            }

            .navbar-btn-logout {
                padding-right: 20px;
                padding-left: 20px;
            }
            body{
                background-color: whitesmoke;
                padding-top: 56px;
            }

            #myInput{
                margin-bottom: 20px;
            }

            .card-text{
                margin-bottom: 5px;
            }

            .progressnum{
                font-size: 12px;
                padding-bottom: 10px;
                border-bottom: 1px solid lightgray;
            }

            .krascards:hover {
                background-color: lightgreen;
            }

            tr:hover {
                background-color: lightgreen;
            }

            h2{
                font-size: 40px;
                text-align: left;
                margin-top: 20px;
                border-bottom: 2px solid green;
                padding-bottom: 10px;
                margin-bottom: 25px;
            }

            .budget{
                font-size: 70px; 
                text-align: center; 
                border-bottom: 2px solid lightgray;
                padding-bottom: 20px;
                font-family: 'Montserrat', sans-serif;
            }

            .table{
                border-bottom: 2px solid lightgray;
                margin-bottom: 30px;
            }

            .quickhead{
                border-bottom: 1px solid gray;
                padding-bottom: 10px; 
                margin-bottom: 20px;
            }
            .quickview{
                margin-bottom: 50px;
            }

            .panels{
                margin-top: 20px;
                background-color: white;
                padding-bottom: 15px;
                border-style: solid;
                border-color: lightgray;
                border-width: 1px;
                border-radius: 8px;
            }
            .viewButton{
                text-align: center;
                margin-bottom: 0%;
            }
            
            #notifsScroll {
                overflow-y: auto; 
                overflow-x: hidden;
                height: 250px;
            }

            .navbar-btn-profile {
                padding-right: 20px;
                padding-left: 20px;
            }

            .navbar-btn-logout {
                padding-right: 20px;
                padding-left: 20px;
            }
            html {
                background: #e6e9e9;
                background-image: linear-gradient(270deg, rgb(230, 233, 233) 0%, rgb(216, 221, 221) 100%);
                -webkit-font-smoothing: antialiased;
            }

            table,th,td{
                border:.5px solid
                    black;
            }

            hr{
                background-color:green;
            }

            textarea{
                resize: none;
            } 

            .form-style-5{

                max-width: 95%;
                margin: 10px auto;
                padding: 20px;
                border-radius: 8px;
                font-family: Georgia, "Times New Roman", Times, serif;
                font-size: 12px;
            }

            .form-style-5 fieldset{
                border: none;
            }

            .form-style-5 legend {
                font-size: 1.4em;
                margin-bottom: 10px;
            }

            .form-style-5 label {
                display: block;
                margin-bottom: 8px;
            }

            .form-style-5 input[type="text"],
            .form-style-5 input[type="date"],
            .form-style-5 input[type="datetime"],
            .form-style-5 input[type="email"],
            .form-style-5 input[type="number"],
            .form-style-5 input[type="search"],
            .form-style-5 input[type="time"],
            .form-style-5 input[type="url"],
            .form-style-5 textarea,
            .form-style-5 select {

                font-family: Georgia, "Times New Roman", Times, serif;
                background: rgba(255,255,255,.1);
                border: 0;
                border-radius: 4px;
                font-size: 16px;
                margin-bottom: -5px;
                outline: 0;
                padding: 7px;
                width: 100%;
                box-sizing: border-box; 
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box; 
                background-color: #e8eeef;
                color:black;
                -webkit-box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
                box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;


            }
            .form-style-5 input[type="text"]:focus,
            .form-style-5 input[type="date"]:focus,
            .form-style-5 input[type="datetime"]:focus,
            .form-style-5 input[type="email"]:focus,
            .form-style-5 input[type="number"]:focus,
            .form-style-5 input[type="search"]:focus,
            .form-style-5 input[type="time"]:focus,
            .form-style-5 input[type="url"]:focus,
            .form-style-5 textarea:focus,
            .form-style-5 select:focus{
                background: #d2d9dd;
            }

            .form-style-5 select{
                -webkit-appearance: menulist-button;
                height:35px;
            }

            .form-style-5 .number {
                background: green;
                color: #fff;
                height: 30px;
                width: 30px;
                display: inline-block;
                font-size: 0.8em;
                margin-right: 4px;
                line-height: 30px;
                text-align: center;
                text-shadow: 0 1px 0 rgba(255,255,255,0.2);
                border-radius: 15px 15px 15px 0px;
            }

            .form-style-5 input[type="submit"]
            {
                position: relative;
                display: block;
                padding: 19px 39px 18px 39px;
                color: #FFF;
                margin: 0 auto;
                background: green;
                font-size: 18px;
                text-align: center;
                font-style: normal;
                width: 20%;
                border-radius: 10px;
                border: 1px solid darkgreen;
                border-width: 1px 1px 3px;
                margin-bottom: 10px;
            }
            .form-style-5 input[type="button"]
            {
                position: relative;
                display: block;
                padding: 9px 9px 9px 9px;
                color: #FFF;
                margin: 0 auto;
                background: green;
                font-size: 15px;
                text-align: center;
                font-style: normal;
                border-radius: 10px;
                border: 1px solid darkgreen;
                margin-bottom: 10px;
            }
            .form-style-5 input[type="submit"]:hover,
            .form-style-5 input[type="button"]:hover
            {
                background: #109177;
            }


            h1, h2, h3, h4, h5, h6 {
                color: #222;
                font-weight: 600;
                line-height: 1.3;
            }

            h2 {
                margin-top: 1.3em;
            }

            a {
                color: #0083e8;
            }

            b, strong {
                font-weight: 600;
            }

            samp {
                display: none;
            }

            th {
                background-color: green;
                color: white;
            }

            table {
                border-collapse: collapse;

            }
            th{
                padding:15px;
            }

            #addRowButton {
                padding: 10px;
                padding-right: 17px;
                padding-left: 17px;
            }

            #deleteRowButton {
                padding: 10px;
            }

            #addRowButton, #deleteRowButton {
                display:inline-block;
            }

            .button{
                background-color: #4CAF50;
                border: none;
                color: white;
                padding: 15px 32px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 16px;
            }


            @keyframes colorize {
                0% {
                    -webkit-filter: grayscale(100%);
                    filter: grayscale(100%);
                }
                100% {
                    -webkit-filter: grayscale(0%);
                    filter: grayscale(0%);
                }
            }
        </style>
        
                <script type='text/javascript'>

            function addRow() {
                var count = document.getElementById("countresponsible").value;
                var table = document.getElementById("responsibletable");
                var rows = document.getElementById("responsibletable").rows.length;
                var row = table.insertRow(rows);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                cell1.innerHTML = "<td><textarea rows = '1' cols = '50%' name ='responsiblename" + count + "'></textarea></td>";
                cell2.innerHTML = "<td><textarea rows = '1' cols = '50%' name ='responsibleemail" + count + "'></textarea></td>";
                count++;
                document.getElementById("countresponsible").setAttribute('value', count);
            }

            function deleteRow() {
                var count = document.getElementById("countresponsible").value;
                var rows = document.getElementById("responsibletable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("responsibletable").deleteRow(rows - 1);
                    count--;
                    document.getElementById("countresponsible").setAttribute('value', count);
                } else {

                }

            }

            function addRow2() {

                var count = document.getElementById("countproject").value;
                var table = document.getElementById("projectplantable");
                var rows = document.getElementById("projectplantable").rows.length;
                var row = table.insertRow(rows);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                var cell5 = row.insertCell(4);
                cell1.innerHTML = "<td><input type ='date' name='date" + count + "'/></td>";
                cell2.innerHTML = "<textarea rows = '2' cols = '25%' name ='activity" + count + "'></textarea>";
                cell3.innerHTML = "<textarea rows = '2' cols = '25%' name ='time" + count + "'></textarea>";
                cell4.innerHTML = "<textarea rows = '2' cols = '25%' name ='timeend" + count + "'></textarea>";
                cell5.innerHTML = "<textarea rows = '2' cols = '25%' name ='venue" + count + "'></textarea>";
                count++;
                document.getElementById("countproject").setAttribute('value', count);
            }

            function deleteRow2() {
                var count = document.getElementById("countproject").value;

                var rows = document.getElementById("projectplantable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("projectplantable").deleteRow(rows - 1);
                    count--;
                    document.getElementById("countproject").setAttribute('value', count);
                } else {

                }

            }

            function addRow3() {
                var count = document.getElementById("countexpenses").value;
                var table = document.getElementById("breakdowntable");
                var rows = document.getElementById("breakdowntable").rows.length;
                var row = table.insertRow(rows - 1);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                cell1.innerHTML = "<td><textarea rows = '2' cols = '20%' name ='seitem" + count + "'></textarea></td>";
                cell2.innerHTML = "<td><textarea rows = '2' cols = '20%' name ='seunitcost" + count + "'></textarea></td>";
                cell3.innerHTML = "<td><textarea rows = '2' cols = '20%' name ='sequantity" + count + "'></textarea></td>";
                cell4.innerHTML = "<td><textarea rows = '2' cols = '20%' name ='sesubtotal" + count + "'></textarea></td>";
                count++;
                document.getElementById("countexpenses").setAttribute('value', count);
            }

            function deleteRow3() {
                var count = document.getElementById("countexpenses").value;
                var rows = document.getElementById("breakdowntable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("breakdowntable").deleteRow(rows - 2);
                    count--;
                    document.getElementById("countexpenses").setAttribute('value', count);
                } else {

                }

            }

            function addRow4() {
                var table = document.getElementById("provisionstable");
                var rows = document.getElementById("provisionstable").rows.length;
                var row = table.insertRow(rows);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                cell1.innerHTML = '<td><textarea rows = "1" cols = "50%" name ="provisionsname"></textarea></td>';
                cell2.innerHTML = '<td><textarea rows = "1" cols = "50%" name ="provisionsemail"></textarea></td>';
            }

            function deleteRow4() {
                var rows = document.getElementById("provisionstable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("provisionstable").deleteRow(rows - 1);
                } else {

                }

            }

        </script>

        <script type="text/javascript">
            <%
                if (request.getAttribute("successSE") != null) {

            %>
            $("document").ready(function () {

                alert("<%=request.getAttribute("successSE")%>");
            });

            <%
                }

                if (request.getAttribute("successFF") != null) {

            %>
            $("document").ready(function () {

                alert("<%=request.getAttribute("successFF")%>");
            });

            <%
                }
            %>
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
                        <a class="nav-link" href="UR-home.jsp" id="smallerscreenmenu">
                            Home
                        </a>
                        <a class="nav-link" href="MULTIPLE-faithFormationProgramsList.jsp" id="smallerscreenmenu">
                            Programs
                        </a>
                        <a class="nav-link" href="MULTIPLE-unitsList.jsp" id="smallerscreenmenu">
                            Units
                        </a>
                        <a class="nav-link" href="MULTIPLE-communityList.jsp" id="smallerscreenmenu">
                            Communities
                        </a>
                        <a class="nav-link" href="MULTIPLE-krasList.jsp" id="smallerscreenmenu">
                            Key Result Areas
                        </a>
                        <a class="nav-link" href="MULTIPLE-evaluationSEResponsesList.jsp" id="smallerscreenmenu">
                            Evaluation Forms
                        </a>
                    </li>
                </ul>
            </div>
            <ul class="navbar-nav mr auto">
                <div class="nav-button">
                    <button type="button" class="btn btn-info navbar-btn-profile">
                        <i class="fa fa-user-circle"></i>
                    </button>
                </div>
                <div class="nav-button">
                    <div class="dropdown">
                        <button type="button" class="btn btn-info navbar-btn-notifications" href="#" data-toggle="dropdown">
                            <span class="badge badge-pill badge-primary" style="background-color:red; color:white; float:right;margin-bottom:-20px;">!</span> 
                            <i class="fa fa-bell"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <div id="notifsScroll">
                                <%
                                    UserDAO UserDAO = new UserDAO();
                                    ArrayList<Notification> n = new ArrayList();
                                    n = UserDAO.retrieveNotificationByUserID(Integer.parseInt(session.getAttribute("userID").toString()));

                                    for (int i = 0; i < n.size(); i++) {
                                %>
                                <li class="notification-box" href="#">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <strong class="notificationBoxHeader"><%=n.get(i).getTitle()%></strong>
                                            <div class="notificationBoxMessage">
                                                <%=n.get(i).getBody()%>
                                            </div>
                                        </div>    
                                    </div>
                                </li>

                                <%
                                    }
                                %> 
                            </div>
                        </ul>
                    </div>
                </div>
                <div class="nav-button">
                    <a href="index.jsp" class="btn btn-info navbar-btn-logout">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </div>
            </ul>
        </nav>
        <!-- NavBar END -->

        <!-- Bootstrap row -->
        <div class="row" id="body-row">

            <!-- Sidebar -->
            <div id="sidebar-container" class="sidebar-expanded d-none d-md-block">
                <%if (session.getAttribute("unit").toString().equals("Center For Social Concern and Action (COSCA)")) {

                %>

                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="COSCA-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-createSE.jsp"  class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create SE Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-createFF.jsp"  class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create FF Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                        <%                            if (Integer.parseInt(session.getAttribute("userID").toString()) == 18) {
                        %>
                        <a href="MULTIPLE-seProgramsForApproval.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">For Approval</span>
                        </a>
                        <%
                            }
                        %>                        

                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-addCommunity.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Community</span>
                        </a>
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-evaluationSEResponsesList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-pencil-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Evaluation Forms</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                </ul>

                <%                        }
                %>

                <%
                    if (session.getAttribute("unit").equals("Office of the Vice President for Lasallian Mission (OVPLM)")) {
                %>

                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="OVPLM-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-createSE.jsp"  class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create SE Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-createFF.jsp"  class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create FF Program Proposal</span>
                        </a>

                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                        <%
                            if (Integer.parseInt(session.getAttribute("userID").toString()) == 16 || Integer.parseInt(session.getAttribute("userID").toString()) == 17 || Integer.parseInt(session.getAttribute("userID").toString()) == 27) {
                        %>
                        <a href="MULTIPLE-seProgramsForApproval.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">For Approval</span>
                        </a>
                        <%
                            }
                        %>
                    </div>
                    <a href="#submenuUnits" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuUnits" class="collapse sidebar-submenu">
                        <a href="OVPLM-addUnit.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Unit</span>
                        </a>
                        <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                    </div>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-addCommunity.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Community</span>
                        </a>
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuKRA" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuKRA" class="collapse sidebar-submenu">
                        <a href="OVPLM-createKRA.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create KRA</span>
                        </a>
                        <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Edit KRA</span>
                        </a>
                        <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">KRAs List</span>
                        </a>
                    </div>
                    <a href="#submenuReports" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-bar-chart fa-fw mr-2"></span>
                            <span class="menu-collapsed">Reports</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuReports" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-seReportsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Accomplishment</span>
                        </a>
                        <a href="OVPLM-budgetPerformanceReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Budgets</span>
                        </a>
                        <a href="OVPLM-perCommunityReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Programs</span>
                        </a>
                        <a href="OVPLM-perUnitReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                        <a href="OVPLM-termEndReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Term-End</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-evaluationSEResponsesList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-pencil-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Evaluation Forms</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                </ul>
                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Dean of Student Affairs")) {
                %>
                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="DSA-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuReports" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-bar-chart fa-fw mr-2"></span>
                            <span class="menu-collapsed">Reports</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuReports" class="collapse sidebar-submenu">
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Accomplishment</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Budgets</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Programs</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                        <a href="OVPLM-termEndReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Term-End</span>
                        </a>
                    </div>
                </ul>
                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Laguna Campus Lasallian Mission")) {
                %>

                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="LCLM-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuReports" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-bar-chart fa-fw mr-2"></span>
                            <span class="menu-collapsed">Reports</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuReports" class="collapse sidebar-submenu">
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Accomplishment</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Budgets</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Programs</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                        <a href="OVPLM-termEndReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Term-End</span>
                        </a>
                    </div>
                </ul>

                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Lasallian Mission Director")) {
                %>
                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="LMD-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuReports" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-bar-chart fa-fw mr-2"></span>
                            <span class="menu-collapsed">Reports</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuReports" class="collapse sidebar-submenu">
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Accomplishment</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Budgets</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Programs</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                        <a href="OVPLM-termEndReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Term-End</span>
                        </a>
                    </div>
                </ul>

                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Lasallian Pastoral Office (LSPO)")) {
                %>
                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="LSPO-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-createSE.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create SE Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-createFF.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create FF Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                        <a href="MULTIPLE-seProgramsForApproval.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">For Approval</span>
                        </a>  
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-addCommunity.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Community</span>
                        </a>
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-evaluationSEResponsesList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-pencil-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Evaluation Forms</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                </ul>
                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Office of Personnel Management Director")) {
                %>
                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="OPMD-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-createSE.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create SE Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-createFF.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create FF Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                    </div>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-addCommunity.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Community</span>
                        </a>
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>

                    <a href="MULTIPLE-evaluationSEResponsesList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-pencil-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Evaluation Forms</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                </ul>

                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("College of Computer Studies (CCS)") || (session.getAttribute("unit").toString().equals("Br. Andrew Gonzales College of Education")) || (session.getAttribute("unit").toString().equals("College of Law")) || (session.getAttribute("unit").toString().equals("College of Liberal Arts"))
                            || (session.getAttribute("unit").toString().equals("College of Science")) || (session.getAttribute("unit").toString().equals("Gokongwei College of Engineering (GCOE)")) || (session.getAttribute("unit").toString().equals("Ramon V. Del Rosario College of Business (RVRCOB)")) || (session.getAttribute("unit").toString().equals("School of Economics (SOE)"))) {
                %>

                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="UR-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-createSE.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create SE Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-createFF.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Create FF Program Proposal</span>
                        </a>
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-addCommunity.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Add Community</span>
                        </a>
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>

                    <a href="MULTIPLE-evaluationSEResponsesList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-pencil-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Evaluation Forms</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                </ul>

                <%
                    }
                %>
                
                <%
                    if (session.getAttribute("unit").equals("Lasallian Mission Council")) {
                %>
                <ul class="list-group sticky-top sticky-offset">
                    <!-- Menu with submenu -->
                    <a href="LMC-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategoryActive">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-home fa-fw mr-2"></span>
                            <span class="menu-collapsed">Home</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuProposals" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-folder-open fa-fw mr-2"></span>
                            <span class="menu-collapsed">Programs</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuProposals" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-socialEngagementProgramsList.jsp" class="list-group-item list-group-item-action" id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">SE Programs</span>
                        </a>
                        <a href="MULTIPLE-faithFormationProgramsList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">FF Programs</span>
                        </a>
                        <a href="MULTIPLE-seProgramsForApproval.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">For Approval</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-unitsList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-group fa-fw mr-2"></span>
                            <span class="menu-collapsed">Units</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuCommunity" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-building fa-fw mr-2"></span>
                            <span class="menu-collapsed">Communities</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuCommunity" class="collapse sidebar-submenu">
                        <a href="MULTIPLE-communityList.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                    </div>
                    <a href="MULTIPLE-viewBudget.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-money fa-fw mr-2"></span>
                            <span class="menu-collapsed">Budget</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="MULTIPLE-krasList.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-check-square-o fa-fw mr-2"></span>
                            <span class="menu-collapsed">Key Result Areas</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <a href="#submenuReports" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
                        <div class="d-flex w-100 justify-content-start align-items-center">
                            <span class="fa fa-bar-chart fa-fw mr-2"></span>
                            <span class="menu-collapsed">Reports</span>
                            <span class="submenu-icon ml-auto"></span>
                        </div>
                    </a>
                    <div id="submenuReports" class="collapse sidebar-submenu">
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Accomplishment</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Budgets</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Communities</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Programs</span>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Units</span>
                        </a>
                        <a href="OVPLM-termEndReport.jsp" class="list-group-item list-group-item-action"  id="subMenuCategoryBox">
                            <span class="menu-collapsed" id="subMenuCategory">Term-End</span>
                        </a>
                    </div>
                </ul>

                <%
                    }
                %>
                <!-- List Group END-->
            </div>
            <!-- sidebar-container END -->

            <!-- MAIN -->
            <div class="col py-3">
    <hr size="5" noshade>    
    <center><h1>Social Engagement Proposal</h1></center>
    <hr size="5" noshade>


    <div class="form-style-5">
        <form action = "addSE2" method="post">

            <%
                SE SE = new SE();
                SE = (SE) session.getAttribute("SE");
            %>
            <legend><b>Work Plan - Implementation Date: <%=SE.getActualDate()%></b></legend>
            <fieldset>
                <center><table style = "width:100%" id = "projectplantable">
                        <tr>
                            <th>Date</th>
                            <th>Activity</th>
                            <th>Time Start</th>
                            <th>Time End</th>
                            <th>Venue</th>
                        </tr>
                        <tr>
                            <td><input type ="date" name="date0"/></td>
                            <td><textarea rows = "2" cols = "25%" name ="activity0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="time0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="timeend0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="venue0"></textarea></td>

                        <input type="hidden" value="1" id="countproject" name="countproject">
                        </tr>
                    </table></center>
                <br>
                <div>
                    <center><input type ="button" id="addRowButton" onclick ="addRow2()" value="Add Row">
                        <input style="background-color:red; border: red;" type ="button" id="addRowButton" onclick ="deleteRow2()" value="Delete Row"></center>
                </div>
                <br><br>
            </fieldset>
            <%

                DecimalFormat df = new DecimalFormat("#,###,###,###.##");

                UserDAO = new UserDAO();
            %>
          
            
            <legend><b>Breakdown of Expenses - Amount Requested: PHP <%=df.format(SE.getTotalAmount())%></b></legend>
            <input type="hidden" value="1" id="countexpenses" name="countexpenses">
            <fieldset>
                <center><table style = "width:100%" id="breakdowntable">
                        <tr>
                            <th>Item</th>
                            <th>Unit Cost</th>
                            <th>Quantity</th>
                            <th>Subtotal</th>
                        </tr>
                        <tr>
                            <td><textarea rows = "2" cols = "25%" name ="seitem0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="seunitcost0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="sequantity0"></textarea></td>
                            <td><textarea rows = "2" cols = "25%" name ="sesubtotal0"></textarea></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>Grand Total: </td>
                        </tr>
                    </table></center>
                <br>
                <center><input type ="button" id="addRowButton" onclick ="addRow3()" value="Add Row">
                    <input style="background-color:red; border: red;" type ="button" id="deleteRowButton" onclick ="deleteRow3()" value="Delete Row"></center>
                    <br><br>
            </fieldset>

            <legend><b>Expected Participants vs. Total Population of the Unit</b></legend>
            <%
                Unit u = new Unit();
                u = UserDAO.getUnitByName(session.getAttribute("unit").toString());
            %>
            <fieldset>
                <center><table style = "width:75%" id="breakdowntable">
                        <tr>
                            <th>Sector</th>
                            <th>Total<br> Population</th>
                            <th>Expected Number<br> of Participants</th>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;Academic Staff from the Unit</td>
                            <td><textarea rows = "1" cols = "10%" name ="seacademictotal"><%=u.getSaf()%></textarea></td>
                            <td><textarea rows = "1" cols = "10%" name ="seacademicexpected"></textarea></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;Support Staff from the Unit</td>
                            <td><textarea rows = "1" cols = "10%" name ="sesupporttotal"><%=u.getStaff()%></textarea></td>
                            <td><textarea rows = "1" cols = "10%" name ="sesupportexpected"></textarea></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;Undergraduate Students</td>
                            <td><textarea rows = "1" cols = "10%" name ="seundergraduatetotal"><%=u.getStudent()%></textarea></td>
                            <td><textarea rows = "1" cols = "10%" name ="seundergraduateexpected"></textarea></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;Graduate Students</td>
                            <td><textarea rows = "1" cols = "10%" name ="segraduatetotal"><%=u.getApsp()%></textarea></td>
                            <td><textarea rows = "1" cols = "10%" name ="segraduateexpected"></textarea></td>
                        </tr>
                        
                        <tr>
                            <td>&nbsp;&nbsp;Others</td>
                            <td><textarea rows = "1" cols = "10%" name ="segraduatetotal"></textarea></td>
                            <td><textarea rows = "1" cols = "10%" name ="segraduateexpected"></textarea></td>
                        </tr>

                    </table></center>
                    <br><br>
            </fieldset>

            <fieldset>
                <legend><b>Persons Responsible</b></legend>
                <input type="hidden" value="1" id="countresponsible" name="countresponsible">
                <center><table style = "width:100%" id="responsibletable">
                        <tr>
                            <th>Name (First name, Last name)</th>
                            <th>Email</th>
                        </tr>
                        <tr>    
                            <td><textarea rows = "1" cols = "50%" name ="responsiblename0"></textarea></td>
                            <td><textarea rows = "1" cols = "50%" name ="responsibleemail0"></textarea></td>
                        </tr>
                    </table></center>
                <br>
                <center><input type ="button" id="addRowButton" onclick ="addRow()" value="Add Row">
                    <input style="background-color:red; border: red;" type ="button" id="deleteRowButton" onclick ="deleteRow()" value="Delete Row"></center>
                    <br>
            </fieldset>

            <fieldset>
                <legend><b>Endorsement</b></legend>
                <table style = "width:30%" id="responsibletable">
                        <tr>
                            <th>Position</th>
                        </tr>
                        <tr>    
                            <td>&nbsp;&nbsp;Department/Unit Chair</td>
                        </tr>
                        <tr>    
                            <td>&nbsp;&nbsp;External Affairs/Social Engagement Director</td>
                        </tr>
                        <tr>    
                            <td>&nbsp;&nbsp;Dean/VP/VC</td>
                        </tr>
                    </table>
                <br><br>
            </fieldset>
            <center><a href="MULTIPLE-createSEChecklist.jsp" target="_blank"><button type="button" class="button">View Checklist</button></a>
                <br><br>
                <button type="submit" class="button">Submit</button></center>
        </form>
    </div>
            </div>

        </div>
        <!-- body-row END -->

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





