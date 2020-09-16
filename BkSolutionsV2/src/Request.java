import java.io.Serializable;

@SuppressWarnings("serial")
public class Request implements Serializable {

	private String codeRequest;
	private String typeRequest, msgRequest, status;

	public String getCodeRequest() {
		return codeRequest;
	}

	public void setCodeRequest(String codeRequest) {
		this.codeRequest = codeRequest;
	}

	public String getTypeRequest() {
		return typeRequest;
	}

	public void setTypeRequest(String typeRequest) {
		this.typeRequest = typeRequest;
	}

	public String getMsgRequest() {
		return msgRequest;
	}

	public void setMsgRequest(String msgRequest) {
		this.msgRequest = msgRequest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public class TipoRequisicao {
		public static final String LOGIN_REQUEST = "requisicaoLogin";
		public static final String LOGOUT_REQUEST = "requesicaoLogout";
	}

	public class StatusRequisicao {
		public static final String FEITA = "requisicaoFeita";
		public static final String AGUARDANDO = "requesicaoAguardando";
		public static final String RECEBIDA = "requesicaoRecebida";
	}
}
