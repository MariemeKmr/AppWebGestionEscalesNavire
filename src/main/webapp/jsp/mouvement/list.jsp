<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Mouvements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mouvement.css">
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
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à l'acceuil
        </a>
    </div>
</body>
</html>
