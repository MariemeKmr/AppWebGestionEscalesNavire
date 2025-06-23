<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${formTitle}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/utilisateur.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Formulaire utilisateur"/>
</jsp:include>

<div class="content-container">
    <h2><c:out value="${formTitle}"/></h2>
    <form method="post" action="${formAction}">
        <input type="hidden" name="id" value="${utilisateur.id}"/>

        <!-- Infos de base utilisateur -->
        <div class="section-card">
            <div class="section-card-title">Informations personnelles</div>
            <label for="nomComplet">Nom complet :</label>
            <input type="text" id="nomComplet" name="nomComplet" value="${utilisateur.nomComplet}" required/>

            <label for="email">Email :</label>
            <input type="email" id="email" name="email" value="${utilisateur.email}" required/>

            <label for="telephone">Téléphone :</label>
            <input type="text" id="telephone" name="telephone" value="${utilisateur.telephone}" required/>
        </div>

        <!-- Authentification -->
        <div class="section-card">
            <div class="section-card-title">Sécurité et rôle</div>
            <label for="motDePasse">Mot de passe :</label>
            <input type="password" id="motDePasse" name="motDePasse" value="${utilisateur.motDePasse}" required/>

            <label for="role">Rôle :</label>
            <select id="role" name="role" required>
                <option value="admin" ${utilisateur.role == 'admin' ? 'selected' : ''}>Admin</option>
                <option value="agent_portuaire" ${utilisateur.role == 'agent_portuaire' ? 'selected' : ''}>Agent portuaire</option>
                <option value="agent_facturation" ${utilisateur.role == 'agent_facturation' ? 'selected' : ''}>Agent facturation</option>
            </select>
        </div>

        <button type="submit">Enregistrer</button>
    </form>
    <a href="${pageContext.request.contextPath}/utilisateur?action=list" class="btn-back" style="margin-top:15px;">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>

<jsp:include page="/includes/footer.jsp"/>
</body>
</html>