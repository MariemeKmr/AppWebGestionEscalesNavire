package com.gestionescale.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.gestionescale.dao.ConsignataireDAO;
import com.gestionescale.model.Consignataire;

public class ConsignataireServlet extends jakarta.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsignataireDAO consignataireDAO;

    public void init() {
        consignataireDAO = new ConsignataireDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            listConsignataires(request, response);
        } else {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertConsignataire(request, response);
                    break;
                case "/delete":
                    deleteConsignataire(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateConsignataire(request, response);
                    break;
                default:
                    listConsignataires(request, response);
                    break;
            }
        }
    }

    private void listConsignataires(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
        request.setAttribute("consignataires", consignataires);
        request.getRequestDispatcher("/jsp/consignataire/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/consignataire/form.jsp").forward(request, response);
    }

    private void insertConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String raisonSociale = request.getParameter("raisonSociale");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");

        Consignataire newConsignataire = new Consignataire();
        newConsignataire.setRaisonSociale(raisonSociale);
        newConsignataire.setAdresse(adresse);
        newConsignataire.setTelephone(telephone);

        consignataireDAO.addConsignataire(newConsignataire);
        response.sendRedirect(request.getContextPath() + "/consignataire/");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Consignataire existingConsignataire = consignataireDAO.getConsignataireById(id);
        request.setAttribute("consignataire", existingConsignataire);
        request.getRequestDispatcher("/jsp/consignataire/form.jsp").forward(request, response);
    }

    private void updateConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String raisonSociale = request.getParameter("raisonSociale");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");

        Consignataire consignataire = new Consignataire(id, raisonSociale, adresse, telephone);
        consignataireDAO.updateConsignataire(consignataire);
        response.sendRedirect(request.getContextPath() + "/consignataire/");
    }

    private void deleteConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        consignataireDAO.deleteConsignataire(id);
        response.sendRedirect(request.getContextPath() + "/consignataire/");
    }
}
