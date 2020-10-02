package br.com.bksolutionsdomotica.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorBK implements Runnable {

	private ServerSocket serverSocket;
	private int port;
	private List<SocketCliente> socketClientes;

	public ServidorBK(ServerSocket serverSocket, int port, List<SocketCliente> socketClientes) {
		super();
		this.serverSocket = serverSocket;
		this.port = port;
		this.socketClientes = socketClientes;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Servidor ouvindo na porta " + port);
			while (true) {
				socket = serverSocket.accept();
				onSocketConnected(socket);
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

	private void onSocketConnected(Socket socket) throws IOException {
		SocketCliente sc = new SocketCliente(socket);
		socketClientes.add(sc);
//		new TimeOut(null, socket).start();
		System.out.println("cliente connectado/ Total: " + socketClientes.size());
	}

}
