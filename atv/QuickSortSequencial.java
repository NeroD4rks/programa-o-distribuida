package atv;

public class QuickSortSequencial {

    public static void ordene(int[ ] v, int inicio, int fim)
    {
        if(inicio < fim) {
        	int p = particao(v, inicio, fim);
        	ordene(v, inicio, p-1);
        	ordene(v, p+1, fim);
        }	
    }
    
    public static int particao(int[ ] v, int inicio, int fim) {
    	int pivo = v[fim];
    	int i = inicio;
    	for(int j=inicio; j < fim; j++) {
    		if (v[j]>pivo) continue;
    		int aux = v[j];
    		v[j]= v[i];
    		v[i] = aux;
    		i++;
    	}
    	int aux = v[i];
		v[i]= v[fim];
		v[fim] = aux;
    	return i;
    }

    
}
