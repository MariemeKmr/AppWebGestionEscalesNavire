<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Escales</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">    
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
