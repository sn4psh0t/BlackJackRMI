
/**
 * Classe blackjackClient 
 * 
 * La classe si occupa di recuperare l'object remote reference e invocare metodi remoti.
 * Ha inoltre un metodo di callback specificato dall'interfaccia blackjackCallback che lei stessa implementa.
 * 
 * @author (Aurelio) 
 * @version (10/04/2007)
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;


public class blackjackClient implements blackjackCallback
{
   private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
   private static int clientScore;
   private static blackjackInterface obj;
   private static int listenerIndex;
                                            // Implementazione della funzione di callback
  public void callback(int value) throws RemoteException {
                                            System.out.println("\n+++++++++++ Hai ricevuto una Callback. Il banco ha estratto la carta con valore:" +value+"+++++++++++\n");
                                                           }
  
  public static void closegame(int id)   {
                        try {
                            obj.deregister(id);
                            }
                        catch (Exception error){
                                            System.out.println("Hai ricevuto una eccezzione prima: " + error.getMessage());
                                            error.printStackTrace();
                                        }
                            System.out.println("\n.....:: Mi sono deregistrato dal servizio di callback. Grazie per avere giocato!::.....\n");
                            //UnicastRemoteObject.unexportObject(this);
                            //UnicastRemoteObject.unexportObject(this);
                            System.exit(0); 
                                   }                        
                                                           
  public void blackjackClient() { }
                                            // In base alla scelta effettuata dal giocatore chiedo una nuova carta o mi fermo          
  public static int newCardChoosen(int choice) {
                String result;
                try {          
                    if (choice==1)  {
                                    int newCard=obj.getCard();
                                    clientScore+=newCard;
                                        
                                    if (clientScore<21) {
                                                System.out.println("\n****** E' uscita la carta con punteggio: "+newCard+"******\n******Il tuo punteggio totale è: "+clientScore+". ******\nCosa vuoi fare? Premi 1 per avere un'altra carta 2 per fermarti");            
                                                String inputLine = reader.readLine();
                                                int n = Integer.parseInt(inputLine);
                                                newCardChoosen(n);
                                                            }
                                    else if (clientScore==21) {
                                                         System.out.println("\n****** Hai realizzato un Blackjack (21)! Ora tocca al banco giocare... ******");
                                                         result=obj.stop(clientScore);
                                                         System.out.println(result);
                                                        }
                                    else {
                                           System.out.println("\n******* Hai realizzato un punteggio maggiore a 21: Il banco vince (Hai realizzato: "+clientScore+") ******");
                                           closegame(listenerIndex);
                                           return 0;
                                         }                     
                                    }
               
                    else   {
                            System.out.println("\nHai scelto di fermarti. Hai totalizzato un punteggio pari a "+clientScore+".\nOra tocca al banco giocare...\n");
                            result=obj.stop(clientScore);
                            System.out.println(result);
                            closegame(listenerIndex);
                           }                        
                
                    }
                catch (Exception error){
                                            System.out.println("Hai ricevuto una eccezzione prima: " + error.getMessage());
                                            error.printStackTrace();
                                        }
                return 0;                    
                }
                                            // Inizializzazione della comunicazione RMI con blackjackServer
public void init(String remoteHost) {
    try
    {
    System.out.println("+**************************************************************************************************************+");
    System.out.println("* Benvenuto nel gioco blackjack: lo scopo è arrivare più vicino possibile al punteggio di 21. Fai la tua mossa *");
    System.out.println("+**************************************************************************************************************+\n\n");
    
    System.out.println("+******************************************************+");
    System.out.println("\n* Preparo l'RMI                                        *");      
                                                // Esporto l'oggetto
    UnicastRemoteObject.exportObject(this); 
    
    System.out.println("\n* Ho esportato l'oggetto...                            *");
        
    System.out.println("\n* Cerco il remote object reference...                  *");
        
    obj = (blackjackInterface) Naming.lookup(remoteHost);
    
    System.out.println("\n* Mi registro per ricevere le callback dal server...   *");
       
    listenerIndex=obj.register(this);
    
    System.out.println("\n* Registrato in posizione: "+listenerIndex+"                           *");
    
    System.out.println("\n* Mi sono registrato per ricevere le callback...       *");    
       
    System.out.println("\n* Invoco i metodi remoti...                            *\n");
    
    System.out.println("+******************************************************+\n\n");
    
    int firstCard = obj.getCard();
    
    clientScore+=firstCard;
    
    System.out.println("+**********************************************************************************************+");
    
    System.out.println("\n*E' uscita la carta con punteggio: "+firstCard+"\nIl tuo punteggio totale è: "+clientScore+". *\nCosa vuoi fare? Premi 1 per avere un'altra carta 2 per fermarti\n");
    
    System.out.println("+**********************************************************************************************+\n\n");
    
    String inputLine = reader.readLine();
    
    int decision = Integer.parseInt(inputLine);
    
    newCardChoosen(decision);
    
    }
    catch (Exception e){
         System.out.println("Hai ricevuto una eccezzione seconda: " + e.getMessage());
         e.printStackTrace();
    }
  }

  public static void main (String args[]) {
                                        if(args.length == 0) {
                                            System.out.println("Errore. USO: java blackjackClient <remote host>");
                                            System.exit(0);
                                                             }
                                    //System.setSecurityManager(new RMISecurityManager());
                                    blackjackClient client=new blackjackClient();
                                    client.init("rmi://"+args[0]+"/blackjackRemote");
                                         }
  }



