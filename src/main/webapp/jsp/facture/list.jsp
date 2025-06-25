<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des factures</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
    <script>
        function filterFactures() {
            let input = document.getElementById('searchFacture');
            let filter = input.value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                let text = row.innerText.toLowerCase();
                row.style.display = text.indexOf(filter) > -1 ? '' : 'none';
            });
        }
    </script>
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des factures" />
</jsp:include>

<div class="content-container">
    <h2>Liste des factures</h2>
    <div class="search-bar">
        <div class="search-input-group">
            <input type="text" id="searchFacture" placeholder="Rechercher une facture..." onkeyup="filterFactures()">
            <i class="fas fa-search"></i>
        </div>
		<a href="${pageContext.request.contextPath}/facture?action=escalesTerminees" class="btn-new">
		    <i class="fas fa-plus"></i> Générer facture pour les escales terminées
		</a>
    </div>
    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Date</th>
                    <th>Escale</th>
                    <th>Navire</th>
                    <th>Montant</th>
                    <th>Agent</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="facture" items="${factures}">
                <tr>
                    <td>${facture.numeroFacture}</td>
                    <td><fmt:formatDate value="${facture.dateGeneration}" pattern="dd/MM/yyyy"/></td>
                    <td>${facture.escale.numeroEscale}</td>
                    <td>${facture.escale.myNavire.nomNavire}</td>
                    <td><fmt:formatNumber value="${facture.montantTotal}" type="number" groupingUsed="true"/> F CFA</td>
                   	<td>${facture.agentNom}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/facture?action=view&id=${facture.id}" title="Voir">
                            <i class="fas fa-eye"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/facture?action=edit&id=${facture.id}" title="Modifier">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/facture?action=delete&id=${facture.id}" title="Supprimer"
                           onclick="return confirm('Confirmer la suppression de cette facture ?');">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
        <i class="fas fa-arrow-left"></i> Retour à l'accueil
    </a>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>