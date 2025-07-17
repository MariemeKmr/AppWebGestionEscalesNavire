package com.gestionescale.filter;

import com.gestionescale.util.UtilisateurContext;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpSession session = httpRequest.getSession(false);
                if (session != null) {
                    // Adapte selon ta gestion de session (userMail ou l'objet Utilisateur)
                    Object userMailObj = session.getAttribute("userMail");
                    if (userMailObj != null) {
                        UtilisateurContext.setMail(userMailObj.toString());
                    } else {
                        // Si tu stockes plutôt l'objet Utilisateur en session
                        Object userObj = session.getAttribute("utilisateur");
                        if(userObj != null) {
                            try {
                                java.lang.reflect.Method getEmail = userObj.getClass().getMethod("getEmail");
                                Object email = getEmail.invoke(userObj);
                                if(email != null) {
                                    UtilisateurContext.setMail(email.toString());
                                }
                            } catch(Exception ignored) {}
                        }
                    }
                }
            }
            chain.doFilter(request, response);
        } finally {
            UtilisateurContext.clear(); // Important pour éviter les fuites de contexte 
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}