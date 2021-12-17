package ex_parte1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class Trabalhador extends Thread {

    private Socket t;
    private Servidor sv_origem;

    public Trabalhador(Socket t, Servidor sv)
    {
        this.t = t;
        this.sv_origem = sv;
        
    }

    public void run(){
      while(true){
        try
        {
          DataInputStream entrada = new DataInputStream( t.getInputStream());
          DataOutputStream saida = new DataOutputStream( t.getOutputStream());

          String k = entrada.readUTF();
          System.out.println( "Recebidos: " + k);
          if(k == "getTmax") {
            saida.writeUTF("" + sv_origem.getT_max());
          }else if(k.contains("setTmax")) {
            sv_origem.setT_max(Integer.parseInt(k.split(":")[1]));
          }else if (k.contains("search")) {
            String resposta = sv_origem.resultSearch(k.split(":")[1]);
            saida.writeUTF(resposta);
          }else if(k.contains("showHistory")) {
        	  String resposta = sv_origem.logs();
              saida.writeUTF(resposta);
          }
      }
      catch( Exception e )
      {
       continue;
      }

    }
  }
    
}
