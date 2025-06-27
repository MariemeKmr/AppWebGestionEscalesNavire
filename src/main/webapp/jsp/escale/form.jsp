<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>
        <c:out value="${empty escale.numeroEscale ? 'Créer une escale' : 'Modifier une escale'}"/>
    </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/escale.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Formulaire escale"/>
</jsp:include>

<div class="content-container">
    <h2>
        <c:out value="${empty escale.numeroEscale ? 'Créer une escale' : 'Modifier une escale'}"/>
    </h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${empty escale.numeroEscale ? pageContext.request.contextPath.concat('/escale/insert') : pageContext.request.contextPath.concat('/escale/update')}" autocomplete="off">
        <c:if test="${not empty escale.numeroEscale}">
            <input type="hidden" name="numeroEscale" value="${escale.numeroEscale}" />
            <label>Numéro escale :</label>
            <input type="text" value="${escale.numeroEscale}" readonly class="form-control-plaintext"/><br/>
        </c:if>

        <label>Navire :</label>
        <select name="numeroNavire" class="form-select" required>
            <option value="">-- Choisir un navire --</option>
            <c:forEach var="navire" items="${navires}">
                <option value="${navire.numeroNavire}" <c:if test="${not empty escale.myNavire && escale.myNavire.numeroNavire == navire.numeroNavire}">selected</c:if>>
                    ${navire.nomNavire}
                </option>
            </c:forEach>
        </select>

        <label>Prix unitaire (par jour) :</label>
        <input type="number" step="0.01" name="prixUnitaire" id="prixUnitaire"
            value="${not empty escale.prixUnitaire ? escale.prixUnitaire : ''}" required/>

        <label>Date début :</label>
		 <input type="date" name="debutEscale" id="debutEscale"
		    value="${not empty escale.debutEscale ? escale.debutEscale : ''}" 
		    required min="<%= java.time.LocalDate.now() %>"/>

        <label>Date fin :</label>
		<input type="date" name="finEscale" id="finEscale"
		    value="${not empty escale.finEscale ? escale.finEscale : ''}" 
		    required min="<%= java.time.LocalDate.now() %>"/>
    
        <label>Prix séjour (calculé) :</label>
        <input type="number" step="0.01" name="prixSejour" id="prixSejour"
            value="${not empty escale.prixSejour ? escale.prixSejour : ''}" readonly required/>

        <label>Zone :</label>
        <select name="zone" class="form-select" required>
            <option value="rade" <c:if test="${escale.zone == 'rade'}">selected</c:if>>Rade</option>
            <option value="intérieur" <c:if test="${escale.zone == 'intérieur'}">selected</c:if>>Intérieur</option>
        </select>

        <button type="submit" class="btn-submit">Enregistrer</button>
    </form>

    <a href="${pageContext.request.contextPath}/escale/" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>

<script>
function calculerPrixSejour() {
    const debut = document.getElementById('debutEscale').value;
    const fin = document.getElementById('finEscale').value;
    const prixUnitaire = parseFloat(document.getElementById('prixUnitaire').value);
    if (debut && fin && !isNaN(prixUnitaire)) {
        const dtDebut = new Date(debut);
        const dtFin = new Date(fin);
        const nbJours = Math.floor((dtFin - dtDebut) / (1000*60*60*24)) + 1;
        if (nbJours > 0) {
            document.getElementById('prixSejour').value = (prixUnitaire * nbJours).toFixed(2);
        } else {
            document.getElementById('prixSejour').value = "";
        }
    } else {
        document.getElementById('prixSejour').value = "";
    }
}
document.getElementById('prixUnitaire').addEventListener('input', calculerPrixSejour);
document.getElementById('debutEscale').addEventListener('change', calculerPrixSejour);
document.getElementById('finEscale').addEventListener('change', calculerPrixSejour);

window.onload = calculerPrixSejour;

document.addEventListener("DOMContentLoaded", function() {
    // Met la date du jour au format yyyy-mm-dd
    var today = new Date().toISOString().split('T')[0];
    document.getElementById("debutEscale").setAttribute("min", today);
    document.getElementById("finEscale").setAttribute("min", today);
});

</script>

<jsp:include page="/includes/footer.jsp"/>
</body>
</html>