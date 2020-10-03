package br.com.bksolutionsdomotica.manager;

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

	public void addUserApp(Cliente cliente) {
		if (!clientes.contains(cliente)) {
			clientes.add(cliente);
		}
	}

	public void removeUserApp(Cliente cliente) {
		clientes.remove(cliente);
	}
	
	public void addUserHard(Hardware hardware) throws ClassNotFoundException, SQLException {
		if(!hardwares.containsKey(hardware)) {
			hardwares.put(hardware, hardware.getChaves());
		}
}

}