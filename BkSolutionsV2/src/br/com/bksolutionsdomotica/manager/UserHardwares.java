package br.com.bksolutionsdomotica.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bksolutionsdomotica.servidor.SocketCliente;

public class UserHardwares {

	private List<SocketCliente> clientesUser = new ArrayList<SocketCliente>();
	private HashMap<SocketCliente, JSONHardware> clientesHardware = new HashMap<SocketCliente, JSONHardware>();

	public void addUserApp(SocketCliente socketCliente) {
		if (!clientesUser.contains(socketCliente)) {
			clientesUser.add(socketCliente);
		}
	}

	public void removeClienteUser(SocketCliente socketCliente) {
		clientesUser.remove(socketCliente);
	}
	
	public void addUserHard(SocketCliente socketCliente) {
		if(!clientesHardware.containsKey(socketCliente)) {
			clientesHardware.put(socketCliente, value)
		}
		clientesUser.add(socketCliente);
}

}