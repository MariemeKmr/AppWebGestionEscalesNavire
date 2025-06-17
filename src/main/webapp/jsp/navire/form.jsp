<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${empty navire.numeroNavire ? 'Créer un navire' : 'Modifier un navire'}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Formulaire navire"/>
</jsp:include>

<div class="content-container">
    <h2><c:out value="${empty navire.numeroNavire ? 'Créer un navire' : 'Modifier un navire'}"/></h2>
    <form method="post" action="${pageContext.request.contextPath}/navire">
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

        <!-- Nom du navire -->
        <label for="nomNavire">Nom du navire :</label>
        <c:choose>
            <c:when test="${empty navire.numeroNavire}">
                <input type="text" id="nomNavire" name="nomNavire" value="${navire.nomNavire}" required/>
            </c:when>
            <c:otherwise>
                <input type="text" id="nomNavire" name="nomNavire"
                       value="${navire.nomNavire}" required readonly style="background: #e9ecef;"/>
            </c:otherwise>
        </c:choose>

        <label for="longueurNavire">Longueur :</label>
        <input type="number" step="0.01" id="longueurNavire" name="longueurNavire" value="${navire.longueurNavire}" required/>

        <label for="largeurNavire">Largeur :</label>
        <input type="number" step="0.01" id="largeurNavire" name="largeurNavire" value="${navire.largeurNavire}" required/>

        <label for="volumeNavire">Volume :</label>
        <input type="number" step="0.01" id="volumeNavire" name="volumeNavire" value="${navire.volumeNavire}" required/>

        <label for="tiranEauNavire">Tirant d'eau :</label>
        <input type="number" step="0.01" id="tiranEauNavire" name="tiranEauNavire" value="${navire.tiranEauNavire}" required/>

        <label>Consignataire</label>
        <!-- Un seul input caché pour gérer le mode sélection ou création -->
        <input type="hidden" name="consignataire" id="consignataireInput" />

        <c:if test="${navire != null && navire.consignataire != null}">
            <div id="currentConsignataire">
                <span>
                    ${navire.consignataire.raisonSociale}
                    (${navire.consignataire.adresse}, ${navire.consignataire.telephone})
                </span>
                <a href="#" onclick="showConsignataireActions();return false;" class="btn btn-link">Changer de consignataire</a>
            </div>
        </c:if>

        <div id="consignataireActions" style="display:${navire != null && navire.consignataire != null ? 'none' : 'block'};">
            <div class="consignataire-actions">
                <button type="button" class="btn btn-primary" onclick="showSearchConsignataire()">Rechercher un consignataire</button>
                <button type="button" class="btn btn-secondary" onclick="showAddConsignataire()">Ajouter un consignataire</button>
            </div>

            <!-- Recherche avec datalist -->
            <div id="searchConsignataireDiv" style="display:block;">
                <label for="consignataireSearch">Recherche consignataire :</label>
                <input list="consignataire-list" id="consignataireSearch" autocomplete="off" placeholder="Tapez un nom ou une adresse..." />
                <datalist id="consignataire-list">
                    <c:forEach var="c" items="${consignataires}">
                        <option value="${c.raisonSociale} (${c.adresse}, ${c.telephone})" data-id="${c.idConsignataire}"></option>
                    </c:forEach>
                </datalist>
            </div>

            <!-- Ajout d'un nouveau consignataire -->
            <div id="addConsignataireDiv" style="display:none;">
                <label for="raisonSocialeField">Raison sociale :</label>
                <input type="text" name="raisonSociale" id="raisonSocialeField"/>
                <label for="adresseField">Adresse :</label>
                <input type="text" name="adresseConsignataire" id="adresseField"/>
                <label for="telephoneField">Téléphone :</label>
                <input type="text" name="telephoneConsignataire" id="telephoneField"/>
            </div>
        </div>

        <!-- Champs cachés pour valeurs originales (utilisés seulement en modification) -->
        <c:if test="${navire != null}">
            <input type="hidden" id="orig_nomNavire" value="${navire.nomNavire}" />
            <input type="hidden" id="orig_longueurNavire" value="${navire.longueurNavire}" />
            <input type="hidden" id="orig_largeurNavire" value="${navire.largeurNavire}" />
            <input type="hidden" id="orig_volumeNavire" value="${navire.volumeNavire}" />
            <input type="hidden" id="orig_tiranEauNavire" value="${navire.tiranEauNavire}" />
            <input type="hidden" id="orig_consignataireId" value="${navire.consignataire.idConsignataire}" />
            <input type="hidden" id="orig_consignataireLabel"
                   value="${navire.consignataire.raisonSociale} (${navire.consignataire.adresse}, ${navire.consignataire.telephone})" />
        </c:if>

        <button type="submit" style="margin-top:15px;">Enregistrer</button>
    </form>
    <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back" style="margin-top:15px;">
        <i class="fas fa-arrow-left"></i> Retour à la liste
    </a>
</div>

<jsp:include page="/includes/footer.jsp"/>

<script>
function showConsignataireActions() {
    document.getElementById('consignataireActions').style.display = 'block';
    var curr = document.getElementById('currentConsignataire');
    if(curr) curr.style.display = 'none';
    document.getElementById('consignataireInput').value = '';
}
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

// Quand l'utilisateur sélectionne un élément dans la liste, on récupère son data-id
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

// Afficher la recherche par défaut si pas de consignataire déjà assigné
window.onload = function() {
    var hasCons = ${navire != null && navire.consignataire != null ? 'true' : 'false'};
    if(!hasCons) showSearchConsignataire();
};

// Validation consignataire et confirmation avant modification
document.querySelector("form").addEventListener("submit", function(e){
    var consInput = document.getElementById('consignataireInput');
    if (consInput.value !== "new" && !consInput.value) {
        alert("Veuillez sélectionner un consignataire valide.");
        e.preventDefault();
        return false;
    }
    // Confirmation des modifications (optionnel)
    var modifications = getModifiedFields();
    if(modifications.length > 0) {
        var msg = "Êtes-vous sûr(e) de vouloir modifier les informations suivantes :\n\n";
        msg += modifications.join("\n");
        if(!confirm(msg)) {
            e.preventDefault();
            return false;
        }
    }
});

function getModifiedFields() {
    var modifications = [];
    if(document.getElementById('orig_nomNavire')) {
        var champs = [
            {name: "nomNavire", label: "Nom du navire"},
            {name: "longueurNavire", label: "Longueur"},
            {name: "largeurNavire", label: "Largeur"},
            {name: "volumeNavire", label: "Volume"},
            {name: "tiranEauNavire", label: "Tirant d'eau"}
        ];
        champs.forEach(function(ch){
            var oldVal = document.getElementById('orig_' + ch.name).value;
            var newVal = document.getElementsByName(ch.name)[0].value;
            if (oldVal != newVal) {
                modifications.push(ch.label + " : " + oldVal + " → " + newVal);
            }
        });
        // Consignataire
        var oldConsId = document.getElementById('orig_consignataireId').value;
        var oldConsLabel = document.getElementById('orig_consignataireLabel').value;
        var newConsId = document.getElementById("consignataireInput").value;
        if(newConsId && newConsId !== oldConsId && newConsId !== "new") {
            var opts = document.getElementById('consignataire-list').options;
            var newCons = "";
            for (var i=0; i<opts.length; i++) {
                if(opts[i].getAttribute('data-id') === newConsId) {
                    newCons = opts[i].value;
                    break;
                }
            }
            modifications.push("Consignataire : " + oldConsLabel + " → " + newCons);
        }
    }
    return modifications;
}
</script>
</body>
</html>