<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des escales</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des escales" />
</jsp:include>

<c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="content-container">
    <h2>Liste des escales</h2>
    <a href="${pageContext.request.contextPath}/escale/new" class="btn-new">
        <i class="fas fa-plus"></i> Ajouter une escale
    </a>
    <table>
        <thead>
            <tr>
                <th>Numéro</th>
                <th>Numéro Navire</th>
                <th>Nom Navire</th>
                <th>Début</th>
                <th>Fin</th>
                <th>Prix Séjour</th>
                <th>Zone</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="escale" items="${escales}">
            <tr>
                <td>${escale.numeroEscale}</td>
                <td><c:out value="${escale.myNavire.numeroNavire}"/></td>
                <td><c:out value="${escale.myNavire.nomNavire}"/></td>
                <td>${escale.debutEscale}</td>
                <td>${escale.finEscale}</td>
                <td>${escale.prixSejour}</td>
                <td>${escale.zone}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/escale/view?numeroEscale=${escale.numeroEscale}" title="Voir"><i class="fas fa-eye"></i></a>
                    <a href="${pageContext.request.contextPath}/escale/edit?numeroEscale=${escale.numeroEscale}" title="Modifier"><i class="fas fa-edit"></i></a>
                    <a href="${pageContext.request.contextPath}/escale/delete?numeroEscale=${escale.numeroEscale}" title="Supprimer" onclick="return confirm('Supprimer cette escale ?');"><i class="fas fa-trash"></i></a>
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