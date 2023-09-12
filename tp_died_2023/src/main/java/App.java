import clases.*;
import connectionpool.ConnectionPool;
import dao.OrdenProvisionRepository;
import dao.ProductoRepository;
import dao.StockProductoRepository;
import dao.SucursalRepository;
import gui.*;
import servicios.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ConnectionPool.setup();
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		//gestionOrden ordens = new gestionOrden();
		//ordens.prueba();
		//SucursalServicios sucursalServicios = new SucursalServicios();
		//Sucursal sucursal = sucursalServicios.buscarSucursal(7);
		//sucursalServicios.pageRank();
		//PageRank pageRank ne

	}


}
