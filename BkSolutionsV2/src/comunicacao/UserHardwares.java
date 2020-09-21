package comunicacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import br.com.bksolutionsdomotica.conexao.SocketCliente;

public class UserHardwares {

	private List<SocketCliente> inputsClient = new ArrayList<SocketCliente>();  //guarda a lista de Buffered Writers conectados ao Servidor
	private HashMap<String, JSONHardware> hardwares = new HashMap<String, JSONHardware>(); //guarda a lista de Jsons e Buffereds Writers organizados pelo MAC das Placas

//	public List<BufferedWriter> getInputsClient() {
//		return inputsClient;
//	}
//
//	public void setInputsClient(List<BufferedWriter> inputsClient) {
//		this.inputsClient = inputsClient;
//	}

	public HashMap<String, JSONHardware> getHardwares() {
		return hardwares;
	}

	public void setHardwares(HashMap<String, JSONHardware> hardwares) {
		this.hardwares = hardwares;
	}

}