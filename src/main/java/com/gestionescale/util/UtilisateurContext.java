package com.gestionescale.util;

/**
 * Utilitaire de gestion du contexte utilisateur courant via ThreadLocal.
 * Permet de stocker et récupérer l'email de l'utilisateur en cours,
 * pour les opérations nécessitant une traçabilité ou un contexte d'audit.
 * (c) Marieme KAMARA
 */
public class UtilisateurContext {
    // Stocke l'email utilisateur dans le contexte du thread courant
    private static final ThreadLocal<String> userMail = new ThreadLocal<>();

    /**
     * Définit l'email utilisateur courant dans le contexte du thread.
     * @param mail email utilisateur
     */
    public static void setMail(String mail) {
        userMail.set(mail);
    }

    /**
     * Récupère l'email utilisateur courant dans le contexte du thread.
     * @return email utilisateur ou null si non défini
     */
    public static String getMail() {
        return userMail.get();
    }

    /**
     * Nettoie le contexte utilisateur courant (à appeler en fin de traitement).
     */
    public static void clear() {
        userMail.remove();
    }
}