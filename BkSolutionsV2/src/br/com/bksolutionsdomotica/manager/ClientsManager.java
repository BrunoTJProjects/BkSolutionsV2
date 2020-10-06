package br.com.bksolutionsdomotica.manager;

import java.util.HashMap;

import br.com.bksolutionsdomotica.conexaobd.dao.BKHardwareDAO;
import br.com.bksolutionsdomotica.modelo.Cliente;

public class ClientsManager {

	private static HashMap<Integer, UserHardwares> userHardwares;

	public ClientsManager() {
		userHardwares = new HashMap<Integer, UserHardwares>();
	}

	private void addUserApp(Cliente cliente) {
		int id = cliente.getId();
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addUserApp(cliente);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			if (!userHard.containsClienteUser(cliente)) {
				userHard.addUserApp(cliente);
			}
		}
	}
	
	private void addUserHard(Cliente cliente) {
		int id = cliente.getId();
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addUserApp(cliente);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			if (!userHard.containsClienteUser(cliente)) {
				userHard.addUserApp(cliente);
			}
		}
		
		
		
//		if (!userHardwares.containsKey(id)) {
//		UserHardwares users = new UserHardwares();
//		users.getHardwares().put("Serial desse Hardware", new JSONHardware(output, hardware.getChaves()));
//		userHardwares.put(id, users);
//	} else {
//		UserHardwares userHard = userHardwares.get(id);
//		if (!userHard.getHardwares().containsKey(id_hardware)) {
//			userHard.getHardwares().put(id_hardware, new JSONHardware(output, hardware.getChaves()));
//		} else {
//			userHard.getHardwares().get(id_hardware).setHardwareInput(output);
//		}
//	}
	}

	public void addCliente(SocketCliente sc) {
		if (sc.getCliente() != null) {
			if (!sc.isHarware()) {
				addUserApp(sc);
			} else {
				addUserHard(sc);
			}
		}
	}

}
