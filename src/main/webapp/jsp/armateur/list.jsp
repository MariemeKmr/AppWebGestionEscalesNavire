<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des armateurs</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/armateur.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des armateurs"/>
    </jsp:include>

    <div class="content-container">
        <h2>Liste des armateurs</h2>
        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="armateur" items="${armateurs}">
                    <tr>
                        <td>${armateur.nomArmateur}</td>
                        <td>${armateur.adresseArmateur}</td>
                        <td>${armateur.telephoneArmateur}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/armateur?action=view&idArmateur=${armateur.idArmateur}" title="Voir"><i class="fas fa-eye"></i></a>
                            <a href="${pageContext.request.contextPath}/armateur?action=edit&idArmateur=${armateur.idArmateur}" title="Modifier"><i class="fas fa-edit"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à l'accueil
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>