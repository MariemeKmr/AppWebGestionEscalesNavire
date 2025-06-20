<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des bons de pilotage"/>
</jsp:include>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">
<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if> 
<div class="content-container">
    <h2>Liste des bons de pilotage</h2>

    <!-- Filtres -->
    <form method="get" action="${pageContext.request.contextPath}/bonpilotage/list" class="row g-3 mb-4">
        <div class="col-md-3">
            <input type="text" name="navire" class="form-control" placeholder="Nom ou numéro navire" value="${param.navire}">
        </div>
        <div class="col-md-3">
            <input type="text" name="consignataire" class="form-control" placeholder="Nom consignataire" value="${param.consignataire}">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn-submit">Filtrer</button>
        </div>
        <div class="col-md-2">
            <a href="${pageContext.request.contextPath}/bonpilotage/list" class="btn-back">Réinitialiser</a>
        </div>
        <div class="col-md-2 text-end">
            <a href="${pageContext.request.contextPath}/bonpilotage/new" class="btn-new"><i class="fas fa-plus"></i> Nouveau bon</a>
        </div>
    </form>

    <c:if test="${empty bons}">
        <div class="alert alert-info">Aucun bon trouvé.</div>
    </c:if>
    <c:if test="${not empty bons}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Navire</th>
                    <th>Consignataire</th>
                    <th>Type</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Poste à quai</th>
                    <th>Prix</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="bon" items="${bons}">
                    <tr>
                        <td>${bon.idMouvement}</td>
                        <td>
                            <c:out value="${bon.monEscale.myNavire.nomNavire}"/> 
                            (<c:out value="${bon.monEscale.myNavire.numeroNavire}"/>)
                        </td>
                        <td>
                            <c:out value="${bon.monEscale.myNavire.consignataire.nomConsignataire}" />
                        </td>
                        <td>${bon.typeMouvement.nomTypeMvt}</td>
                        <td>${bon.dateDeBon}</td>
                        <td>${bon.dateFinBon}</td>
                        <td>${bon.posteAQuai}</td>
                        <td>${bon.prix}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/bonpilotage/view?id=${bon.idMouvement}" title="Voir"><i class="fas fa-eye"></i></a>
                            <a href="${pageContext.request.contextPath}/bonpilotage/edit?id=${bon.idMouvement}" title="Modifier"><i class="fas fa-edit"></i></a>
                            <a href="${pageContext.request.contextPath}/bonpilotage/delete?id=${bon.idMouvement}" onclick="return confirm('Supprimer ce bon ?');" title="Supprimer"><i class="fas fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<jsp:include page="/includes/footer.jsp"/>