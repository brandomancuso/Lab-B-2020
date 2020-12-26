package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtilityGui {
    
    public static String controlDbResult(String host, String username, String password){
        String controlResult;
        if(host.equals("jdbc:postgresql:///")){
            //Se l'utente non ha inserito l'host del database
            controlResult = "Inserisci l'host!";
        }
        else{
            if(username.isEmpty()){
                controlResult = "Inserisci lo username!";
            }
            else{
               //Se l'utente non ha inserito lo username
               if(password.isEmpty()){
                    //Se l'utente non ha inserito la password
                    controlResult = "Inserisci la password!";
                }
                else{
                    //Se tutti i campi sono inseriti
                    controlResult = null;
                }
            }
        }
        return controlResult;
    }
    
    public static String controlRegisterResult(String username, String password, String rPassword, String email){
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String controlResult;
        if(username.isEmpty()){
            //Se l'utente non ha inserito lo username
            controlResult = "Inserire lo username!";
        }
        else{
            if(password.isEmpty()){
                //Se l'utente non ha inserito la password
                controlResult = "Inserire la password!";
            }
            else{
                if(rPassword.isEmpty()){
                    //Se l'utente non ha ripetuto la password
                    controlResult = "Ripetere la password!";
                }
                else{
                    if(email.isEmpty()){
                        //Se l'utente non ha inserito la mail
                        controlResult = "Inserire l'indirizzo email!";
                    }
                    else{
                        if(password.equals(rPassword)){
                            matcher = EMAIL_PATTERN.matcher(email);
                            if(matcher.find()){
                                //Tutte le informazioni corrette
                                controlResult = null;
                            }
                            else{
                                //L'utente non ha inserito una mail corretta
                                controlResult = "Inserire email valida!";
                            }
                        }
                        else{
                            //Se le due password sono diverse
                            controlResult = "Le due password non coincidono!";
                        }
                    }
                }
            }
        }
        return controlResult;
    }
    
    public static String controlLoginResult(String username, String password){
        String controlResult;
        if(username.isEmpty()){
            //Se l'utente non ha inserito lo username
            controlResult = "Inserisci lo username!";
        }
        else{
            if(password.isEmpty()){
                //Se l'utente non ha inserito la password
                controlResult = "Inserisci la password!";
            }
            else{
                //Se l'utente ha inserito tutte le informazioni
                controlResult = null;
            }
        }
        return controlResult;
    }
}