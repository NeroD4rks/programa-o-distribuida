package atv;
import java.io.FileWriter;
import java.util.Random;

public abstract class Experimento {

    int x_min;
    int x_max;
    int num_execucoes;
    FileWriter log;

    public Experimento(int x_min, int x_max, int num_execucoes, String arq_tempos)
            throws java.io.IOException
    {
        this.x_min = x_min;
        this.x_max = x_max;
        this.num_execucoes = num_execucoes;

        log = new FileWriter( arq_tempos );
    }

    abstract protected void ordene(int[] v, int inicio, int fim, int processadores);
    abstract protected void ordene(int[] v, int inicio, int fim);

    public void doit()
    {
        try {
            long t_inicial = System.currentTimeMillis();
            for (int x = x_min; x <= x_max; x++) {
                System.out.println();
                System.out.println( "Experimento para x = " + x );
                final int N = (int) Math.pow(2, x);
                System.out.println( "N = " + N );
                int[] numeros_aleatorios = new int[N];
                GereNumeros(numeros_aleatorios);
                //System.out.println("Antes de ordenar:");
                //ImprimaNumeros(numeros_aleatorios);

                double tempo_total_exec = 0;

                for (int exec = 1; exec <= num_execucoes; exec++) {
                    System.out.println( "==> x = " + x + ": execução " + exec );

                    int[] numeros_ordenados = new int[N];
                    for (int i = 0; i < N; i++) numeros_ordenados[i] = numeros_aleatorios[i];

                    long t0 = System.currentTimeMillis();
                    ordene(numeros_ordenados, 0, N-1);
                    long tempo_exec = System.currentTimeMillis() - t0;
                    //System.out.println( "====> tempo de ordenaÃ§Ã£o = " + tempo_exec );
                    tempo_total_exec = tempo_total_exec + tempo_exec;
                    
                    /*
                    System.out.println("Depois de ordenar:");
                    ImprimaNumeros(numeros_ordenados);
                    if (OrdenacaoCorreta(numeros_ordenados))
                        System.out.println( "========> ordenaÃ§Ã£o OK" );
                    else
                        System.out.println( "========> erro na ordenaÃ§Ã£o!" );
                    */
                }
                double tempo_medio = tempo_total_exec / num_execucoes;
                System.out.println( "x, N, Tempo médio de execução" );
                String resultado = x + "; " + N + "; " + tempo_medio;
                System.out.println( resultado );
                log.write( resultado );
                log.write( '\n' );
            }
            log.close();
            long tempo_experimento = System.currentTimeMillis() - t_inicial;
            System.out.println( "Tempo de experimento = " + tempo_experimento );
        
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doit2()
    {
        try {
        	int x = x_min;
            long t_inicial = System.currentTimeMillis();

            int numero_processadores = Runtime.getRuntime().availableProcessors();
            
            final int N = (int) Math.pow(2, x);
            System.out.println( "N = " + N );
            int[] numeros_aleatorios = new int[N];
            GereNumeros(numeros_aleatorios);
           
            int[] numeros_ordenados_aux = new int[N];
            for (int z = 0; z < N; z++) numeros_ordenados_aux[z] = numeros_aleatorios[z];
            
            long t0_aux = System.currentTimeMillis();
            QuickSortSequencial.ordene(numeros_ordenados_aux, 0, N-1);
            long tempo_exec_sequencial = System.currentTimeMillis() - t0_aux;
            
            for (int p = 1; p <= numero_processadores; p++) {
                System.out.println();
                System.out.println( "Experimento para x = " + x );
                //System.out.println("Antes de ordenar:");
                //ImprimaNumeros(numeros_aleatorios);

                double tempo_total_exec = 0;

                for (int exec = 1; exec <= num_execucoes; exec++) {
                    System.out.println( "==> x = " + p + ": execução " + exec );

                    int[] numeros_ordenados = new int[N];
                    for (int z = 0; z < N; z++) numeros_ordenados[z] = numeros_aleatorios[z];

                    long t0 = System.currentTimeMillis();
                    ordene(numeros_ordenados, 0, N-1, p);
                    long tempo_exec = System.currentTimeMillis() - t0;
                    //System.out.println( "====> tempo de ordenaÃ§Ã£o = " + tempo_exec );
                    tempo_total_exec = tempo_total_exec + tempo_exec;
                    
                    /*
                    System.out.println("Depois de ordenar:");
                    ImprimaNumeros(numeros_ordenados);
                    if (OrdenacaoCorreta(numeros_ordenados))
                        System.out.println( "========> ordenaÃ§Ã£o OK" );
                    else
                        System.out.println( "========> erro na ordenaÃ§Ã£o!" );
                    */
                }
                
                double tempo_medio = tempo_total_exec / num_execucoes;
                
                System.out.println( "x, N, Tempo médio de execução, Tempo Sequencial, speedup, eficiencia");
                double speedup = tempo_exec_sequencial / tempo_medio;
                double eficiencia = speedup / p;
                String resultado = p + "; " + N + "; " + tempo_medio + "; " + tempo_exec_sequencial + "; " + speedup + "; " + eficiencia;
                // resultado = resultado.replace('.', ',');
                System.out.println( resultado );
                log.write( resultado );
                log.write( '\n' );
            }
            log.close();
            long tempo_experimento = System.currentTimeMillis() - t_inicial;
            System.out.println( "Tempo de experimento = " + tempo_experimento );
        
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    protected void GereNumeros(int[] numeros)
    {
        Random aleatorio = new Random();
        int N = numeros.length;
        for (int i=0; i<N; i++)
            numeros[i] = aleatorio.nextInt(N);
    }

    protected void ImprimaNumeros(int[] numeros)
    {
        int N = numeros.length;
        for (int i=0; i<N; i++)
        {
            System.out.print("[" + i + "] = " + numeros[i] + ", ");
            if (i%4 == 3) System.out.println();
        }
        System.out.println();
    }

    protected boolean OrdenacaoCorreta(int[] numeros)
    {
        int N = numeros.length;
        int i = 1;
        boolean OK = true;
        while (i < N && OK)
        {
            if (numeros[i-1] > numeros[i])
                OK = false;
            else
                i++;
        }
        return OK;
    }
}
