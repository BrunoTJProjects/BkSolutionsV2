package br.com.bksolutionsdomotica.conexaobd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
	
	static Connection conexao = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		String driver = "com.mysql.jdbc.Driver";

		Class.forName(driver);

		String server = "localhost";
		String banco = "bk_solutions";
		String url = "jdbc:mysql://" + server + "/" + banco;// + "?autoReconnect=true&useSSL=false";

		String usuario = "root";
		String senha = "8aB1yGj4";

		conexao = DriverManager.getConnection(url, usuario, senha);
		
		return conexao;
	}
}
