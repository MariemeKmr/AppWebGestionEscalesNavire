<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Facture ${facture.numeroFacture}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Facture ${facture.numeroFacture}" />
</jsp:include>
<div class="content-container">
    <div class="card mb-4">
        <div class="card-body">
            <h2>Facture n°${facture.numeroFacture}</h2>
            <p><strong>Date :</strong> <fmt:formatDate value="${facture.dateGeneration}" pattern="dd/MM/yyyy"/></p>
            <h5>Escale : ${escale.numeroEscale} - Navire : ${escale.myNavire.nomNavire}</h5>
        </div>
    </div>
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
                <td>${bon.montEscale}</td>
            </tr>
        </c:forEach>
        <tr style="font-weight: bold;">
            <td colspan="3" style="text-align:right;">Total</td>
            <td>
                <span style="color:#1765a0;">${facture.montantTotal}</span>
            </td>
        </tr>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/facture/download?facture=${facture.id}" class="btn-new">
        <i class="fas fa-download"></i> Télécharger la facture (PDF)
    </a>
    <a href="${pageContext.request.contextPath}/facture" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>