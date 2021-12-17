package atv;

public class QuickSortParalelo extends Thread {
    private int[ ] v;
    private int inicio;
    private int fim;
    private int max_threads;
    private int nivel;
    private int min_numeros;

    public QuickSortParalelo(int[] v, int inicio, int fim, int max_threads, int nivel, int min_numeros) {
        this.v = v;
        this.inicio = inicio;
        this.fim = fim;
        this.max_threads = max_threads;
        this.nivel = nivel;
        this.min_numeros = min_numeros;
    }

    public void run() {
        int num_elementos = fim - inicio + 1;
        
        int num_threads_proximo_nivel = (int) Math.pow( 2, nivel + 1 );
        if (num_elementos >= min_numeros && num_threads_proximo_nivel <= max_threads) {
            int pivo = QuickSortSequencial.particao(v, inicio, fim);
            QuickSortParalelo m1 = new QuickSortParalelo(v, inicio, pivo-1, max_threads, nivel+1, min_numeros);
            QuickSortParalelo m2 = new QuickSortParalelo(v, pivo + 1, fim, max_threads, nivel+1, min_numeros);
            m1.start();
            m2.start();
            try {
                m1.join();
                m2.join();
            } catch (Exception e) {
            }
        } else {
            QuickSortSequencial.ordene(v, inicio, fim);
        }
        /*
        System.out.println();
        System.out.println("Fim de thread ...");
        System.out.println("nivel = " + nivel);
        System.out.println("inicio = " + inicio);
        System.out.println("fim = " + fim);
        System.out.println("número de elementos = " + num_elementos);
         */
    }
    
    
}
