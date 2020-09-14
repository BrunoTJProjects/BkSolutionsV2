package comunicacao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHardware extends Thread {
	private ServerSocket serverHardware;

	public ServerHardware(ServerSocket serverHardware) {
		this.serverHardware = serverHardware;
	}

	@Override
	public void run() {
		System.out.println("Servidor escutando na porta " + serverHardware.getLocalPort());

		while (true) {
			Socket socket;
			try {
				socket = serverHardware.accept();
				new ConnectSocket(socket).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
