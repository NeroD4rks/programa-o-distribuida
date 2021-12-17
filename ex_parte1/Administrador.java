package ex_parte1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.FileReader;

public class Administrador {
	private Socket s;
	private String ip;
	private int porta;
	
	public Administrador(String ip, int porta) {
		this.ip = ip;
		this.porta = porta;
		
	}
		
	public void startConn(){	
		    try {
				this.s = new Socket(ip, porta);
				System.out.println( "Cliente: conexao feita" );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.startConn();
			}
	        
	}
	
	public void closeConn() {
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeTmax(int Tmax) {
		DataOutputStream saida;
		try {
			saida = new DataOutputStream(s.getOutputStream());
	        saida.writeUTF("setTmax:" + Tmax);
	        System.out.println( "Tmax mudado para " + Tmax);
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
	
	public void showTMax() {
		DataOutputStream saida;
		try {
			saida = new DataOutputStream(s.getOutputStream());
			saida.writeUTF("getTmax");
      		DataInputStream entrada = new DataInputStream( s.getInputStream());
      		String resultado = entrada.readUTF();
      		System.out.println( "resultado = \n" + resultado );      
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showHistory() {
		DataOutputStream saida;
		try {
			saida = new DataOutputStream(s.getOutputStream());
			saida.writeUTF("showHistory");
      		DataInputStream entrada = new DataInputStream( s.getInputStream());
      		String resultado = entrada.readUTF();
      		System.out.println( "logs:\n" + resultado);
      		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
