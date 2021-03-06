<%-- 
    Document   : MULTIPLE-viewUnitDetails
    Created on : 06 18, 18, 8:48:26 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Department"%>
<%@page import="entity.Unit"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Inactive Academic Unit Details</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <script>
            function myFunction() {
                // Declare variables 
                var input, filter, table, tr, td, i;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");

                // Loop through all table rows, and hide those who don't match the search query
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>

        <style>   
            h2{
                font-size: 30px;
                text-align: left;
                border-bottom: 2px solid green;
                padding-bottom: 10px;
                font-family: 'Roboto', sans-serif;
            }

            .table{
                margin-top: 30px;
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
                            Communities
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
                <div class="row">
                    <div class="col-lg-7">
                        <form action="passUnit" method="post">
                            <div class="tableDiv">
                                <%
                                    Unit u = new Unit();
                                    u = UserDAO.getUnitbyID(Integer.parseInt(request.getAttribute("unitID").toString()));
                                    ArrayList<Department> Departments = UserDAO.getDepartmentsIDsByUnitID(u.getUnitID());
                                %>

                                <h2><%=u.getName()%></h2>



                                <table class="table">

                                    <tbody>
                                        <tr>
                                            <td style="width:15%"><b>Unit Type</b></td>
                                            <td style="width:15%"><%=u.getType()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Unit Head</b></td>
                                            <td><%=u.getHead()%></td>
                                        </tr> 
                                        <tr>
                                            <td><b>Description</b></td>
                                            <td><%=u.getDescription()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Number of Departments:</b></td>
                                            <td><%=u.getDepartments()%></td>
                                        </tr>
                                </table>

                                <%
                                    for (int x = 0; x < Departments.size(); x++) {
                                        Department d = new Department();
                                        d = UserDAO.getDepartmentByID(Departments.get(x).getDepartmentID());
                                %>
                                <table class="table">
                                    <tr>
                                        <th style="width:20%"><b>Department Name:</b></th>
                                        <th style="width:20%"><%=d.getName()%></th>
                                    </tr>
                                    <tr>
                                            <td><b>Total CAP:</b></td>
                                            <td><%=d.getCap()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total APSP:</b></td>
                                            <td><%=d.getApsp()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total ASF:</b></td>
                                            <td><%=d.getAsf()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total Faculty:</b></td>
                                            <td><%=d.getFaculty()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total Administrators:</b></td>
                                            <td><%=d.getAdmin()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total Number of Direct Hired Contractuals:</b></td>
                                            <td><%=d.getDirecthired()%></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total Number of Independent Contractors:</b></td>
                                            <td><%=d.getIndependent() %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Total Number of External Service Personnel:</b></td>
                                            <td><%=d.getExternal() %></td>
                                        </tr>
                                        <br>
                                    <% } %>
                                    </tbody>
                                </table>
                                    <br><br>
                                    

                                <p align="right"> 
                                    <input type="text" hidden name="type" id="type" value="<%=u.getType()%>"/>
                                    <%if(session.getAttribute("unit").equals("Office of the Vice President for Lasallian Mission (OVPLM)")){%>
                                    <button type="submit" name="unit" value="<%=u.getUnitID()%>" class="btn btn-warning">Edit</button>
                                    <button type="submit" onclick="return window.confirm('Are you sure you want to enable this Unit?')" class="btn btn-danger" style="background-color: green; border-color: green" name="enable" value="<%=u.getUnitID()%>">Enable</button>
                                    <%}%>
                                </p>

                            </div>
                        </form>
                    </div>
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
