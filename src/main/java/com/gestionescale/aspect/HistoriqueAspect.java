package com.gestionescale.aspect;

import com.gestionescale.dao.implementation.HistoriqueDAO;
import com.gestionescale.model.Historique;
import com.gestionescale.util.UtilisateurContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import java.util.Date;

@Aspect
public class HistoriqueAspect {
    @After("execution(* *(..))")
    public void logDaoAction(JoinPoint joinPoint) {
        System.out.println(">>> [AspectJ] CATCH ALL: " + joinPoint.getSignature());
    }
}

//@Aspect
//public class HistoriqueAspect {
//
//    private HistoriqueDAO historiqueDAO = new HistoriqueDAO();
//
//    @Pointcut("execution(public * com.gestionescale.dao.implementation.*DAO.*(..)) && !within(com.gestionescale.dao.implementation.HistoriqueDAO)")
//    public void allDaoMethods() {}
//
//    // Advice après chaque exécution de méthode DAO
//    @After("allDaoMethods()")
//    public void logDaoAction(JoinPoint joinPoint) {
//    	 System.out.println(">>> [AspectJ] INTERCEPTION: " + joinPoint.getSignature());
//    	try {
//            String classe = joinPoint.getTarget().getClass().getSimpleName();
//            String methode = joinPoint.getSignature().getName();
//            String params = "";
//            Object[] args = joinPoint.getArgs();
//            for (Object arg : args) {
//                if (arg != null) params += arg.toString() + "; ";
//            }
//            String utilisateur = UtilisateurContext.getMail();
//            Date dateOperation = new Date();
//
//            Historique historique = new Historique();
//            historique.setUtilisateur(utilisateur != null ? utilisateur : "inconnu");
//            historique.setOperation(classe + "." + methode);
//            historique.setDescription(params);
//            historique.setDateOperation(dateOperation);
//
//            historiqueDAO.ajouterHistorique(historique);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}