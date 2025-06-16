<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion Escale - Accueil</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="icon">
        <i class="fas fa-anchor"></i>
    </div>
    <h1>Bienvenue sur la plateforme Gestion Escale</h1>
    <p>Veuillez vous connecter en tant qu'Administrateur ou Agent pour accéder à la gestion des escales et des navires.</p>

    <div class="btn-container">
        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn-login">Se connecter</a>
    </div>

    <div class="footer">
        <p>&copy; 2025 Gestion Escale, Marieme KAMARA. Tous droits réservés.</p>
    </div>
</body>
</html>
