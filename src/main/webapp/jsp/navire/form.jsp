<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${formTitle}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .content-container {
            margin-top: 80px;
            max-width: 600px;
            margin: 80px auto 20px;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button[type="submit"] {
            background-color: #3498db;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #2980b9;
        }

        .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .btn-back:hover {
            background-color: #2980b9;
        }
    </style>
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
            <select id="consignataire" name="consignataire" required>
                <c:forEach var="consignataire" items="${consignataires}">
                    <option value="${consignataire.idConsignataire}" ${navire.consignataire.idConsignataire == consignataire.idConsignataire ? 'selected' : ''}>
                        ${consignataire.nomConsignataire}
                    </option>
                </c:forEach>
            </select>

            <button type="submit">Enregistrer</button>
        </form>
        <a href="${pageContext.request.contextPath}/navire?action=list" class="btn-back">
            <i class="fas fa-arrow-left"></i> Retour à la liste
        </a>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
</body>
</html>
