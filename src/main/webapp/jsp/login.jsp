<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion Escale</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <div class="login-container">
        <div class="icon">
            <i class="fas fa-anchor"></i>
        </div>
        <h2>Connexion</h2>
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <label for="email">Adresse e-mail :</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Se connecter</button>
        </form>

        <a href="${pageContext.request.contextPath}/jsp/forgotPassword.jsp" class="forgot-password">Mot de passe oublié ?</a>
        
        <% String error = (String) request.getAttribute("errorMessage");
           if(error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
    </div>
</body>
</html>
