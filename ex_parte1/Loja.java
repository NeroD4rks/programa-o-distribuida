package ex_parte1;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Loja extends Thread{
	
	private String nome;
	private ArrayList<Produto> lista_produtos = new ArrayList<Produto>();
	
	public Loja(String nome) {
		this.nome = nome;
	}
	
	public void addProduto(Produto p) {
		this.lista_produtos.add(p);
	}
	
	public void run() {
		try {
			int port = 3000;
          InetAddress grupo = InetAddress.getByName("224.0.0.1");
	        MulticastSocket s = new MulticastSocket(port);
	        
	        System.out.println("loja "+ this.nome +": Entrando no grupo ...");
	        s.joinGroup(grupo);
	        System.out.println("loja "+ this.nome +": Ok.");
	        while(true) {
            byte[] mensagem_bytes = new byte[1000];
            DatagramPacket mensagem_pacote = new DatagramPacket(mensagem_bytes, mensagem_bytes.length);

            System.out.println("loja "+ this.nome +": Aguardando mensagem...");
            s.receive(mensagem_pacote);
            System.out.println("loja "+ this.nome +": Mensagem Ok.");
            
            String dados_texto = new String(mensagem_pacote.getData(), 
                mensagem_pacote.getOffset(),mensagem_pacote.getLength());
            
            
            System.out.println("loja "+ this.nome +":Mensagen recebida: "+ dados_texto);
            
            String result = "";
            int contador = 0;
            String pesquisa = dados_texto.split(";")[1];
            for(Produto p: this.lista_produtos) {
              String msg = this.nome + ", " + p.getTitulo() + ", " + p.getPreco();
              if(msg.toLowerCase().contains(pesquisa)){
                result += msg + "\n";
                contador = 1 + contador;
              }
		    	  }
		    	
		    	System.out.println(this.nome +": Msg de resposta loja:\n" + result);
		    	result = this.nome+ ":" + contador + "-" + result;
		    	
		    	int porta_resposta = Integer.parseInt(dados_texto.split(";")[0]);
		    	DatagramSocket socket_servidor = new DatagramSocket();
	        InetAddress IP_servidor = mensagem_pacote.getAddress();

	        byte[] resposta_bytes = result.getBytes();

	            
	        DatagramPacket resposta_pacote = new DatagramPacket( resposta_bytes, resposta_bytes.length, IP_servidor, porta_resposta);
	        System.out.println(this.nome +": Enviando a resposta ...");
	        socket_servidor.send(resposta_pacote);
	        System.out.println(this.nome +": Resposta enviada.");	            
          socket_servidor.close();
	        }
	
    } catch(Exception exc) { exc.printStackTrace(); }
    
		
	}
	
}
