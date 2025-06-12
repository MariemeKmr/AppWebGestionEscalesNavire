<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion Escale</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: white;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 320px;
            text-align: center;
        }

        h2 {
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .icon {
            font-size: 40px;
            color: #2980b9;
            margin-bottom: 15px;
        }

        label {
            display: block;
            text-align: left;
            margin-top: 15px;
            font-weight: bold;
            color: #2c3e50;
        }

        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            margin-top: 25px;
            width: 100%;
            background-color: #2980b9;
            color: white;
            border: none;
            padding: 12px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        button:hover {
            background-color: #1f618d;
            transform: scale(1.02);
        }

        .error {
            color: #e74c3c;
            margin-top: 15px;
            font-size: 14px;
        }

        .forgot-password {
            margin-top: 15px;
            display: block;
            color: #3498db;
            text-decoration: none;
            font-size: 14px;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="icon">
            <i class="fas fa-anchor"></i>
        </div>
        <h2>Connexion</h2>
        <form action="/LoginServlet" method="post">
            <label for="email">Adresse e-mail :</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Se connecter</button>
        </form>

        <a href="forgotPassword.jsp" class="forgot-password">Mot de passe oublié ?</a>
        
        <% String error = (String) request.getAttribute("errorMessage");
           if(error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
    </div>
</body>
</html>
