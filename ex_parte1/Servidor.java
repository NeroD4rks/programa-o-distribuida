package ex_parte1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.text.*;

public class Servidor extends Thread{
	private ServerSocket ss;
	private int porta;
	private int id_search = 0;
	private int t_max = 10000;
	private FileWriter log;	
	
	public Servidor(int porta){
		this.porta = porta;
	    try {
          log = new FileWriter("log_server.txt");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	}
	
	public void setT_max(int n) {
		this.t_max = n;
	}
	
	public int getT_max() {
		return this.t_max;
	}
	public void run() {
    try {
			ss = new ServerSocket(porta);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    while (true)
      {
        System.out.println( "Servidor aguardando um cliente ...");
        Socket t;
        try {
          t = ss.accept();
        } catch (IOException e) {
          e.printStackTrace();
          continue;
        }
        System.out.println( "Servidor: conexao feita");
        Trabalhador trab = new Trabalhador(t, this);
        trab.start();
    }
	}
	
	public void closeConn() throws IOException {
    ss.close();
    System.out.println( "Servidor: conexao encerrada");
	}
	
	public String resultSearch(String texto_original) {
		this.id_search += 1;
		int id_p = this.id_search; 
    	String texto = this.id_search + ";" + texto_original.toLowerCase();
    	System.out.println( "Servidor: "+ "Iniciando pergunta ..." + id_search);
        try {
            InetAddress grupo = InetAddress.getByName("224.0.0.1");
            MulticastSocket s = new MulticastSocket();
            DatagramPacket ola = new DatagramPacket(texto.getBytes(), texto.length(), grupo, 3000);
            
            DatagramSocket socket_cliente = new DatagramSocket(id_search);
            
            System.out.println( "Servidor: Enviando mensagem para o grupo ..." + texto.length());
            s.send(ola);
            System.out.println("Servidor: Ok.");
            
            DatagramPacket resposta_pacote = new DatagramPacket(new byte[512],512);
            
            try {
              String resposta_completa = "";
              String log_aux = "";
              socket_cliente.setSoTimeout(this.t_max);
              for(int i =0; i <3; i++){
                System.out.println("Servidor: Recebendo resposta ...");
                socket_cliente.receive(resposta_pacote);
                System.out.print("Servidor: Resposta: ");
                String resposta_texto = new String(resposta_pacote.getData(),
				        resposta_pacote.getOffset(),resposta_pacote.getLength());
                log_aux += "," + resposta_texto.split("-")[0];
                if(resposta_texto.split("-").length == 1) continue;
                resposta_completa += "\n" + resposta_texto.split("-")[1];
              }
              SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
              Date y=new Date();

              String resultado = sdf1.format(y)+ " - " + id_p +","+ texto_original + log_aux;
              
              System.out.println("Resposta da "+ resultado);
              socket_cliente.close();
              
              log.write( resultado );
              log.write( '\n' );
              log.flush();
              return resposta_completa;
              
            }catch(SocketTimeoutException exc) {
            	exc.printStackTrace();	
            }
            
            socket_cliente.close();
            
        } catch(Exception exc) {
        	exc.printStackTrace(); 
        
        }
      return "";   
    }
	
	public String logs() {
		File file = new File("log.txt");
        String lines = "";
        Scanner inputStream;
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNextLine()){
                String l = inputStream.nextLine();
                lines += l;
            }
            
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       return lines;
	}
	
}
