<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.gestionescale.model.Utilisateur" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    String role = null;
    String nom = "Invité";

    if (utilisateur != null) {
        role = utilisateur.getRole();
        nom = utilisateur.getNomComplet() != null ? utilisateur.getNomComplet() : utilisateur.getEmail();
        session.setAttribute("utilisateurNom", nom);
    } else {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Tableau de bord"/>
</jsp:include>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f7f9;
        margin: 0;
        padding: 0;
        color: #333;
    }

    .dashboard {
        padding: 40px 20px;
        max-width: 1200px;
        margin: auto;
    }

    h1 {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 10px;
    }

    .welcome-message {
        text-align: center;
        font-size: 18px;
        margin-bottom: 30px;
        color: #7f8c8d;
    }

    .dashboard-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 30px;
        margin-top: 30px;
    }

    .dashboard-card {
        background: white;
        border-radius: 10px;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
        transition: all 0.3s ease;
        border: 1px solid #e0e0e0;
        height: 150px; /* Hauteur fixe pour éviter que les cartes ne deviennent trop longues */
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .dashboard-card:hover {
        transform: translateY(-10px);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
        border-color: #3498db;
    }

    .dashboard-card a {
        color: #2c3e50;
        text-decoration: none;
        font-weight: bold;
        margin-top: 10px;
    }

    .dashboard-card i {
        font-size: 36px;
        color: #3498db;
        margin-bottom: 10px;
    }

    .dashboard-card p {
        margin: 10px 0;
        font-size: 16px;
    }

    .dashboard-card:hover i {
        color: #2980b9;
    }

    .dashboard-card:hover a {
        color: #2980b9;
    }
</style>

<div class="dashboard">
    <h1>Tableau de bord, <%= nom %>.</h1>
    <p class="welcome-message">Vous êtes connecté en tant que <strong><%= role %></strong>.</p>

    <div class="dashboard-grid">
        <% if ("admin".equals(role)) { %>
            <div class="dashboard-card">
                <i class="fas fa-users"></i>
				<a href="${pageContext.request.contextPath}/utilisateur?action=list"><p>Gérer les utilisateurs</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-handshake"></i>
                <a href="${pageContext.request.contextPath}/consignataire/list/"><p>Gérer les consignataires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-calendar-alt"></i>
                <a href="${pageContext.request.contextPath}/escale/list/"><p>Gérer les escales</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-ship"></i>
                <a href="${pageContext.request.contextPath}/navire/list/"><p>Gérer les navires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-chart-line"></i>
                <a href="#"><p>Consulter les rapports</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-file-invoice"></i>
                <a href="#"><p>Bons de pilotage</p></a>
            </div>
        <% } else if ("agent".equals(role)) { %>
			<div class="dashboard-card">
			    <i class="fas fa-calendar-plus"></i>
			    <a href="${pageContext.request.contextPath}/escale/new"><p>Créer une escale</p></a>
			</div>
			<div class="dashboard-card">
			    <i class="fas fa-eye"></i>
			    <a href="${pageContext.request.contextPath}/escale/list/"><p>Voir les escales</p></a>
			</div>
			<div class="dashboard-card">
			    <i class="fas fa-ship"></i>
			    <a href="${pageContext.request.contextPath}/navire/new"><p>Créer un navire</p></a>
			</div>
			<div class="dashboard-card">
			    <i class="fas fa-ship"></i>
			    <a href="${pageContext.request.contextPath}/navire/list/"><p>Voir les navires</p></a>
			</div>
			<div class="dashboard-card">
			    <i class="fas fa-file-alt"></i>
			    <a href="${pageContext.request.contextPath}/bonpilotage/new"><p>Créer un bon de pilotage</p></a>
			</div>
			<div class="dashboard-card">
			    <i class="fas fa-file-alt"></i>
			    <a href="${pageContext.request.contextPath}/bonpilotage/"><p>Voir les bons de pilotage</p></a>
			</div>
        <% } %>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>