import java.io.Serializable;


public interface IFBancoDadosDAO extends Serializable {

	
	void conectar();
	boolean executarSQL(String sql);
	
}
