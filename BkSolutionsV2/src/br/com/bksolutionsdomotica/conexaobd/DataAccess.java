package br.com.bksolutionsdomotica.conexaobd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.Hardware;

public class DataAccess {
	
	public DataAccess() {
		
	}
	
	public synchronized int excluirChave(Cliente cliente, Hardware hardware, String chave)
			throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao.prepareStatement("DELETE FROM bk_solutions.clientes_has_hardware "
				+ "WHERE (clientes_cliente_id = ? AND hardware_hardware_mac = ? AND chave = ?)");
		ps.setInt(1, cliente.getId());
		ps.setString(2, hardware.getMac());
		ps.setString(3, chave);
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}
	
	public synchronized JSONObject getChaves(Cliente cliente, Hardware hardware)
			throws SQLException, ClassNotFoundException {
		JSONObject jsonObj = new JSONObject();
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT chave, valor FROM bk_solutions.clientes_has_hardware WHERE clientes_cliente_id = ? AND hardware_hardware_mac = ?");
		ps.setInt(1, cliente.getId());
		ps.setString(2, hardware.getMac());
		ResultSet rs = null;
		rs = ps.executeQuery();
		while (rs.next()) {
			jsonObj.put(rs.getString("chave"), rs.getString("valor"));
		}
		ps.close();
		conexao.close();
		return jsonObj;
	}

	public synchronized JSONObject getChaves(Hardware hardware) throws SQLException, ClassNotFoundException {
		JSONObject jsonObj = new JSONObject();
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT chave, valor FROM bk_solutions.clientes_has_hardware WHERE hardware_hardware_mac = ?");
		ps.setString(1, hardware.getMac());
		ResultSet rs = null;
		rs = ps.executeQuery();
		while (rs.next()) {
			jsonObj.put(rs.getString("chave"), rs.getString("valor"));
		}
		ps.close();
		conexao.close();
		return jsonObj;
	}

	public synchronized int setChaves(Cliente cliente, Hardware hardware, JSONObject jsonObj)
			throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		int linhasafetadas = 0;

		for (String key : jsonObj.keySet()) {
			ps = null;
			ps = (PreparedStatement) conexao.prepareStatement("UPDATE bk_solutions.clientes_has_hardware SET valor = ? "
					+ "WHERE (clientes_cliente_id = ? AND hardware_hardware_mac = ? AND chave = ?)");
			ps.setString(1, jsonObj.getString(key));
			ps.setInt(2, cliente.getId());
			ps.setString(3, hardware.getMac());
			ps.setString(4, key);
			linhasafetadas += ps.executeUpdate();
		}
		ps.close();
		conexao.close();
		return linhasafetadas;
	}
	
	public synchronized int setChaves(Hardware hardware, JSONObject jsonObj)
			throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		int linhasafetadas = 0;

		for (String key : jsonObj.keySet()) {
			ps = null;
			ps = (PreparedStatement) conexao.prepareStatement("UPDATE bk_solutions.clientes_has_hardware SET valor = ? "
					+ "WHERE (hardware_hardware_mac = ? AND chave = ?)");
			ps.setString(1, jsonObj.getString(key));
			ps.setString(2, hardware.getMac());
			ps.setString(3, key);
			linhasafetadas += ps.executeUpdate();
		}
		ps.close();
		conexao.close();
		return linhasafetadas;
	}

	public synchronized String getChave(Cliente cliente, Hardware hardware, String chave)
			throws SQLException, ClassNotFoundException {
		String valor = null;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT valor FROM bk_solutions.clientes_has_hardware WHERE clientes_cliente_id = ? AND hardware_hardware_mac = ? AND chave = ?");
		ps.setInt(1, cliente.getId());
		ps.setString(2, hardware.getMac());
		ps.setString(3, chave);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			valor = rs.getString("valor");
		}
		ps.close();
		conexao.close();
		if (valor == null) {
			int linhasafetadas = 0;
			ps = null;
			conexao = MysqlConnection.getConnection();
			ps = conexao.prepareStatement(
					"INSERT INTO bk_solutions.clientes_has_hardware (clientes_cliente_id, hardware_hardware_mac, chave, valor) VALUES(?,?,?,?)");
			ps.setInt(1, cliente.getId());
			ps.setString(2, hardware.getMac());
			ps.setString(3, chave);
			ps.setString(4, "initial");
			linhasafetadas = ps.executeUpdate();
			ps.close();
			conexao.close();
			if (linhasafetadas != 0) {
				return getChave(cliente, hardware, chave);
			} else {
				return valor;
			}
		} else {
			return valor;
		}
	}
	
	public synchronized String getChave(Hardware hardware, String chave) throws SQLException, ClassNotFoundException {

		String dado = null;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT valor FROM bk_solutions.clientes_has_hardware WHERE hardware_hardware_mac = ? AND chave = ?");
		ps.setString(1, hardware.getMac());
		ps.setString(2, chave);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			dado = rs.getString("valor");
		}
		ps.close();
		conexao.close();
		return dado;
	}

	public synchronized int setChave(Cliente cliente, Hardware hardware, String chave, String valor)
			throws ClassNotFoundException, SQLException {

		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao.prepareStatement(
				"UPDATE bk_solutions.clientes_has_hardware SET valor = ? WHERE (clientes_cliente_id = ? AND hardware_hardware_mac = ? AND chave = ?)");
		ps.setString(1, valor);
		ps.setInt(2, cliente.getId());
		ps.setString(3, hardware.getMac());
		ps.setString(4, chave);
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}
	
	public synchronized int setChave(Hardware hardware, String chave, String valor)
			throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		Connection conexao = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		ps = (PreparedStatement) conexao.prepareStatement("UPDATE bk_solutions.clientes_has_hardware SET valor = ? "
				+ "WHERE (hardware_hardware_mac = ? AND chave = ?)");
		ps.setString(1, valor);
		ps.setString(2, hardware.getMac());
		ps.setString(3, chave);
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}
		
}
