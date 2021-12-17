package ex_parte1;

public class Produto {
	private String titulo;
	private double preco;
	
	public Produto(String titulo, double preco) {
		this.titulo = titulo;
		this.preco = preco;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public double getPreco() {
		return this.preco;
	}
}
