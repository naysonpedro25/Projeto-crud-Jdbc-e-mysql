

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AgendaContatos {
	private ContatoDAO contD = new ContatoController();
	private EnderecoDAO endD = new EnderecoController();
	private Scanner entrada;

	private Agenda agenda;
	private static Connection conexao;

	public AgendaContatos() {
		agenda = new Agenda();
		entrada = new Scanner(System.in);
	}

	public void menuOpcao() {
		System.out.println("1) listar Contatos");
		System.out.println("2) adicionar Contato");
		System.out.println("3) atualizar Contato");
		System.out.println("4) remover Contato");
		System.out.println("5) detalhar Contato");
		System.out.println("0) sair");
	}

	public void menu() {
		int opcao;
		while (true) {
			menuOpcao();
			opcao = lerNumero();
			if (opcao == 1)
				listarContatos();
			else if (opcao == 2)
				adicionarContato();
			else if (opcao == 3)
				atualizarContato();
			else if (opcao == 4)
				removerContato();
			else if (opcao == 5)
				contatoDetalhado();
			else if (opcao == 0)
				break;
			else
				System.out.println("\nOpção Inválida!\n");

		}

	}

	public void listarContatos() {

		if (agenda.listarContatos().isEmpty()) {
			System.out.println("\nNenhum contato foi inserido!\n");
		} else {
			System.out.println("ID NOME \tTELEFONE \tE-MAIL");
			for (Contato contato : agenda.listarContatos()) {
				System.out.print(contato.getId() + " ");
				System.out.print(contato.getNome() + " ");
				System.out.print(contato.getTelefone() + " ");
				System.out.println(contato.getEmail() + " ");

			}

			System.out.println();

		}

	}

	public void contatoDetalhado() {

		System.out.println("Digite o id do contato");
		int id = entrada.nextInt();
		Contato contato;
		Endereco endereco;

		contato = contD.buscarContato(id);
			
		if (contato == null) {
			System.out.println("\nContato nao encontrado!\n");
		} else {
			endereco = endD.buscarEndereco(contato.getEndereco().getId());

			System.out.println(">>> Informações do Contato");
			System.out.println("Nome: " + contato.getNome());
			System.out.println("Email: " + contato.getEmail());
			System.out.println("Telefone: " + contato.getTelefone());

			System.out.println(">>> Informações do Endereço");
			System.out.println("Nome da rua: " + endereco.getRua());
			System.out.println("Número: " + endereco.getNumero());
			System.out.println("Bairro: " + endereco.getBairro());
			System.out.println("Cidade: " + endereco.getCidade() + "\n");
		}

	}

	public void adicionarContato() {

		String nome, email, tell, rua, bairro, cidade;
		int numero;

		Endereco endereco;
		Contato contato;

		System.out.println("Digite as informações do contato");
		System.out.println("Nome:");
		nome = entrada.next();
		System.out.println("Email:");
		email = entrada.next();
		System.out.println("Telefone:");
		tell = entrada.next();

		System.out.println("Digite as informações do endereço");
		System.out.println("Nome da rua:");
		rua = entrada.next();
		System.out.println("Número:");
		numero = lerNumero();
		System.out.println("Bairro:");
		bairro = entrada.next();
		System.out.println("Cidade:");
		cidade = entrada.next();

		endereco = new Endereco(rua, numero, bairro, cidade);
		
		contato = new Contato(nome, email, tell, endereco);

		endD.inserirEndereco(endereco);
		contD.inserirContato(contato);

		System.out.println("Contato adicionado!");

	}

	public void removerContato() {

		System.out.println("Digite o id");
		int id = entrada.nextInt();

		Contato cont = contD.buscarContato(id);

		if (cont == null) {
			System.out.println("\nContato nao encotrado\n");
		} else {
			contD.removerContato(cont);
			
			System.out.println("Contato removido");
		}
	}

	public void atualizarContato() {

		System.out.println("Digite o id");
		int id = entrada.nextInt();

		Contato cnt = contD.buscarContato(id);

		if (cnt == null) {
			System.out.println("\nContato nao encontrado\n");
		} else {
			
			String nome, email, tell, rua, bairro, cidade;
			int numero;

			System.out.println("Digite as novas informações do contato");
			System.out.println("Nome:");
			nome = entrada.next();
			System.out.println("Email:");
			email = entrada.next();
			System.out.println("Telefone:");
			tell = entrada.next();

			System.out.println("Digite as novas informações do endereço");
			System.out.println("Nome da rua:");
			rua = entrada.next();
			System.out.println("Número:");
			numero = lerNumero();
			System.out.println("Bairro:");
			bairro = entrada.next();
			System.out.println("Cidade:");
			cidade = entrada.next();

			Endereco endereco = new Endereco(rua,numero,bairro,cidade);
			
			Contato contato = new Contato(nome,email,tell,endereco);
			
			endD.atualizarEndereco(endereco);
			contD.atualizarContato(contato);
			
			System.out.println("Contato Atualizado");
		}
	}

	public int lerNumero() {
		int num = -1;
		
		while (num == -1){
			try {
				num = entrada.nextInt();
			} catch (InputMismatchException e){
				System.out.println("Valor invalido!");
				entrada.nextLine(); // faz com que o seja ignorado o que o user digitou e vai para proxima linha
			}
		}
		return num;

	}

	public static void main(String[] args) {
		
		AgendaContatos agenda = new AgendaContatos();
		agenda.menu();

	}

}
