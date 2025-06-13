<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails du navire</title>
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

        .navire-details {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .navire-details p {
            margin: 10px 0;
            font-size: 16px;
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
        <jsp:param name="title" value="Détails du navire"/>
    </jsp:include>

    <div class="content-container">
        <h2>Détails du navire</h2>
        <div class="navire-details">
            <p><strong>Numéro du navire :</strong> ${navire.numeroNavire}</p>
            <p><strong>Nom du navire :</strong> ${navire.nomNavire}</p>
            <p><strong>Longueur :</strong> ${navire.longueurNavire} mètres</p>
            <p><strong>Largeur :</strong> ${navire.largeurNavire} mètres</p>
            <p><strong>Volume :</strong> ${navire.volumeNavire} tonnes</p>
            <p><strong>Tirant d'eau :</strong> ${navire.tiranEauNavire} mètres</p>
            <p><strong>Consignataire :</strong> ${navire.consignataire.nomConsignataire}</p>
        </div>
        <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
