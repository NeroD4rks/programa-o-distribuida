package atv;

public class main {

	public static void main(String[] args) {
		
		int[] lista = new int[5];
		lista[0] = 5;
		lista[1] = 10;
		lista[2] = 9;
		lista[3] = 8;
		lista[4] = 7;
		QuickSortSequencial.ordene(lista, 0, lista.length-1);
		for(int i=0;i<5;i++)
		System.out.println(lista[i]);
	}

}
