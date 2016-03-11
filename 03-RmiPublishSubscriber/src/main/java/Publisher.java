import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.DashBoardInterface;
import common.PublisherInterface;

public class Publisher implements PublisherInterface {
	public static final String serverAddress = "127.0.0.1";
	public static final String serverPort = "3232";

	DashBoardInterface dashboard;
	Registry registry;
	String topico;

	public Publisher(String topico) {
		this.topico = topico;

		try {

			registry = LocateRegistry.getRegistry(serverAddress, (new Integer(
					serverPort)).intValue());
			dashboard = (DashBoardInterface) (registry.lookup("dashboard"));
			dashboard.addTopico(topico);
			
			try {
				while (true) {
					Thread.sleep(3000);
					this.sendMensagem("Foi lançado o novo gol 2.0");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void sendMensagem(String msg) throws RemoteException {
		dashboard.registrarMensagem(topico, msg);

	}

	

	static public void main(String args[]) {
		Publisher publisher = new Publisher("TopicoCarros");

	}
}
