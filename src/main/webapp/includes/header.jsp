<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Gestion des Escales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .navbar {
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 10px 0;
            background: linear-gradient(90deg, #2c3e50, #3498db);
        }

        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
        }

        .nav-icon {
            margin-right: 8px;
        }

        .user-menu img {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            object-fit: cover;
        }

        .dropdown-menu {
            display: block;
            position: absolute;
            right: 0;
            left: auto;
            min-width: 150px;
            opacity: 0;
            visibility: hidden;
            transition: opacity 0.3s ease, visibility 0.3s;
        }

        .dropdown:hover .dropdown-menu {
            opacity: 1;
            visibility: visible;
        }

        .dropdown-item {
            padding: 8px 20px;
        }

        .dropdown-item i {
            margin-right: 10px;
        }

        .navbar-nav .nav-link {
            display: flex;
            align-items: center;
        }

        .navbar-toggler {
            border: none;
        }

        .notification-icon {
            position: relative;
            margin-right: 15px;
        }

        .notification-icon .badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: #e74c3c;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 10px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="/jsp/dashboard.jsp">
                <i class="fas fa-anchor nav-icon"></i> Gestion Escales
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item notification-icon">
                        <a class="nav-link" href="notification.jsp">
                            <i class="fas fa-bell"></i>
                            <span class="badge">3</span>
                        </a>
                    </li>
                    <li class="nav-item dropdown user-menu">
                        <a class="nav-link" href="#" id="navbarDropdown" role="button">
                            <i class="fas fa-user nav-icon"></i>
                            <span><%= session.getAttribute("utilisateurNom") %></span>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-user nav-icon"></i> Profil
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item" href="<c:url value='/logout'/>">
                                    <i class="fas fa-sign-out-alt nav-icon"></i> Déconnexion
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
