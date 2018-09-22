<%-- 
    Document   : MULTIPLE-evaluateSE
    Created on : 07 24, 18, 4:48:35 PM
    Author     : Karl Madrid
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>SE Evaluation</title>


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

        </script>

    </head>

    <body>
        <hr size="5" noshade>    
    <center><h1>STUDENT / EMPLOYEE EVALUATION OF SOCIAL-ENGAGEMENT EXPERIENCE / ACTIVITY</h1></center>
    <hr size="5" noshade>


    <div class="form-style-5">
        <form action = "addSEevaluation" method="post">
            <%
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date javaDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

            %>
            <fieldset>
                <legend><b>Date: <%=sqlDate%></b></legend>
                <br><br>
            </fieldset>

            <fieldset>
                <legend><b>Name (optional):</b></legend>
                <center><input type = "text" name ="name"></center>
                <br><br>
            </fieldset>

            <fieldset>
                <legend><b>College / Department / Unit:</b></legend>
                <center><input type = "text" name ="unit"></center>
                <br><br>
            </fieldset>

            <fieldset>
                <legend><b>Subject Name / Code (for SL Students):</b></legend>
                <center><input type = "text" name ="subject"></center>
                <br><br>
            </fieldset>

            <p>Instructions: Using the following scale: circle the letter that corresponds to your level of agreement or
                disagreement with each statement. If you don’t have enough information to rate the item, encircle Not
                Enough Information (NIE). If the item is not applicable to your social engagement project, encircle Not
                Applicable (N/A)
                <br><br>
                SCALE:<br> 
                <b>5</b>- Strongly Agree<br> 
                <b>4</b>- Agree<br>
                <b>3</b>- Moderate<br> 
                <b>2</b>- Disagree<br> 
                <b>1</b>- Strongly Disagree<br>
                <b>NEI</b> – Not Enough Information<br> 
                <b>N/A</b>- Not Applicable</p>
            <br><br>

            <fieldset>
                <center><table style = "width:100%" id = "SEEvaluation">
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
                            <td><b><p>1. STUDENT LEARNING (For SL Related Projects Only)</b></p></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><p>1.1. Service-Learning is relevant to the course I am taking.</p></td>
                            <td><center><input type="radio" size = "10%" name="q1.1" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.1" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>1.2. We were provided with course-based knowledge and skills necessary for project implementation.</p></td>
                            <td><center><input type="radio" size = "10%" name="q1.2" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.2" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>1.3. The learning outcomes of the course have been attained.</p></td>
                            <td><center><input type="radio" size = "10%" name="q1.3" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.3" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>1.4. The service component of the course has strengthened my belief that students like me can contribute to the empowerment of poor and marginalized communities/sectors.</p></td>
                            <td><center><input type="radio" size = "10%" name="q1.4" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.4" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>1.5. I have gained better understanding of Service-Learning because of course.</p></td>
                            <td><center><input type="radio" size = "10%" name="q1.5" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q1.5" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><b><p>2. STUDENT SAFETY (For Students Only)</b></p></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><p>2.1. We were oriented about the partner organization and off-campus policies prior to the actual visit.</p></td>
                            <td><center><input type="radio" size = "10%" name="q2.1" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.1" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>2.2. The vehicle drivers were alert and careful. (If DLSU Provided)</p></td>
                            <td><center><input type="radio" size = "10%" name="q2.2" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.2" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>2.3. The vehicles are in good condition. (If DLSU Provided)</p></td>
                            <td><center><input type="radio" size = "10%" name="q2.3" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.3" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><p>2.4. The partner coordinators and adult companions were proactive in ensuring the safety of the students at all times.</p></td>
                            <td><center><input type="radio" size = "10%" name="q2.4" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.4" value="7"></center></td>
                        <tr>
                            <td><p>2.5. We felt safe and secure in the community during the community service / social engagement activity.</p></td>
                            <td><center><input type="radio" size = "10%" name="q2.5" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q2.5" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><b><p>3. PROJECT EFFECTIVENESS</b></p></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><p>3.1. Our project contributed to the efforts of the organization / community to respond to their own needs.</p></td>
                            <td><center><input type="radio" size = "10%" name="q3.1" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q3.1" value="7"></center></td>
                        </tr>
                        <tr>
                            <td><b><p>4. PROJECT EFFICIENCY</b></p></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><p>4.1. A project / activity plan was formulated.</p></td>
                            <td><center><input type="radio" size = "10%" name="q4.1" value="5"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="4"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="3"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="2"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="1"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="6"></center></td>
                        <td><center><input type="radio" size = "10%" name="q4.1" value="7"></center></td>
                        </tr>

                    </table></center>
                <br>
            </fieldset>

            <button type="submit" name="seID" value="<%=request.getAttribute("seID")%>">Next</button>
        </form>
    </div>
</body>

</html>
