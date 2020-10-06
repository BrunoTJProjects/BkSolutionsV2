package br.com.bksolutionsdomotica.conexaobd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bksolutionsdomotica.conexaobd.MysqlConnection;
import br.com.bksolutionsdomotica.modelo.Modelo;

public class BKModeloDAO {

	public BKModeloDAO() {

	}

	public synchronized List<Modelo> getModelos() throws SQLException, ClassNotFoundException {
		Modelo modelo = null;
		List<Modelo> modelos = new ArrayList<Modelo>();
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement("SELECT * FROM bk_solutions.modelo");
		ResultSet rs = null;
		rs = ps.executeQuery();
		while (rs.next()) {
			modelo = new Modelo(rs.getInt("modelo_id"), rs.getString("modelo_nome"));
			modelos.add(modelo);
		}
		ps.close();
		conexao.close();
		return modelos;
	}

	public synchronized Modelo getModelo(int id) throws SQLException, ClassNotFoundException {
		Modelo modelo = null;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement("SELECT * FROM bk_solutions.modelo WHERE modelo_id = ?");
		ps.setInt(1, id);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			modelo = new Modelo(rs.getInt("modelo_id"), rs.getString("modelo_nome"));
		}
		ps.close();
		conexao.close();
		return modelo;
	}

	public synchronized int inserirModelo(Modelo modelo) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement("INSERT INTO bk_solutions.modelo" + " (modelo_nome) VALUES (?)");
		ps.setString(1, modelo.getNome());
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}

	public synchronized int excluirModelo(Modelo modelo) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao.prepareStatement("delete from bk_solutions.modelo where modelo_id = ?");
		ps.setInt(1, modelo.getId());
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}

	public synchronized int atualizarModelo(Modelo modelo) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao
				.prepareStatement("UPDATE bk_solutions.modelo SET modelo_nome = ? WHERE (modelo_id = ?)");
		ps.setString(1, modelo.getNome());
		ps.setInt(2, modelo.getId());
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}
}
