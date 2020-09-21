package br.com.bksolutionsdomotica.conexao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import modelo.Cliente;

public class SocketCliente {

	private boolean isHarware;
	private Cliente cliente;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private ObjectInputStream inObj;
	private ObjectOutputStream outObj;

	public SocketCliente(Socket socket) throws IOException {
		this.socket = socket;
		if (socket != null) {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			inObj = new ObjectInputStream(socket.getInputStream());
//			outObj = new ObjectOutputStream(socket.getOutputStream());
		}
	}

	public void closeResouces() throws IOException {
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (socket != null) {
			socket.close();
		}
	}

	public void sendCommand(String comando) throws IOException {
		if (comando != null && !comando.isEmpty()) {
			if (out != null) {
				out.write(comando);
				out.flush();
			}
		}
	}

	public String commandReceiver() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		if (in != null && in.ready()) {
			while (in.ready()) {
				int retorno = in.read();
				stringBuilder.append(Character.toChars(retorno));
			}
		}
		return stringBuilder.toString();
	}

//	public void sendObject(Object obj) throws IOException {
//		if (obj != null) {
//				outObj = new ObjectOutputStream(socket.getOutputStream());
//				outObj.writeObject(obj);
//				outObj.flush();
//				outObj = null;
//			
//		}
//	}

//	public Object objectReceiver() throws IOException, ClassNotFoundException {
//		Object obj = null;
//		if (inObj != null) {
//			while (inObj.available() > -1) {
//				obj = inObj.readObject();
//			}
//		}
//		return obj;
//	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isHarware() {
		return isHarware;
	}

	public void setHarware(boolean isHarware) {
		this.isHarware = isHarware;
	}

}
