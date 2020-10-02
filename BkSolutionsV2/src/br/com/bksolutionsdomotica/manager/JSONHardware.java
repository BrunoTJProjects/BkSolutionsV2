package br.com.bksolutionsdomotica.manager;

import org.json.JSONObject;

import br.com.bksolutionsdomotica.servidor.SocketCliente;

public class JSONHardware {
	private SocketCliente hardClient = null;
	private JSONObject jsonObj = null;

	public JSONHardware(SocketCliente hardClient, JSONObject jsonObj) {
		super();
		this.hardClient = hardClient;
		this.jsonObj = jsonObj;
	}

	public SocketCliente getHardClient() {
		return hardClient;
	}

	public void setHardClient(SocketCliente hardClient) {
		this.hardClient = hardClient;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

}
