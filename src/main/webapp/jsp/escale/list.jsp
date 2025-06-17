<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Escales</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des escales" />
    </jsp:include>

    <div class="content-container">
        <h2>Liste des Escales</h2>
        <a href="${pageContext.request.contextPath}/escale/new" class="btn-new">
            <i class="fas fa-plus"></i> Ajouter une Escale
        </a>
        <table>
            <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Prix Séjour</th>
                    <th>Date Début</th>
                    <th>Date Fin</th>
                    <th>Navire</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="escale" items="${escales}">
                    <tr>
                        <td>${escale.numeroEscale}</td>
                        <td>${escale.prixSejour}</td>
                        <td>${escale.debutEscale}</td>
                        <td>${escale.finEscale}</td>
                        <td>${escale.myNavire.nomNavire}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/escale/view?id=${escale.numeroEscale}" title="Voir">
                                <i class="fas fa-eye"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/escale/edit?id=${escale.numeroEscale}" title="Modifier">
                                <i class="fas fa-edit"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/escale/delete?id=${escale.numeroEscale}" title="Supprimer" onclick="return confirm('Supprimer cette escale ?');">
                                <i class="fas fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à l'accueil
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp" />
</body>
</html>