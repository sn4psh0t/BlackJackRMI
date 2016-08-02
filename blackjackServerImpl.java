
/**
 * Classe blackjackServerImpl
 * 
 * La classe si occupa di estrarre le carte e implementare i metodi specificati nella sua interfaccia remota.
 * 
 * Si mandano callback ai client ogni volta che il banco estrae una carta
 * 
 * @author (Aurelio) 
 * @version (10/04/2007)
 * 
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.lang.Math.*;


public class blackjackServerImpl extends UnicastRemoteObject implements blackjackInterface  
{ 
    private static final int MAX_PUNTEGGIO=21;
    private static final int MAX_CARTA=17;
    private static final int MIN_CARTA=16;
    
    public int serverScore;
    public String partialResult=new String();
    
    private Vector registered=null;                                // Tengo traccia dei registrati alla callback
    
    public int register(blackjackCallback client) throws RemoteException {                                                                    
                                                                    registered.addElement(client);
                                                                    System.out.println("Il client si è registrato per ottenere le callback");
                                                                    int n=registered.indexOf(registered.lastElement());
                                                                    return n;
                                                                            }
                                                                            
    public void deregister(int callbackID) throws RemoteException {
                                                               registered.removeElement(callbackID);
                                                               System.out.println("Il client si è cancellato dalla lista delle callback");
                                                                  }
    
    
    public blackjackServerImpl() throws RemoteException
    {
       serverScore=0;
       registered = new Vector();
    }

                                          // Mi viene assegnata una carta compresa tra 1 e 10
    public int getCard() throws RemoteException                    
    {
        int gotCard=(int)(Math.random()*10);
          if (gotCard!=0) {
           System.out.println("Numero estratto: "+gotCard);    
           partialResult=partialResult.concat(Integer.toString(gotCard)+" - ");           
           return gotCard;
                          }
        else { 
                                    //E' uscito lo 0
            return this.getCard();
             }
    }
 
                                                                      
                                          // Gioca il banco e si controlla il risultato 
    public String stop(int clientScore) throws RemoteException {
                                      
                                      blackjackServerImpl game=new blackjackServerImpl();
                                      String result;                                
                                      
                                      while(game.serverScore<=21) {
                                      
                                      if(game.serverScore>=clientScore) {
                                                                    System.out.println("Il banco ha vinto");
                                                                    result="Il banco ha vinto:  ha realizzato "+game.serverScore+" con la serie: "+game.partialResult;
                                                                    return result;
                                                                    }  
                                      else {
                                          int newCard=game.getCard();
                                          game.serverScore+=newCard;
                                                                                    // Notifico a tutti i registrati la carta che è stata estratta dal banco
                                          for(Enumeration clients = registered.elements(); clients.hasMoreElements();) {
                                                                    blackjackCallback cardExtracted=(blackjackCallback) clients.nextElement();
                                                                    try {Thread.sleep(2000);}
                                                                    catch (InterruptedException e) {}
                                                                    cardExtracted.callback(newCard);                
                                                                                                                        }  
                                        }

                                                                 }
                                                                 
                                      System.out.println("Il giocatore ha vinto: il banco ha sballato");
                                      result="HAI VINTO! Il banco ha sballato realizzando un punteggio di "+game.serverScore+" con la serie: "+game.partialResult;
                                      return result;
                                    }
}
