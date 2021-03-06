<%-- 
    Document   : MULTIPLE-approveCoscaSE
    Created on : 06 18, 18, 7:21:12 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.FF"%>
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

        <title>Audit Trail FF</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/homepagestyle.css">
        <link rel="stylesheet" href="css/progressbar.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

        <style>
            p{
                font-size: 15px;
                font-family: "Arial", Helvetica, sans-serif;
            }

            table, td, th {
                border: 1px solid black;
                border-collapse: collapse;
                text-align: center;
            }

            h2{
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 20px;
            }

            h4{
                color: white;
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 15px;
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
        </style>

    </head>

    <%
        UserDAO UserDAO = new UserDAO();
        FF FF = new FF();

        FF = UserDAO.retrieveFFByFFID(Integer.parseInt(request.getAttribute("auditFF").toString()));

        if (request.getAttribute("current") == null) {
            FF = UserDAO.retrieveFFRevisionByFFID(Integer.parseInt(request.getAttribute("auditFF").toString()));
        }


    %>

    <body>
        <!-- MAIN -->
        <div class="col py-3">
            <form action="approveFF4" method="post" enctype="multipart/form-data">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">

                            <div class="panel panel-success ">

                                <div class="card">
                                    <div class="card-body">
                                        <h3><%=FF.getProjectName()%></h3>
                                        <p><b>Unit: </b> <%=FF.getUnit()%></p>
                                        <p><b>Department: </b> <%=FF.getDepartment()%></p>
                                        <p><b>Target Date of Implementation: </b> <%=FF.getActualDate()%></p>
                                        <br>
                                        <p><b>Program Head: </b> <%=FF.getProgramHead()%></p>
                                        <p><b>Program Classification: </b> <%=FF.getActivityClassification()%></p>
                                        <p><b>Total Amount Requested:</b> ₱<%=FF.getTotalAmount()%></p>
                                        <br>
                                        <p><b>Venue: </b> <%=FF.getVenue()%></p>
                                        <p><b>Speaker: </b> <%=FF.getSpeaker()%></p>
                                    </div>
                                </div>

                            </div>

                            <br/><br/>

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
                                    <h4>Breakdown of Expenses (Requested: ₱<%=FF.getTotalAmount()%>)</h4>
                                </div>
                            </div>

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
                                    <td>Grand Total: <%=count%></td>
                                </tr>
                            </table>
                            <br/>

                            <div class="card">
                                <div class="card-header">
                                    <h4>Source of Funds: </h4>
                                </div>
                                <div class="card-body">   
                                    <p><%=FF.getSourceOfFunds()%></p>
                                </div>
                            </div>
                            <br/>


                            <div class="card">
                                <div class="card-header">
                                    <h4>Attendees List</h4>
                                </div>
                                <div class="card-body">
                                <center><table style = "width:100%">
                                        <tr>
                                            <th style="width:50%">Name</th>
                                            <th>Email</th>
                                        </tr>
                                        <%
                                            for (int i = 0; i < FF.getAttendees().size(); i++) {
                                        %>
                                        <tr>
                                            <td><%=FF.getAttendees().get(i).getName()%></td>
                                            <td><%=FF.getAttendees().get(i).getEmail()%></td>
                                        </tr>

                                        <%
                                            }
                                        %>
                                    </table></center>
                                <br>
                                </div>
                            </div>
                            <br>

                            <div class="card">
                                <div class="card-header">
                                    <h4>Remarks: </h4>
                                </div>
                            </div>
                            <table style="width:100%">
                                <tr>
                                    <td>Assistant Dean for Lasallian Mission</td>
                                    <td><%if (FF.getADLMRemarks() != null) {%><%=FF.getADLMRemarks()%><%if (FF.getApprove1() == 1) {%><br><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise1() == 1) {%><br><b><font color = "orange">Marked for revision:</font></b><%} else if (FF.getReject1() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getADLMdatetime()%><%}%></td>
                                </tr>
                                <tr>
                                    <td>Chairperson/Unit Head</td>
                                    <td><%if (FF.getChairpersonRemarks() != null) {%><%=FF.getChairpersonRemarks()%><%if (FF.getApprove2() == 1) {%><br><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise2() == 1) {%><br><b><font color = "orange">Marked for revision:</font></b><%} else if (FF.getReject2() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getChairpersondatetime()%><%}%> <%if (FF.getUnitheadremarks() != null) {%><%=FF.getUnitheadremarks()%><%if (FF.getApprove2() == 1) {%><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise2() == 1) {%><br><b><font color = "orange">Marked for Revision:</font></b><%} else if (FF.getReject2() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getUnitheaddatetime()%><%}%></td>
                                </tr>
                                <tr>
                                    <td>Dean/Director</td>
                                    <td><%if (FF.getDeanRemarks() != null) {%><%=FF.getDeanRemarks()%><%if (FF.getApprove3() == 1) {%><br><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise3() == 1) {%><br><b><font color = "orange">Marked for revision:</font></b><%} else if (FF.getReject3() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getDeandatetime()%><%}%> <%if (FF.getDirectorremarks() != null) {%><%=FF.getDirectorremarks()%><%if (FF.getLspoRemarks() != null) {%><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise3() == 1) {%><br><b><font color = "orange">Marked for Revision:</font></b><%} else if (FF.getReject3() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getDirectordatetime()%><%}%></td>
                                </tr>
                                <tr>
                                    <td>Evaluation by LSPO</td>
                                    <td><%if (FF.getLspoRemarks() != null) {%><%=FF.getLspoRemarks()%><%if (FF.getApprove4() == 1) {%><br><b><font color = "green"> Approved:</font></b> <%} else if (FF.getRevise4() == 1) {%><br><b><font color = "orange">Marked for revision:</font></b><%} else if (FF.getReject4() == 1) {%><br><b><font color = "red">Rejected:</font></b><%}%> <%=FF.getLspodatetime()%><%}%></td>
                                </tr>
                            </table>
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