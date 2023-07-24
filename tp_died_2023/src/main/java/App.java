import dao.Conexion;
import gui.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		Conexion conexion= dao.Conexion.getInstance();
		conexion.abrir();
		conexion.cerrar();
	}


}
