package br.com.bksolutionsdomotica.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.Hardware;

public class UserHardwares {

	private List<Cliente> clientes = new ArrayList<Cliente>();
	private HashMap<Hardware, JSONObject> hardwares = new HashMap<Hardware, JSONObject>();
	
	

	public void onClienteCommand(Cliente cliente, Hardware hardware, JSONObject comando) throws IOException {
		for (Cliente c : clientes) {
			if (c == cliente)
				continue;
			c.sendCommand(comando.toString());
		}

		hardware.sendCommand(comando.toString());

	}

	public void onHardwareCommand(Hardware hardware, JSONObject comando) throws IOException {
		for (Cliente c : clientes) {
			c.sendCommand(comando.toString());
		}


	}

	public void addClientes(Cliente cliente) {
		if (!clientes.contains(cliente)) {
			clientes.add(cliente);
		}
	}

	public void removeCliente(Cliente cliente) {
		if (clientes.contains(cliente)) {
			clientes.remove(cliente);
		}
	}

	public boolean contemCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}

	public boolean naoContemClientes() {
		return clientes.isEmpty();
	}

	public void addHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
		if (!hardwares.containsKey(hardware)) {
			hardwares.put(hardware, hardware.getChaves());
		}
	}

	public void removeHardware(Hardware hardware) {
		if (hardwares.containsKey(hardware)) {
			hardwares.remove(hardware);
		}
	}

	public boolean contemHardware(Hardware hardware) {
		return hardwares.containsKey(hardware);
	}

	public boolean naoContemHardwares() {
		return hardwares.isEmpty();
	}

	public JSONObject getJSONChaves(Hardware hardware) {
		return hardwares.get(hardware);

	}

	@Override
	public String toString() {
		return "Numero de clientes: " + clientes.size() + "/ Numero de Hardwares: " + hardwares.size();
	}

}