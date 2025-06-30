<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rapport.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>

<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Tous les rapports"/>
</jsp:include>

<div class="content-container">
    <h1>Tableau de bord des rapports</h1>

    <form class="filter-form" method="get" action="${pageContext.request.contextPath}/rapport">
        <div class="form-group">
            <label for="debut">Date début :</label>
            <input type="date" id="debut" name="debut" value="${debut}"/>
        </div>
        <div class="form-group">
            <label for="fin">Date fin :</label>
            <input type="date" id="fin" name="fin" value="${fin}"/>
        </div>
        <button type="submit" class="btn-submit">Filtrer</button>
    </form>

    <hr>

    <!-- Bloc Escales & Navires côte à côte -->
    <div class="double-report-row">
      <!-- Bloc Escales par période -->
      <div class="double-report-col">
        <h2>Escales entre ${debut} et ${fin}</h2>
        <div class="chart-fix-container">
            <canvas id="escalesBarChart" width="400" height="400"></canvas>
        </div>
       </div>

      <!-- Bloc Navires en escale / hors escale -->
      <div class="double-report-col">
        <h2>Répartition des navires </h2>
        <div class="chart-fix-container">
            <canvas id="naviresPieChart" width="400" height="400"></canvas>
        </div>
      </div>
    </div>

    <!-- Rapport 3 : Navires par consignataire (en plein largeur) -->
	<div class="double-report-row">
	  <div class="double-report-col">
	    <h2>Navires par consignataire</h2>
	    <div class="chart-fix-container">
	      <canvas id="naviresConsignataireChart" width="400" height="400"></canvas>
	    </div>
	  </div>
	  <div class="double-report-col">
	    <h3>Liste consignataires et nombre de navires gérés</h3>
	    <ul class="consignataire-list">
	      <c:forEach var="entry" items="${naviresParConsignataire.entrySet()}">
	        <li>
	          <span class="consignataire-nom">${entry.key}</span>
	          <span class="consignataire-count">${entry.value} navire<c:if test="${entry.value > 1}">s</c:if></span>
	        </li>
	      </c:forEach>
	    </ul>
	  </div>
	</div>

    <!-- Bloc Facturation & Recettes côte à côte -->
    <div class="double-report-row">
      <!-- Bloc Facturation -->
      <div class="double-report-col">
        <h2>Facturation</h2>
        <div class="facturation-details">
            <p><strong>Chiffre d'affaires</strong> : ${ca} F CFA</p>
            <p><strong>Nombre de factures</strong> : ${nbFactures}</p>
        </div>
        <div class="chart-fix-container" style="max-width:440px;margin:auto;">
            <canvas id="facturationPieChart" width="400" height="400"></canvas>
        </div>
        <div style="text-align:center;margin-top:10px;">
            <span class="pie-legend pie-facturee"></span> Clôturée & facturée : <span style="font-weight:600">${nbClotureesFacturees}</span>
            &nbsp;&nbsp;
            <span class="pie-legend pie-nonfacturee"></span> Clôturée non facturée : <span style="font-weight:600">${nbClotureesNonFacturees}</span>
            &nbsp;&nbsp;
            <span class="pie-legend pie-noncloturee"></span> Non clôturée : <span style="font-weight:600">${nbNonCloturees}</span>
        </div>
      </div>

      <!-- Bloc Recettes par période -->
      <div class="double-report-col">
        <h2>Recettes par période</h2>
        <div class="filter-group" style="margin-bottom:10px;">
            <label for="recetteGranularity"><strong>Afficher par :</strong></label>
            <select id="recetteGranularity">
                <option value="mois" selected>Mois</option>
                <option value="annee">Année</option>
                <option value="jour">Jour</option>
            </select>
        </div>
        <div class="chart-fix-container">
            <canvas id="recettesLineChart" width="400" height="400"></canvas>
        </div>
        <table class="report-table">
            <thead>
                <tr>
                    <th>Période</th>
                    <th>Montant</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="rec" items="${recettes}">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${not empty rec.annee && not empty rec.mois}">
                                    ${rec.annee}-${rec.mois}
                                </c:when>
                                <c:when test="${not empty rec.date}">
                                    ${rec.date}
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${rec.montant}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
      </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>


<script>
// Pour les graphes, insère ici ton script Chart.js adapté à tes données côté Java (comme déjà vu)
var escalesLabels = [<c:forEach var="entry" items="${escalesParNavire.entrySet()}" varStatus="status">"${entry.key}"<c:if test="${!status.last}">,</c:if></c:forEach>];
var escalesData = [<c:forEach var="entry" items="${escalesParNavire.entrySet()}" varStatus="status">${entry.value}<c:if test="${!status.last}">,</c:if></c:forEach>];

var naviresPieLabels = ["En escale", "Hors escale"];
var naviresPieData = [${fn:length(naviresEnEscale)}, ${fn:length(naviresHorsEscale)}];

var consignataireLabels = [<c:forEach var="entry" items="${naviresParConsignataire.entrySet()}" varStatus="status">"${entry.key}"<c:if test="${!status.last}">,</c:if></c:forEach>];
var consignataireData = [<c:forEach var="entry" items="${naviresParConsignataire.entrySet()}" varStatus="status">${entry.value}<c:if test="${!status.last}">,</c:if></c:forEach>];

var recettesDataSets = {
  "annee": {
    labels: [<c:forEach var="rec" items="${recettesParAn}" varStatus="status">"<c:out value="${rec.annee}" />"<c:if test="${!status.last}">,</c:if></c:forEach>],
    data: [<c:forEach var="rec" items="${recettesParAn}" varStatus="status"><c:out value="${rec.montant}" /><c:if test="${!status.last}">,</c:if></c:forEach>]
  },
  "mois": {
    labels: [<c:forEach var="rec" items="${recettesParMois}" varStatus="status">"<c:out value="${rec.annee}" />-<c:out value="${rec.mois}" />"<c:if test="${!status.last}">,</c:if></c:forEach>],
    data: [<c:forEach var="rec" items="${recettesParMois}" varStatus="status"><c:out value="${rec.montant}" /><c:if test="${!status.last}">,</c:if></c:forEach>]
  },
  "jour": {
    labels: [<c:forEach var="rec" items="${recettesParJour}" varStatus="status">"<c:out value="${rec.date}" />"<c:if test="${!status.last}">,</c:if></c:forEach>],
    data: [<c:forEach var="rec" items="${recettesParJour}" varStatus="status"><c:out value="${rec.montant}" /><c:if test="${!status.last}">,</c:if></c:forEach>]
  }
};

var colors = [
    "#4e73df", "#1cc88a", "#36b9cc", "#f6c23e", "#e74a3b", "#858796", "#7a6fbe", "#f672a7", "#5FD0F3", "#E77F67", "#778beb", "#fc5c65"
];

// Barres escales par navire
new Chart(document.getElementById('escalesBarChart').getContext('2d'), {
    type: 'bar',
    data: {
        labels: escalesLabels,
        datasets: [{ label: 'Nombre d\'escales', data: escalesData, backgroundColor: colors }]
    },
    options: { responsive: false, plugins: { legend: { display: false } }, maintainAspectRatio: false }
});

// Pie navires en escale / hors escale
new Chart(document.getElementById('naviresPieChart').getContext('2d'), {
    type: 'pie',
    data: {
        labels: naviresPieLabels,
        datasets: [{ data: naviresPieData, backgroundColor: [colors[0], colors[1]] }]
    },
    options: { responsive: false, maintainAspectRatio: false }
});

// Doughnut navires par consignataire
new Chart(document.getElementById('naviresConsignataireChart').getContext('2d'), {
  type: 'doughnut',
  data: {
    labels: consignataireLabels,
    datasets: [{ data: consignataireData, backgroundColor: colors }]
  },
  options: { responsive: false, maintainAspectRatio: false }
});

// Facturation Pie Chart
new Chart(document.getElementById('facturationPieChart').getContext('2d'), {
    type: 'doughnut',
    data: {
        labels: [
            "Clôturée & facturée",
            "Clôturée non facturée",
            "Non clôturée"
        ],
        datasets: [{
            data: [
                ${nbClotureesFacturees},
                ${nbClotureesNonFacturees},
                ${nbNonCloturees}
            ],
            backgroundColor: [
                "#4e73df", // bleu
                "#f6c23e", // jaune
                "#e74a3b"  // rouge
            ]
        }]
    },
    options: {
        responsive: false,
        maintainAspectRatio: false,
        plugins: { legend: { position: "bottom" } }
    }
});

// Ligne recettes par période (avec changement dynamique)
var recettesChartCanvas = document.getElementById('recettesLineChart').getContext('2d');
var recettesChart = new Chart(recettesChartCanvas, {
  type: 'line',
  data: {
    labels: recettesDataSets["mois"].labels,
    datasets: [{
      label: "Recettes (F CFA)",
      data: recettesDataSets["mois"].data,
      borderColor: colors[4],
      backgroundColor: "rgba(78,115,223,0.1)",
      fill: true,
      tension: 0.3
    }]
  },
  options: { responsive: false, maintainAspectRatio: false }
});
document.getElementById('recetteGranularity').addEventListener('change', function() {
  var gran = this.value;
  recettesChart.data.labels = recettesDataSets[gran].labels;
  recettesChart.data.datasets[0].data = recettesDataSets[gran].data;
  recettesChart.update();
});
</script>