package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.dao.BKHardwareDAO;
import br.com.bksolutionsdomotica.manager.ClientsManager;
import br.com.bksolutionsdomotica.modelo.Hardware;
import br.com.bksolutionsdomotica.modelo.SocketBase;

public class MyServerBk implements ServerCoreBK.InterfaceCommand {

	private ServerCoreBK server;
	private static ClientsManager gerenciador = new ClientsManager();

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
		Hardware.setBkHardwareDAO(new BKHardwareDAO());
		Hardware hardware = Hardware.getBkHardwareDAO().getHardware(login, password);
		if(hardware != null) {
			hardware.setSocketBase(socketBase);
			hardware.sendCommand(hardware.toString());	
		}		
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