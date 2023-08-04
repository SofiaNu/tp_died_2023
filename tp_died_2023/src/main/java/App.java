import clases.*;
import dao.OrdenProvisionRepository;
import dao.ProductoRepository;
import dao.StockProductoRepository;
import dao.SucursalRepository;
import gui.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		/*ProductoRepository productoRepository = ProductoRepository.getInstance();
		Producto producto = new Producto("Computadora",500.0, 2.0F);
		productoRepository.altaProducto(producto);*/
		/*SucursalRepository sucursalRepository = SucursalRepository.getInstance();
		Sucursal sucursal = new Sucursal("Pepe", LocalTime.now(),LocalTime.now(), Estado.OPERATIVO ,25500);
		sucursalRepository.altaSucursal(sucursal);
		Sucursal mod = sucursalRepository.buscarSucursal(5);
		mod.setNombre("San Luis");
		sucursalRepository.editarSucursal(mod);

		//sucursalRepository.bajaSucursal(sucursalRepository.buscarSucursal(6));
		//sucursalRepository.buscarSucursal(1);
		//sucursalRepository.listarSucursal();

		StockProductoRepository stockProductoRepository = StockProductoRepository.getInstance();
*/
		//stockProductoRepository.altaStockProducto(7,5,20);
		//stockProductoRepository.editarStockProductoEnSucursal(4,5,10);
		//stockProductoRepository.bajaStockProductoEnSucursal(3,5);
		//stockProductoRepository.listarStockProductosEnSucursal(5);
		//stockProductoRepository.buscarEnSucursal(3,3);
		Producto prod1 = ProductoRepository.getInstance().buscarProducto(3);
		Producto prod2 = ProductoRepository.getInstance().buscarProducto(4);

		Sucursal sucursalEx = SucursalRepository.getInstance().buscarSucursal(1);
		OrdenProvision op = new OrdenProvision();
		op.setFecha(LocalDate.ofYearDay(2044, 22));
		op.setDestino(sucursalEx);
		ArrayList<ProductoProvisto> provisiones = new ArrayList<>();

		ProductoProvisto pp1 = new ProductoProvisto();
		pp1.setProducto(prod1);
		pp1.setOrdenProvision(op);
		pp1.setCantidad(100);
		ProductoProvisto pp2 = new ProductoProvisto();
		pp2.setProducto(prod2);
		pp2.setOrdenProvision(op);
		pp2.setCantidad(200);
		provisiones.add(pp1);
		provisiones.add(pp2);
		op.setListaProductos(provisiones);

		OrdenProvisionRepository.getInstance().altaOrdenProvision(op);
		if(op != null){
			OrdenProvision OP18 = OrdenProvisionRepository.getInstance().buscar(op.getId());
			System.out.println("almost");
		}
		System.out.println("fin");
//		OrdenProvision op = OrdenProvisionRepository.getInstance().buscar(9);
//		if(op != null){
//			System.out.println("op: " + op.getId() + " " + op.getFecha() + " dest" + op.getDestino());
//		}
//		List<ProductoProvisto> prodsprs = OrdenProvisionRepository.getInstance().productosProvistosDeOrden(op);
		//Sucursal mod = SucursalRepository.getInstance().buscarSucursal(100);
		//List<OrdenProvision> ordenes = OrdenProvisionRepository.getInstance().ordenesDeSucursal(mod);
		//OrdenProvisionRepository.getInstance().bajaOrden(op);
		//OrdenProvision op2 = OrdenProvisionRepository.getInstance().buscar(8);
		//System.out.println("op: " + op2.getId() + " " + op2.getFecha() + " dest" + op2.getDestino());
		System.out.println("fin");
	}


}
