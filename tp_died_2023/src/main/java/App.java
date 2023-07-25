import clases.Producto;
import dao.ProductoRepository;
import gui.*;

import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		/*ProductoRepository productoRepository = ProductoRepository.getInstance();
		Producto producto = new Producto("Computadora",500.0, 2.0F);
		productoRepository.altaProducto(producto); */
	}


}
