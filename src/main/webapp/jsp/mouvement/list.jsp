<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Mouvements</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
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
        <h2>${message}</h2>
        <a href="${pageContext.request.contextPath}/mouvement/new" class="button">Ajouter un Mouvement</a>
        <table>
            <tr>
                <th>ID</th>
                <th>Type de Mouvement</th>
                <th>Poste à Quai</th>
                <th>Date</th>
                <th>Navire</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="mouvement" items="${mouvements}">
                <tr>
                    <td>${mouvement.id}</td>
                    <td>${mouvement.typeMouvement.libelle}</td>
                    <td>${mouvement.posteAQuai}</td>
                    <td>${mouvement.date}</td>
                    <td>${mouvement.navire.nom}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/mouvement/edit?id=${mouvement.id}" class="button">Modifier</a>
                        <a href="${pageContext.request.contextPath}/mouvement/delete?id=${mouvement.id}" class="button">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
