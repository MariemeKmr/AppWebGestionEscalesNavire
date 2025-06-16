<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mot de passe oublié - Gestion Escale</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/forgotPassword.css">

    
</head>
<body>
    <div class="forgot-password-container">
        <div class="icon">
            <i class="fas fa-anchor"></i>
        </div>
        <h2>Mot de passe oublié</h2>
        <p>Veuillez entrer votre adresse e-mail pour recevoir un lien de réinitialisation de mot de passe.</p>
        <form action="ForgotPasswordServlet" method="post">
            <label for="email">Adresse e-mail :</label>
            <input type="email" id="email" name="email" required>

            <button type="submit">Envoyer le lien de réinitialisation</button>
        </form>

        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="back-to-login">Retour à la page de connexion</a>
    </div>
</body>
</html>
