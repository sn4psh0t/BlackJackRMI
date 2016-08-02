
/**
 * Classe blackjackReg
 * 
 * La classe fa il binding oggetto remoto - nome
 * 
 * @author (Aurelio) 
 * @version (10/04/2007)
 */

import java.rmi.*;
import java.rmi.server.*;

public class blackjackReg
{
   public static void main(String args[]) {
        try
        {
            blackjackServerImpl obj = new blackjackServerImpl();
            Naming.rebind("blackjackRemote", obj);
            System.out.println("L'interfaccia remota blackjack è stata registrata nell'RMI Registry");
        } 
        catch (Exception e) {
        System.out.println("Errore nella registrazione: " + e.getMessage());
        e.printStackTrace();}
   }
}
