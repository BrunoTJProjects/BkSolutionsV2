package br.com.bksolutionsdomotica.conexaobd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.modelo.Hardware;

public class BKHardwareDAO {

	public BKHardwareDAO() {

	}

//	public synchronized List<Hardware> getHardwares() throws SQLException, ClassNotFoundException {
//		Hardware hardware = null;
//		List<Hardware> hardwares = new ArrayList<Hardware>();
//		BKModeloDAO modeloDAO = new BKModeloDAO();
//		PreparedStatement ps = null;
//		Connection conexao = MysqlConnection.getConnection();
//		ps = conexao.prepareStatement("SELECT * FROM bk_solutions.hardware");
//		ResultSet rs = null;
//		rs = ps.executeQuery();
//		while (rs.next()) {
//			hardware = new Hardware(rs.getString("hardware_mac"), modeloDAO.getModelo(rs.getInt("modelo_modelo_id")));
//			hardwares.add(hardware);
//		}
//		ps.close();
//		conexao.close();
//		return hardwares;
//	}

	public synchronized Hardware getHardware(String mac, String serial) throws SQLException, ClassNotFoundException {
		Hardware hardware = null;
		BKModeloDAO modeloDAO = new BKModeloDAO();
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement("SELECT * FROM bk_solutions.hardware WHERE hardware_mac = ? AND harware_serial = ?");
		ps.setString(1, mac);
		ps.setString(2, serial);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			hardware = new Hardware(rs.getString("hardware_mac"), modeloDAO.getModelo(rs.getInt("modelo_modelo_id")));
		}
		ps.close();
		conexao.close();
		return hardware;
	}

//	public synchronized int inserirHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
//		int linhasafetadas = 0;
//		PreparedStatement ps = null;
//		Connection conexao = MysqlConnection.getConnection();
//		ps = conexao.prepareStatement("INSERT INTO bk_solutions.hardware (hardware_mac, modelo_modelo_id) VALUES(?,?)");
//		ps.setString(1, hardware.getMac());
//		ps.setInt(2, hardware.getModelo().getId());
//		linhasafetadas = ps.executeUpdate();
//		ps.close();
//		conexao.close();
//		return linhasafetadas;
//	}

//	public synchronized int excluirHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
//		int linhasafetadas = 0;
//		PreparedStatement ps = null;
//		Connection conexao = MysqlConnection.getConnection();
//		ps = (PreparedStatement) conexao.prepareStatement("delete from bk_solutions.hardware where hardware_mac = ?");
//		ps.setString(1, hardware.getMac());
//		linhasafetadas = ps.executeUpdate();
//		ps.close();
//		conexao.close();
//		return linhasafetadas;
//	}

//	public synchronized int atualizarHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
//		int linhasafetadas = 0;
//		PreparedStatement ps = null;
//		Connection conexao = MysqlConnection.getConnection();
//		ps = (PreparedStatement) conexao
//				.prepareStatement("UPDATE bk_solutions.hardware SET modelo_modelo_id = ? WHERE (hardware_mac = ?)");
//		ps.setInt(1, hardware.getModelo().getId());
//		ps.setString(2, hardware.getMac());
//		linhasafetadas = ps.executeUpdate();
//		ps.close();
//		conexao.close();
//		return linhasafetadas;
//	}
	
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
	
	public synchronized int getCliente(Hardware hardware) throws ClassNotFoundException, SQLException {
		int id_cliente = 0;
		PreparedStatement ps = null;
		Connection conexao = MysqlConnection.getConnection();
		ps = conexao.prepareStatement(
				"SELECT DISTINCT clientes_cliente_id FROM bk_solutions.clientes_has_hardware WHERE hardware_hardware_mac = ?");
		ps.setString(1, hardware.getMac());
		ResultSet rs = null;
		rs = ps.executeQuery();
		if (rs.first()) {
			id_cliente = rs.getInt("clientes_cliente_id");
		}
		ps.close();
		conexao.close();
		return id_cliente;
	}
}
