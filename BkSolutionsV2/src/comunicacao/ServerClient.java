package comunicacao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient extends Thread {
	private ServerSocket serverClient;

	public ServerClient(ServerSocket serverClient) {
		this.serverClient = serverClient;
	}

	@Override
	public void run() {

		System.out.println("Servidor escutando na porta " + serverClient.getLocalPort());

		while (true) {

			try {
				Socket socket = serverClient.accept();
				new ConnectSocket(socket).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
