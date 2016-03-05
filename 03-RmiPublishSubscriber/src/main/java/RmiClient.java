import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient
{
    static public void main(String args[])
    {
       ReceiveMessageInterface rmiPublisher;
       Registry registry;
       String serverAddress="127.0.0.1";//args[0];
       String serverPort="3232";//args[1];
       String text="oi";//args[2];
       System.out.println("sending "+text+" to "+serverAddress+":"+serverPort);
       try{
        
           registry=LocateRegistry.getRegistry(
               serverAddress,
               (new Integer(serverPort)).intValue()
           );
           // look up the remote object
           rmiPublisher=
              (ReceiveMessageInterface)(registry.lookup("rmiPublisher"));
           // call the remote method
           rmiPublisher.receiveMessage(text);
       }
       catch(RemoteException e){
           e.printStackTrace();
       }
       catch(NotBoundException e){
           e.printStackTrace();
       }
    }
}
