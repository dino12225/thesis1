<%-- 
    Document   : UR-home
    Created on : 06 27, 18, 1:25:59 PM
    Author     : Karl Madrid
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Community"%>
<%@page import="dao.OvplmDAO"%>
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

        <title>Create KRA</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar2.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

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
                border-radius: 0px;
                font-size: 16px;
                margin-bottom: -5px;
                outline: 0;
                padding: 7px;
                width:100%;
                box-sizing: border-box; 
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box; 
                background-color: #d2d9dd;
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

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type='text/javascript'>


            function addRow2() {
                var count = document.getElementById("countmeasuregoal1").value;
                var table = document.getElementById("goaltable");
                var rows = document.getElementById("goaltable").rows.length;
                var row = table.insertRow(rows);

                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);

                cell1.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal1measure" + count + "'></textarea>";
                cell2.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal1target" + count + "'></textarea>";
                count++;
                document.getElementById("countmeasuregoal1").setAttribute('value', count);
            }

            function deleteRow2() {
                var count = document.getElementById("countmeasuregoal1").value;
                var rows = document.getElementById("goaltable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("goaltable").deleteRow(rows - 1);
                    count--;
                    document.getElementById("countmeasuregoal1").setAttribute('value', count);
                } else {

                }
            }

            function addRow3() {
                var count = document.getElementById("countmeasuregoal2").value;
                var table = document.getElementById("goaltable2");
                var rows = document.getElementById("goaltable2").rows.length;
                var row = table.insertRow(rows);

                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);

                cell1.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal2measure" + count + "'></textarea>";
                cell2.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal2target" + count + "'></textarea>";
                count++;
                document.getElementById("countmeasuregoal2").setAttribute('value', count);

            }

            function deleteRow3() {
                var count = document.getElementById("countmeasuregoal2").value;
                var rows = document.getElementById("goaltable2").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("goaltable2").deleteRow(rows - 1);
                    count--;
                    document.getElementById("countmeasuregoal2").setAttribute('value', count);
                } else {

                }
            }

            function addRow4() {
                var count = document.getElementById("countmeasuregoal3").value;
                var table = document.getElementById("goaltable3");
                var rows = document.getElementById("goaltable3").rows.length;
                var row = table.insertRow(rows);

                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);

                cell1.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal3measure" + count + "'></textarea>";
                cell2.innerHTML = "<textarea rows = '2' cols = '10%' name ='goal3target" + count + "'></textarea>";
                count++;
                document.getElementById("countmeasuregoal3").setAttribute('value', count);

            }

            function deleteRow4() {
                var count = document.getElementById("countmeasuregoal3").value;
                var rows = document.getElementById("goaltable3").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("goaltable3").deleteRow(rows - 1);
                    count--;
                    document.getElementById("countmeasuregoal3").setAttribute('value', count);
                } else {

                }
            }

            $(document).ready(function () {
                $("#addgoal").click(function () {
                    $("#container").append('<h2>Goal 1: </h2><textarea rows = "1" cols = "1%" name ="goalnumber"></textarea><center><table style = "width:100%" id="goaltable"><tr><th>Measure</th><th>Description</th><th>Target</th></tr><tr><td><textarea rows = "2" cols = "1%" name ="measure"></textarea></td><td><textarea rows = "2" cols = "10%" name ="description"></textarea></td><td><textarea rows = "2" cols = "1%" name ="target"></textarea></td></tr><tr><td><textarea rows = "2" cols = "1%" name ="measure"></textarea></td><td><textarea rows = "2" cols = "10%" name ="description"></textarea></td><td><textarea rows = "2" cols = "1%" name ="target"></textarea></td></tr><tr><td><textarea rows = "2" cols = "1%" name ="measure"></textarea></td><td><textarea rows = "2" cols = "10%" name ="description"></textarea></td><td><textarea rows = "2" cols = "1%" name ="target"></textarea></td></tr></table></center><br><center><input type ="button" id="addRowButton" onclick ="addRow2()" value="Add row">&nbsp;<input type ="button" id="deleteRowButton" onclick ="deleteRow2()" value="Delete row"></center>');

                });
            });


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
                        <%                            if (session.getAttribute("position").toString().equals("COSCA - Sir Neil Position")) {
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
                            if (session.getAttribute("position").toString().equals("OVPLM - Vice President for Lasallian Mission") || session.getAttribute("position").toString().equals("OVPLM - Executive Officer")) {
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
                    if (session.getAttribute("unit").equals("Dean of Student Affairs (DSA)")) {
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
                    if (session.getAttribute("unit").equals("Laguna Campus Lasallian Mission (LCLM)")) {
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
                    if (session.getAttribute("unit").equals("Lasallian Mission Director (LMD)")) {
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
                        <%
                            if (session.getAttribute("position").toString().equals("LSPO - Director")) {
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
                <%
                    }
                %>

                <%
                    if (session.getAttribute("unit").equals("Office of Personnel Management Director (OPMD)")) {
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
                    if (session.getAttribute("unit").equals("College of Computer Studies (CCS)") || (session.getAttribute("unit").toString().equals("Br. Andrew Gonzales College of Education (BAGCED)")) || (session.getAttribute("unit").toString().equals("College of Law (COL)")) || (session.getAttribute("unit").toString().equals("College of Liberal Arts (CLA)"))
                            || (session.getAttribute("unit").toString().equals("College of Science (COS)")) || (session.getAttribute("unit").toString().equals("Gokongwei College of Engineering (GCOE)")) || (session.getAttribute("unit").toString().equals("Ramon V. Del Rosario College of Business (RVR-COB)")) || (session.getAttribute("unit").toString().equals("School of Economics (SOE)"))) {
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
                    if (session.getAttribute("unit").equals("Lasallian Mission Council (LMC)")) {
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
                        <%
                            if (session.getAttribute("position").toString().equals("DSA - Dean") || session.getAttribute("position").toString().equals("LCLM - Executive Director")
                                    || session.getAttribute("position").toString().equals("LSPO - Director") || session.getAttribute("position").toString().equals("COSCA - Director")
                                    || session.getAttribute("position").toString().equals("DLSU Vocations Director") || session.getAttribute("position").toString().equals("TRED - Chair")
                                    || session.getAttribute("position").toString().equals("TRED - Director LCC")) {
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
                <center><h1>Create KRA</h1></center>
                <hr size="5" noshade>

                <div class="form-style-5">
                    <form action = "addKRA" method="post">
                            <%
                                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                                java.util.Date javaDate = new java.util.Date();
                                java.sql.Date Datejava = new java.sql.Date(javaDate.getTime());
                            %>
                            <fieldset>
                                <legend>Date: <input type = "date" rows = "1" cols = "1%" name ="date" value="<%=Datejava%>"/></legend>
                            </fieldset>
                            
                            <fieldset>
                                <legend>KRA Name: <textarea rows = "1" cols = "1%" name ="kraname"></textarea></legend>
                            </fieldset>

                            <div class="add-goal">
                                <h4>Goal 1: </h4><textarea rows = "1" cols = "1%" name ="goalname1"></textarea>
                                <center><table style = "width:100%" id="goaltable">
                                        <tr>
                                            <th>Measure</th>
                                            <th>Target</th>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal1measure1"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal1target1"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal1measure2"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal1target2"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal1measure3"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal1target3"></textarea></td>
                                        </tr>

                                        <input type="hidden" value="4" id="countmeasuregoal1" name="countmeasuregoal1">
                                    </table></center>
                                <br>
                                <center>
                                    <input type ="button" id="addRowButton" onclick ="addRow2()" value="Add row">  
                                    <input type ="button" id="deleteRowButton" onclick ="deleteRow2()" value="Delete row">
                                </center>
                                <br>
                            </div>

                            <div class="add-goal">
                                <h4>Goal 2: </h4><textarea rows = "1" cols = "1%" name ="goalname2"></textarea>
                                <center><table style = "width:100%" id="goaltable2">
                                        <tr>
                                            <th>Measure</th>
                                            <th>Target</th>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal2measure1"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal2target1"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal2measure2"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal2target2"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal2measure3"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal2target3"></textarea></td>
                                        </tr>

                                        <input type="hidden" value="4" id="countmeasuregoal2" name="countmeasuregoal2">
                                    </table></center>
                                <br>
                                <center>
                                    <input type ="button" id="addRowButton" onclick ="addRow3()" value="Add row">  
                                    <input type ="button" id="deleteRowButton" onclick ="deleteRow3()" value="Delete row">
                                </center>
                                <br>
                            </div>

                            <div class="add-goal">
                                <h4>Goal 3: </h4><textarea rows = "1" cols = "1%" name ="goalname3"></textarea>
                                <center><table style = "width:100%" id="goaltable3">
                                        <tr>
                                            <th>Measure</th>
                                            <th>Target</th>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal3measure1"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal3target1"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal3measure2"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal3target2"></textarea></td>
                                        </tr>
                                        <tr>    
                                            <td><textarea rows = "2" cols = "50%" name ="goal3measure3"></textarea></td>
                                            <td><textarea rows = "2" cols = "50%" name ="goal3target3"></textarea></td>
                                        </tr>

                                        <input type="hidden" value="4" id="countmeasuregoal3" name="countmeasuregoal3">
                                    </table></center>
                                <br>
                                <center>
                                    <input type ="button" id="addRowButton" onclick ="addRow4()" value="Add row">  
                                    <input type ="button" id="deleteRowButton" onclick ="deleteRow4()" value="Delete row">
                                </center>
                                <br>

                                <div id="container"></div>
                            </div>
                            <br>
                            <center><button class="button" type="submit" value="Submit" >Create KRA</button></center>
                    </form>
                </div>
            </div>

        </div>
        <!-- body-row END -->


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