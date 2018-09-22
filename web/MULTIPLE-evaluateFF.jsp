<%-- 
    Document   : MULTIPLE-evaluateFF
    Created on : 07 24, 18, 5:56:37 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.FFreport"%>
<%@page import="entity.FF"%>
<%@page import="dao.UserDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>FF Evaluation</title>


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

                max-width: 100%;
                margin: 10px auto;
                padding: 20px;
                border-radius: 8px;
                font-family: Georgia, "Times New Roman", Times, serif;
                font-size: 13px;
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
                border: 5;
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

            body {
                background: #fff;
                box-shadow: 0 0 2px rgba(0, 0, 0, 0.06);
                color: #545454;
                font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
                font-size: 16px;
                line-height: 1.5;
                margin: 0 auto;
                max-width: 800px;
                padding: 2em 2em 4em;
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

            img {
                animation: colorize 2s cubic-bezier(0, 0, .78, .36) 1;
                background: transparent;
                border: 10px solid rgba(0, 0, 0, 0.12);
                border-radius: 4px;
                display: block;
                margin: 1.3em auto;
                max-width: 95%;
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

            td{
                font-size: 15px;
                margin-left: 10px;
            }

            p{
                margin-left: 10px;
                font-size: 17px;
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

            function addField() {
                container.appendChild(document.createTextNode("Name: "));
                var input = document.createElement("input");
                input.type = "text";
                input.name = "member";
                container.appendChild(input);
                container.appendChild(document.createElement("br"));
                container.appendChild(document.createElement("br"));
            }

            function addRow() {
                var table = document.getElementById("projectplantable");
                var rows = document.getElementById("projectplantable").rows.length;
                var row = table.insertRow(rows);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                cell1.innerHTML = '<td><input type ="date"/></td>';
                cell2.innerHTML = '<textarea rows = "2" cols = "25%" name ="planoutput"></textarea>';
                cell3.innerHTML = '<textarea rows = "2" cols = "25%" name ="planoutput"></textarea>';
                cell4.innerHTML = '<textarea rows = "2" cols = "25%" name ="planoutput"></textarea>';
            }

            function deleteRow() {
                var rows = document.getElementById("projectplantable").rows.length;
                if (rows - 1 > 0) {
                    document.getElementById("projectplantable").deleteRow(rows - 1);
                } else {

                }

            }

        </script>

    </head>

    <body>
        <hr size="5" noshade>    
    <center><h1>FAITH FORMATION - EVALUATION</h1></center>
    <hr size="5" noshade>

    <form action = "addFFevaluation" method="post">
        <%
            UserDAO UserDAO = new UserDAO();
            FF FF = new FF();
            FF = UserDAO.retrieveFFByFFID(Integer.parseInt(request.getAttribute("ffID").toString()));

            FFreport FFreport = new FFreport();
            FFreport = UserDAO.retrieveFFreportByFFID(Integer.parseInt(request.getAttribute("ffID").toString()));
        %>
        <div class="form-style-5">

            <fieldset>
                <legend><b>Date of Activity: <%=FF.getActualDate()%></b></legend>
                <br><br>
            </fieldset>

            <fieldset>
                <legend><b>Location of Activity: <%=FF.getVenue()%></b></legend>
                <br><br>
            </fieldset>

            <fieldset>
                <legend><b>Facilitator: <%=FFreport.getNameOfFacilitator()%></b></legend>
                <br>
            </fieldset>

            <fieldset>
                <legend><b>Name (optional):</b></legend>
                <center><input type = "text" name ="name"></center>
                <br><br>
            </fieldset>

            <p>SCALE:<br> 
                <b>5</b>- Highest Rating<br>
                <b>1</b>- Lowest Rating<br></p>
            <br><br>

            <fieldset>
                <center><table style = "width:100%" id = "FFEvaluation">
                        <tr>
                            <th>Criteria</th>
                            <th>5</th>
                            <th>4</th>
                            <th>3</th>
                            <th>2</th>
                            <th>1</th>
                            <th>NEI</th>
                            <th>N/A</th>
                        </tr>
                        <tr>
                            <td><p>The announcements and reminders about the retreat were enough. </p></td>
                            <td><center><input type="radio" size = "10%" name="q1" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>The registration process was simple and easy.</p></td>
                            <td><center><input type="radio" size = "10%" name="q2" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>The venue was suited for the activity.</p></td>
                            <td><center><input type="radio" size = "10%" name="q3" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>The food was sufficient, balanced/healthy, and delicious.</p></td>
                            <td><center><input type="radio" size = "10%" name="q4" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>The rooms allowed me to rest well and provided what I needed.</p></td>
                            <td><center><input type="radio" size = "10%" name="q5" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q5" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>Transportation was comfortable and safe.</p></td>
                            <td><center><input type="radio" size = "10%" name="q6" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q6" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>Overall, my experience in the retreat was meaningful.</p></td>
                            <td><center><input type="radio" size = "10%" name="q7" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q7" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p><b>[THIS QUESTION IS FOR ORGANIZERS ONLY]</b> Coordination with LSPO regarding this retreat was efficient.</p></td>
                            <td><center><input type="radio" size = "10%" name="q8" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q8" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>Please give an overall rating for the program</p></td>
                            <td><center><input type="radio" size = "10%" name="q9" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q9" value="7"></center></td>
                        </tr>
                    </table></center>
                <br>
                <p>One important learning I had was...</p>
                <center><input type = "text" name ="learning"></center>
                <p>One memorable activity was...</p>
                <center><input type = "text" name ="memorable"></center>
                <p>Comments, suggestions, and recommendations</p>
                <center><input type = "text" name ="feedback"></center>
                <br>
                <center><p>Thank you very much!</p></center>
            </fieldset>

            <button type="submit" name="ffID" value="<%=FF.getId()%>">Submit</button>

        </div>
    </form>
</body>

</html>
