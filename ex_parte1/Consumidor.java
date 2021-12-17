package ex_parte1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Consumidor {
	private Socket s;
	private String ip;
	private int porta;
	
	public Consumidor(String ip, int porta) {
		this.ip = ip;
		this.porta = porta;
		
	}
	
	public void startConn(){	
		    try {
				this.s = new Socket(this.ip, this.porta);
				System.out.println( "Cliente: conexao feita" );
				
			} catch (IOException e) {
				e.printStackTrace();
				this.startConn();
			}
	        
	}
	
	public void closeConn() throws IOException {
        s.close();
        System.out.println( "Cliente: conexao encerrada");
	}
	
	public void search(String request) {
		try {
			DataOutputStream saida = new DataOutputStream(s.getOutputStream());
	        saida.writeUTF("search:" + request);
	        DataInputStream entrada = new DataInputStream( s.getInputStream());
	        String resultado = entrada.readUTF();
	        System.out.println("Consumidor: search = \n" + resultado);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
