package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.BKClienteDAO;
import br.com.bksolutionsdomotica.modelo.Cliente;

public class ServerCoreBK {
	private int port;
	private boolean threadStart = true;
	private ServerSocket serverSocket;
	private volatile ServerCore serverCore;
	private volatile List<SocketCliente> socketClientes;

	public ServerCoreBK(int port, InterfaceCommand listener) {
		this.port = port;
		serverCore = new ServerCore();
		serverCore.setInterfaceConnectionListener(listener);
		socketClientes = new ArrayList<SocketCliente>();
	}

	public void init() throws IOException {

		serverCore.start();
		new Thread(new ServidorBK(serverSocket, port, socketClientes)).start();
	}

	public void restartThreadServer() {
		threadStart = true;
		if (!serverCore.isAlive()) {
			serverCore = null;
			serverCore = new ServerCore();
			serverCore.start();
		}
	}

	public void stopThread() {
		threadStart = false;
		while (serverCore.isAlive()) {

		}
		return;
	}

	public Cliente clienteLogado(String email, String senha) throws ClassNotFoundException, SQLException {
		Cliente cliente = null;
		BKClienteDAO clienteDAO = new BKClienteDAO();
		cliente = clienteDAO.logarCliente(email, senha);
		return cliente;
	}

	public void enviaComando(SocketCliente sc, String command) throws IOException {
		if (sc != null && command != null && !command.isEmpty()) {
			serverCore.comando = command;
			serverCore.sc = sc;
		} else {
			serverCore.comando = null;
			serverCore.sc = null;
		}
	}

	public void removeSocketCliente(SocketCliente sc) throws IOException {
		sc.closeResouces();
		socketClientes.remove(sc);
	}

	private class ServerCore extends Thread {
		private String comando;
		private SocketCliente sc;
		private InterfaceCommand interfaceCommand;

		public ServerCore() {

		}

		@Override
		public void run() {
			while (threadStart) {

				List<SocketCliente> listaTemporaria = new ArrayList<SocketCliente>(socketClientes);

				for (SocketCliente sc : listaTemporaria) {
					try {
						runOnce(sc);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void runOnce(SocketCliente sc) throws ClassNotFoundException, SQLException {

			try {
				String string = sc.commandReceiver();

				if (string != null && !string.isEmpty()) {

					JSONObject jsonObject;

					if (JSONObject.isJSONValid(string)) {
						jsonObject = new JSONObject(string);
						String tipo = jsonObject.getJSONObject("requisicao").getString("tipo");

						switch (tipo) {

						case "login":
							sc.setCliente(interfaceCommand.onRequestSignIn(sc));
							break;
						case "Logout":
							interfaceCommand.onRequestSignOut(sc);
							break;
						case "Desconectar":
							interfaceCommand.onRequestDisconnectSocket(sc);
							break;
						default:
							interfaceCommand.onCommandReceveived(sc, jsonObject.toString());
						}
					}
				}
				enviarComando(sc);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		private void enviarComando(SocketCliente sc) throws IOException {
			if (comando != null && !comando.isEmpty()) {
				if (this.sc != null && sc != null) {
					if (this.sc.equals(sc)) {
						sc.sendCommand(comando);
						this.comando = null;
						this.sc = null;
					}
				}
			}
		}

		public void setInterfaceConnectionListener(InterfaceCommand interfaceCommand) {
			this.interfaceCommand = interfaceCommand;
		}

	}

	public interface InterfaceCommand {

		public Cliente onRequestSignIn(SocketCliente socketCliente)
				throws ClassNotFoundException, SQLException, IOException;

		public Cliente onRequestSignOut(SocketCliente socketCliente)
				throws ClassNotFoundException, SQLException, IOException;

		public void onRequestDisconnectSocket(SocketCliente socketCliente) throws IOException;

		public void onCommandReceveived(SocketCliente socketCliente, String stringRecebida) throws IOException;

	}
}