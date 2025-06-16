package com.gestionescale.servlet;

import com.gestionescale.dao.EscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EscaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EscaleDAO escaleDAO;

    @Override
    public void init() {
        escaleDAO = new EscaleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            listEscales(request, response);
        } else {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertEscale(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEscale(request, response);
                    break;
                case "/delete":
                    deleteEscale(request, response);
                    break;
                case "/view":
                    showDetails(request, response);
                    break;
                default:
                    listEscales(request, response);
                    break;
            }
        }
    }

    private void listEscales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Escale> escales = escaleDAO.getAllEscales();
        request.setAttribute("escales", escales);
        request.getRequestDispatcher("/jsp/escale/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/escale/form.jsp").forward(request, response);
    }

    private void insertEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int idNavire = Integer.parseInt(request.getParameter("idNavire"));
            String debutEscale = request.getParameter("debutEscale");
            String finEscale = request.getParameter("finEscale");
            double prixSejour = Double.parseDouble(request.getParameter("prixSejour"));
            String zone = request.getParameter("zone");
            String consignataire = request.getParameter("consignataire");

            Escale escale = new Escale();
            escale.setMyNavire(new Navire(idNavire)); // Navire déjà existant
            escale.setDebutEscale(java.sql.Date.valueOf(debutEscale));
            escale.setFinEscale(java.sql.Date.valueOf(finEscale));
            escale.setPrixSejour(prixSejour);
            escale.setZone(zone);
            escale.setConsignataire(consignataire);

            escaleDAO.addEscale(escale);
            response.sendRedirect(request.getContextPath() + "/escale/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur lors de l'insertion.");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Escale escale = escaleDAO.getEscaleById(id);
        request.setAttribute("escale", escale);
        request.getRequestDispatcher("/jsp/escale/form.jsp").forward(request, response);
    }

    private void updateEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idNavire = Integer.parseInt(request.getParameter("idNavire"));
            String debutEscale = request.getParameter("debutEscale");
            String finEscale = request.getParameter("finEscale");
            double prixSejour = Double.parseDouble(request.getParameter("prixSejour"));
            String zone = request.getParameter("zone");
            String consignataire = request.getParameter("consignataire");

            Escale escale = new Escale();
            escale.setNumeroEscale(id);
            escale.setMyNavire(new Navire(idNavire));
            escale.setDebutEscale(java.sql.Date.valueOf(debutEscale));
            escale.setFinEscale(java.sql.Date.valueOf(finEscale));
            escale.setPrixSejour(prixSejour);
            escale.setZone(zone);
            escale.setConsignataire(consignataire);

            escaleDAO.updateEscale(escale);
            response.sendRedirect(request.getContextPath() + "/escale/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur lors de la mise à jour.");
        }
    }

    private void deleteEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        escaleDAO.deleteEscale(id);
        response.sendRedirect(request.getContextPath() + "/escale/");
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Escale escale = escaleDAO.getEscaleById(id);
        request.setAttribute("escale", escale);
        request.getRequestDispatcher("/jsp/escale/detail.jsp").forward(request, response);
    }
}
