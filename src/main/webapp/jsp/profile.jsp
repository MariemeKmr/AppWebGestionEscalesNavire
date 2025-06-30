<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon profil - Gestion des Escales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body class="profile-page">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/dashboard.jsp">
                <i class="fas fa-anchor nav-icon"></i> Gestion Escales
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item notification-icon">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/notification.jsp">
                            <i class="fas fa-bell"></i>
                            <span class="badge">3</span>
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button">
                            <i class="fas fa-user nav-icon"></i>
                            <span><%= session.getAttribute("utilisateurNom") %></span>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/utilisateur?action=profile">
                                    <i class="fas fa-user nav-icon"></i> Profil
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <form action="${pageContext.request.contextPath}/LogoutServlet" method="post" style="display:inline;">
                                    <button type="submit" class="dropdown-item" style="background:none; border:none; padding:0; width:100%; text-align:left;">
                                        <i class="fas fa-sign-out-alt nav-icon"></i> Déconnexion
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container profile-card mt-5 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <h2 class="mb-4 text-center">Mon profil</h2>
                <!-- Formulaire mise à jour email/tel -->
                <form method="post" action="${pageContext.request.contextPath}/utilisateur">
                    <input type="hidden" name="action" value="updateProfile">
                    <div class="mb-3">
                        <label class="form-label">Nom Complet</label>
                        <input type="text" class="form-control" value="${utilisateur.nomComplet}" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Rôle</label>
                        <input type="text" class="form-control" value="${utilisateur.role}" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Adresse email</label>
                        <input type="email" name="email" class="form-control" value="${utilisateur.email}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Numéro de téléphone</label>
                        <input type="text" name="telephone" class="form-control" value="${utilisateur.telephone}" required>
                    </div>
                    <button type="submit" class="btn btn-success">Mettre à jour mes informations</button>
                    <c:if test="${not empty messageUpdate}">
                        <div class="alert alert-info mt-3">${messageUpdate}</div>
                    </c:if>
                </form>

                <hr>
                <h4 class="mb-3">Modifier mon mot de passe</h4>
                <form method="post" action="${pageContext.request.contextPath}/utilisateur">
                    <input type="hidden" name="action" value="updatePassword">
                    <div class="mb-3">
                        <label class="form-label">Ancien mot de passe</label>
                        <input type="password" name="ancienMotDePasse" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Nouveau mot de passe</label>
                        <input type="password" name="nouveauMotDePasse" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Confirmer le nouveau mot de passe</label>
                        <input type="password" name="confirmerMotDePasse" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Modifier le mot de passe</button>
                    <c:if test="${not empty messagePwd}">
                        <div class="alert alert-info mt-3">${messagePwd}</div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>