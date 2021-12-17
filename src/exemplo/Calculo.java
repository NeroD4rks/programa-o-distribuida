package exemplo;

public class Calculo {
	private double[][] a;
	private double[][] b;
	private double[][] c;
	private int start, end;
	
	
	public Calculo(double[][] a, double[][] b, double[][] c, int start, int end)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.start= start;
		this.end = end;
	}
	
	public void execute()
	{		
		int l_point = 0;
		int c_point = 0;
		for (int i = start; i < end; i++) {
			l_point = start / c.length;
			c_point = start % c.length;
			for (int k = 0; k < a[0].length; k++) {
					c[l_point][c_point] += (a[l_point][k] * b[k][c_point]);
			}
	    }	
	}
}
