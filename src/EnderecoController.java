
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class EnderecoController implements EnderecoDAO {
	private Connection conexao = null; // cria uma variavel conexao nula
	private Statement statement = null; // cria um statement vazio
	private ResultSet resultSet = null; // cria um resultSet vazio
	PreparedStatement preparedStatement = null; // cria um prepraredStatement vazio
	
	public EnderecoController() { // construtor do EnderecoController

		String DB_PATH  = "jdbc:mysql://localhost:3306/db2?user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&useOldAliasMetadataBehavior=true"; // caminho para acessar o banco no computador
		try { // try server para verificar se existe algum erro no if
			
		    if (conexao == null) { // caso a conexao for nula ele cria uma conexao nova com 
		    	// o caminho que tá em DB_PATH
		        conexao =  DriverManager.getConnection(DB_PATH);
		    }
		    
		} catch (SQLException e){ // caso exista algum erro no try, o catch imprimi quais foram os erros
			e.printStackTrace();
		}
	}


	@Override
	public Endereco buscarEndereco(int id /* parametro*/) { // funcao que vai procurar na tabela endereco a linha que tenha esse id,
		//pegar as informacoes dessa linha (ou seja as colunas da linha ) e colocar como atributos ( cada coluna da linha vira uma tributo de um objeto endereco )  
		
		Endereco enderecoVazio = null;

		String sql = "select * from endereco where id = " + id; // esse sql vai selecionar tudo da linha, que esta na tabela endereco,
		// que tivar o id igual a o id do parametro da funcao
		
		try {
			statement = conexao.createStatement(); // define um valor para o statement que antes era nulo
			resultSet = statement.executeQuery(sql); // cria um valor para o resultset que antes era nulo. Esse novo valor é um objeto
			// que da acesso as informacoes que foram selecionadas com o sql.
			
			while (resultSet.next()) {// esse while percorre pelas informacoes que esta dentro o resultset
				enderecoVazio = new Endereco(); // cria um objeto Endereco vazio que vai servir para armazenar 
				// as informacos que esta na linha que foi selecionada. Cada atributo desse objeto vai ser o valor de cada coluna da linha
				// que tenha o nome correspo
				enderecoVazio.setRua(resultSet.getString("rua")); // pega o valor da coluna rua e coloca no atributo rua do objeto vazio
				enderecoVazio.setBairro(resultSet.getString("bairro")); // mesma coisa 
				enderecoVazio.setCidade(resultSet.getString("cidade"));// mesma coisa
				enderecoVazio.setNumero(resultSet.getInt("numero")); // mesma coisa 
				enderecoVazio.setId(resultSet.getInt("id"));// mesma coisa 			
			}

		} catch (SQLException e) {
			e.printStackTrace(); // caso no try de algum erro, é imprimido pelo catch
		}finally { // esse finally e um bloco de codigo que sempre vai ser executado mesmo que exista erros
			
			try { // dentro desse try tem o fechamento do statement e o do resultSet para que nao tenha nenhum erros acesso simultaneos
				// ao banco
				statement.close();
				resultSet.close();
				
			} catch (Exception e2) {
				e2.printStackTrace(); // caso nao seja possivel fechar o statement ou o resultset, esse catch imprime e informa pq nao pode fechar 
			}
		}

		return enderecoVazio; // caso der tudo certo aquele objeto que antes era vazio, agora e retornado com os novos valores defindos iguais a linha selecionada na tabela
		
	}

	@Override
	public void inserirEndereco(Endereco endereco /*parametro*/) {
		String sql = "insert into endereco (rua, bairro, cidade, numero) values (?,?,?,?)"; // sql que sera primeiro modificada e depois executada  
		
		try {
			preparedStatement = conexao.prepareStatement(sql);
			// o preparedstatement primeiro e declarad o preparedStatement com o sql que vai ser modificada
			// a sql vai ser modifica a partir das interrogacoes
			preparedStatement.setString(1, endereco.getRua()); // modifica o valor da 1° interrogaca com o valor depois da virgula
			// nesse caso o valor e o valor da propriedade rua que vem do endereco no parametro
			preparedStatement.setString(2, endereco.getBairro()); //merma coisa
			preparedStatement.setString(3, endereco.getCidade());// merma coisa
			preparedStatement.setInt(4, endereco.getNumero()); // merma coisa
			
			preparedStatement.executeUpdate(); // depois das modificacoes o statement executa 
			
			// em resumo o prepared statement primeiro prepara a sql modificando e depois executa
			
		} catch (SQLException e) {
			e.printStackTrace(); // caso de um erro e imprimodo pelo catch
			
		}finally {// esse finally e um bloco de codigo que sempre vai ser executado mesmo que exista erros
			
			try { // dentro desse try tem o fechamento do preparedstatement para que nao tenha nenhum erros acesso simultaneos
				// ao banco
				preparedStatement.close(); // fecha o prepared statement
				
			}catch (SQLException e) { // caso nao seja possivel fechar o statement ou o resultset, esse catch imprime e informa pq nao pode fechar 
				e.printStackTrace();
			}
		}
	
	}

	@Override
	public void atualizarEndereco(Endereco end) { // funcao vai receber um endereco com as informacoes que vai modificar o linha da tabela com o id correspondente
		String sql = "update endereco set rua = ?, bairro = ?, cidade = ?, numero = ? where id = " + end.getId(); // sql que sera primeiro modificada e depois executada
		
		// o preparedstatement primeiro e declarad o preparedStatement com o sql que vai ser modificada
		
		// a sql vai ser modifica a partir das interrogacoes
		try {
			preparedStatement = conexao.prepareStatement(sql); 
			// o preparedstatement primeiro e declarad o preparedStatement com o sql que vai ser modificada
			// a sql vai ser modifica a partir das interrogacoes
			preparedStatement.setString(1, end.getRua());// modifica o valor da 1° interrogaca com o valor depois da virgula para substituir o antigo 
			preparedStatement.setString(2, end.getBairro()); // merma coisa
			preparedStatement.setString(3, end.getCidade());//merma coisa
			preparedStatement.setInt(4, end.getNumero());// merma coisa
			preparedStatement.executeUpdate(); // atualiza as informacoes no banco 

	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {// esse finally e um bloco de codigo que sempre vai ser executado mesmo que exista erros
			try {// dentro desse try tem o fechamento do perparedstatement para que nao tenha nenhum erros acesso simultaneos
				// ao banco
				preparedStatement.close();// fecha o prepared statement
			}catch (SQLException e) { // caso nao seja possivel fechar o statement ou o resultset, esse catch imprime e informa pq nao pode fechar 
				e.printStackTrace();
			}
		}
	}

	@Override
	public void removerEndereco(Endereco endereco) {
		String sql = "delete from endereco where id = " + endereco.getId();// essa codigo sql vai deletar da tabela endereco a linha que tiver
		// o id igual ao id do endereco que vem como parametro da funcao
		
		try { // bloco de try verifica se existe algum erro
			
			statement = conexao.createStatement(); // e definido o statement
			statement.execute(sql); // executa o sql
			// Assim o obj endereco que tiver o meso id de uma linha no banco, vai ser excluido essa linha da tabela de enderecos
			
		} catch (SQLException e) { // caso exista algum erro e impresso pelo catch
			
			e.printStackTrace();
		}finally { // esse finally e um bloco de codigo que sempre vai ser executado mesmo que exista erros
			try {// dentro desse try tem o fechamento do statement para que nao tenha nenhum erros acesso simultaneos
				// ao banco
				statement.close();// 
			}catch (SQLException e) {  // caso nao seja possivel fechar s statement imprime o erro
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<Endereco> listarEnderecos() { // funcao retorna um array list de Enderecos
		
		ArrayList <Endereco> enderecos = new ArrayList <Endereco>(); // arrayList que vai armazenar os enderecos

		String sql = "select * from endereco"; // essa sql seleciona todas as linhas da tabale endereco 

		try {
			statement = conexao.createStatement(); // define o valor do statement
			resultSet = statement.executeQuery(sql); // define o valor do resultset, que e o que retorna do sql
			// logo o resultset armazena toda a tabela
			while (resultSet.next()) { // esse while vai percorrer toda tabela ( que ta no resultset )
				// como o resultset tem varias linha entao cada repeticao do while e um acesso a uma das linhas
				
				Endereco endereco = new Endereco(); // cria um objeto endereco vasio
				endereco.setRua(resultSet.getString("rua")); // pega o valor da coluna rua e coloca na propriedade rua que esta em endereco vazio
				endereco.setBairro(resultSet.getString("bairro")); // mermo coisa
				endereco.setCidade(resultSet.getString("cidade"));//merma coisa
				endereco.setNumero(resultSet.getInt("numero"));// merma coisa
				endereco.setId(resultSet.getInt("id"));// merma coisa
				
				//depois de construir todo o objeto endereco ele adiciona dentro do array list  
				enderecos.add(endereco);
				
				// depois disso, o loop vai pra segunda linha da tabela, isso vai ate a ultima 
			}
			// serve para evitar o erro de inserção simultânea 
			
		} catch (SQLException e) { // caso exista algum erro e impresso pelo catch
			e.printStackTrace();
		}finally {// esse finally e um bloco de codigo que sempre vai ser executado mesmo que exista erros
			try {// dentro desse try tem o fechamento do statement e resultset para que nao tenha nenhum erros acesso simultaneos
				// ao banco
				statement.close();
				resultSet.close();
			}catch (SQLException e) {// caso nao seja possivel fechar s statement imprime o erro
				e.printStackTrace();
			}
		}

		return enderecos; // depois de que o loop acabar ele retorna o arrayList de endereco
	}


}
