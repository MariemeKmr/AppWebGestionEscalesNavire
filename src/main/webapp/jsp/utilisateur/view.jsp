<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails utilisateur</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .content-container {
            margin-top: 80px;
            max-width: 800px;
            margin: 80px auto 20px;
            padding: 40px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            padding: 15px;
            margin-bottom: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
            font-size: 18px;
        }

        .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            font-size: 16px;
        }

        .btn-back:hover {
            background-color: #2980b9;
        }
    </style>
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
