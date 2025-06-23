<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="${empty bon.idMouvement ? 'Nouveau bon de pilotage' : 'Modifier bon de pilotage'}"/>
</jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">

<div class="content-container">
    <h2>
        <c:out value="${empty bon.idMouvement ? 'Nouveau bon de pilotage' : 'Modifier bon de pilotage'}"/>
    </h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post"
          action="${empty bon.idMouvement ? pageContext.request.contextPath.concat('/bonpilotage/insert') : pageContext.request.contextPath.concat('/bonpilotage/update')}">

        <c:if test="${not empty bon.idMouvement}">
            <input type="hidden" name="idMouvement" value="${bon.idMouvement}" />
        </c:if>

        <label>Navires en escale :</label>
        <select name="numeroEscale" required>
            <option value="">-- Sélectionner une escale --</option>
            <c:forEach var="escale" items="${escalesEnCours}">
                <option value="${escale.numeroEscale}"
                    <c:if test="${not empty bon.monEscale && bon.monEscale.numeroEscale == escale.numeroEscale}">selected</c:if>>
                    ${escale.myNavire.nomNavire} (${escale.myNavire.numeroNavire}) - ${escale.debutEscale} à ${escale.finEscale}
                </option>
            </c:forEach>
        </select>

        <label>Type de mouvement :</label>
        <select name="codeTypeMvt" required>
            <option value="">-- Choisir le type --</option>
            <c:forEach var="type" items="${typesMouvement}">
                <option value="${type.codeTypeMvt}"
                    <c:if test="${not empty bon.typeMouvement && bon.typeMouvement.codeTypeMvt == type.codeTypeMvt}">selected</c:if>>
                    ${type.libelleTypeMvt}
                </option>
            </c:forEach>
        </select>

        <label>Date début du bon :</label>
        <input type="date" name="dateDeBon" value="${not empty bon.dateDeBon ? bon.dateDeBon : ''}" required />

        <label>Date fin du bon :</label>
        <input type="date" name="dateFinBon" value="${not empty bon.dateFinBon ? bon.dateFinBon : ''}" required />

        <label>Poste à quai :</label>
        <input type="text" name="posteAQuai" value="${not empty bon.posteAQuai ? bon.posteAQuai : ''}" />

		<label>Prix :</label>
		<input type="number" step="0.01" name="montEscale" value="${not empty bon.montEscale ? bon.montEscale : ''}" required />
        
        <button type="submit" class="btn-submit">Enregistrer</button>
        <a href="${pageContext.request.contextPath}/bonpilotage/list" class="btn-back">Annuler</a>
    </form>
</div>
<jsp:include page="/includes/footer.jsp"/>