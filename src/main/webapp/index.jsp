<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>

<%
    String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Proyecto Enrolamiento Digital</title>

        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
                background-color: #f4f6f9;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 800px;
                margin: 80px auto;
                background: #ffffff;
                padding: 40px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }

            h1 {
                color: #2c3e50;
                margin-bottom: 10px;
            }

            h2 {
                color: #16a085;
                margin-top: 0;
            }

            .info {
                margin-top: 30px;
                padding: 15px;
                background: #ecf0f1;
                border-left: 5px solid #16a085;
            }

            .label {
                font-weight: bold;
                color: #2c3e50;
            }

            footer {
                margin-top: 40px;
                text-align: center;
                font-size: 12px;
                color: #7f8c8d;
            }
        </style>
    </head>

    <body>

        <div class="container">

            <h1>Proyecto Enrolamiento Digital</h1>
            <h2>Version 1.1.0</h2>

            <div class="info">
                <p><span class="label">Fecha:</span> <%= fecha%></p>
                <p><span class="label">Servidor:</span> Weblogic</p>
            </div>

        </div>

    </body>

</html>