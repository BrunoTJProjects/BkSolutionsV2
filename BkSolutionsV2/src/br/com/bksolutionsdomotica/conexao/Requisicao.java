package br.com.bksolutionsdomotica.conexao;

import org.json.JSONPropertyName;

public class Requisicao {
	
//	public static final String REQUEST_SIGNIN = "solicitacaoSignIn";
//	public static final String REQUEST_SIGNOUT = "solicitacaoSignOut";
	
	private String tipo;
	private String email;
	private String password;
	private String session;
	
	@JSONPropertyName(value = "tipo")
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@JSONPropertyName(value = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JSONPropertyName(value = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JSONPropertyName(value = "session")
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
	
	
//	public String[] dados;
}
