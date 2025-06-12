<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Footer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        html, body {
            height: 100%;
        }

        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        .content {
            flex: 1;
        }

        .footer {
            background-color: #2c3e50;
            color: white;
            padding: 20px 0;
            text-align: center;
            margin-top: auto;
        }

        .footer-content {
            display: flex;
            justify-content: space-around;
            max-width: 1200px;
            margin: auto;
        }

        .footer-section {
            flex: 1;
            padding: 0 10px;
        }

        .footer-section h3 {
            margin-bottom: 10px;
            font-size: 16px;
        }

        .footer-section p, .footer-section a {
            color: #ecf0f1;
            margin-bottom: 5px;
            display: block;
            text-decoration: none;
        }

        .footer-section a:hover {
            color: #3498db;
        }

        .social-icons {
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        .social-icons a {
            color: white;
            font-size: 20px;
        }

        .copyright {
            margin-top: 15px;
            font-size: 14px;
            color: #bdc3c7;
        }
    </style>
</head>
<body>
    <div class="content">
        <!-- Contenu principal de votre page ici -->
    </div>

    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>À propos</h3>
                <p>Gestion des escales portuaires pour une logistique maritime optimisée.</p>
            </div>
            <div class="footer-section">
                <h3>Contact</h3>
                <p><i class="fas fa-map-marker-alt"></i> Dakar, Sénégal</p>
<!--                 <p><i class="fas fa-phone"></i> +221 77 609 29 90</p>-->
                <p><i class="fas fa-envelope"></i> contact@gestionescales.com</p>
            </div>
            <div class="footer-section">
                <h3>Suivez-nous</h3>
                <div class="social-icons">
                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                    <a href="#"><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
        </div>
        <div class="copyright">
            <p>&copy; 2025 Marieme KAMARA - Gestion des Escales. Tous droits réservés.</p>
        </div>
    </footer>
</body>
</html>
