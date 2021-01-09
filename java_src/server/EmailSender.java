package server;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Timer;

/**
 * Implementazione dell'interfaccia {@link java.lang.Runnable} utilizzata per inviare mail da parte del server tramite <code>Thread</code>
 * @author Fedeli Andrea
 * @see java.lang.Runnable
 */
public class EmailSender implements Runnable{
    private final String HOST = "ilparoliere2020@outlook.it"; //Email da cui inviare la mail
    private final String PASSWORD = "InfoInsubria2020"; //Password della mail da cui inviare la mail
    private final String VERIFICATION_OBJECT = "Il Paroliere - Verifica il tuo account"; //Oggetto della mail
    private final String RESET_PSW_OBJECT = "Il Paroliere - Modifica Password";
    private final String VERIFICATION_BODY = "Inserisci il tuo codice nell'applicazione per attivare il tuo profilo."
            + "\nQuesto è il tuo codice di verifica: ";
    private final String RESET_PSW_BODY = "Hai richiesto il reset della password!\nQuesta è la tua nuova password: ";
    private final long VALIDATION_TIME = 600000; //Tempo per verificare l'account
    
    private String destinatario;
    private String messageContent;
    private String nick;
    private int mailType; //1 = codice di verifica  2 = reset password
    
    /**
     * Costruttore della classe
     * @param dest Email dell'utente a cui spedire la mail
     * @param content Contenuto della mail
     * @param nickname Nickname dell'utente a cui spedire la mail
     * @param mailType Tipologia della mail da spedire. <p> 1 - mail di verifica. 2 - mail per reset password.
     */
    public EmailSender(String dest, String content, String nickname, int mailType){
        this.destinatario = dest;
        this.messageContent = content;
        this.nick = nickname;
        this.mailType = mailType;
    }
    
    /**
     * Operazione da svolgere per spedire la mail
     * @see java.lang.Runnable#run() 
     */
    @Override
    public void run(){
        try {
            String emailBody;
            switch(mailType){
                case 1:
                    emailBody = this.VERIFICATION_BODY + this.messageContent;
                    sendEmail(HOST, PASSWORD, destinatario, this.VERIFICATION_OBJECT, emailBody);
                    //TODO Aggiunta Timer di 10 minuti
                    Timer t = new Timer();
                    AccountDeleter ad = new AccountDeleter(nick);
                    t.schedule(ad, VALIDATION_TIME);
                    break;
                case 2:
                    emailBody = this.RESET_PSW_BODY + this.messageContent;
                    sendEmail(HOST, PASSWORD, destinatario, this.RESET_PSW_OBJECT, emailBody);
                    break;
            }
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
