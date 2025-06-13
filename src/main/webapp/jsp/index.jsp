<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion Escale - Accueil</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f7fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
            color: #2c3e50;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 10px;
        }

        p {
            text-align: center;
            max-width: 600px;
            margin-bottom: 30px;
            font-size: 18px;
        }

        .btn-container {
            margin-top: 20px;
        }

        .btn-login {
            background-color: #2980b9;
            border: none;
            color: white;
            padding: 15px 30px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-login:hover {
            background-color: #1f618d;
            transform: scale(1.05);
        }

        .icon {
            margin-bottom: 20px;
            font-size: 50px;
            color: #2980b9;
        }

        .footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 10px 0;
        }
    </style>
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
