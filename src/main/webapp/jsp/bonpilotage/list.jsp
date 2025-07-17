<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des bons de pilotage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bonpilotage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des bons de pilotage"/>
</jsp:include>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="content-container">
    <h2>Liste des bons de pilotage</h2>
    <div class="search-bar">
        <div class="search-input-group">
            <input type="text" id="searchBon" placeholder="Recherche (navire, escale, date...)" onkeyup="filterBons()">
            <i class="fas fa-search"></i>
        </div>
        <a href="${pageContext.request.contextPath}/bonpilotage/new" class="btn-new"><i class="fas fa-plus"></i> Nouveau bon</a>
    </div>
    <c:if test="${empty bons}">
        <div class="alert alert-info">Aucun bon trouvé.</div>
    </c:if>
    <c:if test="${not empty bons}">
        <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Numéro Escale</th>
                        <th>Navire</th>
                        <th>Type</th>
                        <th>Date début</th>
                        <th>Date fin</th>
                        <th>Poste à quai</th>
                        <th>Prix</th>
                        <th>État</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="bon" items="${bons}">
                    <tr>
                        <td>${bon.idMouvement}</td>
                        <td><c:out value="${bon.monEscale.numeroEscale}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty bon.monEscale.myNavire}">
                                   ${bon.monEscale.myNavire.numeroNavire}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger"><em>Non renseigné</em></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty bon.typeMouvement}">
                                    ${bon.typeMouvement.libelleTypeMvt}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger"><em>–</em></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${bon.dateDeBon}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${bon.dateFinBon}" pattern="yyyy-MM-dd"/></td>
                        <td>${bon.posteAQuai}</td>
                        <td><span class="fw-bold"><fmt:formatNumber value="${bon.montEscale}" type="number" groupingUsed="true"/> F CFA</span></td>
                        <td>
                            <span class="badge
                                <c:choose>
                                    <c:when test="${bon.etat eq 'Valide'}">badge-success</c:when>
                                    <c:when test="${bon.etat eq 'Saisie'}">badge-warning</c:when>
                                    <c:otherwise>badge-secondary</c:otherwise>
                                </c:choose>">${bon.etat}</span>
                        </td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/bonpilotage/view?id=${bon.idMouvement}" title="Voir"><i class="fas fa-eye"></i></a>
                            <a href="${pageContext.request.contextPath}/bonpilotage/edit?id=${bon.idMouvement}" title="Modifier"><i class="fas fa-edit"></i></a>
                            <a href="${pageContext.request.contextPath}/bonpilotage/delete?id=${bon.idMouvement}" title="Supprimer" onclick="return confirm('Supprimer ce bon ?');"><i class="fas fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à l'accueil
    </a>
</div>
<jsp:include page="/includes/footer.jsp" />
<script>
    function filterBons() {
        let input = document.getElementById('searchBon');
        let filter = input.value.toLowerCase();
        let rows = document.querySelectorAll('tbody tr');
        rows.forEach(row => {
            let text = row.innerText.toLowerCase();
            row.style.display = text.indexOf(filter) > -1 ? '' : 'none';
        });
    }
</script>
</body>
</html>