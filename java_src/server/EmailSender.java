package server;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender implements Runnable{
    private final String HOST = ""; //Email da cui inviare la mail
    private final String PASSWORD = ""; //Password della mail da cui inviare la mail
    private final String OBJECT = "Verifica il tuo account!"; //Oggetto della mail
    private final String STANDARD_BODY = "Inserisci il tuo codice nell'applicazione per verificarlo.\nQuesto � il tuo codice di verifica: ";
    private final int VALIDATION_TIME = 600000; //Tempo per verificare l'account
    
    private String destinatario;
    private String generatedCode;
    
    public EmailSender(String dest, String code){
        this.destinatario = dest;
        this.generatedCode = code;
    }
    
    @Override
    public void run() {
        String emailBody = new String();
        emailBody.concat(generatedCode);
        try {
            sendEmail(HOST, PASSWORD, destinatario, OBJECT, emailBody);
            //TODO Aggiunta Timer di 10 minuti
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendEmail(String usr, String pwd, String to, String subject, String body) throws MessagingException{
        String password=pwd;
	String username=usr;
	    	       
	String host = "smtp.office365.com";
	String from=username;
	   
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
	    
	Session session = Session.getInstance(props);
	    
	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
	msg.setSubject(subject);
	msg.setText(body);
	    
	Transport.send(msg,username,password);
    }

}
