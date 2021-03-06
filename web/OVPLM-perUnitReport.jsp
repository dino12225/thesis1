<%-- 
    Document   : OVPLM-perUnitReport
    Created on : 06 18, 18, 8:06:20 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.Unit"%>
<%@page import="entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Per-unit Report</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
        <script src="https://cdn.rawgit.com/emn178/Chart.PieceLabel.js/master/build/Chart.PieceLabel.min.js"></script>

        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
        <style type="text/css" class="init"></style>

        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
        <script type="text/javascript" language="javascript" src="../resources/demo.js"></script>
        <script type="text/javascript" class="init"></script>

        <script>
            $(document).ready(function () {
                $('#example').DataTable();
            });
            $(document).ready(function () {
                $('#example2').DataTable();
            });
            $(document).ready(function () {
                $('#example3').DataTable();
            });
            $(document).ready(function () {
                $('#example4').DataTable();
            });
            $(document).ready(function () {
                $('#example5').DataTable();
            });
            $(document).ready(function () {
                $('#example6').DataTable();
            });
        </script>

        <style>   
            #myInput, #myInput2, #myInput3{
                margin-bottom: 20px;
            }

            tr:hover {
                background-color: lightgreen;
            }

            h2{
                font-size: 30px;
                text-align: left;
                margin-top: 15px;
                border-bottom: 2px solid green;
                padding-bottom: 5px;
                margin-bottom: 25px;
                font-family: 'Roboto', sans-serif;
            }


            .table{
                margin-bottom: 20px;
            }

            .totalsdiv{
                margin-top: 20px;
                background-color: white;
                padding-bottom: 15px;
                border-style: solid;
                border-color: lightgray;
                border-width: 1px;
                border-radius: 8px;
                align-content: center;
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

            h3{
                text-align: center;
                margin-top: 20px;
            }

            .totaltitle{
                text-align: center;
                font-size: 40px;
            }

            .total{
                color: white;
                font-size: 40px;
                font-family: 'Montserrat', sans-serif;
            }

            .total2{
                color: white;
                font-size: 30px;
                font-family: 'Montserrat', sans-serif;
            }

            .kras{
                margin-top: 20px;
                background-color: white;
                padding-bottom: 15px;
                border-style: solid;
                border-color: lightgray;
                border-width: 1px;
                border-radius: 8px;
                margin-bottom: 20px;
            }
            
            .daterange{
                text-align: right;
                margin-top: 30px;
            }
            
            .button {
                background-color: dodgerblue;
                border: none;
                color: white;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                padding: 14px 40px;
            }

            .pbutton{
                margin-top: 40px;
                text-align: center;
            }

            .quickhead3{
                border-bottom: 2px solid black;
                padding-bottom: 10px; 
                margin-bottom: 20px;
                color: black;
            }

            #buttonSE {
                margin: 5px 5px 5px 10px;
                padding-left: 60px;
                padding-bottom: 15px;
                background-color: #cc0099;
            }

            #buttonFF {
                margin: 5px 5px 5px 10px;
                padding-left: 60px;
                padding-bottom: 15px;
                background-color: #00e699;
            }

            #buttonBR {
                margin: 5px 5px 5px 10px;
                padding-left: 60px;
                padding-bottom: 15px;
                background-color: #55F024;
            }

            #buttonBU {
                margin: 5px 5px 5px 10px;
                padding-left: 60px;
                padding-bottom: 15px;
                background-color: #F0A624;
            }

            .budget{
                font-size: 70px; 
                text-align: center; 
                font-family: 'Montserrat', sans-serif;
                border-bottom: 1px solid darkgreen;
                padding-bottom: 20px;
                margin-bottom: 20px;
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
                        <a class="nav-link" href="OVPLM-home.html" id="smallerscreenmenu">
                            Home
                        </a>
                        <a class="nav-link" href="MULTIPLE-faithFormationProgramsList.html" id="smallerscreenmenu">
                            Programs
                        </a>
                        <a class="nav-link" href="MULTIPLE-unitsList.html" id="smallerscreenmenu">
                            Units
                        </a>
                        <a class="nav-link" href="MULTIPLE-communityList.html" id="smallerscreenmenu">
                            Communities
                        </a>
                        <a class="nav-link" href="MULTIPLE-krasList.html" id="smallerscreenmenu">
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
                <form action="viewUnitReport" method="post">
                    <div class="container-fluid panels">
                        <p></p>
                        <p>Enter Report Range: From: <input type="date" name="startDate"> To: <input type="date" name="endDate"></p>
                        <div class="form-group">
                            <% if (session.getAttribute("position").equals("CCS - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "College of Computer Studies (CCS)">College of Computer Studies (CCS)</option>
                            </select>


                            <% } else if (session.getAttribute("position").equals("COS - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "College of Science (COS)">College of Science (COS)</option>
                            </select>

                            <% } else if (session.getAttribute("position").equals("GCOE - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "Gokongwei College of Engineering">Gokongwei College of Engineering (GCOE)</option>
                            </select>

                            <% } else if (session.getAttribute("position").equals("RVRCOB - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "Ramon V. Del Rosario College of Business (RVR-COB)">Ramon V. Del Rosario College of Business (RVR-COB)</option>
                            </select>

                            <% } else if (session.getAttribute("position").equals("BAGCED - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "Br. Andrew Gonzales College of Education (BAGCED)">Br. Andrew Gonzales College of Education (BAGCED)</option>
                            </select>

                            <% } else if (session.getAttribute("position").equals("CLA - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "College of Liberal Arts (CLA)">College of Liberal Arts (CLA)</option>
                            </select>

                            <% } else if (session.getAttribute("position").equals("COL - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "College of Law (COL)">College of Law (COL)</option>
                            </select>


                            <% } else if (session.getAttribute("position").equals("SOE - ADEALM")) { %>
                            <label for="sel1">Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option value = "School of Economics (SOE)">School of Economics (SOE)</option>
                            </select>

                            <% } else {
                            %>
                            <label for="sel1">Choose Unit:</label>
                            <select class="form-control" id="type" name="unit">
                                <option></option>
                                <optgroup label="Non-Academic Units">
                                    <%
                                        ArrayList<Unit> units = new ArrayList();
                                        units = UserDAO.retrieveUnitsNonAcademic();
                                        for (int i = 0; i < units.size(); i++) {
                                    %>
                                    <option value="<%=units.get(i).getName()%>"><%=units.get(i).getName()%></option>
                                    <%
                                        }
                                    %>
                                    <option></option>
                                </optgroup>
                                <optgroup label="Academic Units">
                                    <%
                                        units = UserDAO.retrieveUnitsAcademic();
                                        for (int i = 0; i < units.size(); i++) {
                                    %>
                                    <option value="<%=units.get(i).getName()%>"><%=units.get(i).getName()%></option>
                                    <%
                                        }
                                    %>
                                </optgroup>
                            </select>
                            <% }%>
                        </div>
                        <button class="btn btn-success" type="submit">Submit</button>
                    </div>
                </form>


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
