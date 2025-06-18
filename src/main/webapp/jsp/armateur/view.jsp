<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails de l'armateur</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/armateur.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Détails de l'armateur"/>
    </jsp:include>

    <div class="content-container">
        <h2>Détails de l'armateur</h2>
        <div class="consignataire-details">
            <p><strong>Nom :</strong> ${armateur.nomArmateur}</p>
            <p><strong>Adresse :</strong> ${armateur.adresseArmateur}</p>
            <p><strong>Téléphone :</strong> ${armateur.telephoneArmateur}</p>
        </div>
        
        <h3>Navires associés</h3>
        <c:choose>
            <c:when test="${empty navires}">
                <p>Aucun navire associé à cet armateur.</p>
            </c:when>
            <c:otherwise>
                <ul>
                    <c:forEach var="navire" items="${navires}">
                        <li>${navire.numeroNavire} - ${navire.nomNavire}</li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/armateur" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>