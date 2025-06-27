package com.gestionescale.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class HistoriqueAspect {

    @Pointcut("execution(* com.gestionescale.dao.implementation.*.*(..))")
    public void allDaoMethods() {}

    @After("allDaoMethods()")
    public void logAction(JoinPoint joinPoint) {
        System.out.println("=== [AspectJ TEST] Intercepted: " + joinPoint.getSignature());
    }
}





//package com.gestionescale.aspect;
//
//import com.gestionescale.dao.implementation.HistoriqueDAO;
//import com.gestionescale.model.Historique;
//import com.gestionescale.util.UtilisateurContext;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import java.util.Date;
//
//@Aspect
//public class HistoriqueAspect {
//
//    private HistoriqueDAO historiqueDAO = new HistoriqueDAO();
//
//    // Création : ajouter* ou save*
//    @Pointcut("execution(* com.gestionescale.dao.implementation.*.ajouter*(..)) || execution(* com.gestionescale.dao.implementation.*.save*(..))")
//    public void creation() {}
//
//    // Modification : modifier* ou update*
//    @Pointcut("execution(* com.gestionescale.dao.implementation.*.modifier*(..)) || execution(* com.gestionescale.dao.implementation.*.update*(..))")
//    public void modification() {}
//
//    // Suppression : supprimer* ou delete*
//    @Pointcut("execution(* com.gestionescale.dao.implementation.*.supprimer*(..)) || execution(* com.gestionescale.dao.implementation.*.delete*(..))")
//    public void suppression() {}
//
//    @After("creation() || modification() || suppression()")
//    public void logAction(JoinPoint joinPoint) {
//    	   
//    	System.out.println("=== [AspectJ] Méthode interceptée : " + joinPoint.getSignature());
//    	    // ... reste du code
//    	
//        String method = joinPoint.getSignature().getName();
//        String operation = method.startsWith("ajouter") || method.startsWith("save") ? "CREATION"
//                         : method.startsWith("modifier") || method.startsWith("update") ? "MODIFICATION"
//                         : "SUPPRESSION";
//        Object[] args = joinPoint.getArgs();
//        String description = "";
//        if (args != null && args.length > 0 && args[0] != null) {
//            description = args[0].getClass().getSimpleName() + " : " + args[0].toString();
//        }
//        // Récupération de l'utilisateur courant
//        String utilisateur = UtilisateurContext.getMail(); // à configurer dans tes servlets
//
//        Historique h = new Historique();
//        h.setUtilisateur(utilisateur != null ? utilisateur : "SYSTEME");
//        h.setOperation(operation);
//        h.setDescription(description);
//        h.setDateOperation(new Date());
//        historiqueDAO.enregistrer(h);
//
//        System.out.println("=== [AspectJ Historique] " + operation + " : " + description + " par " + utilisateur);
//    }
//}