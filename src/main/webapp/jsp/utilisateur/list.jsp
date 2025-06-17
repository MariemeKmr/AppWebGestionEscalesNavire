<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des utilisateurs</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/utilisateur.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des utilisateurs"/>
    </jsp:include>

    <div class="content-container">
        <h2>Liste des utilisateurs</h2>
        <a href="${pageContext.request.contextPath}/utilisateur?action=new" class="btn-new">
            <i class="fas fa-plus"></i> Ajouter un utilisateur
        </a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Rôle</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="utilisateur" items="${utilisateurs}">
                    <tr>
                        <td>${utilisateur.id}</td>
                        <td>${utilisateur.nomComplet}</td>
                        <td>${utilisateur.email}</td>
                        <td>${utilisateur.role}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/utilisateur?action=view&id=${utilisateur.id}" title="Voir"><i class="fas fa-eye"></i></a>
                            <a href="${pageContext.request.contextPath}/utilisateur?action=edit&id=${utilisateur.id}" title="Modifier"><i class="fas fa-edit"></i></a>
                            <a href="${pageContext.request.contextPath}/utilisateur?action=delete&id=${utilisateur.id}" title="Supprimer" onclick="return confirm('Supprimer cet utilisateur ?');"><i class="fas fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à l'acceuil
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>