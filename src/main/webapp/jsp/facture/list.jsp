<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des factures</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des factures" />
</jsp:include>

<div class="content-container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Liste des factures</h2>
        <a href="${pageContext.request.contextPath}/facture?action=escalesTerminees" class="btn-new">
            <i class="fas fa-plus"></i> Facturer une escale terminée
        </a>
    </div>
    <table>
        <thead>
            <tr>
                <th>Numéro</th>
                <th>Date</th>
                <th>Escale</th>
                <th>Navire</th>
                <th>Montant</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="facture" items="${factures}">
            <tr>
                <td>${facture.numeroFacture}</td>
                <td><fmt:formatDate value="${facture.dateGeneration}" pattern="dd/MM/yyyy"/></td>
                <td>${facture.escale.numeroEscale}</td>
                <td>${facture.escale.myNavire.nomNavire}</td>
                <td>${facture.montantTotal}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/facture?action=view&id=${facture.id}" title="Voir">
                        <i class="fas fa-eye"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/facture/download?facture=${facture.id}" title="Télécharger PDF">
                        <i class="fas fa-file-pdf"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- Pagination éventuelle -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Pagination">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage - 1}">&laquo; Précédent</a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage + 1}">Suivant &raquo;</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </c:if>

    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à l'accueil
    </a>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>