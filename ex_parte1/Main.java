package ex_parte1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Servidor ss = new Servidor(5000);
		Consumidor c = new Consumidor("127.0.0.1", 5000);
		GrupoDeLojas lojas = new GrupoDeLojas();
		
		Loja l1 = new Loja("Lojas Americanas");
		Loja l2 = new Loja("Casas Bahia");
		Loja l3 = new Loja("Magalu");
		
		Produto p1 = new Produto("Geladeira	Consul      500 litros", 2500.00);
		Produto p2 = new Produto("Geladeira	Brastemp    440 litros", 3800.00);
		Produto p3 = new Produto("Geladeira	Electrolux	380	litros", 2100.00);
		Produto p4 = new Produto("Geladeira	Electrolux	380	litros", 2300.00);
		Produto p5 = new Produto("Geladeira	Brastemp    400	litros", 3700.00);
		
		
		l1.addProduto(p1);
		l2.addProduto(p2);
		l2.addProduto(p3);
		l3.addProduto(p4);
		l3.addProduto(p5);
		
		lojas.addLoja(l1);
		lojas.addLoja(l2);
		lojas.addLoja(l3);
		
		lojas.ativar_lojas();
		ss.start();
		
		c.startConn();
		
		Scanner sc = new Scanner(System.in);
	    boolean while_bk = true;
		while(while_bk){
	      System.out.print("Digite um texto:");
	      String texto_digitado = sc.nextLine();
	      if(texto_digitado == "sair") {
	    	  while_bk=false;
	    	  break;
	      }else {
	    	  c.search(texto_digitado);  
	      }
	      
	    }
	    
	    sc.close();
		// c.search();
		
		
	}

}
