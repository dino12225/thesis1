<%-- 
    Document   : UR-home
    Created on : 06 27, 18, 1:25:59 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.Notification"%>
<%@page import="entity.SE"%>
<%@page import="entity.KRA"%>
<%@page import="dao.OvplmDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Collections"%>
<%@page import="entity.Community"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Edit SE Program</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/formstyle5.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

        <style>
            html{
                font-size:14px;
            }
            .navbar{
                height:8%;
            }
            .sidebar-expanded{
                margin-top:0.1%;
            }
            
            #notifsScroll {
                overflow-y: auto; 
                overflow-x: hidden;
                height: 250px;
            }

            #myInput{
                margin-bottom: 20px;
            }

            .card-text{
                margin-bottom: 5px;
            }

            .krascards:hover {
                background-color: lightgreen;
            }

            tr:hover {
                background-color: lightgreen;
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

        <script type="text/javascript">
            <%
                UserDAO UserDAO = new UserDAO();

                SE SE = new SE();
                SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getAttribute("seID").toString()));

                OvplmDAO OvplmDAO = new OvplmDAO();
                ArrayList<KRA> k = new ArrayList();
                k = OvplmDAO.retrieveKRA();

            %>

            function changegoal(c1, c2) {

            var c1 = document.getElementById(c1);
            var c2 = document.getElementById(c2);
            c2.innerHTML = "";
            <%                    for (int i = 0; i < k.size(); i++) {
            %>
            if (c1.value == "<%=k.get(i).getId()%>") {
            var optionArray = ["|",<%for (int j = 0; j < k.get(i).getGoals().size(); j++) {%>"<%=k.get(i).getGoals().get(j).getGoalID()%>|<%=k.get(i).getGoals().get(j).getName()%>",<%}%>];
                }

            <%
                }
            %>

                for (var option in optionArray) {
                var pair = optionArray[option].split("|");
                var newOption = document.createElement("option");
                newOption.value = pair[0];
                newOption.innerHTML = pair[1];
                c2.options.add(newOption);
                }

                }

                function changemeasure(c1, c2) {

                var c1 = document.getElementById(c1);
                var c2 = document.getElementById(c2);
                c2.innerHTML = "";
            <%
                for (int i = 0; i < k.size(); i++) {
                    for (int j = 0; j < k.get(i).getGoals().size(); j++) {
            %>

                if (c1.value == "<%=k.get(i).getGoals().get(j).getGoalID()%>") {
                var optionArray = ["|",<%for (int l = 0; l < k.get(i).getGoals().get(j).getMeasures().size(); l++) {%>"<%=k.get(i).getGoals().get(j).getMeasures().get(l).getMeasureID()%>|<%=k.get(i).getGoals().get(j).getMeasures().get(l).getDescription()%>",<%}%>];
                    }

            <%
                    }
                }
            %>

                    for (var option in optionArray) {
                    var pair = optionArray[option].split("|");
                    var newOption = document.createElement("option");
                    newOption.value = pair[0];
                    newOption.innerHTML = pair[1];
                    c2.options.add(newOption);
                    }

                    }
        </script>

        <script type='text/javascript'>

            function addRow() {
            var count = document.getElementById("countresponsible").value;
            var table = document.getElementById("responsibletable");
            var rows = document.getElementById("responsibletable").rows.length;
            var row = table.insertRow(rows);
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            cell1.innerHTML = "<td><textarea style='border-radius: 0px;'  rows = '1' cols = '50%' name ='responsiblename" + count + "'></textarea></td>";
            cell2.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '1' cols = '50%' name ='responsibleemail" + count + "'></textarea></td>";
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
            cell1.innerHTML = "<td><input style='border-radius: 0px;' type ='date' name='date" + count + "'/></td>";
            cell2.innerHTML = "<textarea style='border-radius: 0px;' rows = '2' cols = '25%' name ='activity" + count + "'></textarea>";
            cell3.innerHTML = "<textarea style='border-radius: 0px;' rows = '2' cols = '25%' name ='time" + count + "'></textarea>";
            cell4.innerHTML = "<textarea style='border-radius: 0px;' rows = '2' cols = '25%' name ='timeend" + count + "'></textarea>";
            cell5.innerHTML = "<textarea style='border-radius: 0px;' rows = '2' cols = '25%' name ='venue" + count + "'></textarea>";
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
            cell1.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '2' cols = '20%' name ='seitem" + count + "'></textarea></td>";
            cell2.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '2' cols = '20%' name ='seunitcost" + count + "'></textarea></td>";
            cell3.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '2' cols = '20%' name ='sequantity" + count + "'></textarea></td>";
            cell4.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '2' cols = '20%' name ='sesubtotal" + count + "'></textarea></td>";
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
            cell1.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '1' cols = '50%' name ='provisionsname'></textarea></td>";
            cell2.innerHTML = "<td><textarea style='border-radius: 0px;' rows = '1' cols = '50%' name ='provisionsemail'></textarea></td>";
            }

            function deleteRow4() {
            var rows = document.getElementById("provisionstable").rows.length;
            if (rows - 1 > 0) {
            document.getElementById("provisionstable").deleteRow(rows - 1);
            } else {

            }

            }

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
                <hr size="5" noshade>    
                <center><h2>Social Engagement Proposal</h2></center>
                <hr size="5" noshade>
                <br>
                
                <%
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date javaDate = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                %>

                <div class="form-style-5">
                    <form action = "editSE2" method="post">
                        <%
                            SE = UserDAO.retrieveSEBySEID(Integer.parseInt(request.getParameter("seID").toString()));
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
                                    <%
                                        for (int i = 0; i < SE.getWorkplan().size(); i++) {
                                    %>
                                    <tr>

                                        <td><input style="border-radius: 0px;" type ="date" name="date<%=i%>" value="<%=SE.getWorkplan().get(i).getDate()%>" min="<%=sqlDate%>"/></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="activity<%=i%>"><%=SE.getWorkplan().get(i).getActivity()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="time<%=i%>"><%=SE.getWorkplan().get(i).getTimestarttimeend()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="timeend<%=i%>"><%=SE.getWorkplan().get(i).getTimestarttimeend2()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="venue<%=i%>"><%=SE.getWorkplan().get(i).getVenue()%></textarea></td>

                                    </tr>
                                    <%
                                        }
                                    %>
                                    <input type="hidden" value="<%=SE.getWorkplan().size()%>" id="countproject" name="countproject">
                                </table></center>
                            <br>
                            <div>
                                <center><input type ="button" id="addRowButton" onclick ="addRow2()" value="Add Row">
                                    <input style="background-color:red; border: red;" type ="button" id="addRowButton" onclick ="deleteRow2()" value="Delete Row"></center>
                            </div>
                        </fieldset>


                        <legend><b>Breakdown of Expenses</b></legend>
                        <input type="hidden" value="<%=SE.getExpenses().size()%>" id="countexpenses" name="countexpenses">
                        <fieldset>
                            <center><table style = "width:100%" id="breakdowntable">
                                    <tr>
                                        <th>Item</th>
                                        <th>Unit Cost</th>
                                        <th>Quantity</th>
                                        <th>Subtotal</th>
                                    </tr>
                                    <%
                                        for (int i = 0; i < SE.getExpenses().size(); i++) {
                                    %>
                                    <tr>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="seitem<%=i%>"><%=SE.getExpenses().get(i).getItem()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="seunitcost<%=i%>"><%=SE.getExpenses().get(i).getUnitcost()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="sequantity<%=i%>"><%=SE.getExpenses().get(i).getQuantity()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "2" cols = "25%" name ="sesubtotal<%=i%>"><%=SE.getExpenses().get(i).getSubtotal()%></textarea></td>
                                    </tr>
                                    <%
                                        }
                                    %>
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
                        </fieldset>

                        <legend><b>Expected Participants vs. Total Population of the Unit</b></legend>
                        <fieldset>
                            <center><table style = "width:75%" id="breakdowntable">
                                    <tr>
                                        <th>Sector</th>
                                        <th>Total<br> Population</th>
                                        <th>Expected Number<br> of Participants</th>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;&nbsp;Academic Staff from the Unit</td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="seacademictotal"><%=SE.getTotalpopulationAcademicStaff()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="seacademicexpected"><%=SE.getExpectedAcademicStaff()%></textarea></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;&nbsp;Support Staff from the Unit</td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="sesupporttotal"><%=SE.getTotalpopulationSupportStaff()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="sesupportexpected"><%=SE.getExpectedSupportStaff()%></textarea></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;&nbsp;Undergraduate Students</td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="seundergraduatetotal"><%=SE.getTotalpopulationUndergraduate()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="seundergraduateexpected"><%=SE.getExpectedUndergraduate()%></textarea></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;&nbsp;Graduate Students</td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="segraduatetotal"><%=SE.getTotalPopulationGraduate()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "10%" name ="segraduateexpected"><%=SE.getExpectedGraduate()%></textarea></td>
                                    </tr>

                                </table></center>
                            <br>
                        </fieldset>

                        <fieldset>
                            <legend><b>Persons Responsible</b></legend>
                            <input type="hidden" value="<%=SE.getResponsible().size()%>" id="countresponsible" name="countresponsible">
                            <center><table style = "width:100%" id="responsibletable">
                                    <tr>
                                        <th>Name</th>
                                        <th>Email</th>
                                    </tr>
                                    <%
                                        for (int i = 0; i < SE.getResponsible().size(); i++) {
                                    %>
                                    <tr>    
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "50%" name ="responsiblename<%=i%>"><%=SE.getResponsible().get(i).getName()%></textarea></td>
                                        <td><textarea style="border-radius: 0px;" rows = "1" cols = "50%" name ="responsibleemail<%=i%>"><%=SE.getResponsible().get(i).getEmail()%></textarea></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </table></center>
                            <br>
                            <center><input type ="button" id="addRowButton" onclick ="addRow()" value="Add Row">
                                <input style="background-color:red; border: red;" type ="button" id="deleteRowButton" onclick ="deleteRow()" value="Delete Row"></center>
                        </fieldset>
                                <br><br>
                        <center><button class="button" type="submit">Submit</button></center>
                    </form>
                </div>
            </div>
            <script>
                $('#date').datepicker({
                startDate: new Date()
                });
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
                if (links[i].href.indexOf('#') != - 1) {
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