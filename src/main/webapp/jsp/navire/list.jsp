<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<title>Liste des navires</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des navires" />
    </jsp:include>

    <div class="content-container">
        <h2>Liste des navires</h2>
        <a href="${pageContext.request.contextPath}/navire?action=new"
           class="btn-new"> <i class="fas fa-plus"></i> Ajouter un navire
        </a>
        <table>
            <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Nom</th>
                    <th>Longueur</th>
                    <th>Largeur</th>
                    <th>Volume</th>
                    <th>Tirant d'eau</th>
                    <th>Consignataire</th>
                    <th>Armateur</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="navire" items="${navires}">
                <tr>
                    <td>${navire.numeroNavire}</td>
                    <td>${navire.nomNavire}</td>
                    <td>${navire.longueurNavire}</td>
                    <td>${navire.largeurNavire}</td>
                    <td>${navire.volumeNavire}</td>
                    <td>${navire.tiranEauNavire}</td>
                    <!-- Consignataire -->
                    <td>
                        <c:choose>
                            <c:when test="${not empty navire.consignataire}">
                                ${navire.consignataire.raisonSociale}
                            </c:when>
                            <c:otherwise>
                                <span class="text-danger bg-light-rouge"><em>Non renseigné</em></span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <!-- Armateur -->
                    <td>
                        <c:choose>
                            <c:when test="${not empty navire.armateur}">
                                ${navire.armateur.nomArmateur}
                            </c:when>
                            <c:otherwise>
                                <span class="text-danger"><em>Non défini !</em></span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/navire?action=view&numeroNavire=${navire.numeroNavire}" title="Voir"><i class="fas fa-eye"></i></a>
                        <a href="${pageContext.request.contextPath}/navire?action=edit&numeroNavire=${navire.numeroNavire}" title="Modifier"><i class="fas fa-edit"></i></a>
                        <a href="${pageContext.request.contextPath}/navire?action=delete&numeroNavire=${navire.numeroNavire}" title="Supprimer" onclick="return confirm('Supprimer ce navire ?');"><i class="fas fa-trash"></i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
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
            <i class="fas fa-arrow-left"></i> Retour à l'acceuil
        </a>
    </div>
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>