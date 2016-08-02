
/**
 * Interface blackjackCallback
 * 
 * Interfaccia remota implementata da blackjackClient
 * 
 * @author (Aurelio) 
 * @version (10/04/2007)
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public interface blackjackCallback extends Remote {
                         public void callback(int version) throws RemoteException;
                                                     }
