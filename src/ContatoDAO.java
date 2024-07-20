
import java.util.ArrayList;

public interface ContatoDAO {	
	public Contato buscarContato(int id);
	public void inserirContato(Contato contato);
	public void atualizarContato(Contato contato);
	public void removerContato(Contato contato);
	public ArrayList<Contato> listarContatos();
	
}
