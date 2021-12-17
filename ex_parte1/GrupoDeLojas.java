package ex_parte1;
import java.util.ArrayList;

public class GrupoDeLojas {
	
	private ArrayList<Loja> lista_lojas = new ArrayList<Loja>();
	
	public GrupoDeLojas() {	
	}
	
	public void addLoja(Loja l) {
		this.lista_lojas.add(l);
	}
	
	public void ativar_lojas() {
		for(Loja l: lista_lojas) {
			l.start();
		}
	}
	
	
}
