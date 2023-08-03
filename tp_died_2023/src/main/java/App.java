import clases.Estado;
import clases.Producto;
import clases.StockProducto;
import clases.Sucursal;
import dao.ProductoRepository;
import dao.StockProductoRepository;
import dao.SucursalRepository;
import gui.*;

import java.sql.SQLException;
import java.time.LocalTime;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		/*ProductoRepository productoRepository = ProductoRepository.getInstance();
		Producto producto = new Producto("Computadora",500.0, 2.0F);
		productoRepository.altaProducto(producto);*/
		SucursalRepository sucursalRepository = SucursalRepository.getInstance();
		Sucursal sucursal = new Sucursal("Pepe", LocalTime.now(),LocalTime.now(), Estado.OPERATIVO ,25500);
		sucursalRepository.altaSucursal(sucursal);
		//sucursalRepository.bajaSucursal(sucursalRepository.buscarSucursal(6));
		//sucursalRepository.buscarSucursal(1);
		//sucursalRepository.listarSucursal();

		StockProductoRepository stockProductoRepository = StockProductoRepository.getInstance();

		//stockProductoRepository.altaStockProducto(7,5,20);
		//stockProductoRepository.editarStockProductoEnSucursal(4,5,10);
		//stockProductoRepository.bajaStockProductoEnSucursal(3,5);
		//stockProductoRepository.listarStockProductosEnSucursal(5);
		//stockProductoRepository.buscarEnSucursal(3,3);
	}


}
