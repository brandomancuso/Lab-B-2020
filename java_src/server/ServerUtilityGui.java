package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtilityGui {
    //private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public ServerUtilityGui(){
        
    }
    
    public String controlDbResult(String host, String username, String password){
        if(host.isEmpty()){
            //Se l'utente non ha inserito l'host del database
            return "Inserisci l'host!";
        }
        else{
            if(username.isEmpty()){
                return "Inserisci lo username!";
            }
            else{
               //Se l'utente non ha inserito lo username
               if(password.isEmpty()){
                    //Se l'utente non ha inserito la password
                    return "Inserisci la password!";
                }
                else{
                    //Se tutti i campi sono inseriti
                    return null;
                }
            }
        }
    }
    
    public String controlRegisterResult(String username, String password, String rPassword, String email){
        if(username.isEmpty()){
            //Se l'utente non ha inserito lo username
            return "Inserire lo username!";
        }
        else{
            if(password.isEmpty()){
                //Se l'utente non ha inserito la password
                return "Inserire la password!";
            }
            else{
                if(rPassword.isEmpty()){
                    //Se l'utente non ha ripetuto la password
                    return "Ripetere la password!";
                }
                else{
                    if(email.isEmpty()){
                        //Se l'utente non ha inserito la mail
                        return "Inserire l'indirizzo email!";
                    }
                    else{
                        if(password.equals(rPassword)){
                            Matcher matcher = EMAIL_PATTERN.matcher(email);
                            if(matcher.find()){
                                //Tutte le informazioni corrette
                                return null;
                            }
                            else{
                                //L'utente non ha inserito una mail corretta
                                return "Inserire email valida!";
                            }
                        }
                        else{
                            //Se le due password sono diverse
                            return "Le due password non coincidono!";
                        }
                    }
                }
            }
        }
    }
    
    public String controlLoginResult(String username, String password){
        if(username.isEmpty()){
            //Se l'utente non ha inserito lo username
            return "Inserisci lo username!";
        }
        else{
            if(password.isEmpty()){
                //Se l'utente non ha inserito la password
                return "Inserisci la password!";
            }
            else{
                //Se l'utente ha inserito tutte le informazioni
                return null;
            }
        }
    }
    
    /*public boolean checkPassword(String password){
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }*/
}