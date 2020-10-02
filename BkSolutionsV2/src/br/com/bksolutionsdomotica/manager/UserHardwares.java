package br.com.bksolutionsdomotica.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bksolutionsdomotica.servidor.SocketCliente;

public class UserHardwares {

	private List<SocketCliente> clientesUser = new ArrayList<SocketCliente>();  //guarda a lista de Buffered Writers conectados ao Servidor
	private HashMap<String, JSONHardware> clientesHardware = new HashMap<String, JSONHardware>(); //guarda a lista de Jsons e Buffereds Writers organizados pelo MAC das Placas

//	public List<SocketCliente> getClientesUser() {
//		return clientesUser;
//	}
//
//	public void setClientesUser(List<SocketCliente> clientesUser) {
//		this.clientesUser = clientesUser;
//	}
//
	public HashMap<String, JSONHardware> getHardwares() {
		return clientesHardware;
	}
//
//	public void setClientesHardware(HashMap<String, JSONHardware> clientesHardware) {
//		this.clientesHardware = clientesHardware;
//	}
	
	public boolean containsClienteUser(SocketCliente socketCliente) {
			return clientesUser.contains(socketCliente);
	}
	
	public void addClienteUser(SocketCliente socketCliente) {
			clientesUser.add(socketCliente);
	}
	
	public void removeClienteUser(SocketCliente socketCliente) {
			clientesUser.remove(socketCliente);
	}
	
}