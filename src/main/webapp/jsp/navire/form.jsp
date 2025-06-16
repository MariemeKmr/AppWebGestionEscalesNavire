<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${formTitle}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navire.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Formulaire navire"/>
    </jsp:include>

    <div class="content-container">
        <h2><c:out value="${formTitle}"/></h2>
        <form method="post" action="${formAction}">
            <input type="hidden" name="numeroNavire" value="${navire.numeroNavire}"/>

            <label for="nomNavire">Nom du navire :</label>
            <input type="text" id="nomNavire" name="nomNavire" value="${navire.nomNavire}" required/>

            <label for="longueurNavire">Longueur :</label>
            <input type="number" id="longueurNavire" name="longueurNavire" value="${navire.longueurNavire}" required/>

            <label for="largeurNavire">Largeur :</label>
            <input type="number" id="largeurNavire" name="largeurNavire" value="${navire.largeurNavire}" required/>

            <label for="volumeNavire">Volume :</label>
            <input type="number" id="volumeNavire" name="volumeNavire" value="${navire.volumeNavire}" required/>

            <label for="tiranEauNavire">Tirant d'eau :</label>
            <input type="number" id="tiranEauNavire" name="tiranEauNavire" value="${navire.tiranEauNavire}" required/>

            <label for="consignataire">Consignataire :</label>
			<label>Raison sociale consignataire</label>
			<input type="text" name="raisonSociale" required />
			<label>Adresse consignataire</label>
			<input type="text" name="adresseConsignataire" required />
			<label>Téléphone consignataire</label>
			<input type="text" name="telephoneConsignataire" required />

            <button type="submit">Enregistrer</button>
        </form>
        <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
