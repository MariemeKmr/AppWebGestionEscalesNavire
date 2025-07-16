<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications - Gestion des Escales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Notifications"/>
    </jsp:include>

    <div class="notification-container">
        <h2><i class="fas fa-bell"></i> Notifications</h2>

        <h3>Navires attendus cette semaine</h3>
        <c:if test="${empty escalesArrivees}">
            <p>Aucun navire attendu cette semaine.</p>
        </c:if>
        <ul>
            <c:forEach var="escale" items="${escalesArrivees}">
                <li>
                    <strong>${escale.myNavire.nomNavire}</strong>
                    — arrivée prévue le <fmt:formatDate value="${escale.debutEscale}" pattern="dd/MM/yyyy"/>
                    (Escale n°${escale.numeroEscale})
                </li>
            </c:forEach>
        </ul>

        <h3>Navires partis cette semaine</h3>
        <c:if test="${empty escalesParties}">
            <p>Aucun navire n'a quitté le port cette semaine.</p>
        </c:if>
        <ul>
            <c:forEach var="escale" items="${escalesParties}">
                <li>
                    <strong>${escale.myNavire.nomNavire}</strong>
                    — départ le <fmt:formatDate value="${escale.finEscale}" pattern="dd/MM/yyyy"/>
                    (Escale n°${escale.numeroEscale})
                </li>
            </c:forEach>
        </ul>

		<h3>Escales terminées à facturer</h3>
		<c:if test="${empty escalesNonFacturees}">
		    <p>Aucune escale terminée à facturer.</p>
		</c:if>
		<ul>
		  <c:forEach var="escale" items="${escalesNonFacturees}">
		    <li>
		      <strong>${escale.myNavire.nomNavire}</strong>
		      — terminée le <fmt:formatDate value="${escale.finEscale}" pattern="dd/MM/yyyy"/>
		      (Escale n°${escale.numeroEscale}) <span class="badge bg-warning text-dark">À facturer</span>
		    </li>
		  </c:forEach>
		</ul>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>