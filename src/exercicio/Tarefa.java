package exercicio;
import java.util.Random;
import java.util.concurrent.*;

public class Tarefa extends Thread {
	private int ID;
	private Semaphore conclusao;
	private double[][] a;
	private double[][] b;
	private double[][] c;
	private int start, end;
	
	public Tarefa(int ID, Semaphore conclusao, double[][] a, double[][] b, double[][] c, int start, int end)
	{
		this.ID = ID;
		this.conclusao = conclusao;
		this.a = a;
		this.b = b;
		this.c = c;
		this.start= start;
		this.end = end;
	}
	
	public void run()
	{
		Calculo calc = new Calculo(a, b, c, start, end);
		calc.execute();
		conclusao.release();
	}
}
