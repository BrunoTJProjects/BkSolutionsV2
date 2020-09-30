package br.com.bksolutionsdomotica.conexao;

public class Request {
	
	public static final String REQUEST_SIGNIN = "solicitacaoSignIn";
	public static final String REQUEST_SIGNOUT = "solicitacaoSignOut";

	public String codeRequest;
	public String typeRequest, msgRequest, status;
}
