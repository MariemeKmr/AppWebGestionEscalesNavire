<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
            <th>Navire</th>
            <td>${bon.monEscale.myNavire.nomNavire} (${bon.monEscale.myNavire.numeroNavire})</td>
        </tr>
        <tr>
            <th>Consignataire</th>
            <td>${bon.monEscale.consignataire.raisonSociale}</td>
        </tr>
        <tr>
            <th>Type de mouvement</th>
            <td>${bon.typeMouvement.libelleTypeMvt}</td>
        </tr>
        <tr>
            <th>Date début</th>
            <td>${bon.dateDeBon}</td>
        </tr>
        <tr>
            <th>Date fin</th>
            <td>${bon.dateFinBon}</td>
        </tr>
        <tr>
            <th>Poste à quai</th>
            <td>${bon.posteAQuai}</td>
        </tr>
        <tr>
            <th>Prix</th>
            <td><fmt:formatNumber value="${bon.montEscale}" type="number" groupingUsed="true"/> F CFA</td>
        </tr>
    </table>
    <a href="${pageContext.request.contextPath}/bonpilotage/list" class="btn-back" style="margin-top:18px;">Retour à la liste</a>
</div>
<jsp:include page="/includes/footer.jsp"/>