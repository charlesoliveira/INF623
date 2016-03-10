package br.ifba.gsort.jgroup;

import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;

import br.ifba.gsort.dao.BancoDadosDAO;

public class JCluster {
	public static final String DEFAULT_NAME = "DataBaseCluster9999";
	public static final int MAX_MEMBERS = 3;
	JChannel channel;
	
	String[] jdbcList = new String[] { 
			"jdbc:postgresql://localhost:5434/jgroup?user=postgres&password=postgres",
			"jdbc:postgresql://localhost:5435/jgroup?user=postgres&password=postgres",
			"jdbc:postgresql://localhost:5432/jgroup?user=postgres&password=postgres"
	};
	List<DataBaseReceiver> lista = new ArrayList<DataBaseReceiver>();

	public JCluster() throws Exception {
		channel = new JChannel();
		channel.connect(DEFAULT_NAME);
		channel.getState(null, 10000);
	}

	public List<DataBaseReceiver> getLista() {
		return lista;
	}

	public void setLista(List<DataBaseReceiver> lista) {
		this.lista = lista;
	}

	public void addDataBaseReceiver() throws Exception {
		if (this.lista.size() <= MAX_MEMBERS){
			
			String jdbc = lista.isEmpty() ? jdbcList[0] : jdbcList[this.lista.size() - 1];
			this.lista.add(new DataBaseReceiver(this.getChannel(), new BancoDadosDAO(jdbc)));
		}else{
			System.err.println("Numero maximo de membros alcançados!!");
			throw new Exception("Numero maximo de membros alcançados!!");
		}
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

	public String[] getJdbcList() {
		return jdbcList;
	}

	public void setJdbcList(String[] jdbcList) {
		this.jdbcList = jdbcList;
	}

	


}
