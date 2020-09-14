package modelo;

public class Modelo {
	private int id;
	private String nome;

	public Modelo(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		String info = null;
		info = "ID: " + id + " MODELO: " + nome;
		return info;
	}
}
