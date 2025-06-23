<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Générer la facture</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Générer la facture" />
</jsp:include>
<div class="content-container">
	<c:if test="${not empty errorMessage}">
	    <div class="alert alert-danger">${errorMessage}</div>
	</c:if>
    <h2>Générer la facture pour l'escale ${escale.numeroEscale} (${escale.myNavire.nomNavire})</h2>
    <form method="post" action="${pageContext.request.contextPath}/facture">
        <input type="hidden" name="numeroEscale" value="${escale.numeroEscale}"/>
        <table>
            <thead>
                <tr>
                    <th>Type de bon</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Montant (€)</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="bon" items="${bons}">
                <tr>
                    <td>${bon.typeMouvement.libelleTypeMvt}</td>
                    <td><fmt:formatDate value="${bon.dateDeBon}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${bon.dateFinBon}" pattern="dd/MM/yyyy"/></td>
                    <td>
                        <input type="hidden" name="idsBons" value="${bon.idMouvement}"/>
                        ${bon.montEscale}
                    </td>
                </tr>
            </c:forEach>
            <tr style="font-weight: bold;">
                <td colspan="3" style="text-align:right;">Total</td>
                <td>
                    <span style="color:#1765a0;">${totalMontant}</span>
                    <input type="hidden" name="montantTotal" value="${totalMontant}"/>
                </td>
            </tr>
            </tbody>
        </table>
        <button type="submit" class="btn-new"><i class="fas fa-check"></i> Générer la facture</button>
        <a href="${pageContext.request.contextPath}/facture?action=escalesTerminees" class="btn-back">
            <i class="fas fa-arrow-left"></i> Annuler
        </a>
    </form>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>