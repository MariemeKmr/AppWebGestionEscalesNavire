<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${formTitle}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/consignataire.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Formulaire consignataire"/>
    </jsp:include>

    <div class="content-container">
        <h2><c:out value="${formTitle}"/></h2>
        <form method="post" action="${formAction}">
            <input type="hidden" name="action" value="${formActionType}" />
            <c:if test="${not empty consignataire.idConsignataire}">
                <input type="hidden" name="idConsignataire" value="${consignataire.idConsignataire}"/>
            </c:if>

            <label for="raisonSociale">Raison Sociale :</label>
            <input type="text" id="raisonSociale" name="raisonSociale" value="${consignataire.raisonSociale}" required/>

            <label for="adresse">Adresse :</label>
            <input type="text" id="adresse" name="adresse" value="${consignataire.adresse}" required/>

            <label for="telephone">Téléphone :</label>
            <input type="tel" id="telephone" name="telephone" value="${consignataire.telephone}" required/>

            <button type="submit">Enregistrer</button>
        </form>
        <a href="${pageContext.request.contextPath}/consignataire?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>