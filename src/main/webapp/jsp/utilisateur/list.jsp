<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des utilisateurs</title>
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
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }

        .btn-new {
            display: inline-block;
            margin: 20px 0;
            padding: 10px 15px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .btn-new:hover {
            background-color: #2980b9;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
            background: white;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .actions a {
            margin-right: 10px;
            color: #3498db;
            text-decoration: none;
        }

        .actions a:hover {
            color: #2980b9;
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des utilisateurs"/>
    </jsp:include>

    <div class="container">
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
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
