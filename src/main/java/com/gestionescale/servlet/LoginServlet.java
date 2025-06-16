package com.gestionescale.servlet;

import com.gestionescale.dao.UtilisateurDAO;
import com.gestionescale.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		Utilisateur utilisateur = utilisateurDAO.trouverParEmailEtMotDePasse(email, password);

		if (utilisateur != null) {
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", utilisateur);
			response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); 
		} else {
			request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); 
	}
}