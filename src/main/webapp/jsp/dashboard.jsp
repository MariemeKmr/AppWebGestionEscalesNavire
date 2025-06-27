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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">

<div class="dashboard">
    <h1>Tableau de bord, <%= nom %>.</h1>
    <p class="welcome-message">Vous êtes connecté en tant que <strong><%= role %></strong>.</p>

    <div class="dashboard-grid">
        <%-- Cartes pour ADMINISTRATEUR --%>
        <% if ("admin".equals(role)) { %>
            <div class="dashboard-card">
                <i class="fas fa-users"></i>
                <a href="${pageContext.request.contextPath}/utilisateur?action=list"><p>Gérer les utilisateurs</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-user-tie"></i>
                <a href="${pageContext.request.contextPath}/armateur?action=list"><p>Gérer les armateurs</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-handshake"></i>
                <a href="${pageContext.request.contextPath}/consignataire?action=list"><p>Gérer les consignataires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-ship"></i>
                <a href="${pageContext.request.contextPath}/navire?action=list"><p>Gérer les navires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-calendar-alt"></i>
                <a href="${pageContext.request.contextPath}/escale?action=list"><p>Gérer les escales</p></a>
            </div>
			<div class="dashboard-card">
			    <i class="fas fa-file-invoice"></i>
			    <a href="${pageContext.request.contextPath}/bonpilotage?action=list"><p>Bons de pilotage</p></a>
			</div>
            <div class="dashboard-card">
                <i class="fas fa-file-invoice-dollar"></i>
                <a href="${pageContext.request.contextPath}/facture?action=list"><p>Facturation</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-chart-line"></i>
                <!-- Lien vers la page des rapports -->
                <a href="${pageContext.request.contextPath}/rapport"><p>Consulter les rapports</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-history"></i>
                <a href="${pageContext.request.contextPath}/historique"><p>Historique du système</p></a>
            </div>
        <% } %>
        
        <%-- Cartes pour AGENT PORTUAIRE --%>
        <% if ("agent_portuaire".equals(role)) { %>
            <div class="dashboard-card">
                <i class="fas fa-calendar-plus"></i>
                <a href="${pageContext.request.contextPath}/escale?action=new"><p>Créer une escale</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-eye"></i>
                <a href="${pageContext.request.contextPath}/escale?action=list"><p>Voir les escales</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-ship"></i>
                <a href="${pageContext.request.contextPath}/navire?action=new"><p>Créer un navire</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-ship"></i>
                <a href="${pageContext.request.contextPath}/navire?action=list"><p>Voir les navires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-user-tie"></i>
                <a href="${pageContext.request.contextPath}/armateur?action=list"><p>Voir les armateurs</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-user-tie"></i>
                <a href="${pageContext.request.contextPath}/consignataire?action=list"><p>Voir les consignataires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-file-invoice"></i>
                <a href="${pageContext.request.contextPath}/bonpilotage?action=list"><p>Bons de pilotage</p></a>
            </div>
        <% } %>
        
        <%-- Cartes pour AGENT FACTURATION --%>
        <% if ("agent_facturation".equals(role)) { %>
            <div class="dashboard-card">
                <i class="fas fa-file-invoice-dollar"></i>
                <a href="${pageContext.request.contextPath}/facture?action=list"><p>Gérer la facturation</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-clock"></i>
                <a href="${pageContext.request.contextPath}/facture?action=historique"><p>Historique de facturation</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-ship"></i>
                <a href="${pageContext.request.contextPath}/navire?action=list"><p>Voir les navires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-user-tie"></i>
                <a href="${pageContext.request.contextPath}/armateur?action=list"><p>Voir les armateurs</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-user-tie"></i>
                <a href="${pageContext.request.contextPath}/consignataire?action=list"><p>Voir les consignataires</p></a>
            </div>
            <div class="dashboard-card">
                <i class="fas fa-file-invoice"></i>
                <a href="${pageContext.request.contextPath}/bonpilotage?action=list"><p>Voir les bons de pilotage</p></a>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>