package com.example.tourisme_medicale.Helpers;

import com.example.tourisme_medicale.MedicinController;
import com.example.tourisme_medicale.models.*;

import java.time.LocalDate;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {

    public static void sendMail(RendezVous rdv){
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail = "oussama.touati.178@gmail.com";
        String password = "ixkisxwxqdhfzrud";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return  new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = preparMessage(session,myAccountEmail,rdv);
        try {
            Transport.send(message);
            System.out.println("Message sent succesfullly");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private static Message preparMessage(Session session,String myAccountEmail,RendezVous rdv) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(rdv.patient().getEmail()));
            message.setSubject("Confirmation de rendez-vous");
            message.setText(getText(rdv));
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getText(RendezVous rdv){
        MedicinController medicinController = new MedicinController();
        String type = null;
        if (rdv.type() instanceof Chirurgie)
        {
            Chirurgie ch = (Chirurgie) rdv.type();
            type = "Type de rendez-vous: "+ ch.getTypeChirurgie()+"\n"+
                    "Duree totale de traitement: "+ ch.getDuree() +"jours\n"+
                    "Prix de traitement: "+ ch.getPrix()+ "Dinars\n"+
                    "Specialite: "+ ch.getSpecialite()+ "\n";
            if (rdv.hebergement() instanceof ChambreHotel){
                ChambreHotel chHot = (ChambreHotel)rdv.hebergement();
                type += "******** Hebergement ***************\n"+
                        "Type de hebergement: Hotel "+ chHot.getHotel()+ "\n"+
                        "Chambre: " + chHot.getNom()+"\n"+
                        "Prix par jour: " + chHot.getPrixJour()+"DT\n"+
                        "Adresse: "+ chHot.hotel().adresse()+"\n"+
                        "Telephone: +(216)"+ chHot.hotel().telephone()+"\n"+
                        "Le prix de hebergement: "+ chHot.getPrixJour()*ch.getDuree()+ "DT.\n";
            }
            else if (rdv.hebergement() instanceof ChambreClinique){
                ChambreClinique chHot = (ChambreClinique)rdv.hebergement();
            type += "******** Hebergement ***************\n"+
                    "Type de hebergement: Clinique"+ chHot.getClinique()+ "\n"+
                    "Chambre: " + chHot.getNom()+"\n"+
                    "Prix par jour: " + chHot.getPrixJour()+"DT\n"+
                    "Adresse: "+ chHot.clinique().adresse()+"\n"+
                    "Telephone: +(216)"+ chHot.clinique().telephone()+"\n"+
                    "Le prix de hebergement: "+ chHot.getPrixJour()*ch.getDuree()+ "DT.\n";
            }
            else if (rdv.hebergement() instanceof AppartementMeuble){
                AppartementMeuble chHot = (AppartementMeuble)rdv.hebergement();
                type += "******** Hebergement ***************\n"+
                        "Type de hebergement: "+ chHot.getNom()+"\n"+
                        "Prix par jour: " + chHot.getPrixJour()+"DT\n"+
                        "Adresse: "+ chHot.getAdresse()+"\n"+
                        "Le prix de hebergement: "+ chHot.getPrixJour()*ch.getDuree()+ "DT.\n";
            }
            LocalDate dateF = rdv.getDateDebut().toLocalDate().plusDays(ch.getDuree());
            type+= "La date de debut de traitement: "+ rdv.getDateDebut()+"\n"+
            "La date de fin de traitement: " + dateF+"\n";
        }
        else if (rdv.type() instanceof SoinsMedicaux)
        {
            SoinsMedicaux ch = (SoinsMedicaux) rdv.type();
            type = "Type de rendez-vous: "+ ch.getSpecialite()+"\n"+
                    "Date de rendez-vous: "+ rdv.getDateDebut() +"\n"+
                    "Heure: "+ rdv.getHeure()+"\n";
        }
        String msg = "Bonjour "+ rdv.getPatient()+"\n"+
                "Nous avons le plaisir de vous anoncer que votre rendez-vous à été bien confirmé !\n"+
                "Voici les informations nécessaires: \n"+
                type +
                "************************* Medicin *****************\n"+
                "Le medicin: "+ rdv.getMedicin()+"\n"+
                "Le clinique: "+ rdv.getClinique()+"\n"+
                "L'adresse de clinique: "+ rdv.medicin().clinique().adresse()+"\n"+
                "Telephone de clinique: +(216)"+ rdv.medicin().clinique().telephone()+".\n"+
                "Le prix totale apres reduction: "+ rdv.getPrixTotal()+ "DT.";
        return  msg;
    }
}
