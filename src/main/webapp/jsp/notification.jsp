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
        <div>
          <c:forEach var="escale" items="${escalesArrivees}">
            <div class="notification-card notification-arrivee">
              <span class="notification-icon"><i class="fas fa-ship"></i></span>
              <div class="notification-content">
                <div class="notification-title">
                  <c:choose>
                    <c:when test="${not empty escale.myNavire}">
                      ${escale.myNavire.nomNavire}
                    </c:when>
                    <c:otherwise>
                      <span class="text-danger">Navire inconnu</span>
                    </c:otherwise>
                  </c:choose>
                  <span class="notification-numero">(Escale n°${escale.numeroEscale})</span>
                </div>
                <div class="notification-time">
                  Arrivée prévue le <fmt:formatDate value="${escale.debutEscale}" pattern="dd/MM/yyyy"/>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>

        <h3>Navires partis cette semaine</h3>
        <c:if test="${empty escalesParties}">
            <p>Aucun navire n'a quitté le port cette semaine.</p>
        </c:if>
        <div>
          <c:forEach var="escale" items="${escalesParties}">
            <div class="notification-card notification-partie">
              <span class="notification-icon"><i class="fas fa-sign-out-alt"></i></span>
              <div class="notification-content">
                <div class="notification-title">
                  <c:choose>
                    <c:when test="${not empty escale.myNavire}">
                      ${escale.myNavire.nomNavire}
                    </c:when>
                    <c:otherwise>
                      <span class="text-danger">Navire inconnu</span>
                    </c:otherwise>
                  </c:choose>
                  <span class="notification-numero">(Escale n°${escale.numeroEscale})</span>
                </div>
                <div class="notification-time">
                  Départ le <fmt:formatDate value="${escale.finEscale}" pattern="dd/MM/yyyy"/>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>

        <h3>Escales terminées à facturer</h3>
        <c:if test="${empty escalesNonFacturees}">
            <p>Aucune escale terminée à facturer.</p>
        </c:if>
        <div>
          <c:forEach var="escale" items="${escalesNonFacturees}">
            <div class="notification-card notification-facture">
              <span class="notification-icon"><i class="fas fa-file-invoice-dollar"></i></span>
              <div class="notification-content">
                <div class="notification-title">
                  <c:choose>
                    <c:when test="${not empty escale.myNavire}">
                      ${escale.myNavire.nomNavire}
                    </c:when>
                    <c:otherwise>
                      <span class="text-danger">Navire inconnu</span>
                    </c:otherwise>
                  </c:choose>
                  <span class="notification-numero">(Escale n°${escale.numeroEscale})</span>
                  <span class="badge bg-warning text-dark ms-2">À facturer</span>
                </div>
                <div class="notification-time">
                  Terminée le <fmt:formatDate value="${escale.finEscale}" pattern="dd/MM/yyyy"/>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>