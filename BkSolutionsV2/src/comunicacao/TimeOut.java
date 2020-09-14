package comunicacao;

import java.io.IOException;
import java.net.Socket;

public class TimeOut extends Thread {
	Socket socket = null;
	ConnectSocket cs = null;

	public TimeOut(ConnectSocket cs, Socket socket) {
		this.socket = socket;
		this.cs = cs;
	}

	@Override
	public void run() {
		do {
			try {
				sleep(6000);
				socket.getOutputStream().write('\0');
				socket.getOutputStream().flush();
				sleep(1000);// LEEEEEMMMMBRAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRR
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cs.setExit();
				break;
			}
		} while (true);
	}
}
