package servicios;
import clases.*;
import dao.ProductoRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductoServicios {
    private ProductoRepository productoRepository;

    public ProductoServicios(){
        super();
    };
    public List<Producto> listarProductos() throws SQLException {
        productoRepository = ProductoRepository.getInstance();
        return productoRepository.listarProductos();
    }
    public Producto buscarProducto(String nombre){
        productoRepository = ProductoRepository.getInstance();
        return productoRepository.buscarProducto(nombre);
    }

    public Producto buscarProducto(int id ) throws SQLException {
        productoRepository = ProductoRepository.getInstance();
        return productoRepository.buscarProducto(id);
    }

    public void agregarProducto(Producto producto) throws SQLException {
        productoRepository = ProductoRepository.getInstance();
        productoRepository.altaProducto(producto);
    }

    public void borrarProducto(Producto producto) throws SQLException {
        productoRepository = ProductoRepository.getInstance();
        productoRepository.bajaProducto(producto);
    }

}
