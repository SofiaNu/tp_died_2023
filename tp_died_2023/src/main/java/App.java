import clases.Producto;
import dao.Conexion;
import dao.ProductoRepositoryImpl;
import gui.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		/*Conexion conexion= dao.Conexion.getInstance();
		conexion.abrir();
		conexion.cerrar();
		ProductoRepositoryImpl productoRepository = new ProductoRepositoryImpl();
		Producto producto = new Producto(2,"Computadora",500.0, 2.0F);
		productoRepository.bajaProducto(2);*/
	}


}
