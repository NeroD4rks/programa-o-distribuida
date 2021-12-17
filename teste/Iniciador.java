package teste;
import java.util.concurrent.*;

public class Iniciador {
	
	public static long execute_sequencial(long tamSequencia) throws InterruptedException
	{
		Calculo c = new Calculo(tamSequencia);
		long T_inicio = System.currentTimeMillis();
		c.execute();
		long T_fim = System.currentTimeMillis();
		long T_exec = T_fim - T_inicio;
		return T_exec;
	}
	
	
	public static long execute_paralelo(long tamSequencia, int numTarefas) throws InterruptedException
	{
		long tamSequenciaPorTarefa = tamSequencia / numTarefas;
		Semaphore conclusao = new Semaphore(1 - numTarefas);
		
		Tarefa[] tarefa = new Tarefa[numTarefas];
		for (int i=0; i<numTarefas; i++)
			tarefa[i] = new Tarefa(i, tamSequenciaPorTarefa, conclusao);
		
		//ExecutorService exec = Executors.newFixedThreadPool(numTarefas);
		
		long T_inicio = System.currentTimeMillis();
		for (int i=0; i<numTarefas; i++)
			//exec.execute(tarefa[i]);
			tarefa[i].start();
		conclusao.acquire();
		long T_fim = System.currentTimeMillis();
		//exec.shutdown();
		long T_exec = T_fim - T_inicio;
		return T_exec;
	}
	
	public static void main(String[] args) throws InterruptedException {

		final int tarefasPorProcessador = 1;
		final int totalProcessadores = Runtime.getRuntime().availableProcessors();
		final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
		
		System.out.println("Total de processadores: " + totalProcessadores);
		System.out.println("Número de tarefas: " + numTarefas);
		System.out.println("Tarefas por processador: " + tarefasPorProcessador);

		final int min = 15;
		final int max = 28;

		String SEP = ", ";
		System.out.println("N" + SEP + "Tamanho da Sequência" + SEP + "Tempo Sequencial" + SEP +
				"Tempo Paralelo" + SEP + "Razão entre os Tempos");

		for (int N=min; N<=max; N++)
		{
			long tamSequencia = (long) Math.pow(2,N);
			long T_exec_sequencial = execute_sequencial(tamSequencia);
			long T_exec_paralelo = execute_paralelo(tamSequencia, numTarefas);
			double razao = (double) T_exec_sequencial / T_exec_paralelo;

			System.out.println(N + SEP + tamSequencia + SEP + T_exec_sequencial + SEP +
					T_exec_paralelo + SEP + razao);
		}	
	}
}