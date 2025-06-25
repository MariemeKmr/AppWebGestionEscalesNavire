<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Modifier la facture</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
</head>
<body>
<jsp:include page="/includes/header.jsp" />

<div class="content-container">
    <h2>Modifier la facture n°${facture.numeroFacture}</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/facture">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="id" value="${facture.id}"/>
        <div class="mb-3">
            <label>Prix du séjour</label>
            <input type="number" step="0.01" class="form-control" name="prixSejour" value="${facture.escale.prixSejour}" required/>
        </div>
        <h5>Bons de pilotage</h5>
        <table class="table">
            <thead>
                <tr>
                    <th>Type de bon</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Montant (F CFA)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="bon" items="${facture.bonsPilotage}">
                <tr>
                    <td>${bon.typeMouvement.libelleTypeMvt}</td>
                    <td><fmt:formatDate value="${bon.dateDeBon}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${bon.dateFinBon}" pattern="dd/MM/yyyy"/></td>
                    <td>
                        <input type="hidden" name="bonId" value="${bon.idMouvement}"/>
                        <input type="number" step="0.01" class="form-control" 
                               name="montantBon_${bon.idMouvement}" value="${bon.montEscale}" required/>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit" class="btn-new"><i class="fas fa-check"></i> Valider les modifications</button>
        <a href="${pageContext.request.contextPath}/facture" class="btn-back"><i class="fas fa-arrow-left"></i> Annuler</a>
    </form>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>