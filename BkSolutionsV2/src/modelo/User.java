package modelo;

import java.sql.Date;

import conexaobd.BKClienteDAO;
import conexaobd.BKHardwareDAO;

@SuppressWarnings("unused")
public class User {

	private int id;
	private String nome;
	private String sexo;
	private Date nasc;
	private String rua;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cpf;
	private String email;
	private String password;
	private BKClienteDAO clienteDAO;
	private BKHardwareDAO hardwareDAO;

	public User() {

	}

}
