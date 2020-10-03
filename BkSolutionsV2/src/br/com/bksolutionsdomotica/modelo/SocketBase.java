package br.com.bksolutionsdomotica.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public abstract class SocketBase {
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	public SocketBase(Socket socket) throws IOException {
		this.socket = socket;
		if (socket != null) {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
}