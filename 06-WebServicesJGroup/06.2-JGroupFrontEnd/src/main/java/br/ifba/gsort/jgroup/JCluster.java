package br.ifba.gsort.jgroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;

public class JCluster {
	static final String DEFAULT_NAME = "ChatCluster";
	JChannel channel;
	ArrayList<String> jdbcList = new ArrayList<String>();
	List<Receiver> lista = new ArrayList<Receiver>();

	public JCluster(String name) throws Exception {
		jdbcList.add("jdbc://localhost:5432/sa@12345");
		jdbcList.add("jdbc://localhost:5433/sa@12345");
		jdbcList.add("jdbc://localhost:5434/sa@12345");
		jdbcList.add("jdbc://localhost:5435/sa@12345");
		jdbcList.add("jdbc://localhost:5436/sa@12345");

		channel = new JChannel();
		channel.connect(name);
		channel.getState(null, 10000);

	}

	public JCluster() throws Exception {
		jdbcList.add("jdbc://localhost:5432/sa@12345");
		jdbcList.add("jdbc://localhost:5433/sa@12345");
		jdbcList.add("jdbc://localhost:5434/sa@12345");
		jdbcList.add("jdbc://localhost:5435/sa@12345");
		jdbcList.add("jdbc://localhost:5436/sa@12345");

		channel = new JChannel();
		channel.connect(DEFAULT_NAME);
		channel.getState(null, 10000);
		// eventLoop();

	}

	public List<Receiver> getLista() {
		return lista;
	}

	public void setLista(List<Receiver> lista) {
		this.lista = lista;
	}

	public void addReceiver(Receiver receiver) {
		this.lista.add(receiver);
	}
	public void addReceiver() {
		this.lista.add(new Receiver(this));
	}

	public void destroyMe() {
		channel.close();
	}

	public JChannel getChannel() {
		return channel;
	}

	public void setChannel(JChannel channel) {
		this.channel = channel;
	}

	public ArrayList<String> getJdbcList() {
		return jdbcList;
	}

	public void setJdbcList(ArrayList<String> jdbcList) {
		this.jdbcList = jdbcList;
	}

	private void eventLoop() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.print("> ");
				System.out.flush();
				String line = in.readLine().toLowerCase();
				if (line.startsWith("quit") || line.startsWith("exit")) {
					break;
				}
				Message msg = new Message(null, null, line);
				channel.send(msg);
			} catch (Exception e) {
			}
		}
	}

}
