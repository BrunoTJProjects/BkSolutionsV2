package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.modelo.SocketBase;

public class MyServerBk implements ServerCoreBK.InterfaceCommand {

	private ServerCoreBK server;
//	private static HashMap<String, UserHardwares> userHardwares = new HashMap<String, UserHardwares>();

	public MyServerBk(int port) {
		server = new ServerCoreBK(port, this);
		try {
			server.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onHardwareSignIn(SocketBase socketBase, String login, String password)
			throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Login Hardware");
		
	}

	@Override
	public void onHardwareSignOut(SocketBase socketBase) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Logout Hardware");
	}

	@Override
	public void onHardwareCommand(SocketBase socketBase, JSONObject jsonObject) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("comm Hardware: " + jsonObject);
	}

	@Override
	public void onClienteSignIn(SocketBase socketBase, String login, String password)
			throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Login Cliente");
	}

	@Override
	public void onClienteSignOut(SocketBase socketBase) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Logout Cliente");
	}

	@Override
	public void onClienteCommand(SocketBase socketBase, JSONObject jsonObject) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("comm Cliente");
	}

}