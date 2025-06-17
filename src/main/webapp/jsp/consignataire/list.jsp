<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des consignataires</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/consignataire.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp">
        <jsp:param name="title" value="Liste des consignataires"/>
    </jsp:include>

    <div class="content-container">
        <h2>Liste des consignataires</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Raison Sociale</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="consignataire" items="${consignataires}">
                    <tr>
                        <td>${consignataire.idConsignataire}</td>
                        <td>${consignataire.raisonSociale}</td>
                        <td>${consignataire.adresse}</td>
                        <td>${consignataire.telephone}</td>
                        <td class="actions">
							<a href="${pageContext.request.contextPath}/consignataire?action=view&idConsignataire=${consignataire.idConsignataire}" title="Voir"><i class="fas fa-eye"></i></a>
							<a href="${pageContext.request.contextPath}/consignataire?action=edit&idConsignataire=${consignataire.idConsignataire}" title="Modifier"><i class="fas fa-edit"></i></a>        
       			         </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à l'acceuil
        </a>    
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
