package comunicacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexaobd.dao.BKClienteDAO;
import br.com.bksolutionsdomotica.conexaobd.dao.BKHardwareDAO;
import br.com.bksolutionsdomotica.manager.UserHardwares;
import br.com.bksolutionsdomotica.modelo.Cliente;
import br.com.bksolutionsdomotica.modelo.Hardware;

public class ConnectSocket extends Thread {
	private Socket socket = null;
	private Cliente cliente = null;
	private Hardware hardware = null;
	private String id = null;
	private String id_hardware = null;
	private BufferedWriter output = null;
	private BufferedReader input = null;
	private static HashMap<String, UserHardwares> userHardwares = new HashMap<String, UserHardwares>();
	private boolean exit = false;
	private Thread th;

	public ConnectSocket(Socket socket) throws IOException {
		this.socket = socket;
		this.socket.setKeepAlive(true);
		output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {
		try {
			th = new TimeOut(this, socket);
			th.start();

			System.out.println(socket.getLocalPort());

			System.out.println(activeCount());

			if (socket.getLocalPort() == 58365) {

				enviaDados("Digite seu e-mail:");
				String email = recebeDados();
				enviaDados("Digite sua senha:");
				String senha = recebeDados();

				if (email == null || (email.trim().equals("")) || senha == null || (senha.trim().equals(""))
						|| !clienteLogado(email, senha)) {
					enviaDados("Login não realizado!!");
				} else {

					id = String.valueOf(cliente.getId());

					userGetJSONHardwares();

					enviaDados("what you want?");

					String[] content = null;
					String acao = null;
					String mac = null;
					String chave = null;
					String valor = null;
					JSONObject jsonObj = null;

					do {

						try {
							content = recebeDados().trim().split(" ");
							acao = content[0];
							mac = content[1];

							UserHardwares usuario = userHardwares.get(id);

							if (usuario.getHardwares().containsKey(content[1])) {

								switch (acao) {

								case "getKey":
									chave = content[2];
									// enviaDados(cliente.getChave(mac, chave));
									enviaDados(usuario.getHardwares().get(mac).getJsonObj().getString(chave));
									break;

								case "getKeys":
									// enviaDados(cliente.getChaves(mac).toString());
									enviaDados(usuario.getHardwares().get(mac).getJsonObj().toString());
									break;

								case "setKey":
									chave = content[2];
									valor = content[3];
									userAvisoDeAlteracao(mac, setJSONObj(chave, valor));
									usuario.getHardwares().get(mac).getJsonObj().put(chave, valor);
									break;

								case "setKeys":
									jsonObj = new JSONObject(content[2]);
									userAvisoDeAlteracao(mac, jsonObj);
									for (String key : jsonObj.keySet()) {
										usuario.getHardwares().get(mac).getJsonObj().put(key, jsonObj.get(key));
									}
									break;

								case "deleteKey":
									// chave = content[2];
									// enviaDados(String.valueOf(cliente.excluirChave(mac, chave)));
									// avisoDeAlteracao(mac);
									break;
								}

							} else {
								enviaDados("Card of mac: " + mac + " not connected!");
								break;
							}

						} catch (Exception e) {
							System.out.println(e);
							enviaDados("Key not found!");
							break;
						}

					} while (content != null && !(content[0].trim().equals("")) && exit == false && !socket.isClosed());

					userEraseJSONHardwares();

				}

			} else if (socket.getLocalPort() == 58364) {

				enviaDados("Who are you?");

				id_hardware = recebeDados();

				if (id_hardware == null || (id_hardware.trim().equals("")) || !hardwareExiste(id_hardware)) {
					enviaDados("Device not found!");
				} else {

					id_hardware = String.valueOf(hardware.getMac());
					sleep(1000);

					boardGetJSONHardwares();

					enviaDados("what you want?");

					String[] content = null;
					String acao = null;
					String chave = null;
					String valor = null;
					JSONObject jsonObj = null;

					do {

						try {
							content = recebeDados().trim().split(" ");
							acao = content[0];

							UserHardwares usuario = userHardwares.get(id);

							switch (acao) {

							case "getKey":
								chave = content[1];
								// enviaDados(cliente.getChave(mac, chave));
								enviaDados(usuario.getHardwares().get(id_hardware).getJsonObj().getString(chave));
								break;

							case "getKeys":
								// enviaDados(cliente.getChaves(mac).toString());
								enviaDados(usuario.getHardwares().get(id_hardware).getJsonObj().toString());
								break;

							case "setKey":
								chave = content[1];
								valor = content[2];
								boardAvisoDeAlteracao(setJSONObj(chave, valor));
								usuario.getHardwares().get(id_hardware).getJsonObj().put(chave, valor);
								break;

							case "setKeys":
								jsonObj = new JSONObject(content[1]);
								boardAvisoDeAlteracao(jsonObj);
								for (String key : jsonObj.keySet()) {
									usuario.getHardwares().get(id_hardware).getJsonObj().put(key, jsonObj.get(key));
								}
								break;

							case "deleteKey":
								// chave = content[2];
								// enviaDados(String.valueOf(cliente.excluirChave(mac, chave)));
								// avisoDeAlteracao(mac);
								break;
							}

						} catch (Exception e) {
							System.out.println(e);
							enviaDados("Key not found!");
							break;
						}

					} while (content != null && !(content[0].trim().equals("")) && exit == false && !socket.isClosed());

					boardEraseJSONHardwares();

				}

			}

			input.close();
			output.close();
			socket.close();
			System.out.println("Cliente desconectado");

		} catch (IOException | ClassNotFoundException | SQLException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public boolean clienteLogado(String email, String senha) throws ClassNotFoundException, SQLException {
		boolean has = false;
		BKClienteDAO clienteDAO = new BKClienteDAO();
		cliente = clienteDAO.logarCliente(email, senha);
		if (cliente != null) {
			has = true;
		}
		return has;
	}

	public boolean hardwareExiste(String mac) throws ClassNotFoundException, SQLException {
		boolean has = false;
		BKHardwareDAO hardwareDAO = new BKHardwareDAO();
		hardware = hardwareDAO.getHardware(mac);
		if (hardware != null) {
			has = true;
		}
		return has;
	}

	public void userGetJSONHardwares() throws ClassNotFoundException, SQLException {

		if (!userHardwares.containsKey(id)) {
			UserHardwares users = new UserHardwares();
//			users.getInputsClient().add(output);
			userHardwares.put(id, users);

		} else {
			UserHardwares userHard = userHardwares.get(id);
//			if (!userHard.getInputsClient().contains(output)) {
//				userHard.getInputsClient().add(output);
//			}
		}
	}

	public void userEraseJSONHardwares() throws ClassNotFoundException, SQLException {
//		if (id != null && userHardwares.containsKey(id)) {
//			UserHardwares userHard = userHardwares.get(id);
//			if (userHard.getInputsClient().contains(output)) {
//				userHard.getInputsClient().remove(output);
//				System.out.println("Conexão existe e foi removida");
//				if (userHard.getInputsClient().isEmpty()) {
//					System.out.println("Essa foi a ultima");
//					if (userHard.getHardwares().isEmpty()) {
//						userHardwares.remove(id);
//						System.out.println("Nenhum mac guardado!");
//						System.out.println("por isso esse userHardware foi eliminado");
//						System.out.println(userHardwares.size());
//					}
//				}
//			}
//		}
	}

	private void enviaDados(String dados) {
		try {
			output.write(dados + "\r\n");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void userAvisoDeAlteracao(String mac, JSONObject aviso) throws IOException {

//		BufferedWriter bufferedWriter = userHardwares.get(id).getHardwares().get(mac).getHardwareInput();
//		if (bufferedWriter != null) {
//			bufferedWriter.write(aviso.toString());
//			bufferedWriter.flush();
//		}
//		aviso.put("mac", mac);	
//		for (BufferedWriter bufferedWriters : userHardwares.get(id).getInputsClient()) {
//			if(output != bufferedWriters)
//			bufferedWriters.write(aviso.toString());
//			bufferedWriters.flush();
//		}

	}

	private void boardAvisoDeAlteracao(JSONObject aviso) throws IOException {
//		aviso.put("mac", id_hardware);		
//		for (BufferedWriter bufferedWriter : userHardwares.get(id).getInputsClient()) {
//			bufferedWriter.write(aviso.toString());
//			bufferedWriter.flush();
//		}
	}

	private String recebeDados() {
		String receiver = null;
		try {
			receiver = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return receiver;
	}

	public void setExit() {
		// TODO Auto-generated method stub
		exit = true;
	}

	public JSONObject setJSONObj(String chave, String valor) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(chave, valor);
		return jsonObj;
	}

	private void boardGetJSONHardwares() throws ClassNotFoundException, SQLException {
//		id = String.valueOf(hardware.getCliente());
//		if (!userHardwares.containsKey(id)) {
//			UserHardwares users = new UserHardwares();
//			users.getHardwares().put(id_hardware, new JSONHardware(output, hardware.getChaves()));
//			userHardwares.put(id, users);
//		} else {
//			UserHardwares userHard = userHardwares.get(id);
//			if (!userHard.getHardwares().containsKey(id_hardware)) {
//				userHard.getHardwares().put(id_hardware, new JSONHardware(output, hardware.getChaves()));
//			} else {
//				userHard.getHardwares().get(id_hardware).setHardwareInput(output);
//			}
//		}
	}

	private void boardEraseJSONHardwares() throws ClassNotFoundException, SQLException {
//		if (id != null && userHardwares.containsKey(id)) {
//			UserHardwares userHard = userHardwares.get(id);
//			if (userHard.getHardwares().containsKey(id_hardware) && output == userHard.getHardwares().get(id_hardware).getHardwareInput()) {
//				hardware.setChaves(userHard.getHardwares().get(id_hardware).getJsonObj());
//				userHard.getHardwares().remove(id_hardware);
//				if (userHard.getHardwares().isEmpty() && userHard.getInputsClient().isEmpty()) {
//					userHardwares.remove(id);
//				}
//			}
//		}
	}

	/*private void loop() {
		/*
		 * do {
		 * 
		 * try { content = recebeDados().trim().split(" "); acao = content[0];
		 * 
		 * if (jsonClientes.containsKey(content[1])) {
		 * 
		 * mac = content[1];
		 * 
		 * switch (acao) {
		 * 
		 * case "getKey": chave = content[2]; // enviaDados(cliente.getChave(mac,
		 * chave)); enviaDados(jsonClientes.get(mac).getString(chave)); break;
		 * 
		 * case "setKey": chave = content[2]; valor = content[3]; // alterou =
		 * cliente.setChave(mac, chave, valor); // enviaDados(String.valueOf(alterou));
		 * // if (alterou != 0) // avisoDeAlteracao(mac); avisoDeAlteracao(mac,
		 * setJSONObj(chave, valor).toString()); jsonClientes.get(mac).put(chave,
		 * valor); break;
		 * 
		 * case "getKeys": // enviaDados(cliente.getChaves(mac).toString());
		 * enviaDados(jsonClientes.get(mac).toString()); break;
		 * 
		 * case "setKeys": jsonObj = new JSONObject(content[2]); avisoDeAlteracao(mac,
		 * jsonObj.toString()); // alterou = cliente.setChaves(mac, jsonObj); //
		 * enviaDados(String.valueOf(alterou)); // if (alterou != 0) //
		 * avisoDeAlteracao(mac); for (String key : jsonObj.keySet()) {
		 * jsonClientes.get(mac).put(key, jsonObj.get(key)); } break;
		 * 
		 * case "deleteKey": chave = content[2];
		 * enviaDados(String.valueOf(cliente.excluirChave(mac, chave))); //
		 * avisoDeAlteracao(mac); break;
		 * 
		 * case "atualizaJson": getJSONHardware(mac); break; }
		 * 
		 * setJSONHardware(mac, jsonClientes.get(mac));
		 * 
		 * } else { break; }
		 * 
		 * } catch (Exception e) { System.out.println(e); enviaDados("Key not found!");
		 * break; }
		 * 
		 * } while (content != null && !(content[0].trim().equals("")) && exit == false
		 * && !socket.isClosed());
		 *
	}*/
}
