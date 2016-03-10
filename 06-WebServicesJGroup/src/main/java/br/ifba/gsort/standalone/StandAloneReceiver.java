package br.ifba.gsort.standalone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgroups.JChannel;
import org.jgroups.Message;

import br.ifba.gsort.dao.BancoDadosDAO;
import br.ifba.gsort.jgroup.DataBaseReceiver;
import br.ifba.gsort.jgroup.JCluster;
import br.ifba.gsort.jgroup.Mensagem;

public class StandAloneReceiver extends DataBaseReceiver {
	
	static final String jdbc = "jdbc:postgresql://localhost:5433/jgroup?user=postgres&password=postgres";
	
	public StandAloneReceiver() throws Exception {
		super(new JChannel(), new BancoDadosDAO(jdbc));
		channel.connect(JCluster.DEFAULT_NAME);
		channel.getState(null, 10000);
		eventLoop();
		channel.close();
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
				Message msg = new Message(null, null, new Mensagem(line));
				channel.send(msg);
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new StandAloneReceiver();
	}
}
