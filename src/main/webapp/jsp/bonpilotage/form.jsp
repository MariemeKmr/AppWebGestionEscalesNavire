<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${empty bon.idMouvement ? 'Créer un bon de pilotage' : 'Modifier un bon de pilotage'}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bonpilotage.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Formulaire bon de pilotage"/>
</jsp:include>
<div class="content-container" style="max-width: 540px;">
    <h2><c:out value="${empty bon.idMouvement ? 'Créer un bon de pilotage' : 'Modifier un bon de pilotage'}"/></h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/bonpilotage/${not empty bon.idMouvement ? 'update' : 'insert'}" autocomplete="off">
        <c:if test="${not empty bon.idMouvement}">
            <input type="hidden" name="idMouvement" value="${bon.idMouvement}" />
        </c:if>
        <!-- Numéro d'escale -->
        <div class="form-group">
            <label for="numeroEscale">Numéro escale</label>
            <select name="numeroEscale" id="numeroEscale" required onchange="updateNavireInfo()">
                <option value="" disabled <c:if test="${empty bon or empty bon.monEscale}">selected</c:if>>Choisir une escale</option>
                <c:forEach var="escale" items="${escalesEnCours}">
                    <option 
                        value="${escale.numeroEscale}" 
                        data-numero-navire="${escale.myNavire.numeroNavire}" 
                        data-nom-navire="${escale.myNavire.nomNavire}"
                        <c:if test="${not empty bon && bon.monEscale.numeroEscale eq escale.numeroEscale}">selected</c:if>
                    >
                        ${escale.numeroEscale}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Bateau lié à l’escale</label>
            <input id="navireDisplay" type="text" value="" readonly/>
        </div>
        <div class="form-group">
            <label for="codeTypeMvt">Type de mouvement</label>
            <select name="codeTypeMvt" id="codeTypeMvt" required onchange="handleTypeMouvement()">
                <option value="" disabled <c:if test="${empty bon || empty bon.typeMouvement}">selected</c:if>>Choisir le type de mouvement</option>
                <option value="ENTREE" <c:if test="${not empty bon && bon.typeMouvement.codeTypeMvt eq 'ENTREE'}">selected</c:if>>Entrée au port</option>
                <option value="SORTIE" <c:if test="${not empty bon && bon.typeMouvement.codeTypeMvt eq 'SORTIE'}">selected</c:if>>Sortie du port</option>
                <option value="MOUVEMENT" <c:if test="${not empty bon && bon.typeMouvement.codeTypeMvt eq 'MOUVEMENT'}">selected</c:if>>Mouvement</option>
            </select>
        </div>
        <div class="form-group">
            <label for="montEscale">Montant</label>
            <input type="number" step="0.01" id="montEscale" name="montEscale" value="${not empty bon ? bon.montEscale : ''}" required/>
        </div>
        <div class="form-group">
            <label for="dateDeBon">Date début</label>
            <input type="date" id="dateDeBon" name="dateDeBon"
                value="${not empty bon && not empty bon.dateDeBon ? fn:substring(bon.dateDeBon,0,10) : ''}" required/>
        </div>
        <div class="form-group">
            <label for="dateFinBon">Date fin</label>
            <input type="date" id="dateFinBon" name="dateFinBon"
                value="${not empty bon && not empty bon.dateFinBon ? fn:substring(bon.dateFinBon,0,10) : ''}"/>
        </div>
        <div class="form-group">
            <label for="posteAQuai">Poste à quai</label>
            <input type="text" id="posteAQuai" name="posteAQuai" maxlength="100"
                <c:if test="${not empty bon && bon.typeMouvement.codeTypeMvt eq 'SORTIE'}">readonly style="background: #e9ecef;" value=""</c:if>
                <c:if test="${empty bon || bon.typeMouvement.codeTypeMvt ne 'SORTIE'}">required value="${not empty bon ? bon.posteAQuai : ''}"</c:if>
            />
        </div>
        <div class="form-group">
            <label>État du bon</label><br>
            <span id="etatToggleBtn"
                class="toggle-btn-etat ${empty bon.etat || bon.etat eq 'Saisie' ? 'saisie' : 'valide'}"
                tabindex="0"
                onclick="toggleEtatBon()"
                onkeydown="if(event.key==='Enter' || event.key===' '){toggleEtatBon();event.preventDefault();}">
                <span class="toggle-label-etat" id="etatToggleLabel">
                    ${empty bon.etat || bon.etat eq 'Saisie' ? 'Saisie' : 'Valide'}
                </span>
            </span>
            <input type="hidden" name="etat" id="etatInput"
                value="${empty bon.etat ? 'Saisie' : bon.etat}" />
        </div>
        <div class="form-actions">
            <button type="submit" class="btn-primary">Enregistrer</button>
            <a href="${pageContext.request.contextPath}/bonpilotage/list" class="btn-secondary">Annuler</a>
        </div>
    </form>
</div>
<jsp:include page="/includes/footer.jsp"/>

<script>
function updateNavireInfo() {
    var select = document.getElementById('numeroEscale');
    var display = document.getElementById('navireDisplay');
    if (!select.value) {
        display.value = '';
        return;
    }
    var option = select.options[select.selectedIndex];
    var numero = option.getAttribute('data-numero-navire') || '';
    var nom = option.getAttribute('data-nom-navire') || '';
    if(numero && nom) {
        display.value = numero + ' - ' + nom;
    } else {
        display.value = '';
    }
}
function toggleEtatBon() {
    var btn = document.getElementById('etatToggleBtn');
    var label = document.getElementById('etatToggleLabel');
    var input = document.getElementById('etatInput');
    var isSaisie = btn.classList.contains('saisie');
    if(isSaisie) {
        btn.classList.remove('saisie');
        btn.classList.add('valide');
        label.textContent = 'Valide';
        input.value = 'Valide';
    } else {
        btn.classList.remove('valide');
        btn.classList.add('saisie');
        label.textContent = 'Saisie';
        input.value = 'Saisie';
    }
}
function handleTypeMouvement() {
    var typeMvt = document.getElementById('codeTypeMvt').value;
    var posteInput = document.getElementById('posteAQuai');
    if (typeMvt === 'SORTIE') {
        posteInput.value = '';
        posteInput.readOnly = true;
        posteInput.style.background = '#e9ecef';
        posteInput.removeAttribute('required');
    } else {
        posteInput.readOnly = false;
        posteInput.style.background = '';
        posteInput.setAttribute('required', 'required');
    }
}
window.onload = function() {
    updateNavireInfo();
    handleTypeMouvement();
};
</script>
</body>
</html>