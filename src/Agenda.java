
import java.util.ArrayList;

public class Agenda {

	ContatoDAO dao;

	ArrayList<Contato> contatos;

	public Agenda() {
		dao = new ContatoController();
		contatos = dao.listarContatos();
	}

	public ArrayList<Contato> listarContatos() {
		return dao.listarContatos();

	}

	public Contato buscarContato(int id) {
		return dao.buscarContato(id);
	}

	public void adicionarContato(Contato contato) {
		dao.inserirContato(contato);
		contatos = dao.listarContatos();
	}

	public void removerContato(Contato contato) {
		dao.removerContato(contato);
		contatos = dao.listarContatos();
	}

	public void atualizarContato(Contato contato) {
		dao.atualizarContato(contato);
		contatos = dao.listarContatos();

	}

}
