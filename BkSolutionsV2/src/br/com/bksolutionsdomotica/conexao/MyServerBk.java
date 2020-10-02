package br.com.bksolutionsdomotica.conexao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import comunicacao.UserHardwares;
import modelo.Cliente;

public class MyServerBk implements ServerCoreBK.InterfaceCommand {
	
	private ServerCoreBK server;
	private static HashMap<String, UserHardwares> userHardwares = new HashMap<String, UserHardwares>();

	
	
	
	public MyServerBk(int port) {
		server = new ServerCoreBK(port);
		server.setInterfaceConnectionListener(this);
		try {
			server.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	@Override
	public Cliente onRequestSignIn(SocketCliente socketCliente)
			throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Solicitaçao de Login");
		Cliente cliente = null;
		cliente = server.clienteLogado("bruno.melo@tcm10.com.br", "8aB1yGj4");
		server.enviaComando(socketCliente, "Comando para enviado de: " + cliente.getNome());
		return cliente;
	}
	
	

	@Override
	public Cliente onRequestSignOut(SocketCliente socketCliente)
			throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void onRequestDisconnectSocket(SocketCliente socketCliente) throws IOException {
		// TODO Auto-generated method stub

	}
	
	

	@Override
	public void onCommandReceveived(SocketCliente sc, String stringRecebida) throws IOException {
		System.out.println("Cliente: " + sc.getCliente() + "/ comando recebido: " + stringRecebida);
		server.enviaComando(sc, stringRecebida);
	}
	
	

	public static HashMap<String, UserHardwares> getUserHardwares() {
		return userHardwares;
	}
	
	

	public static void setUserHardwares(HashMap<String, UserHardwares> userHardwares) {
		MyServerBk.userHardwares = userHardwares;
	}
	
	

	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	private void userGetJSONHardwares(SocketCliente socketCliente) throws ClassNotFoundException, SQLException {
		int id = socketCliente.getCliente().getId();

		if (!socketCliente.isHarware()) {

			if (!userHardwares.containsKey(id)) {
				UserHardwares userHard = new UserHardwares();
				userHard.addClienteUser(socketCliente);
				userHardwares.put(String.valueOf(id), userHard);
			} else {
				UserHardwares userHard = userHardwares.get(id);
				if (!userHard.containsClienteUser(socketCliente)) {
					userHard.addClienteUser(socketCliente);
				}
			}
		} else {
//			if (!userHardwares.containsKey(id)) {
//				UserHardwares users = new UserHardwares();
//				users.getHardwares().put("Serial desse Hardware", new JSONHardware(output, hardware.getChaves()));
//				userHardwares.put(id, users);
//			} else {
//				UserHardwares userHard = userHardwares.get(id);
//				if (!userHard.getHardwares().containsKey(id_hardware)) {
//					userHard.getHardwares().put(id_hardware, new JSONHardware(output, hardware.getChaves()));
//				} else {
//					userHard.getHardwares().get(id_hardware).setHardwareInput(output);
//				}
//			}

		}
	}
}