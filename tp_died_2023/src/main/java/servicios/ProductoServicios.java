package servicios;
import clases.*;
import dao.ProductoRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductoServicios {
    private ProductoRepository productoRepository;

    public ProductoServicios(){
        super();
        productoRepository = ProductoRepository.getInstance();

    };
    public List<Producto> listarProductos() throws SQLException {
        return productoRepository.listarProductos();
    }
    public Producto buscarProducto(String nombre){
        return productoRepository.buscarProducto(nombre);
    }

    public Producto buscarProducto(int id ) throws SQLException {
        return productoRepository.buscarProducto(id);
    }

    public void agregarProducto(Producto producto) throws SQLException {
        productoRepository.altaProducto(producto);
    }

    public void borrarProducto(Producto producto) throws SQLException {
        productoRepository.bajaProducto(producto);
    }

    public void editarProducto(Producto producto){
        productoRepository.editarProducto(producto);
    }

}
