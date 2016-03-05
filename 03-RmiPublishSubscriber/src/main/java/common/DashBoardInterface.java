package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface DashBoardInterface extends Remote
{
	void registrarMensagem(String topico, String msg) throws RemoteException;
	void assinarTopico(SubscriberInterface subscriber,String topico) throws RemoteException;
	void addTopico(String name)  throws RemoteException;
	void listarTopico() throws RemoteException;

	
}
