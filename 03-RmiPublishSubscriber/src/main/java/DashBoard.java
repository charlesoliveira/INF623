import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

import common.DashBoardInterface;
import common.SubscriberInterface;

public class DashBoard extends java.rmi.server.UnicastRemoteObject implements
		DashBoardInterface {

	int thisPort;
	String thisAddress;
	Registry registry;
	ArrayList<String> topicos = new ArrayList<String>();
	HashMap<String, ArrayList<SubscriberInterface>> assinaturas = new HashMap<String, ArrayList<SubscriberInterface>>();

	public void addTopico(String name) {
		System.out.println(" > Adicionando Topico");
		topicos.add(name);
	}

	public void listarTopico() {

		for (String topico : topicos) {
			System.out.println(" > Listando Topicos");
			System.out.println("Topico #: " + topico);

		}
	}

	public void assinarTopico(SubscriberInterface sub, String topico) {
		System.out.println(" > Assinando Subscriber" + sub + " no topico "
				+ topico);

		ArrayList<SubscriberInterface> assinantes = assinaturas.get(topico);
		if (assinantes == null) {
			assinantes = new ArrayList<SubscriberInterface>();
		}
		assinantes.add(sub);
		assinaturas.put(topico, assinantes);
	}

	public void registrarMensagem(String topico, String msg) throws RemoteException {
		System.out.println(" > Recebido Mensagem " + msg + " para o topico"
				+ topico);
		for (SubscriberInterface jndiSubscriber : assinaturas.get(topico)) {

			jndiSubscriber.receberMensagem(msg);

		}
	}

	public DashBoard() throws RemoteException {
		try {
			thisAddress = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {
			throw new RemoteException("can't get inet address.");
		}
		thisPort = 3232;
		System.out.println("this address=" + thisAddress + ",port=" + thisPort);
		try {
			registry = LocateRegistry.createRegistry(thisPort);
			registry.rebind("dashboard", this);
		} catch (RemoteException e) {
			throw e;
		}
	}

	static public void main(String args[]) {
		try {
			DashBoard s = new DashBoard();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}