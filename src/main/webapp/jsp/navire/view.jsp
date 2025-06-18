<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Détails du navire</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
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
            <p><strong>Consignataire :</strong> ${navire.consignataire.raisonSociale}</p>            
            <tr>
			  <th>Armateur</th>
			  <td>
			    <c:choose>
			      <c:when test="${not empty navire.armateur}">
			        ${navire.armateur.nomArmateur}
			      </c:when>
			      <c:otherwise>
			        <span class="text-danger bg-light-rouge"><em>Non défini !</em></span>
			      </c:otherwise>
			    </c:choose>
			  </td>
			</tr>
        </div>
        <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
