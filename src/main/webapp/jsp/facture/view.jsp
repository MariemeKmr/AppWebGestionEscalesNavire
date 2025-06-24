<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Facture ${facture.numeroFacture}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/facture.css">
    <style>
        .facture-header {
            background: #f4f8fb;
            border-bottom: 2px solid #1765a0;
            padding: 24px 24px 12px 24px;
            border-radius: 8px 8px 0 0;
            margin-bottom: 0;
        }
        .facture-title {
            color: #1765a0;
            font-weight: bold;
            font-size: 2.2em;
            margin-bottom: 8px;
        }
        .facture-infos {
            font-size: 1.15em;
            color: #333;
        }
        .facture-summary {
            background: #f8fafc;
            border: 1px solid #e0e6ee;
            padding: 16px 24px;
            border-radius: 0 0 8px 8px;
            margin-bottom: 24px;
        }
        .facture-table th, .facture-table td {
            vertical-align: middle;
            text-align: center;
        }
        .facture-table th {
            background: #eef4fb;
            color: #1765a0;
        }
        .facture-table tfoot td {
            font-weight: bold;
            background: #f1f6fb;
            color: #1765a0;
        }
        .btn-facture {
            background: #1765a0;
            color: #fff;
            font-weight: 500;
            border-radius: 5px;
            margin-right: 10px;
            transition: background 0.2s;
        }
        .btn-facture:hover {
            background: #124d7e;
            color: #fff;
        }
        @media print {
            body { background: #fff !important; }
            .btn-facture, .btn-back, .navbar, .footer { display: none !important; }
            .facture-header, .facture-summary, .facture-table { box-shadow: none !important; }
        }
    </style>
</head>
<body>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Facture ${facture.numeroFacture}" />
</jsp:include>
<div class="container my-4">
    <div class="facture-header mb-0">
        <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end">
            <div>
                <div class="facture-title">
                    <i class="fas fa-file-invoice"></i> Facture n°${facture.numeroFacture}
                </div>
                <div class="facture-infos">
                    <span><strong>Date :</strong> <fmt:formatDate value="${facture.dateGeneration}" pattern="dd/MM/yyyy"/></span>
                    <span class="ms-4"><strong>Escale :</strong> ${facture.escale.numeroEscale}</span>
                    <span class="ms-4"><strong>Navire :</strong> ${facture.escale.myNavire.nomNavire}</span>
                </div>
            </div>
            <div class="facture-infos mt-2 mt-md-0">
                <i class="fas fa-user-tie"></i> <strong>Agent :</strong> ${agent.nomComplet}
            </div>
        </div>
    </div>
    <div class="facture-summary">
        <div class="row">
            <div class="col-md-4 col-12 mb-2">
                <strong>Prix du séjour :</strong>
                <span class="text-secondary">
                    <fmt:formatNumber value="${facture.escale.prixSejour}" type="number" minFractionDigits="2" maxFractionDigits="2"/> F CFA
                </span>
            </div>
            <div class="col-md-4 col-12 mb-2">
                <strong>Montant des bons :</strong>
                <span class="text-secondary">
                    <fmt:formatNumber value="${facture.sommeBons}" type="number" minFractionDigits="2" maxFractionDigits="2"/> F CFA
                </span>
            </div>
            <div class="col-md-4 col-12 mb-2">
                <strong>Montant total :</strong>
                <span class="fw-bold" style="color: #1765a0;">
                    <fmt:formatNumber value="${facture.montantTotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/> F CFA
                </span>
            </div>
        </div>
    </div>
    <div class="table-responsive my-3">
        <table class="table facture-table align-middle">
            <thead>
                <tr>
                    <th>Type de bon</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Montant (F CFA)</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="bon" items="${facture.bonsPilotage}">
                <tr>
                    <td>${bon.typeMouvement.libelleTypeMvt}</td>
                    <td>
                        <fmt:formatDate value="${bon.dateDeBon}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${bon.dateFinBon}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${bon.montEscale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> F CFA
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="3" class="text-end">Total</td>
                    <td>
                        <span style="color: #1765a0;">
                            <fmt:formatNumber value="${facture.montantTotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/> F CFA
                        </span>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
	<div class="mb-4 d-print-none">
		<a href="${pageContext.request.contextPath}/facture" class="btn-back">
	        <i class="fas fa-arrow-left"></i> Retour à la liste
	    </a>
	
	    <a href="${pageContext.request.contextPath}/facture/download?facture=${facture.id}" class="btn-new">
	        <i class="fas fa-download"></i> Télécharger la facture (PDF)
	    </a>

	    <button onclick="window.print()" class="btn-print">
	        <i class="fas fa-print"></i> Imprimer
	    </button>
	</div>
</div>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>