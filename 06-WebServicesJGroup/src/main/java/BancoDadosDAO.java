
public class BancoDadosDAO implements IFBancoDadosDAO {
	private String jdbc;
	
	public BancoDadosDAO(String jdbc) {
		this.jdbc = jdbc;
	}
	public void conectar() {
		System.out.println("Conectando no banco... " + jdbc);
		
	}

	public boolean executarSQL(String sql) {
		conectar();
		System.out.println("Executando sql... " + sql);
		return false;
	}

}
