package comunicacao;

import java.io.BufferedWriter;

import org.json.JSONObject;

public class JSONHardware {
	private BufferedWriter hardwareInput = null; // Deixei como publico apenas para retirar as Warnings
	private JSONObject jsonObj = null; // Deixei como publico apenas para retirar as Warnings

//	public JSONHardware(JSONObject jsonObj) {
//		this.jsonObj = jsonObj;
//	}

	public JSONHardware(BufferedWriter hardwareInput, JSONObject jsonObj) {
		super();
		this.hardwareInput = hardwareInput;
		this.jsonObj = jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public void setJsonAndBufferedWriter(BufferedWriter hardwareInput, JSONObject jsonObj) {
		this.hardwareInput = hardwareInput;
		this.jsonObj = jsonObj;
	}

	public BufferedWriter getHardwareInput() {
		return hardwareInput;
	}

	public void setHardwareInput(BufferedWriter hardwareInput) {
		this.hardwareInput = hardwareInput;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}
	
}
