<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails de l’escale</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Détails de l’escale"/>
</jsp:include>

<div class="content-container">
    <h2>Détails de l’escale</h2>
    <div class="navire-details">
        <p><strong>Numéro escale :</strong> ${escale.numeroEscale}</p>
        <p><strong>Navire :</strong> ${escale.myNavire.nomNavire}</p>
        <p><strong>Date début :</strong> ${escale.debutEscale}</p>
        <p><strong>Date fin :</strong> ${escale.finEscale}</p>
        <p><strong>Prix séjour :</strong> ${escale.prixSejour}</p>
        <p><strong>Zone :</strong> ${escale.zone}</p>
        <p><strong>Consignataire :</strong>
            <c:choose>
                <c:when test="${not empty escale.consignataire}">
                    ${escale.consignataire.raisonSociale}
                </c:when>
                <c:otherwise>
                    <span class="text-danger"><em>Non défini !</em></span>
                </c:otherwise>
            </c:choose>
        </p>
    </div>
    <a href="${pageContext.request.contextPath}/escale/" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>
<jsp:include page="/includes/footer.jsp"/>
</body>
</html>