<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails utilisateur</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/utilisateur.css">    
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Détails utilisateur"/>
    </jsp:include>

    <div class="content-container">
        <h2>Détails de l'utilisateur</h2>
        <ul>
            <li><strong>Nom complet :</strong> ${utilisateur.nomComplet}</li>
            <li><strong>Email :</strong> ${utilisateur.email}</li>            
            <li><strong>Téléphone :</strong> ${utilisateur.telephone}</li>
            <li><strong>Rôle :</strong> ${utilisateur.role}</li>
        </ul>
        <a href="${pageContext.request.contextPath}/utilisateur?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
