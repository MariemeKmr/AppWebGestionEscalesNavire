<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${empty navire.numeroNavire ? 'Créer un navire' : 'Modifier un navire'}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
    <style>
    .content-container {
        max-width: 700px;
        margin: 2em auto;
        background: #fff;
        border-radius: 1.5em;
        box-shadow: 0 0 10px 2px rgba(50,50,80,0.09);
        padding: 2em 2.5em;
    }
    .section-card {
        border: 1.5px solid #bfc9d1;
        border-radius: 1em;
        background: #fafdff;
        margin-bottom: 2em;
        box-shadow: 0 4px 16px 0 rgba(60, 90, 120, 0.07);
        padding: 1.2em 1.5em 1.5em 1.5em;
    }
    .section-card-title {
        font-weight: 600;
        font-size: 1.2em;
        margin-bottom: 0.9em;
        color: #1765a0;
    }
    .section-card .btn-link {
        padding: 0;
        font-size: 0.95em;
        margin-left: 0.7em;
    }
    .toggle-btn {
        display: inline-flex;
        align-items: center;
        background: #f6f6f6;
        border-radius: 2em;
        border: 2px solid #bfc9d1;
        padding: .4em 1em;
        cursor: pointer;
        font-size: 1.15em;
        transition: border-color .2s, background .2s;
        user-select: none;
        outline: none;
        margin-bottom: 1em;
        margin-top: 2px;
    }
    .toggle-btn.present {
        border-color: #1ec98f;
        background: #eafff4;
        color: #106a4e;
    }
    .toggle-btn.absent {
        border-color: #e04d52;
        background: #fff5f5;
        color: #a22b34;
    }
    .toggle-btn:focus-visible {
        box-shadow: 0 0 0 2px #1765a0;
    }
    .toggle-btn .toggle-label {
        font-weight: bold;
        margin-right: 0.5em;
        letter-spacing: 0.02em;
    }
    </style>
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Formulaire navire"/>
</jsp:include>

<div class="content-container">
    <h2><c:out value="${empty navire.numeroNavire ? 'Créer un navire' : 'Modifier un navire'}"/></h2>
    <form method="post" action="${pageContext.request.contextPath}/navire" id="navireForm" autocomplete="off">
        <input type="hidden" name="action" value="${empty navire.numeroNavire ? 'insert' : 'update'}"/>

        <!-- Numéro du navire -->
        <label for="numeroNavire">Numéro du navire :</label>
        <c:choose>
            <c:when test="${empty navire.numeroNavire}">
                <input type="text" id="numeroNavire" name="numeroNavire" required/>
            </c:when>
            <c:otherwise>
                <input type="text" id="numeroNavire" name="numeroNavire"
                       value="${navire.numeroNavire}" required readonly style="background: #e9ecef;"/>
            </c:otherwise>
        </c:choose>

        <!-- Nom du navire (toujours éditable) -->
        <label for="nomNavire">Nom du navire :</label>
        <input type="text" id="nomNavire" name="nomNavire" value="${navire.nomNavire}" required/>

        <label for="longueurNavire">Longueur (L) :</label>
        <input type="number" step="0.01" id="longueurNavire" name="longueurNavire" value="${navire.longueurNavire}" required oninput="calculerVolume()"/>

        <label for="largeurNavire">Largeur (B) :</label>
        <input type="number" step="0.01" id="largeurNavire" name="largeurNavire" value="${navire.largeurNavire}" required oninput="calculerVolume()"/>

        <label for="tiranEauNavire">Tirant d'eau (T) :</label>
        <input type="number" step="0.01" id="tiranEauNavire" name="tiranEauNavire" value="${navire.tiranEauNavire}" required oninput="calculerVolume()"/>

        <label for="volumeNavire">Volume (V = L × B × T) :</label>
        <input type="number" step="0.01" id="volumeNavire" name="volumeNavire" value="${navire.volumeNavire}" required readonly style="background: #e9ecef; font-weight: bold;"/>

        <!-- ARMATEUR (dans une carte) -->
        <div class="section-card">
            <div class="section-card-title">Armateur</div>
			<input type="hidden" name="armateur" id="armateurInput"
			       value="${navire != null && navire.armateur != null ? navire.armateur.idArmateur : ''}" />     
              <div id="currentArmateur" style="${navire != null && navire.armateur != null ? '' : 'display:none;'}">
                <span>
                    ${navire.armateur.nomArmateur}
                    (${navire.armateur.adresseArmateur}, ${navire.armateur.telephoneArmateur})
                </span>
                <a href="#" onclick="showArmateurActions();return false;" class="btn btn-link">Changer d'armateur</a>
            </div>
            <div id="armateurActions" style="${navire != null && navire.armateur != null ? 'display:none;' : ''}">
                <div class="armateur-actions mb-2">
                    <button type="button" class="btn btn-primary" onclick="showSearchArmateur()">Rechercher un armateur</button>
                    <button type="button" class="btn btn-secondary" onclick="showAddArmateur()">Ajouter un armateur</button>
                </div>
                <div id="searchArmateurDiv" style="display:block;">
                    <label for="armateurSearch">Recherche armateur :</label>
                    <input list="armateur-list" id="armateurSearch" autocomplete="off" placeholder="Tapez un nom ou une adresse..." />
                    <datalist id="armateur-list">
                        <c:forEach var="a" items="${armateurs}">
                            <option value="${a.nomArmateur} (${a.adresseArmateur}, ${a.telephoneArmateur})" data-id="${a.idArmateur}"></option>
                        </c:forEach>
                    </datalist>
                </div>
                <div id="addArmateurDiv" style="display:none;">
                    <label for="nomArmateurField">Nom armateur :</label>
                    <input type="text" name="nomArmateur" id="nomArmateurField" />
                    <label for="adresseArmateurField">Adresse :</label>
                    <input type="text" name="adresseArmateur" id="adresseArmateurField" />
                    <label for="telephoneArmateurField">Téléphone :</label>
                    <input type="text" name="telephoneArmateur" id="telephoneArmateurField" />
                </div>
            </div>
        </div>

        <!-- CONSIGNATAIRE (dans une carte avec toggle) -->
        <div class="section-card">
            <div class="section-card-title">Consignataire (optionnel)</div>
            <span id="toggleConsignataireBtn" 
                class="toggle-btn ${navire != null && navire.consignataire != null ? 'present' : 'absent'}"
                tabindex="0"
                onclick="toggleConsignataireCustom()"
                onkeydown="if(event.key==='Enter' || event.key===' '){toggleConsignataireCustom();event.preventDefault();}">
                <span class="toggle-label" id="consignataireToggleLabel">
                    ${navire != null && navire.consignataire != null ? 'Consignataire présent':'Consignataire absent'}
                </span>
            </span>
            <input type="hidden" name="consignataire" id="consignataireInput" />

            <div id="consignataireBlock" style="${navire != null && navire.consignataire != null ? '' : 'display:none;'}">
                <c:if test="${navire != null && navire.consignataire != null}">
                    <div id="currentConsignataire">
                        <span>
                            ${navire.consignataire.raisonSociale} (${navire.consignataire.adresse}, ${navire.consignataire.telephone})
                        </span>
                        <a href="#" onclick="showConsignataireActions();return false;" class="btn btn-link">Changer de consignataire</a>
                    </div>
                </c:if>
                <div id="consignataireActions" style="${navire != null && navire.consignataire != null ? 'display:none;' : 'block'}">
                    <div class="consignataire-actions mb-2">
                        <button type="button" class="btn btn-primary" onclick="showSearchConsignataire()">Rechercher un consignataire</button>
                        <button type="button" class="btn btn-secondary" onclick="showAddConsignataire()">Ajouter un consignataire</button>
                    </div>
                    <div id="searchConsignataireDiv" style="display:block;">
                        <label for="consignataireSearch">Recherche consignataire :</label>
                        <input list="consignataire-list" id="consignataireSearch" autocomplete="off" placeholder="Tapez un nom ou une adresse..." />
                        <datalist id="consignataire-list">
                            <c:forEach var="c" items="${consignataires}">
                                <option value="${c.raisonSociale} (${c.adresse}, ${c.telephone})" data-id="${c.idConsignataire}"></option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <div id="addConsignataireDiv" style="display:none;">
                        <label for="raisonSocialeField">Raison sociale :</label>
                        <input type="text" name="raisonSociale" id="raisonSocialeField"/>
                        <label for="adresseField">Adresse :</label>
                        <input type="text" name="adresseConsignataire" id="adresseField"/>
                        <label for="telephoneField">Téléphone :</label>
                        <input type="text" name="telephoneConsignataire" id="telephoneField"/>
                    </div>
                </div>
            </div>
        </div>

        <button type="submit" style="margin-top:15px;">Enregistrer</button>
    </form>
    <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back" style="margin-top:15px;">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>

<jsp:include page="/includes/footer.jsp"/>

<script>
function calculerVolume() {
    var l = parseFloat(document.getElementById("longueurNavire").value) || 0;
    var b = parseFloat(document.getElementById("largeurNavire").value) || 0;
    var t = parseFloat(document.getElementById("tiranEauNavire").value) || 0;
    var v = (l > 0 && b > 0 && t > 0) ? (l * b * t).toFixed(2) : "";
    document.getElementById("volumeNavire").value = v;
}

// -------- ARMATEUR --------
function showArmateurActions() {
    document.getElementById('armateurActions').style.display = 'block';
    var curr = document.getElementById('currentArmateur');
    if(curr) curr.style.display = 'none';
    document.getElementById('armateurInput').value = '';
}
function showSearchArmateur() {
    document.getElementById('searchArmateurDiv').style.display = 'block';
    document.getElementById('addArmateurDiv').style.display = 'none';
    document.getElementById('armateurInput').value = '';
}
function showAddArmateur() {
    document.getElementById('addArmateurDiv').style.display = 'block';
    document.getElementById('searchArmateurDiv').style.display = 'none';
    document.getElementById('armateurInput').value = 'new';
}
document.getElementById('armateurSearch').addEventListener('input', function(e){
    var val = e.target.value;
    var opts = document.getElementById('armateur-list').options;
    var found = false;
    for (var i=0; i<opts.length; i++) {
        if(opts[i].value === val) {
            document.getElementById('armateurInput').value = opts[i].getAttribute('data-id');
            found = true;
            break;
        }
    }
    if(!found) document.getElementById('armateurInput').value = '';
});

// -------- CONSIGNATAIRE --------
function showSearchConsignataire() {
    document.getElementById('searchConsignataireDiv').style.display = 'block';
    document.getElementById('addConsignataireDiv').style.display = 'none';
    document.getElementById('consignataireInput').value = '';
}
function showAddConsignataire() {
    document.getElementById('addConsignataireDiv').style.display = 'block';
    document.getElementById('searchConsignataireDiv').style.display = 'none';
    document.getElementById('consignataireInput').value = 'new';
}
function showConsignataireActions() {
    document.getElementById('consignataireActions').style.display = 'block';
    var curr = document.getElementById('currentConsignataire');
    if(curr) curr.style.display = 'none';
    document.getElementById('consignataireInput').value = '';
}
document.getElementById('consignataireSearch').addEventListener('input', function(e){
    var val = e.target.value;
    var opts = document.getElementById('consignataire-list').options;
    var found = false;
    for (var i=0; i<opts.length; i++) {
        if(opts[i].value === val) {
            document.getElementById('consignataireInput').value = opts[i].getAttribute('data-id');
            found = true;
            break;
        }
    }
    if(!found) document.getElementById('consignataireInput').value = '';
});

// -------- CONSIGNATAIRE (toggle présent/absent) --------
function toggleConsignataireCustom() {
    var btn = document.getElementById('toggleConsignataireBtn');
    var block = document.getElementById('consignataireBlock');
    var label = document.getElementById('consignataireToggleLabel');
    var isPresent = btn.classList.contains('present');

    if(isPresent) {
        btn.classList.remove('present');
        btn.classList.add('absent');
        label.textContent = 'Consignataire absent';
        block.style.display = 'none';
        document.getElementById('consignataireInput').value = '';
    } else {
        btn.classList.add('present');
        btn.classList.remove('absent');
        label.textContent = 'Consignataire présent';
        block.style.display = '';
    }
}

window.onload = function() {
    calculerVolume();
    // Armateur
    var hasArm = ${navire != null && navire.armateur != null ? 'true' : 'false'};
    if(!hasArm) showSearchArmateur();

    // Consignataire
    var hasCons = ${navire != null && navire.consignataire != null ? 'true' : 'false'};
    var btn = document.getElementById('toggleConsignataireBtn');
    var block = document.getElementById('consignataireBlock');
    var label = document.getElementById('consignataireToggleLabel');
    if(hasCons) {
        btn.classList.add('present');
        btn.classList.remove('absent');
        block.style.display = '';
        label.textContent = 'Consignataire présent';
    } else {
        btn.classList.remove('present');
        btn.classList.add('absent');
        block.style.display = 'none';
        label.textContent = 'Consignataire absent';
    }
    if(!hasCons) showSearchConsignataire();
};

// Validation avant submit (robuste)
document.getElementById("navireForm").addEventListener("submit", function(e){
    // Armateur obligatoire
    var armInput = document.getElementById('armateurInput');
    var nomArmateurField = document.getElementById('nomArmateurField');
    var adresseArmateurField = document.getElementById('adresseArmateurField');
    var telephoneArmateurField = document.getElementById('telephoneArmateurField');
    if (!armInput.value || (armInput.value === 'new' && (
        !nomArmateurField || !nomArmateurField.value ||
        !adresseArmateurField || !adresseArmateurField.value ||
        !telephoneArmateurField || !telephoneArmateurField.value))) {
        alert("Veuillez sélectionner ou ajouter un armateur.");
        e.preventDefault();
        return false;
    }
    // Consignataire facultatif
    var btn = document.getElementById('toggleConsignataireBtn');
    var consInput = document.getElementById('consignataireInput');
    var isPresent = btn.classList.contains('present');
    if(isPresent && !consInput.value) {
        alert("Veuillez sélectionner ou ajouter un consignataire, ou désactivez le bouton si non applicable.");
        e.preventDefault();
        return false;
    }
});
</script>
</body>
</html>