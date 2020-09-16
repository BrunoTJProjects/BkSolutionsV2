import java.io.IOException;
import java.sql.SQLException;

import modelo.Cliente;

public class MyServerBk implements ServerCoreBK.InterfaceCommand {

	private ServerCoreBK server;

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
	public Cliente onLoginRequest(SocketCliente socketCliente)
			throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Solicitaçao de Login");
		Cliente cliente = null;
		cliente = server.clienteLogado("bruno.melo@tcm10.com.br", "8aB1yGj4");
		try {
			socketCliente.sendObject(cliente);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.enviaComando(socketCliente, "Comando para enviado de: " + cliente.getNome());

		return cliente;
	}

	@Override
	public void removeSocket(SocketCliente socketCliente) throws IOException {
		server.removeSocketCliente(socketCliente);
	}

	@Override
	public void onCommandReceveived(SocketCliente sc, String stringRecebida) throws IOException {
		System.out.println("Cliente: " + sc.getCliente().getNome() + "/ comando recebido: " + stringRecebida);
		server.enviaComando(sc, stringRecebida);
	}

}
