package comunicacao;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.conexao.SocketCliente;

public class JSONHardware {
	private SocketCliente hardClient = null;
	private JSONObject jsonObj = null;

	public JSONHardware(SocketCliente hardClient, JSONObject jsonObj) {
		super();
		this.hardClient = hardClient;
		this.jsonObj = jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public void setJsonAndBufferedWriter(SocketCliente hardClient, JSONObject jsonObj) {
		this.hardClient = hardClient;
		this.jsonObj = jsonObj;
	}

	public SocketCliente gethardClient() {
		return hardClient;
	}

	public void sethardClient(SocketCliente hardClient) {
		this.hardClient = hardClient;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

}
