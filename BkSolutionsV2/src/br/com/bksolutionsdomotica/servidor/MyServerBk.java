package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.BKHardwareDAO;
import br.com.bksolutionsdomotica.manager.UserHardwares;
import br.com.bksolutionsdomotica.modelo.Hardware;
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
	public void onRequestSignIn(SocketBase socketBase, String deviceType, String login, String password)
			throws ClassNotFoundException, SQLException, IOException {
		
			switch(deviceType) {
			
			case ServerCoreBK.TYPE_HARDWARE:
				
				BKHardwareDAO bkHardwareDAO = new BKHardwareDAO();
				Hardware hardware = bkHardwareDAO.getHardware(login, password);
				if(hardware != null) {
					hardware.setSocket(socketBase.getSocket());
				}
				
				
				
			}

		
	}

	@Override
	public void onRequestSignOut(SocketBase socketBase) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCommandReceveived(SocketBase socketBase, JSONObject jsonObject) throws IOException {
		// TODO Auto-generated method stub
		
	}

	

}