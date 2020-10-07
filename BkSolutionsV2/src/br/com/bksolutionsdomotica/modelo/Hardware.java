package br.com.bksolutionsdomotica.modelo;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.DataAccess;

public class Hardware {
	private String mac;
	private String serial;
	private Modelo modelo;
	private static DataAccess dataAccess;
	private SocketBase socketBase;

	public Hardware(String mac, String serial, Modelo modelo) {
		this.mac = mac;
		this.serial = serial;
		this.modelo = modelo;
		if(dataAccess == null) {
			dataAccess = new DataAccess();
		}
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public synchronized JSONObject getChaves() throws SQLException, ClassNotFoundException {
		JSONObject dados = dataAccess.getChaves(this);
		return dados;
	}

	public synchronized JSONObject getChaves(String nada) throws SQLException, ClassNotFoundException {
		JSONObject dados = dataAccess.getChaves(this);
		return dados;
	}

	public synchronized int setChaves(JSONObject jsonObj) throws ClassNotFoundException, SQLException {
		int linhasafetadas = dataAccess.setChaves(this, jsonObj);
		return linhasafetadas;
	}

	public synchronized String getChave(String chave) throws SQLException, ClassNotFoundException {
		String valor = dataAccess.getChave(this, chave);
		return valor;
	}

	public synchronized int setChave(String chave, String valor) throws ClassNotFoundException, SQLException {
		int linhasafetadas = dataAccess.setChave(this, chave, valor);
		return linhasafetadas;
	}

	public void closeResouces() throws IOException {
		socketBase.closeResouces();
	}

	public void sendCommand(String comando) throws IOException {
		socketBase.sendCommand(comando);
	}

	public String commandReceiver() throws IOException {
		return socketBase.commandReceiver();
	}
	
	public void setSocketBase(SocketBase socketBase) {
		this.socketBase = socketBase;
	}

	public String toString() {
		String info = null;
		info = "MAC: " + mac + " MODELO: " + modelo.getNome() + " SENHA: " + serial;
		return info;
	}
}
