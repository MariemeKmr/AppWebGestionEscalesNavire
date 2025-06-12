<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Escales</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .container {
            width: 90%;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Liste des Escales</h1>
        <a href="${pageContext.request.contextPath}/escale/new" class="button">Ajouter une Escale</a>
        <table>
            <tr>
                <th>Numéro</th>
                <th>Prix Séjour</th>
                <th>Date Début</th>
                <th>Date Fin</th>
                <th>Navire</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="escale" items="${escales}">
                <tr>
                    <td>${escale.numero}</td>
                    <td>${escale.prixSejour}</td>
                    <td>${escale.dateDebut}</td>
                    <td>${escale.dateFin}</td>
                    <td>${escale.navire.nom}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/escale/edit?id=${escale.id}" class="button">Modifier</a>
                        <a href="${pageContext.request.contextPath}/escale/delete?id=${escale.id}" class="button">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
