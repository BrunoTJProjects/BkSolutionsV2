package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import br.com.bksolutionsdomotica.manager.UserHardwares;
import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.SocketBase;

public class MyServerBk implements ServerCoreBK.InterfaceCommand {

	private ServerCoreBK server;
	private static HashMap<String, UserHardwares> userHardwares = new HashMap<String, UserHardwares>();

	public MyServerBk(int port) {
		server = new ServerCoreBK(port, this);
		try {
			server.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cliente onRequestSignIn(SocketBase socketBase) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente onRequestSignOut(SocketBase socketBase) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRequestDisconnectSocket(SocketBase socketBase) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCommandReceveived(SocketBase socketBase, String stringRecebida) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	
}