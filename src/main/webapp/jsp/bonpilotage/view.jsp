<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Détail du bon de pilotage"/>
</jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">

<div class="content-container">
    <h2>Détail du bon de pilotage</h2>
    <table>
        <tr>
            <th>ID mouvement</th>
            <td>${bon.idMouvement}</td>
        </tr>
        <tr>
            <th>Numéro Escale</th>
            <td>${bon.monEscale.numeroEscale}</td>
        </tr>
        <tr>
            <th>Navire</th>
            <td>
                <c:choose>
                    <c:when test="${not empty bon.monEscale.myNavire}">
                        ${bon.monEscale.myNavire.nomNavire} (${bon.monEscale.myNavire.numeroNavire})
                    </c:when>
                    <c:otherwise>
                        <span class="text-danger"><em>Non renseigné</em></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>Consignataire</th>
            <td>
                <c:choose>
                    <c:when test="${not empty bon.monEscale.consignataire}">
                        ${bon.monEscale.consignataire.raisonSociale}
                    </c:when>
                    <c:otherwise>
                        <span class="text-danger"><em>Non renseigné</em></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>Type de mouvement</th>
            <td>
                <c:choose>
                    <c:when test="${not empty bon.typeMouvement}">
                        ${bon.typeMouvement.libelleTypeMvt}
                    </c:when>
                    <c:otherwise>
                        <span class="text-danger"><em>Non renseigné</em></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>Date début</th>
            <td><fmt:formatDate value="${bon.dateDeBon}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Date fin</th>
            <td><fmt:formatDate value="${bon.dateFinBon}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Poste à quai</th>
            <td>${bon.posteAQuai}</td>
        </tr>
        <tr>
            <th>Prix</th>
            <td><fmt:formatNumber value="${bon.montEscale}" type="number" groupingUsed="true"/> F CFA</td>
        </tr>
        <tr>
            <th>État</th>
            <td>
                <span class="badge
                    <c:choose>
                        <c:when test="${bon.etat eq 'Valide'}">badge-success</c:when>
                        <c:when test="${bon.etat eq 'Saisie'}">badge-warning</c:when>
                        <c:otherwise>badge-secondary</c:otherwise>
                    </c:choose>">${bon.etat}</span>
            </td>
        </tr>
    </table>
    <a href="${pageContext.request.contextPath}/bonpilotage/list" class="btn-back" style="margin-top:18px;">Retour à la liste</a>
</div>
<jsp:include page="/includes/footer.jsp"/>