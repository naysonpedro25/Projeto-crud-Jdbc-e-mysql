
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContatoController implements ContatoDAO {
	
	private Connection conexao = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public ContatoController() { // essa parte e igual a enderecoController
		
		String DB_PATH = "jdbc:mysql://localhost:3306/db2?user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&useOldAliasMetadataBehavior=true";
				try {
		    if (conexao == null) {
		        conexao = DriverManager.getConnection(DB_PATH);
		    }

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public Contato buscarContato(int id /*Parametro*/) {  // funcao que vai procurar na tabela contato a linha que tenha esse id,
		//pegar as informacoes dessa linha (ou seja as colunas da linha ) e colocar como atributos ( cada coluna da linha vira uma tributo de um objeto endereco )  
		
		Contato contato = null; // contato nulo
		
		String sql = "select * from contato where id = " + id; // seleciona a linha da tabela contato que tenha o id igual ao paramentro da funcao
		
		try {
			statement = conexao.createStatement(); // defini um statement 
			resultSet = statement.executeQuery(sql); // resultset vai ter acesso a linha que foi selecionada no sql
			
			EnderecoDAO end = new EnderecoController(); // endereco controller q tem a funcao buscarEndereco 
			
			while (resultSet.next()) { // esse while vai percorres essa linha selecionada com o sql
				contato = new Contato(); // aqui define um contato vazio 
				contato.setNome(resultSet.getString("nome")); // pegando o valor da coluna nome que na na linha selecionada e colocando dentro da propriedade Nome que objt 
				contato.setEmail(resultSet.getString("email")); // merma coisa
				contato.setTelefone(resultSet.getString("telefone"));// merma coisa
				int chaveEstrangeira = resultSet.getInt("endereco");// aqui eu coloco dentro de uma variavel inteira o valor de id_endereco (chave estrangeira de contato)

				contato.setEndereco(end.buscarEndereco(chaveEstrangeira));// aqui, a partir da chaveEstrangeira eu chamo a funcao buscarEndereco ( que retorna um objt enderoco)
				
				//e passa na funcao buscarEndereco e retorna um objeto endereco 
				contato.setId(resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contato; // retorna o objeto contato ja modificado 
	}

	@Override
	public void inserirContato(Contato contato) {
		String sql = "insert into contato (nome,email, telefone, endereco) values (?,?,?,?)";
		String sql1 = "SELECT MAX(id) as id FROM endereco";
		PreparedStatement statement = null;
		ResultSet rs = null;
		int maiorId = 0;       
		
		try { //selecionar a tabela de maior id
			statement = conexao.prepareStatement(sql1);
			rs = statement.executeQuery();
			 if (rs.next()) {
	                maiorId = rs.getInt("id");
	            } else {
	                System.out.println("A tabela está vazia.");
	            }
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		try { // inserir um novo contato
			statement = conexao.prepareStatement(sql);
			statement.setString(1,contato.getNome());
			statement.setString(2, contato.getEmail());
			statement.setString(3, contato.getTelefone());
			statement.setInt(4, maiorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void atualizarContato(Contato cont) {
		String sql = "update contato set nome = ?, email = ?, telefone = ?, endereco = ? where id = " + cont.getId();
		PreparedStatement statement= null;
		try {
			statement = conexao.prepareStatement(sql); // prepara o sql para ser modificado
			statement.setString(1, cont.getNome());
			statement.setString(2, cont.getEmail());
			statement.setString(3, cont.getTelefone());
			statement.setInt(4,cont.getEndereco().getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				statement.close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void removerContato(Contato cont){
		String sql = "delete from contato where id = " + cont.getId();
		EnderecoDAO enderecoCrontroller = new EnderecoController();
		Statement statement = null;
		
		try {
			statement = conexao.createStatement();
			enderecoCrontroller.removerEndereco(cont.getEndereco()); // remove o endereco
			// a partir da chave estrangeira
			statement.execute(sql); // executa a sql que deleta o contato( já sem o endereco ) 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				statement.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<Contato> listarContatos() {
		ArrayList <Contato> conts = new ArrayList <Contato>();
		
		EnderecoDAO endD = new EnderecoController();
		
		String sql = "select * from contato";
		
		ResultSet rs;
		Statement statement;
		
		try {
			statement = conexao.createStatement();
			rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				Contato cont = new Contato();
				cont.setNome(rs.getString("nome"));// pega o valor da coluna nome
				cont.setEmail(rs.getString("email"));
				cont.setTelefone(rs.getString("telefone"));
				int idEstrangeiro = rs.getInt("endereco"); //
				Endereco endereco = endD.buscarEndereco(idEstrangeiro);
				cont.setEndereco(endereco);  
				cont.setId(rs.getInt("id"));
				
				conts.add(cont);
			}
	
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return conts;
	}

}
