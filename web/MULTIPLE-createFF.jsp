<%-- 
    Document   : UR-home
    Created on : 06 27, 18, 1:25:59 PM
    Author     : Karl Madrid
--%>

<%@page import="dao.OvplmDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Community"%>
<%@page import="java.text.DecimalFormat"%>
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

        <title>Create FF Program</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/formstyle5.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <style>
            #myInput{
                margin-bottom: 20px;
            }

            tr:hover {
                background-color: lightgreen;
            }

            .table{
                border-bottom: 2px solid lightgray;
                margin-bottom: 30px;
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

            h3{
                border-bottom: 2px solid green;
                border-top: 2px solid green;
                padding-bottom: 10px;
                padding-top: 10px;
                font-family: "Arial", Helvetica, sans-serif;
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
                display: inline-block;
                border-radius: 5px;
                border:none;
                font-size: 13px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            .button{
                background-color: darkgreen;
                border-radius: 5px;
                border:none;
                color: white;
                padding: 10px 20px;
                text-align: center;
                margin: 4px 2px;
                font-size: 16px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            legend, h3, #inputText, #classification, option, select, value, td, th, #countexpenses, #ffunitcost0, #ffquantity0, #ffsubtotal0{
                font-family: "Arial", Helvetica, sans-serif;
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
        
        <script>
            function calculate() {
                var count = document.getElementById("countexpenses").value;
                var rows = document.getElementById("breakdowntable").rows.length;
                var total = 0;
                
                if(rows > 0){
                    for (var x = 0; x < count; x++) {
                        var y = document.getElementById("ffquantity" + x).value;
                        var z = document.getElementById("ffunitcost" + x).value;
                        var subtotal = document.getElementById("ffsubtotal" + x);
                        subtotal.setAttribute('value', y * z);
                    }
                }
                
                for(var x = 0 ; x < count ; x++){
                    var b = document.getElementById("ffsubtotal" + x).value;
                    total = (total*1) + (b*1);
                }
                document.getElementById("total").setAttribute('value', total);
            }

        </script>

        <script>
            function addRow(){

            var count = document.getElementById("countexpenses").value;
            var table = document.getElementById("breakdowntable");
            var rows = document.getElementById("breakdowntable").rows.length;
            var row = table.insertRow(rows - 1);
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            cell1.innerHTML = "<td><input type='text' style='border-radius: 0px; margin-bottom:1%' name ='ffitem" + count + "' required></td>";
            cell2.innerHTML = "<td><input type='number' style='border-radius:0px; margin-bottom:1%' id='ffunitcost" + count + "' name ='ffunitcost" + count + "' required></td>";
            cell3.innerHTML = "<td><input type='number' style='border-radius: 0px; margin-bottom:1%' id='ffquantity" + count + "' name ='ffquantity" + count + "' required></td>";
            cell4.innerHTML = "<td><input type='number' style='border-radius: 0px; margin-bottom:1%' id='ffsubtotal" + count + "' name ='ffsubtotal" + count + "' value='0' readonly required></td>";
            count++;
            document.getElementById("countexpenses").setAttribute('value', count);
            }
            function deleteRow(){
            var count = document.getElementById("countexpenses").value;
            var rows = document.getElementById("breakdowntable").rows.length;
            if (rows - 1 > 0){
            document.getElementById("breakdowntable").deleteRow(rows - 2);
            count--;
            document.getElementById("countexpenses").setAttribute('value', count);
            } else {

            }
            }
        </script>

        <script type="text/javascript">
            <%
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
                var optionArray = ["|",<%for (int l = 0; l < k.get(i).getGoals().get(j).getMeasures().size(); l++) {%>"<%=k.get(i).getGoals().get(j).getMeasures().get(l).getMeasureID()%>|<%=k.get(i).getGoals().get(j).getMeasures().get(l).getMeasure()%>",<%}%>];
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
                    <div class="dropdown">
                        <button type="button" class="btn btn-info navbar-btn-profile" href="#" data-toggle="dropdown">
                            <i class="fa fa-user-circle"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <% UserDAO UserDAO = new UserDAO(); %>
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
                <center><h3>Faith Formation Program Proposal</h3></center>

                <div class="form-style-5">
                    <form action = "addFF" method="post">
                        <%
                            UserDAO = new UserDAO();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                            java.util.Date javaDate = new java.util.Date();
                            java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                        %>

                        <center>
                            <fieldset>
                                <legend><b>Unit:</b> <%=session.getAttribute("unit")%></legend>
                            </fieldset>

                            <fieldset>
                                <legend><b>Department:</b> <%=UserDAO.getDepartmentByUserID(Integer.parseInt(session.getAttribute("userID").toString()))%></legend>
                            </fieldset>

                            <fieldset>
                                <legend><b>Date:</b> <%=sqlDate%></legend>
                            </fieldset>
                        </center>

                        <br>
                        <fieldset>
                            <legend><b>Program Head:</b></legend>
                            <input value="<%= UserDAO.getFirstName(Integer.parseInt(session.getAttribute("userID").toString()))%> <%= UserDAO.getLastName(Integer.parseInt(session.getAttribute("userID").toString()))%>"  id="inputText" type = "text" name ="programhead" required>
                            <br><br>
                        </fieldset>

                        <fieldset>
                            <legend><b>Type of Faith Formation:</b> </legend>
                            <select id="classification" name="classification">
                                <option value="Retreat">Retreat</option>
                                <option value="Recollection">Recollection</option>
                                <option value="Prayer Service">Prayer Service</option>
                                <option value="Spiritual Talk">Spiritual Talk</option>
                                <option value="Talk on the life of the Founder">Talk on the life of the Founder</option>
                                <option value="Br. Gabriel Drolin Experience">Br. Gabriel Drolin Experience</option>
                            </select>
                            <br><br><br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">1</span><b> Project Name:</b></legend>
                            <center><input id="inputText" type = "text" name ="pname" required></center>
                            <br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">2</span><b> Venue:</b></legend>
                            <center><input id="inputText" type = "text" name ="pvenue" required></center>
                            <br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">3</span><b> Speaker:</b></legend>
                            <center><input id="inputText" type = "text" name ="pspeaker" required></center>
                            <br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">4</span><b> Objectives:</b></legend>
                            <center><textarea id="inputText" rows="3" cols = "50%" name="objectives" required></textarea></center>
                            <br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">5</span><b> Target Implementation Date:</b></legend>
                            <input style ="width:30%" type = "date" name ="actualdate" min="<%=sqlDate%>" required>
                            <br><br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">6</span><b> Total Amount Requested:</b></legend>
                            <input id="inputText" style ="width:30%" type = "number" name ="pbudget" required>
                            <br><br><br>
                        </fieldset>

                        <fieldset>
                            <legend><span class="number">7</span><b>Source of Funds:</b></legend>
                            <select id="classification" style ="width:50%" name="funds">
                                <option value="OVPLM">Office of the Vice President for Lasallian Mission</option>
                                <option value="Others">Others</option>
                            </select>
                            <br>
                        </fieldset>

                        <%
                            DecimalFormat df = new DecimalFormat("#,###,###,###.##");
                        %>

                        <br><br>
                        <legend><b>Breakdown of Expenses (Budget of 2000 per head)</b></legend>
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
                                        <td><input type='text' style="border-radius: 0px; margin-bottom:1%"  name ="ffitem0" required></td>
                                        <td><input type='number' style='border-radius:0px; margin-bottom:1%' id="ffunitcost0" name ="ffunitcost0" required></td>
                                        <td><input type='number' style="border-radius: 0px; margin-bottom:1%" id="ffquantity0" name ="ffquantity0" required></td>
                                        <td><input type='number' style="border-radius: 0px; margin-bottom:1%" id="ffsubtotal0" name ="ffsubtotal0" value="0" readonly required></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td>Total: </td>
                                        <td><input type="number" style="margin-bottom:1%; border-radius:0px" name="total" id="total" readonly value="0"></td>
                                    </tr>
                                </table></center>
                            <br>
                            <center>
                                <button type ="button" class="button" id="addRowButton" onclick ="addRow()" value="Add Row">Add Row</button>
                                <button type ="button" class="button" style="background-color:dodgerblue" id="addRowButton" onclick="calculate()">Calculate</button>
                                <button class="button" style="background-color:red" type ="button" id="deleteRowButton" onclick ="deleteRow()" value="Delete Row">Delete Row</button>
                            </center>
                        </fieldset>

                        <br><br><br><br>
                        <center>
                            <legend>Page 1 of 2</legend>
                            <button style="width:10%" type = "submit" class="button">Next</button>
                        </center>
                    </form>
                </div>
            </div>

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

