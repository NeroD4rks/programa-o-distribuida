package ex_parte1;

public class main_admin {

	public static void main(String[] args) {
		Administrador admin = new Administrador("127.0.0.1", 5000);
		admin.startConn();
		admin.showHistory();

		//admin.changeTmax(10000);
		//admin.showTMax();
	}

}
