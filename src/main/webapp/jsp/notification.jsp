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

        <div class="notification-badge">
            <span class="badge" id="notificationCount"><c:out value="${notifications.size()}"/></span>
        </div>

        <c:forEach var="notification" items="${notifications}" varStatus="status">
            <div class="notification-card">
                <div class="notification-icon">
                    <c:choose>
                        <c:when test="${notification.type == 'chargement'}">
                            <i class="fas fa-box-open text-success"></i>
                        </c:when>
                        <c:when test="${notification.type == 'pilote'}">
                            <i class="fas fa-user-shield text-info"></i>
                        </c:when>
                        <c:when test="${notification.type == 'sanitaire'}">
                            <i class="fas fa-medkit text-warning"></i>
                        </c:when>
                        <c:when test="${notification.type == 'reservation'}">
                            <i class="fas fa-calendar-check text-primary"></i>
                        </c:when>
                        <c:when test="${notification.type == 'meteo'}">
                            <i class="fas fa-cloud-sun text-dark"></i>
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-ship text-muted"></i>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="notification-content">
                    <div class="notification-title">${notification.title}</div>
                    <div class="notification-description">${notification.description}</div>
                    <div class="notification-time">
                        <fmt:formatDate value="${notification.timestamp}" pattern="Il y a mm:ss" />
                    </div>
                </div>
                <a href="#" class="mark-as-read">Marquer comme lu</a>
            </div>
        </c:forEach>

        <c:if test="${empty notifications}">
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i> Aucune notification trouvée.
            </div>
        </c:if>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
