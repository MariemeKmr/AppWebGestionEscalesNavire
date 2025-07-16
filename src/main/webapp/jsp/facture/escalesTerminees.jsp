<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Escale(s) terminée(s) à facturer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Escale(s) terminée(s) à facturer" />
</jsp:include>

<div class="content-container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Escale(s) terminée(s) à facturer</h2>
    </div>
    <c:if test="${not empty errorMessage}">
	    <div class="alert alert-danger">${errorMessage}</div>
	</c:if>
    <table>
        <thead>
            <tr>
                <th>Numéro Escale</th>
                <th>Navire</th>
                <th>Début</th>
                <th>Fin</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:if test="${empty escalesTerminees}">
		    <tr>
		        <td colspan="5" class="text-center text-danger">
		            <i class="fas fa-info-circle"></i> Aucune escale terminée à facturer pour le moment.
		        </td>
		    </tr>
		</c:if>
        <c:forEach var="escale" items="${escalesTerminees}">
            <tr>
                <td>${escale.numeroEscale}</td>
                <td>${escale.myNavire.nomNavire}</td>
                <td><fmt:formatDate value="${escale.debutEscale}" pattern="dd/MM/yyyy"/></td>
                <td><fmt:formatDate value="${escale.finEscale}" pattern="dd/MM/yyyy"/></td>
                <td>
				<form action="${pageContext.request.contextPath}/facture" method="post" style="display:inline;">
				    <input type="hidden" name="numeroEscale" value="${escale.numeroEscale}" />
				    <button type="submit" class="btn-new btn-sm">
				        <i class="fas fa-file-invoice-dollar"></i> Générer facture
				    </button>
				</form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/facture" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à la liste des factures
    </a>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>