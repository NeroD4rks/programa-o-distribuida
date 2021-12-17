package atv;
import java.io.FileWriter;

public class ExperimentoSequencial extends Experimento{

    public ExperimentoSequencial(int x_min, int x_max, int max_execucoes, String arq_tempos)
            throws java.io.IOException
    {
        super(x_min, x_max, max_execucoes, arq_tempos);
    }

    protected void ordene(int[] v, int inicio, int fim)
    {
        QuickSortSequencial.ordene(v, inicio, fim);
    }
    
    protected void ordene(int[] v, int inicio, int fim, int processadores)
    {
        QuickSortSequencial.ordene(v, inicio, fim);
    }
    public static void main(String args[]) {
        try {
        	
            new ExperimentoSequencial( 22, 22, 10, "tempos_seq22.csv" ).doit();
            System.out.println("Fim do experimento sequencial");
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
