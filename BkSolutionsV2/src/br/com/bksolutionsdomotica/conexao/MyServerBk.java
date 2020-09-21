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
		super();
		server = new ServerCoreBK(port);
		server.setInterfaceConnectionListener(this);
		try {
			server.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cliente onSignIn(SocketCliente socketCliente) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Solicitaçao de Login");
		Cliente cliente = null;
		cliente = server.clienteLogado("bruno.melo@tcm10.com.br", "8aB1yGj4");
		server.enviaComando(socketCliente, "Comando para enviado de: " + cliente.getNome());
		return cliente;
	}

	@Override
	public Cliente onSignOut(SocketCliente socketCliente) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSocket(SocketCliente socketCliente) throws IOException {
//		server.removeSocketCliente(socketCliente);
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
	
	private void userGetJSONHardwares(SocketCliente socketCliente) throws ClassNotFoundException, SQLException {

		if (!userHardwares.containsKey(socketCliente.getCliente().getId())) {
			UserHardwares users = new UserHardwares();
			users.getInputsClient().add(socketCliente.);
			userHardwares.put(id, users);

		} else {
			UserHardwares userHard = userHardwares.get(id);
			if (!userHard.getInputsClient().contains(output)) {
				userHard.getInputsClient().add(output);
			}
		}
	}

}
