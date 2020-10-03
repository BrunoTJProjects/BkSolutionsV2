package br.com.bksolutionsdomotica.manager;

import java.util.HashMap;

public class ClientsManager {

	private static HashMap<Integer, UserHardwares> userHardwares;

	public ClientsManager() {
		userHardwares = new HashMap<Integer, UserHardwares>();
	}

	private void addUserApp(SocketCliente sc) {
		int id = sc.getCliente().getId();
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addUserApp(sc);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			if (!userHard.containsClienteUser(sc)) {
				userHard.addUserApp(sc);
			}
		}
	}
	
	private void addUserHard(SocketCliente sc) {
		int id = sc.getCliente().getId();
		if (!userHardwares.containsKey(id)) {
			UserHardwares userHard = new UserHardwares();
			userHard.addUserApp(sc);
			userHardwares.put(id, userHard);
		} else {
			UserHardwares userHard = userHardwares.get(id);
			if (!userHard.containsClienteUser(sc)) {
				userHard.addUserApp(sc);
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
