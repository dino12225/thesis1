<%-- 
    Document   : MULTIPLE-editUnit
    Created on : 06 18, 18, 7:28:05 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.Unit"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Edit Unit</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/formstyle1.css">
        <link rel="stylesheet" type="text/css" href="styles.css">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="css/formstyle5.css">

        <style>
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

            .progressnum{
                font-size: 12px;
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

            h1{
                text-align: left;
                font-size: 35px;
                border-bottom: 2px solid green;
                padding-bottom: 10px;
            }   

            .formBg{
                width: 60%;
                padding: 10px;
                margin-top: 0px;
            }

            .dropbtn {
                background-color: dimgray;
                color: white;
                padding: 16px;
                font-size: 16px;
                border: none;
                cursor: pointer;
            }

            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {background-color: #f1f1f1}

            .dropdown:hover .dropdown-content {
                display: block;
            }

            .dropdown:hover .dropbtn {
                background-color: #3e8e41;
            }

            .btn-warning{
                color: white;
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
                <span class="menu-collapsed">Office of the Vice President for Lasallian Mission</span>
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
                    <button type="button" class="btn btn-info navbar-btn-notifications" href="#" data-toggle="dropdown">
                        <span class="badge badge-pill badge-primary" style="background-color:red; color:white; float:right;margin-bottom:-20px;">!</span> 
                        <i class="fa fa-bell"></i>
                    </button>
                </div>
                <div class="nav-button">
                    <div class="dropdown">
                        <button type="button" class="btn btn-info navbar-btn-notifications" href="#" data-toggle="dropdown">
                            <i class="fa fa-bell"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <div id="notifsScroll">
                                <li class="notification-box" href="#">
                                    <div class="row">
                                        <div class="col-sm-8">
                                            <strong class="notificationBoxHeader">Databasing</strong>
                                            <div class="notificationBoxMessage">
                                                Status: Approved
                                            </div>
                                        </div>    
                                    </div>
                                </li>
                                <li class="notification-box" href="#">
                                    <div class="row">
                                        <div class="col-sm-8">
                                            <strong class="notificationBoxHeader">Programming 101</strong>
                                            <div class="notificationBoxMessage">
                                                Status: Step 4
                                            </div>
                                        </div>    
                                    </div>
                                </li>
                                <li class="notification-box" href="#">
                                    <div class="row">
                                        <div class="col-sm-8">
                                            <strong class="notificationBoxHeader">*Insert name ng proposal*</strong>
                                            <div class="notificationBoxMessage">
                                                Status: *insert status*
                                            </div>
                                        </div>    
                                    </div>
                                </li>    
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

                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="formBg">
                                    <h1>Edit Unit</h1>

                                    <%
                                        UserDAO UserDAO = new UserDAO();
                                        Unit u = new Unit();
                                        u = UserDAO.getUnitbyID(Integer.parseInt(request.getAttribute("unitID").toString()));
                                    %>
                                    <div class="panel panel-success">

                                        <div class="panel-heading"></div>

                                        <div class="panel-body">

                                            <form action="editUnitNonAcademic" method="post">
                                                <ul class="form-style-1">
                                                    <li>
                                                        <label>Unit Name: <span class="required"></span></label>
                                                        <input type="text" name="unitname" class="field-long" value="<%=u.getName()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Unit Head: <span class="required"></span></label>
                                                        <input type="text" name="unithead" class="field-long" value="<%=u.getHead()%>" />
                                                    </li>

                                                    <li>
                                                        <label>Unit Type: <span class="required"></span></label>
                                                        <select name="unittype">
                                                            <option value="Unit Type 1" <%if (u.getType().equals("Academic")) {%> selected <%}%>>Academic</option>
                                                            <option value="Unit Type 2" <%if (u.getType().equals("Non-Academic")) {%> selected <%}%>>Non-Academic</option>
                                                        </select>
                                                    </li>
                                                    <li>
                                                        <label>Total number of CAP: <span class="required"></span></label>
                                                        <input type="number" name="cap" class="field-num" value="<%=u.getCap()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Total number of APSP: <span class="required"></span></label>
                                                        <input type="number" name="apsp" class="field-num" value="<%=u.getApsp()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Total number of Asf: <span class="required"></span></label>
                                                        <input type="number" name="saf" class="field-num" value="<%=u.getAsf()%>"/>
                                                    </li>
                                                    
                                                    <li>
                                                        <label>Total number for Faculty: <span class="required"></span></label>
                                                        <input type="number" name="faculty" class="field-num" value="<%=u.getFaculty()%>"/>
                                                    </li>                                                                                  
                                                    <li>
                                                        <label>Total number of Admin: <span class="required"></span></label>
                                                        <input type="number" name="admin" class="field-num" value="<%=u.getAdmin()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Total number of Direct Hired Contractuals: <span class="required"></span></label>
                                                        <input type="number" name="directhired" class="field-num" value="<%=u.getDirecthired() %>"/>
                                                    </li>
                                                    <li>
                                                        <label>Total number of Independent Contractors: <span class="required"></span></label>
                                                        <input type="number" name="independent" class="field-num" value="<%=u.getIndependent()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Total number of External Service Personnel: <span class="required"></span></label>
                                                        <input type="number" name="external" class="field-num" value="<%=u.getExternal()%>"/>
                                                    </li>
                                                    <li>
                                                        <label>Unit Description: <span class="required"></span></label>
                                                        <textarea rows="4" cols="40" name="unitdesc"><%=u.getDescription()%></textarea>
                                                    </li>


                                                    <li align="center">
                                                        <button type="submit" name="unit" value="<%=request.getAttribute("unitID")%>" class="btn btn-warning">Edit Unit</button>
                                                    </li>
                                                </ul>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </div>
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