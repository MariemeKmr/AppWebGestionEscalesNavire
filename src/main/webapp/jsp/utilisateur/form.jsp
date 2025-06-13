<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${formTitle}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="email"], input[type="password"], select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button[type="submit"] {
            background-color: #3498db;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #2980b9;
        }
        
       .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .btn-back:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Formulaire utilisateur"/>
    </jsp:include>

    <div class="container">
        <h2><c:out value="${formTitle}"/></h2>
        <form method="post" action="${formAction}">
            <input type="hidden" name="id" value="${utilisateur.id}"/>
            <label for="nomComplet">Nom complet :</label>
            <input type="text" id="nomComplet" name="nomComplet" value="${utilisateur.nomComplet}" required/>

            <label for="email">Email :</label>
            <input type="email" id="email" name="email" value="${utilisateur.email}" required/>

            <label for="motDePasse">Mot de passe :</label>
            <input type="password" id="motDePasse" name="motDePasse" value="${utilisateur.motDePasse}" required/>

            <label for="role">Rôle :</label>
            <select id="role" name="role" required>
                <option value="admin" ${utilisateur.role == 'admin' ? 'selected' : ''}>Admin</option>
                <option value="agent" ${utilisateur.role == 'agent' ? 'selected' : ''}>Agent</option>
            </select>
            <button type="submit">Enregistrer</button>
        </form>
        <a href="${pageContext.request.contextPath}/utilisateur?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
