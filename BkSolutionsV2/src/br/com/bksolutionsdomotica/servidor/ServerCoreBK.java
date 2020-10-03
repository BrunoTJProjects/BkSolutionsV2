package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.BKClienteDAO;
import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.SocketBase;

public class ServerCoreBK {
	private int port;
	private boolean threadStart = true;
	private volatile ServerCore serverCore;
	private volatile List<SocketBase> socketsBase;

	public ServerCoreBK(int port, InterfaceCommand listener) {
		this.port = port;
		serverCore = new ServerCore();
		serverCore.setInterfaceConnectionListener(listener);
		socketsBase = new ArrayList<SocketBase>();
	}

	public void init() throws IOException {

		serverCore.start();
		new Thread(new ServidorBK(port, socketsBase)).start();
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

	public void enviaComando(SocketBase sb, String command) throws IOException {
		if (sb != null && command != null && !command.isEmpty()) {
			serverCore.comando = command;
			serverCore.sb = sb;
		} else {
			serverCore.comando = null;
			serverCore.sb = null;
		}
	}

	public void removeSocketBase(SocketBase sc) throws IOException {
		sc.closeResouces();
		socketsBase.remove(sc);
	}

	private class ServerCore extends Thread {
		private String comando;
		private SocketBase sb;
		private InterfaceCommand interfaceCommand;

		public ServerCore() {

		}

		@Override
		public void run() {
			while (threadStart) {

				List<SocketBase> listaTemporaria = new ArrayList<SocketBase>(socketsBase);

				for (SocketBase sb : listaTemporaria) {
					try {
						runOnce(sb);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void runOnce(SocketBase sb) throws ClassNotFoundException, SQLException {

			try {
				String string = sb.commandReceiver();

				if (string != null && !string.isEmpty()) {

					JSONObject jsonObject;

					if (JSONObject.isJSONValid(string)) {
						jsonObject = new JSONObject(string);
						String tipo = jsonObject.getJSONObject("requisicao").getString("tipo");

						switch (tipo) {

						case "login":
//							sb.setCliente(interfaceCommand.onRequestSignIn(sb));
							break;
						case "Logout":
							interfaceCommand.onRequestSignOut(sb);
							break;
						case "Desconectar":
							interfaceCommand.onRequestDisconnectSocket(sb);
							break;
						default:
							interfaceCommand.onCommandReceveived(sb, jsonObject.toString());
						}
					}
				}
				enviarComando(sb);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		private void enviarComando(SocketBase sb) throws IOException {
			if (comando != null && !comando.isEmpty()) {
				if (this.sb != null && sb != null) {
					if (this.sb.equals(sb)) {
						sb.sendCommand(comando);
						this.comando = null;
						this.sb = null;
					}
				}
			}
		}

		public void setInterfaceConnectionListener(InterfaceCommand interfaceCommand) {
			this.interfaceCommand = interfaceCommand;
		}

	}

	public interface InterfaceCommand {

		public Cliente onRequestSignIn(SocketBase socketBase)
				throws ClassNotFoundException, SQLException, IOException;

		public Cliente onRequestSignOut(SocketBase socketBase)
				throws ClassNotFoundException, SQLException, IOException;

		public void onRequestDisconnectSocket(SocketBase socketBase) throws IOException;

		public void onCommandReceveived(SocketBase socketBase, String stringRecebida) throws IOException;

	}
}