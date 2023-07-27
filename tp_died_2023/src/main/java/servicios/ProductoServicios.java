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
    //funciona
    public Producto buscarProducto(String nombre) throws  SQLException{
        return productoRepository.buscarProducto(nombre);
    }

    //funciona
    public Producto buscarProducto(int id ) throws SQLException {
        return productoRepository.buscarProducto(id);
    }
    //funciona
    public void agregarProducto(Producto producto) throws SQLException {
        productoRepository.altaProducto(producto);
    }
    //funciona
    public void borrarProducto(int producto) throws SQLException {
        productoRepository.bajaProducto(producto);
    }

    public void editarProducto(Producto producto){
        productoRepository.editarProducto(producto);
    }

}
