package br.com.bksolutionsdomotica.conexaobd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.Hardware;

public class BKClienteDAO{

	public BKClienteDAO() {

	}

	public synchronized Cliente logarCliente(String email, String password)
			throws SQLException, ClassNotFoundException {
		Cliente cliente = null;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT * FROM bk_solutions.clientes WHERE cliente_email = ? AND cliente_password =?");
		ps.setString(1, email);
		ps.setString(2, password);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("cliente_nome"), rs.getString("cliente_sexo"),
					rs.getDate("cliente_nasc"), rs.getString("cliente_rua"), rs.getString("cliente_numero"),
					rs.getString("cliente_bairro"), rs.getString("cliente_cidade"), rs.getString("cliente_estado"),
					rs.getString("cliente_cpf"), rs.getString("cliente_email"), rs.getString("cliente_password"));
		}
		ps.close();
		conexao.close();
		return cliente;
	}

	public synchronized Cliente getCliente(int id) throws SQLException, ClassNotFoundException {
		Cliente cliente = null;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement("SELECT * FROM bk_solutions.clientes WHERE cliente_id = ?");
		ps.setInt(1, id);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			cliente = new Cliente(rs.getInt(id), rs.getString("cliente_nome"), rs.getString("cliente_sexo"),
					rs.getDate("cliente_nasc"), rs.getString("cliente_rua"), rs.getString("cliente_numero"),
					rs.getString("cliente_bairro"), rs.getString("cliente_cidade"), rs.getString("cliente_estado"),
					rs.getString("cliente_cpf"), rs.getString("cliente_email"), rs.getString("cliente_password"));
		}
		ps.close();
		conexao.close();
		return cliente;
	}

	public synchronized int inserirCliente(Cliente cliente) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"INSERT INTO bk_solutions.clientes" + " (cliente_nome, cliente_sexo, " + "cliente_nasc, cliente_rua, "
						+ "cliente_numero, cliente_bairro, " + "cliente_cidade, cliente_estado, "
						+ "cliente_cpf, cliente_email, " + "cliente_password) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
		ps.setString(1, cliente.getNome());
		ps.setString(2, cliente.getSexo());
		ps.setDate(3, cliente.getNasc());
		ps.setString(4, cliente.getRua());
		ps.setString(5, cliente.getNumero());
		ps.setString(6, cliente.getBairro());
		ps.setString(7, cliente.getCidade());
		ps.setString(8, cliente.getEstado());
		ps.setString(9, cliente.getCpf());
		ps.setString(10, cliente.getEmail());
		ps.setString(11, cliente.getPassword());
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}

	public synchronized int excluirCliente(Cliente cliente) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao.prepareStatement("delete from bk_solutions.clientes where cliente_id = ?");
		ps.setInt(1, cliente.getId());
		linhasafetadas = ps.executeUpdate();
		ps.close();
		conexao.close();
		return linhasafetadas;
	}

	public synchronized int atualizarCliente(Cliente cliente) throws ClassNotFoundException, SQLException {
		int linhasafetadas = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = (PreparedStatement) conexao.prepareStatement(
				"UPDATE bk_solutions.clientes SET cliente_nome = ?, cliente_sexo = ?, cliente_nasc = ?,"
						+ " cliente_rua = ?, cliente_numero = ?, cliente_bairro = ?, cliente_cidade = ?, cliente_estado = ?,"
						+ " cliente_cpf = ?, cliente_email = ?, cliente_password = ? WHERE (cliente_id = ?)");
		ps.setString(1, cliente.getNome());
		ps.setString(2, cliente.getSexo());
		ps.setDate(3, cliente.getNasc());
		ps.setString(4, cliente.getRua());
		ps.setString(5, cliente.getNumero());
		ps.setString(6, cliente.getBairro());
		ps.setString(7, cliente.getCidade());
		ps.setString(8, cliente.getEstado());
		ps.setString(9, cliente.getCpf());
		ps.setString(10, cliente.getEmail());
		ps.setString(11, cliente.getPassword());
		ps.setInt(12, cliente.getId());
		linhasafetadas = ps.executeUpdate();
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

	public synchronized List<Hardware> getHardwares(Cliente cliente) throws SQLException, ClassNotFoundException {
		BKHardwareDAO hardwareDAO = new BKHardwareDAO();
		Hardware hardware = null;
		List<Hardware> hardwares = new ArrayList<Hardware>();
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT DISTINCT hardware_hardware_mac FROM bk_solutions.clientes_has_hardware WHERE clientes_cliente_id = ?");
		ps.setInt(1, cliente.getId());
		ResultSet rs = null;
		rs = ps.executeQuery();
		while (rs.next()) {
			hardware = hardwareDAO.getHardware(rs.getString("hardware_hardware_mac"));
			hardwares.add(hardware);
		}
		ps.close();
		conexao.close();
		return hardwares;
	}
}
