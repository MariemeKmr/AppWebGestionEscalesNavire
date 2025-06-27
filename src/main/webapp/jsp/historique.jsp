<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/historique.css">

<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Historique du système"/>
</jsp:include>

<div class="content-container">
    <h2>Historique du système</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Ajout d'un formulaire de recherche -->
    <form method="get" action="${pageContext.request.contextPath}/historique" class="search-form">
        <input type="text" name="recherche" placeholder="Recherche (ex: facture, utilisateur…)" value="${param.recherche}" />
        <button type="submit" class="btn-submit">Rechercher</button>
        <c:if test="${not empty param.recherche}">
            <a href="${pageContext.request.contextPath}/historique" class="btn-back">Réinitialiser</a>
        </c:if>
    </form>

    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Utilisateur</th>
                <th>Opération</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="h" items="${historiques}">
                <tr>
                    <td><fmt:formatDate value="${h.dateOperation}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                    <td>${h.utilisateur}</td>
                    <td>${h.operation}</td>
                    <td>${h.description}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour au dashboard
    </a>
</div>

<jsp:include page="/includes/footer.jsp"/>