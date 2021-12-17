package exercicio;
import java.util.Random;
import java.util.concurrent.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Iniciador {
	
	public static void preencheMatriz(double[][] matriz) {
		Random r = new Random();
		for(int i=0; i< matriz.length; i++) {
			for(int j=0; j< matriz[0].length; j++)matriz[i][j]= r.nextDouble();
		}
	}
	
	public static long execute_sequencial(double[][] a, double[][] b, double[][] c) throws InterruptedException
	{	
		
		Calculo obj_calc = new Calculo(a, b, c,0, c.length*c[0].length);
		long T_inicio = System.currentTimeMillis();
		obj_calc.execute();
		long T_fim = System.currentTimeMillis();
		long T_exec = T_fim - T_inicio;
		return T_exec;
	}
	
	
	public static long execute_paralelo(double[][] a, double[][] b, double[][] c, int numTarefas) throws InterruptedException
	{
		int tamSequenciaPorTarefa = (c.length * c[0].length) / numTarefas;
		
		Semaphore conclusao = new Semaphore(1 - numTarefas);
		Tarefa[] tarefa = new Tarefa[numTarefas];
		int start=0, end = tamSequenciaPorTarefa;
		for (int i=0; i<numTarefas; i++) {
			tarefa[i] = new Tarefa(i, conclusao, a, b, c, start, end);
			start+= tamSequenciaPorTarefa;
			end += tamSequenciaPorTarefa;
		}
		
		long T_inicio = System.currentTimeMillis();
		for (int i=0; i<numTarefas; i++)
			tarefa[i].start();
		
		conclusao.acquire();
		long T_fim = System.currentTimeMillis();
		//exec.shutdown();
		long T_exec = T_fim - T_inicio;
		return T_exec;
	}
	
	public static void gravar_dados_desempenho(String nme_arqv) throws InterruptedException, IOException {
		System.out.println("Iniciando a gravar_dados_desempenho");
		final int tarefasPorProcessador = 1;
		final int totalProcessadores = Runtime.getRuntime().availableProcessors();
		final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
		FileWriter arq = new FileWriter("C:\\Users\\henri\\eclipse-workspace\\ProdutoConsumidorThread\\"+ nme_arqv);
	    PrintWriter gravarArq = new PrintWriter(arq);
	    		
	    int[] m = new int[4];
	    m[0] =1000;
	    m[1] =2000;
	    m[2] =1000;
	    m[3] =2000;
	    
	    int[] k = new int[4];
	    k[0] =1000;
	    k[1] =2000;
	    k[2] =2000;
	    k[3] =4000;
	    
	    
	    int[] n = new int[4];
	    n[0] =1000;
	    n[1] =2000;
	    n[2] =1000;
	    n[3] =2000;
	    
	    for(int op=0; op<4; op++) {
			double[][] matrizA = new double[m[op]][k[op]];
			double[][] matrizB = new double[k[op]][n[op]];
			double[][] matrizC = new double[m[op]][n[op]];
			preencheMatriz(matrizA);
			preencheMatriz(matrizB);
			long qtde = (m[op]*n[op]*k[op]);
			long T_exec_paralelo = execute_paralelo(matrizA, matrizB, matrizC, numTarefas);
			System.out.printf("%d		%d * %d * %d \n", T_exec_paralelo, m[op],n[op], k[op]);
			gravarArq.printf("%d		%d * %d * %d \n", T_exec_paralelo, m[op],n[op], k[op]);
	    }
		arq.close();
		
	}

	
	public static void gravar_dados_speed_up(int n, int m, int k, String nme_arqv) throws InterruptedException, IOException {
		System.out.println("Iniciando a gravar_dados_speed_up");
		double[][] matrizA = new double[m][k];
		double[][] matrizB = new double[k][n];
		double[][] matrizC = new double[m][n];
		preencheMatriz(matrizA);
		preencheMatriz(matrizB);
		
		final int totalProcessadores = Runtime.getRuntime().availableProcessors();
		
		FileWriter arq = new FileWriter("C:\\Users\\henri\\eclipse-workspace\\ProdutoConsumidorThread\\"+ nme_arqv);
	    PrintWriter gravarArq = new PrintWriter(arq);

    	System.out.println("T_paralelo\t\t T_sequencial \t\t speedup \t\t processadores \t\t eficiencia\n");
		gravarArq.printf("T_paralelo\t\t T_sequencial \t\t speedup \t\t processadores \t\t eficiencia\n");
	    for(int i=1; i< 9; i ++) {
			long T_exec_paralelo = execute_paralelo(matrizA, matrizB, matrizC, i);
			long T_exec_sequencial = execute_sequencial(matrizA, matrizB, matrizC);
			double razao = (double) T_exec_sequencial / T_exec_paralelo;
			double efc = razao/i;
			System.out.printf("%d \t\t %d \t\t %f \t\t %d \t\t %f \n", T_exec_paralelo, T_exec_sequencial, razao, i, efc);
			gravarArq.printf("%d\t\t %d \t\t %f \t\t %d \t\t %f \n", T_exec_paralelo, T_exec_sequencial, razao, i, efc);
		}
		
	    arq.close();
		
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		gravar_dados_desempenho("dados1.txt");
		gravar_dados_speed_up(1000, 1000, 1000, "dados4.txt");
		
	}
}