
/**
 * Interfaccia blackjackInterface implementata da blackjackServerImpl
 * 
 * @author (Aurelio) 
 * @version (10/04/2007)
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public interface blackjackInterface extends Remote
{
    public int getCard() throws RemoteException;
    public String stop(int clientScore) throws RemoteException;
                                                                   // Metodi per registrare i client che vogliono le callback    
    public int register(blackjackCallback callback) throws RemoteException;
    public void deregister(int callbackID) throws RemoteException;
}
