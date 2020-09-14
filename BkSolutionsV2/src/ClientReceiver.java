//import java.io.IOException;
//import java.net.Socket;
//import java.sql.SQLException;
//
//import modelo.Cliente;
//
////@SuppressWarnings("unused")
//public class ClientReceiver {
//	private String comando;
//	private SocketCliente sc;
//	private InterfaceCommand interfaceCommand;
//	
//	public ClientReceiver() {
//	}
//
//	public void runOnTime(SocketCliente sc) throws ClassNotFoundException, SQLException {
//
//		try {
//
//			if (sc.getCliente() != null) {
//
//				String string = sc.commandReceiver();
//				
//				if(string != null && !string.isEmpty()) {
//					interfaceCommand.onCommandReceveived(sc, string);
//				}
//				
//				enviarComando(sc);
//
//			} else {
//				Cliente cliente = interfaceCommand.onLoginRequest(sc);
//				if (cliente != null) {
//					sc.setCliente(cliente);
//				} else {
//					interfaceCommand.removeSocket(sc);
//				}
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
//		
//
//	}
//
//	private void enviarComando(SocketCliente sc) throws IOException {
//	if (comando != null && !comando.isEmpty()) {
//		if(this.sc != null && sc != null) {
//			if(this.sc.equals(sc)) {
//				sc.sendCommand(comando);
//				comando = null;
//				this.sc = null;
//			}
//		}
//	}
//}
//	
//	public synchronized void enviaComando(SocketCliente sc, String command) throws IOException {
//		if (sc != null && command != null && !command.isEmpty()) {			
//			comando = command;
//			this.sc = sc;
////			enviarComando(sc);
//		}else {
//			comando = null;
//			this.sc = null;
//		}
//	}
//
//	public void setInterfaceConnectionListener(InterfaceCommand interfaceCommand) {
//		this.interfaceCommand = interfaceCommand;
//	}
//
//	public interface InterfaceCommand {
//		
//		public static final String ON_CONNECTED = "conexao_estabelecida";
//		public static final String ON_LOGIN_REQUEST = "pedido_de_login";
//		public static final String ON_COMMAND_RECEIVED = "comando_recebido";
//
//		public void onConnected(Socket socket) throws IOException;
//		public Cliente onLoginRequest(SocketCliente socketCliente) throws ClassNotFoundException, SQLException;
//		public void removeSocket(SocketCliente socketCliente) throws IOException;
//		public void onCommandReceveived(SocketCliente socketCliente, String stringRecebida);
//
//	}
//}