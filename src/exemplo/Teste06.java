package exemplo;
import java.util.Random;
import java.util.concurrent.Semaphore;

import exercicio.Tarefa;

public class Teste06 {
	public static void preencheMatriz(double[][] matriz) {
		Random r = new Random();
		for(int i=0; i< matriz.length; i++) {
			for(int j=0; j< matriz[0].length; j++)matriz[i][j]= r.nextInt(5);
		}
	}
	public static void printarMatriz(double[][] matriz) {
		for(int i=0; i< matriz.length; i++) {
			System.out.print("[ ");
			for(int j=0; j< matriz[0].length; j++)System.out.print(", " + matriz[i][j]);
			System.out.print("] \n");
		}
	}
	
	public static void produtoMatriz(double[][] c, double[][] a, double[][] b) {
		
		 if (a[0].length != b.length) throw new RuntimeException("Dimensões inconsistentes. Impossível multiplicar as matrizes");
    
	    for (int i = 0; i < a.length; i++) {
	        for (int j = 0; j < b[0].length; j++) {
	            for (int k = 0; k < a[0].length; k++) c[i][j] += (a[i][k] * b[k][j]);
	            }
	    }
	}
	
	
	public static void main(String[] args) {
		int n=3, m=3, k=3;
		final int tarefasPorProcessador = 1;
		final int totalProcessadores = Runtime.getRuntime().availableProcessors();
		final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
		final int celulas = (m*n)/numTarefas;
		System.out.println("Total de processadores: " + totalProcessadores);
		System.out.println("Nmr de tarefas: " + numTarefas);
		System.out.println("Tarefas por processador: " + tarefasPorProcessador);
		System.out.println("celulas por processador: " + celulas);
		int x = 2000 * 2000;
		System.out.println("celulas por processador: " + x);
		
		double[][] matrizA = new double[m][k];
		double[][] matrizB = new double[k][n];
		double[][] matrizC = new double[m][n];
		preencheMatriz(matrizA);
		
		preencheMatriz(matrizB);
		
		int start=0, end = celulas;
		int resto = (matrizC.length*matrizC[0].length) - celulas; 
		for (int i=0; i<numTarefas; i++) {
			Calculo calc = new Calculo(matrizA, matrizB, matrizC,start, end);
			calc.execute();
			start+= celulas;
			end += celulas;
			
		}

		printarMatriz(matrizA);
		System.out.println("\n");
		printarMatriz(matrizB);

		System.out.println("\n");
		printarMatriz(matrizC);
		
	
	}
}
