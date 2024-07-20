
import java.util.ArrayList;

public interface EnderecoDAO {
	public Endereco buscarEndereco(int id);
	public void inserirEndereco(Endereco endereco);
	public void atualizarEndereco(Endereco endereco);
	public void removerEndereco(Endereco endereco);
	public ArrayList<Endereco> listarEnderecos();	
	
}
