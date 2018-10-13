<%-- 
    Document   : LCLM-home
    Created on : 06 18, 18, 7:15:44 PM
    Author     : Karl Madrid
--%>

<%@page import="java.util.Collections"%>
<%@page import="entity.KRA"%>
<%@page import="entity.KRA"%>
<%@page import="entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">

            <title>LCLM Home</title>

            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
            <link rel="stylesheet" href="css/sidebar.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

            <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

            <%
            if (session.getAttribute("unit").toString().equals("Laguna Campus Lasallian Mission (LCLM)")) {
                try {
                    session.setAttribute("jspName", "LCLM-home.jsp");
                } catch (Exception e) {
                    
                }
            } else {
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/"+ session.getAttribute("jspName").toString());
                    dispatcher.forward(request, response);
            }
        %>
            
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
            </style>

        </head>

        <body>
            <!--Bootstrap NavBar-->
            <nav class="navbar navbar-expand-md fixed-top" id="navbar">
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation" id="smallerscreenmenuButton">
                    <span class="fa fa-align-justify"></span>
                </button>
                <a class="navbar-brand" href="#" id="navbar-unit">
                    <img src="https://upload.wikimedia.org/wikipedia/en/thumb/c/c2/De_La_Salle_University_Seal.svg/1200px-De_La_Salle_University_Seal.svg.png" width="30" height="30" class="d-inline-block align-top" data-toggle="sidebar-colapse" id="collapse-icon">
                    <span class="menu-collapsed">Laguna Campus Lasallian Mission</span>
                </a>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown d-sm-block d-md-none">
                            <a class="nav-link" href="LCLM-home.jsp" id="smallerscreenmenu">
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
                            <a class="nav-link" href="MULTIPLE-communityList.jsp" id="smallerscreenmenu">
                                Budget
                            </a>
                            <a class="nav-link" href="MULTIPLE-krasList.jsp" id="smallerscreenmenu">
                                Key Result Areas
                            </a>
                            <a class="nav-link" href="#" id="smallerscreenmenu">
                                Reports
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
                    <form action="logout">
                        <button class="btn btn-info navbar-btn-logout"><i class="fa fa-sign-out"></i></button>
                    </form>
                </div>
                </ul>
            </nav>


            <!--Bootstrap row-->
            <div class="row" id="body-row">

                <!--Sidebar-->
                <div id="sidebar-container" class="sidebar-expanded d-none d-md-block">
                    <ul class="list-group sticky-top sticky-offset">
                        <!-- Menu with submenu -->
                        <a href="LCLM-home.jsp" class="list-group-item list-group-item-action flex-column align-items-start" id="sidebarCategory">
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

                </div>


                <!--MAIN-->
                <div class="col py-3">

                    <!---KRAs-->
                    <div class="container-fluid panels">
                        <h2>Key Result Areas</h2>

                        <h5>KRA 3. Formation for all sectors that is truly Lasallian </h5>
                        <table class="table table-bordered">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">Goals</th>
                                    <th scope="col">Measures</th>
                                    <th scope="col">Targets</th>
                                    <th scope="col">Accomplishment</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><b>G1 </b> Implement sustainable, holistic and developmental Lasallian formation  across all sectors based on the Lasallian Guiding Principles </td>
                                    <td><b>M1</b> Integration in curricular and co-curricular programs of formation based on Lasallian spirituality and mission </td>
                                    <td>Development of Lasallian Formation Program for graduate student</td>
                                    <td class="accomplishmentGreen">85%</td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>Existing Lasallian Formation programs for undergraduate students have been reviewed and revised </td>
                                    <td class="accomplishmentYellow">50%</td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>50% of student organizations have implemented a Lasallian formation activity</td>
                                    <td class="accomplishmentRed">15%</td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td><b>M2</b> Participation of administrators, faculty and personnel in Lasallian formation activity </td>
                                    <td>50% of faculty departments have undergone Lasallian formation program</td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>75% of staff have undergone Lasallian formation programs  </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>All administrators have undergone the Lasallian formation activity  </td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td><b>M3</b> Number of Lasallian formation activities available for other sectors in the DLSU community  </td>
                                    <td>At Least one formation activity engaging alumni, parents, and community partners.</td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td><b>G2 </b> Implement sustainable, holistic and developmental Lasallian formation  across all sectors based on the Lasallian Guiding Principles </td>
                                    <td><b>M1</b> Number of fora and other interdisciplinary activities focused on bridging faith and scholarship (e.g. ethics, heritage, culture, science, theology, philosophy) </td>
                                    <td>At least one interdisciplinary activity conducted each term</td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td> </td>
                                    <td><b>M2</b> Integration of faith dimension using the Lasallian Reflection Framework (LRF) in GE courses</td>
                                    <td>Review and integrate the LRF in all NLCC subjects </td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td> </td>
                                    <td><b>M3</b> Participation of international students in co-curricular activities promoting interfaith and multicultural diversity </td>
                                    <td>50% of international students participate in co-curricular activities promoting interfaith and multicultural diversity</td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td><b>G3 </b> Implement sustainable, holistic and developmental Lasallian formation  across all sectors based on the Lasallian Guiding Principles </td>
                                    <td><b>M1</b> Number of Lasallian communities committed to the Lasallian mission  </td>
                                    <td>3 communities</td>
                                    <td></td>
                                </tr>


                            </tbody>
                        </table>
                    </div>



                    <!---table-->
                    <div class="container-fluid panels">

                        <h2>Proposals Progress (insert total)</h2>

                        <input class="form-control" id="myInput" type="text" placeholder="Search table..">

                        <table class="table ">
                            <thead class="thead-dark">
                                <tr>
                                    <th onclick="sortTable(0)">Date</th>
                                    <th onclick="sortTable(1)">Program Name</th>
                                    <th onclick="sortTable(2)">Program Head</th>
                                    <th onclick="sortTable(3)">Funded by</th>
                                    <th onclick="sortTable(4)">Status</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody id="myTable">
                                <tr>
                                    <td>6/18/2018</td>
                                    <td>x</td>
                                    <td>Doe</td>
                                    <td>john@example.com</td>
                                    <td>ary@example.com</td>
                                    <td><button type="button" class="btn btn-primary btn-sm">View</button></td>
                                </tr>
                                <tr>
                                    <td>6/18/2018</td>
                                    <td>y</td>
                                    <td>Moe</td>
                                    <td>mary@example.com</td>
                                    <td>mary@example.com</td>
                                    <td><button type="button" class="btn btn-primary btn-sm">View</button></td>
                                </tr>
                                <tr>
                                    <td>6/18/2018</td>
                                    <td>z</td>
                                    <td>Dooley</td>
                                    <td>july@example.com</td>
                                    <td>ry@example.com</td>
                                    <td><button type="button" class="btn btn-primary btn-sm">View</button></td>
                                </tr>
                            </tbody>
                        </table>
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
