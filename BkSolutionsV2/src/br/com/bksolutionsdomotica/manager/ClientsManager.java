package br.com.bksolutionsdomotica.manager;

import java.sql.SQLException;
import java.util.HashMap;

import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.Hardware;
import br.com.bksolutionsdomotica.servidor.MyServerBk;

public class ClientsManager {

	private static HashMap<Integer, UserHardwares> userHardwares;

	public ClientsManager() {
		userHardwares = new HashMap<Integer, UserHardwares>();
	}

	public void addCliente(Cliente cliente) {
		if (cliente == null)
			return;
		int id = cliente.getId();
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addClientes(cliente);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			userHard.addClientes(cliente);
		}
	}

	public void removeCliente(Cliente cliente) {
		if (cliente == null)
			return;
		int id = cliente.getId();
		if (userHardwares.containsKey(id)) {
			UserHardwares userHard = userHardwares.get(id);
			if (userHard.contemCliente(cliente)) {
				userHard.removeCliente(cliente);
				System.out.println("Cliente existe e foi removido");
				if (userHard.naoContemClientes()) {
					System.out.println("Esse fui o ultimo cliente");
					if (userHard.naoContemHardwares()) {
						userHardwares.remove(id);
						System.out.println(
								"Nenhum hardware guardado também e " + "por isso foi eliminado esse userHardware");
						System.out.println(userHardwares.size());
					}
				}
			}
		}
	}

	public void addHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
		if (hardware == null)
			return;
		int id = MyServerBk.getCodCliente(hardware);
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addHardware(hardware);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			userHard.addHardware(hardware);
		}
	}

	public void removeHardware(Hardware hardware) throws ClassNotFoundException, SQLException {
		if (hardware == null)
			return;
		int id = MyServerBk.getCodCliente(hardware);
		if (userHardwares.containsKey(id)) {
			UserHardwares userHard = userHardwares.get(id);
			if (userHard.contemHardware(hardware)) {
				hardware.setChaves(userHard.getJSONChaves(hardware));
				userHard.removeHardware(hardware);
				if (userHard.naoContemHardwares() && userHard.naoContemClientes()) {
					userHardwares.remove(id);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String string = new String(userHardwares.toString());
		return string;
	}

}
