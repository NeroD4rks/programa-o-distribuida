package atv;
import java.io.*;

public class ExperimentoParalelo extends Experimento {

    private int numero_processadores;
    private static int min_numeros;

    public ExperimentoParalelo(int x_min, int x_max, int max_execucoes, int min_numeros, String arq_tempos)
            throws java.io.IOException
    {
        super(x_min, x_max, max_execucoes, arq_tempos);
        this.min_numeros = min_numeros;
        numero_processadores = Runtime.getRuntime().availableProcessors();
        System.out.println( "Nº de processadores: " + numero_processadores );
    }

    protected void ordene(int[] v, int inicio, int fim, int processadores)
    {	
    	
        QuickSortParalelo m = new QuickSortParalelo(v, inicio, fim, processadores, 0, min_numeros);
        m.start();
        
        try {
            m.join();
        }
        
        catch (Exception e) {}
        
    }

    protected void ordene(int[] v, int inicio, int fim)
    {	
    	
        QuickSortParalelo m = new QuickSortParalelo(v, inicio, fim, numero_processadores, 0, min_numeros);
        m.start();
        
        try {
            m.join();
        }
        
        catch (Exception e) {}
        
    }
    
    public static void main(String args[]) {
        try {
        	
            new ExperimentoParalelo( 22, 22, 10, min_numeros,"tempos_par22.csv" ).doit2();
            System.out.println("Fim do experimento paralelo");
        
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
