package common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubscriberInterface  extends Remote,Serializable{
	void receberMensagem(String msg) throws RemoteException;
}
