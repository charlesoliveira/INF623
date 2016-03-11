package br.ifba.gsort.jgroup;

import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;

import br.ifba.gsort.dao.BancoDadosDAO;

public class JCluster extends DataBaseReceiver{
	public static final String DEFAULT_NAME = "DataBaseCluster9999xxx";
	public static final int MAX_MEMBERS = 1;
	
	static String[] jdbcList = new String[] { 
			"jdbc:postgresql://localhost:5433/jgroup?user=postgres&password=postgres",
			"jdbc:postgresql://localhost:5434/jgroup?user=postgres&password=postgres"
	};
	List<DataBaseReceiver> lista = new ArrayList<DataBaseReceiver>();

	public JCluster() throws Exception {
		super(DEFAULT_NAME, new BancoDadosDAO(jdbcList[0]));
	}

	public List<DataBaseReceiver> getLista() {
		return lista;
	}

	public void setLista(List<DataBaseReceiver> lista) {
		this.lista = lista;
	}

	public void addDataBaseReceiver() throws Exception {
		if (this.lista.size() < MAX_MEMBERS){

			String jdbc = jdbcList[this.lista.size()+1];
			DataBaseReceiver rec = new DataBaseReceiver(DEFAULT_NAME,new BancoDadosDAO(jdbc));
			this.lista.add(rec);
			rec.start();
		}else{
			System.err.println("Numero maximo de membros alcançados!!");
			throw new Exception("Numero maximo de membros alcançados!!");
		}
	}
	public void stopDataBaseReceiver(int index) throws Exception {
		 this.lista.remove(index).stop();
		

	}

	public String[] getJdbcList() {
		return jdbcList;
	}

	public void setJdbcList(String[] jdbcList) {
		this.jdbcList = jdbcList;
	}

	
	
	

}
