package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface  PublisherInterface extends Remote {

	void sendMensagem(String msg) throws RemoteException;
}
