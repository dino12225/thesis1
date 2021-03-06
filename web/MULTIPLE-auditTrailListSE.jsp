<%-- 
    Document   : MULTIPLE-createSE
    Created on : 06 12, 18, 1:25:42 PM
    Author     : Karl Madrid
--%>

<%@page import="entity.SE"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Audit Trail SE</title>

        <link rel="stylesheet" href="css/formstyle5.css">

        <style>
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

            body {
                background: #fff;
                box-shadow: 0 0 2px rgba(0, 0, 0, 0.06);
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 16px;
                line-height: 1.5;
                margin: 0 auto;
                max-width: 800px;
                padding: 2em 2em 4em;
            }

            h1{
                font-family: "Arial", Helvetica, sans-serif;
                font-size: 20px;
                border-bottom: 2px solid green;
                border-top: 2px solid green;
                padding-bottom: 10px;
                padding-top: 10px;
            }

            h2 {
                margin-top: 1.3em;
            }

            a {
                color: #0083e8;
            }

            b{
                font-weight: 600;
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
                font-family: "Arial", Helvetica, sans-serif;
            }

            table {
                border-collapse: collapse;

            }
            
            th{
                padding:15px;
            }

            p{
                margin-left: 10px;
                font-family: "Arial", Helvetica, sans-serif;
            }
            
            .btn-success{
                background-color: dodgerblue;
                border: none;
                border-radius: 5px;
                color: white;
                padding: 8px 10px;
                text-align: center;
                display: inline-block;
                margin: 4px 2px;
                font-size: 12px;
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
        
        <center><h1>List of Revisions</h1></center>

    <%
        UserDAO UserDAO = new UserDAO();
        ArrayList<SE> revisions = new ArrayList();
        revisions = UserDAO.retrieveSERevisions(Integer.parseInt(session.getAttribute("auditSE").toString()));
    %>

    <div class="form-style-5">
        <form action = "viewAuditSE">
            <fieldset>
                <center><table style = "width:100%" id = "SEchecklist">
                        <tr>
                            <th>Proposal Name</th>
                            <th>Date Submitted:</th>
                            <th></th>
                                <%
                                    for (int i = 0; i < revisions.size(); i++) {
                                %>
                        <tr>
                            <td><p><%=revisions.get(i).getName()%></p></td>
                            <td><p><b><%=revisions.get(i).getRevisionTime()%></b></p></td>
                            <td><center><button  type="submit" class="btn-success" name="auditSE<%=i%>" value="<%=revisions.get(i).getId()%>">View</button></center></td>
                        </tr>

                        <%
                            }
                        %>
                    </table></center>
                <br>
            </fieldset>
        </form>
    </div>
</body>

</html>