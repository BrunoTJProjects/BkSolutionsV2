import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaobd.BKClienteDAO;
import modelo.Cliente;

//@SuppressWarnings("unused")
public class ServerCoreBK {
	private int port;
	private boolean threadStart = true;
	private ServerSocket serverSocket;
	private volatile ServerCore serverCore;
	private volatile List<SocketCliente> socketClientes;

	public ServerCoreBK(int port) {
		this.port = port;
		serverCore = new ServerCore();
		socketClientes = new ArrayList<SocketCliente>();
	}
	
	public void init() throws IOException {
		
		serverCore.start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				Socket socket = null;
				try {
					serverSocket = new ServerSocket(port);
					while (true) {
						socket = serverSocket.accept();
						onConnected(socket);
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Erro no Servidor > " + e.getMessage());
					try {
						socket.close();
					} catch (IOException e1) { 
						e1.printStackTrace();
					}
				}
			}
		}).start();
		
		System.out.println("Servidor ouvindo na porta " + port);

	}
	
	public void restartThreadServer() {
		threadStart = true;
		if(!serverCore.isAlive()) {
			serverCore = null;
			serverCore = new ServerCore();
			serverCore.start();
		}
	}
	
	public void stopThread() {
		threadStart = false;
		while(serverCore.isAlive()) {
			
		}
		return;
	}
	
	public Cliente clienteLogado(String email, String senha) throws ClassNotFoundException, SQLException {
		Cliente cliente = null;
		BKClienteDAO clienteDAO = new BKClienteDAO();
		cliente = clienteDAO.logarCliente(email, senha);
		return cliente;
	}
	
	public void onConnected(Socket socket) throws IOException {
		SocketCliente sc = new SocketCliente(socket);
		socketClientes.add(sc);
//		new TimeOut(null, socket).start();
		System.out.println("cliente connectado/ Total: " + socketClientes.size());		
	}
	
	public void enviaComando(SocketCliente sc, String command) throws IOException {
		serverCore.enviaComando(sc, command);
	}

	public void setInterfaceConnectionListener(InterfaceCommand interfaceCommand) {
		serverCore.setInterfaceConnectionListener(interfaceCommand);
	}
	
	public void removeSocketCliente(SocketCliente sc) throws IOException {
		sc.closeResouces();
		socketClientes.remove(sc);
	}

	private class ServerCore extends Thread{
	
		private String comando;
		private SocketCliente sc;
		private InterfaceCommand interfaceCommand;

		public ServerCore() {

		}
		
		@Override
		public void run() {
			while(threadStart) {
				
//				try {
//					sleep(100);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				System.out.println("dentro do for" + socketClientes.size() + " sockets/ " + activeCount());
				
				List<SocketCliente> listTemporia = new ArrayList<SocketCliente>(socketClientes);
				
				for(SocketCliente sc: listTemporia) {
					try {
						runOnTime(sc);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		public void runOnTime(SocketCliente sc) throws ClassNotFoundException, SQLException {

			try {

				if (sc.getCliente() != null) {

					String string = sc.commandReceiver();
					
					if(string != null && !string.isEmpty()) {
						interfaceCommand.onCommandReceveived(sc, string);
					}
					
					enviarComando(sc);

				} else {
					Cliente cliente = interfaceCommand.onLoginRequest(sc);
					if (cliente != null) {
						sc.setCliente(cliente);
					} else {
						interfaceCommand.removeSocket(sc);
					}
				}

			} catch (IOException e) {

				e.printStackTrace();
			}			

		}
		
		private void enviarComando(SocketCliente sc) throws IOException {
			if (comando != null && !comando.isEmpty()) {
				if (this.sc != null && sc != null) {
					if (this.sc.equals(sc)) {
						sc.sendCommand(comando);
						comando = null;
						this.sc = null;
					}
				}
			}
		}

		public void enviaComando(SocketCliente sc, String command) throws IOException {
			if (sc != null && command != null && !command.isEmpty()) {
				comando = command;
				this.sc = sc;
//					enviarComando(sc);
			} else {
				comando = null;
				this.sc = null;
			}
		}

		public void setInterfaceConnectionListener(InterfaceCommand interfaceCommand) {
			this.interfaceCommand = interfaceCommand;
		}
		
	}
	
	public interface InterfaceCommand {

		public static final String ON_CONNECTED = "conexao_estabelecida";
		public static final String ON_LOGIN_REQUEST = "pedido_de_login";
		public static final String ON_COMMAND_RECEIVED = "comando_recebido";

		public Cliente onLoginRequest(SocketCliente socketCliente) throws ClassNotFoundException, SQLException, IOException;

		public void removeSocket(SocketCliente socketCliente) throws IOException;

		public void onCommandReceveived(SocketCliente socketCliente, String stringRecebida) throws IOException;

	}
}