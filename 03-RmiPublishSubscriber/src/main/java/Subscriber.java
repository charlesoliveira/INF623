import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.DashBoardInterface;
import common.SubscriberInterface;

public class Subscriber implements SubscriberInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9022924308259874210L;
	public static final String serverAddress = "127.0.0.1";
	public static final String serverPort = "3232";

	public void receberMensagem(String msg) throws RemoteException {
		System.out.println(" > Recebendo mesnagem" + msg);

	}

	static public void main(String args[]) throws InterruptedException {
		DashBoardInterface dashboard;
		Registry registry;
		SubscriberInterface sub1 = new Subscriber();

		try {

			registry = LocateRegistry.getRegistry(serverAddress, (new Integer(
					serverPort)).intValue());
			dashboard = (DashBoardInterface) (registry.lookup("dashboard"));

			dashboard.assinarTopico(sub1, "TopicoCarros");
			
			while(true){
				Thread.sleep(3000);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
