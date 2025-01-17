package com.ensa.gi4.controller;

import com.ensa.gi4.datatabase.impl.UtilisateurDaoImpl;
import com.ensa.gi4.listeners.ApplicationPublisher;
import com.ensa.gi4.listeners.EventType;
import com.ensa.gi4.listeners.MyEvent;
import com.ensa.gi4.modele.Chaise;
import com.ensa.gi4.modele.Livre;
import com.ensa.gi4.modele.Materiel;
import com.ensa.gi4.modele.Utilisateur;
import com.ensa.gi4.service.api.GestionMaterielService;
import com.ensa.gi4.service.impl.GestionMaterielServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("controllerPricipal")
public class GestionMaterielController {

    @Autowired
    ApplicationPublisher publisher;

    @Autowired
    @Qualifier("materielService")
    private GestionMaterielService gestionMaterielServiceImpl;

    @Autowired
    private UtilisateurDaoImpl log;
    //t

    Utilisateur user;
    

    public void MENU() {

    	 System.out.println("1-  Connecter, entrer 1");
    	 System.out.println("2-  Cr�e utilisateur, entrer 2");
        System.out.println("0- Sortir de l'application, entrer 0");

        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        
        if ("0".equals(next)) {
            sortie();
        }
        else if("1".equals(next))
        {
            user = log.find_user();

            if(user.getRole().equals("admin")){
                while (true)
                	ADMIN();
            }
            else if (user.getRole().equals("user")){
                while (true)
                    USER();
            }
            else {
                sortie();
            }
        }else if("2".equals(next))
        {
        	log.creeUtilisateur();
        	while(true) {
        		MENU();
        	}
        }

        else{
        	sortie();
        }

    }

    private void sortie() {
        System.exit(0);
    }







    public void ADMIN(){
    	System.out.println("---------------Welcome Admin------------------");
    	  System.out.println("0- pour sortir de l'application, entrer 0RRRRRRRRR");
   	 System.out.println("1- Chercher un matériel, tapper 1");
        System.out.println("2- Cr�er un nouveau mat�riel, tapper 2");
        System.out.println("3- Supprimer un mat�riel, tapper 3");
        System.out.println("4- Modifier les informations d'un mat�riel, tapper 4");
        System.out.println("5- Marquer un mat�riel indisponible, tapper 5");
        System.out.println("6- Allouer un mat�riel, tapper 6");
        System.out.println("7- Rendre un materiel, tapper 7");
        System.out.println("8- Afficher la liste des mat�riels allou�s par vous, tapper 8");
        System.out.println("9- Afficher la liste des mat�riels allou�s par chaque utilisateur, tapper 9");
        System.out.println("10- Afficher la liste de tout les materiel, tapper 10");
        System.out.println("11- Deconnixion, tapper 11");
        Scanner scannerAdmin  = new Scanner(System.in);
        String next = scannerAdmin.next();
        if ("0".equals(next)) {
        	sortie();
        } else if("1".equals(next)) {
System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listerMateriel();
       	 System.out.println("Saiasir ID du materiel");
            Long ID = scannerAdmin.nextLong();
            gestionMaterielServiceImpl.chercherMateriel(ID);

        }else if("2".equals(next)) {
       	 System.out.println("Choisir le nom du materiel Livre(tapper1)  ou Chaise(tapper2)");
       	 String choice=scannerAdmin.next();
       	 String nomMateriel=null;
       	 if("1".equals(choice)) {
       		 System.out.println("Saisir le code du materiel");
       		 
           	 String code=scannerAdmin.next();
           	 Materiel materiel=new Livre();
           	 materiel.setName("LIVRE");
           	 materiel.setCode(code);
           	System.out.println("Resultat : ");
           	gestionMaterielServiceImpl.ajouterNouveauMateriel(materiel);
       	 }else if("2".equals(choice)) {
       		 System.out.println("Saisir le code du materiel");
           	 String code=scannerAdmin.next();
           	 Materiel materiel=new Chaise();
           	 materiel.setName("CHAISE");
           	 materiel.setCode(code);
           	System.out.println("Resultat : ");
           	gestionMaterielServiceImpl.ajouterNouveauMateriel(materiel);
       	 }else {
       		System.out.println("error : choisir un nombre entre 1 et 2");
       	 }
        }else if("3".equals(next)) {
        	gestionMaterielServiceImpl.listerMateriel();;
             System.out.println("Saisir ID du materiel");
             Long idMateriel = (long) scannerAdmin.nextInt();
             if(gestionMaterielServiceImpl.isExiste( idMateriel)){
            	 System.out.println("Resultat : ");
                 gestionMaterielServiceImpl.supprimerMateriel(idMateriel);
             }
             else {
                 System.out.println("Ce materiel n'existe pas");
             }
        }
        else if("4".equals(next)) {
        	gestionMaterielServiceImpl.listerMateriel();
       		 System.out.println("Saisir ID du materiel");
           	 Long id_M=scannerAdmin.nextLong();
            if(gestionMaterielServiceImpl.isExiste(id_M)) {
            	System.out.println("Nouveau code");
             	 String code_new=scannerAdmin.next();
             	System.out.println("Resultat : ");

           	gestionMaterielServiceImpl.modifierMateriel(id_M, code_new);
       	 }else {
       		 System.out.println("Ce materiel n'existe pas");
       	 }
        }else if("5".equals(next)) {
        	 gestionMaterielServiceImpl.listerMateriel();
             System.out.println("Saiasir ID du materiel");
             Long ID = (long) scannerAdmin.nextInt();



       	 if(gestionMaterielServiceImpl.isExiste(ID)) {
       		System.out.println("Resultat : ");
       		gestionMaterielServiceImpl.rendreMaterielIndisponible(ID);
       	 }else {
       		 System.out.println("Ce materiel n'existe pas");
       	 }
        }else if("6".equals(next)) {
        	//System.out.println(gestionMaterielServiceImpl.combienChaiseLlouer()+"chaise loer");
        	//System.out.println(gestionMaterielServiceImpl.combienLivreLlouer()+"livre loer");
            gestionMaterielServiceImpl.listerMateriel();
            System.out.println("Saiasir ID du materiel");
            Long ID = scannerAdmin.nextLong();
            System.out.println("kkk "+gestionMaterielServiceImpl.combienMateriel());
            if(ID>0 && ID<= gestionMaterielServiceImpl.combienMateriel()) {
            	 if(gestionMaterielServiceImpl.isDisponible(ID)){
                     System.out.println("Saiasir la duree d'allocation");
                     String duree = scannerAdmin.next();
                     System.out.println("Resultat : ");
                     gestionMaterielServiceImpl.allouerMateriel(ID, duree, user.getId_utilisateur(), user.getUsername());
                     gestionMaterielServiceImpl.rendreMaterielIndisponible(ID);
                 }else System.out.println("indisponible ");

            }else {
            	System.out.println("Ce materiel n'existe jamais sur notre store ");

            }
           

        }else if("7".equals(next)) {
        	  gestionMaterielServiceImpl.listerMateriel();
              System.out.println("Saiasir ID du materiel");
              Long ID = scannerAdmin.nextLong();
              System.out.println("Resultat : ");
              gestionMaterielServiceImpl.rendreMateriel(ID,user.getId_utilisateur());
        }else if("8".equals(next)) {
        	System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listeMaterielAlloue(user.getId_utilisateur());
        }else if("9".equals(next)) {
        	System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listeToutMaterielAlloue();
        }
        else if("10".equals(next)) {
        	System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listerMateriel();
        }
        else if("11".equals(next)) {
        	System.out.println("vous ete deconnecter");
        	MENU();
        }
        else {
            System.out.println("Choix invalide !!!");
        }
    }
    private void USER() {
    	 System.out.println("***********WELCOME USER **********");
    	 System.out.println("0- pour sortir de l'application, entrer 0");
    	 System.out.println("1- Chercher un mat�riel");
         System.out.println("2- Allouer un mat�riel");
         System.out.println("3- Rendre un materiel");
         System.out.println("4- Afficher la liste des mat�riels allou�s par cet utilisateur");
         System.out.println("5- Afficher la liste de tout les matteril");
         System.out.println("6-Deconnexion");


        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        if ("0".equals(next)) {
            sortie();
        } else if ("1".equals(next)) {
        	 System.out.println("Saiasir ID du materiel");
             Long ID = scanner.nextLong();
             System.out.println("Resultat : ");
             gestionMaterielServiceImpl.chercherMateriel(ID);

        }else if("2".equals(next)){
        	 gestionMaterielServiceImpl.listerMateriel();
             System.out.println("Saiasir ID du materiel");
             Long ID = scanner.nextLong();
             if(gestionMaterielServiceImpl.isDisponible(ID)){
                 System.out.println("Saiasir duree d'allocation");
                 String duree = scanner.next();
                 System.out.println("Resultat : ");
                 gestionMaterielServiceImpl.allouerMateriel(ID, duree, user.getId_utilisateur(),user.getUsername());
                 gestionMaterielServiceImpl.rendreMaterielIndisponible(ID);
             }else System.out.println("DEja allouer (indisponile) ");
        }else if("3".equals(next)){
        	 gestionMaterielServiceImpl.listerMateriel();
             System.out.println("Saiasir ID du materiel");
             Long ID = scanner.nextLong();
             System.out.println("Resultat : ");
             gestionMaterielServiceImpl.rendreMateriel(ID,user.getId_utilisateur());

        }else if("4".equals(next)){
        	System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listeMaterielAlloue(user.getId_utilisateur());
        }else if("5".equals(next)){
        	System.out.println("Resultat : ");
        	gestionMaterielServiceImpl.listerMateriel();
        }else if("6".equals(next)) {
        	System.out.println("vous ete deconnecter");
        	MENU();
        }
        else {
            System.out.println("Choix invalide !!!");
        }



    }

    }


