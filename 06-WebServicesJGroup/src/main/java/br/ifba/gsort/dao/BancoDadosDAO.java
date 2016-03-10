package br.ifba.gsort.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.ifba.gsort.jgroup.JCluster;
import br.ifba.gsort.jgroup.Status;

public class BancoDadosDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8174830990735339593L;

	private String jdbc;
	Connection connection = null;

	public BancoDadosDAO(String jdbc) throws ClassNotFoundException, SQLException {
		this.jdbc = jdbc;
		Class.forName("org.postgresql.Driver");
		conectar();
		 desConectar();

	}

	public void conectar() throws SQLException {
	//	System.out.println("Conectando no banco... " + jdbc);
		connection = DriverManager.getConnection(jdbc);
	}

	public String executarSQL(String sql) {
	//	System.out.println("Executando sql... " + sql);
		try {
			conectar();
			Statement stm = connection.createStatement();
			stm.execute(sql);
			stm.close();
			
		} catch (SQLException e) {
			
		//	e.printStackTrace();
			return  e.getMessage();
		}
		return Status.OK.getValue();
		
	}

	public void desConectar() {
		//System.out.println("Desconectando no banco... " + jdbc);
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
				System.out.println("Falha ao desconectar");
			}
	}

}
