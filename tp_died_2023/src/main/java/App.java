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
		productoRepository.altaProducto(producto);
		SucursalRepository sucursalRepository = SucursalRepository.getInstance();
		Sucursal sucursal = new Sucursal("Cordoba", LocalTime.now(),LocalTime.now(),true ,25500);
		sucursalRepository.altaSucursal(sucursal);
		sucursalRepository.buscarSucursal("Cordoba");
		sucursalRepository.listarSucursal();*/
		/*
		StockProductoRepository stockProductoRepository = StockProductoRepository.getInstance();

		 stockProductoRepository.editarStockProductoEnSucursal(3,5,8);
		 stockProductoRepository.listarStockProductosEnSucursal(5);
		 stockProductoRepository.buscarEnSucursal(3,3);*/
	}


}
