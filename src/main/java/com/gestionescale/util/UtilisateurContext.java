package com.gestionescale.util;
public class UtilisateurContext {
    private static final ThreadLocal<String> userMail = new ThreadLocal<>();
    public static void setMail(String mail) { userMail.set(mail); }
    public static String getMail() { return userMail.get(); }
}