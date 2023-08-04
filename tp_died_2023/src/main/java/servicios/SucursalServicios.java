package servicios;

import clases.*;
import dao.StockProductoRepository;
import dao.SucursalRepository;

import java.sql.SQLException;
import java.util.List;

public class SucursalServicios {
    private SucursalRepository sucursalRepository;
    private StockProductoRepository stockProductoRepository;
    public SucursalServicios(){
        super();
        sucursalRepository = SucursalRepository.getInstance();
        stockProductoRepository = StockProductoRepository.getInstance();
    }

    public Sucursal buscarSucursal(int id ) throws SQLException {
        return sucursalRepository.buscarSucursal(id);
    }
    public Sucursal buscarSucursal(String nombre ) throws SQLException {
        return sucursalRepository.buscarSucursal(nombre);
    }

    public List<Sucursal> buscarSucursalesEstado(Estado estado) throws SQLException {
        boolean e;
        if(estado == Estado.OPERATIVO){
            e=true;
        }
        else{
            e=false;
        }

        return sucursalRepository.buscarSucursal(e);
    }

    public void agregarSucursal(Sucursal sucursal) throws SQLException {
        sucursalRepository.altaSucursal(sucursal);
    }
    public void borrarSucursal(Sucursal sucursal) throws SQLException {

        sucursalRepository.bajaSucursal(sucursal);
    }

    public List<Sucursal> listarSucursales() throws SQLException {
        return sucursalRepository.listarSucursal();
    }
    public void modificarEstado(Sucursal sucursal){ sucursalRepository.modificarEstado(sucursal.getId(),sucursal.getEstado());}

    public void editarSucursal(Sucursal sucursal){
        sucursalRepository.editarSucursal(sucursal);
    }

    public void altaStockProducto( Producto producto,Sucursal sucursal, int cantidad) throws SQLException {
        if(stockProductoRepository.altaStockProducto(producto.getId(), sucursal.getId(), cantidad)){
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }

    }
    public void borrarStockProducto(Producto producto,Sucursal sucursal) throws SQLException {
        if(stockProductoRepository.bajaStockProductoEnSucursal(producto.getId(), sucursal.getId())){
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }
    }
    public void editarStockProducto(Producto producto,Sucursal sucursal, int cantidad) throws SQLException {
        stockProductoRepository.editarStockProductoEnSucursal(producto.getId(), sucursal.getId(),cantidad);
        if( stockProductoRepository.editarStockProductoEnSucursal(producto.getId(), sucursal.getId(),cantidad)){
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }
    }
    public List<StockProducto> listarStockProducto(Sucursal sucursal) throws SQLException {
        return stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId());
    }
    public StockProducto buscarStockProducto(Producto producto, Sucursal sucursal){
        return stockProductoRepository.buscarEnSucursal(producto.getId(), sucursal.getId());
    }
    public  int cantidadStockProducto(Producto producto, Sucursal sucursal){
        StockProducto stockProducto= stockProductoRepository.buscarEnSucursal(producto.getId(),sucursal.getId());
        int aux=0;
        if(stockProducto != null){ aux= stockProducto.getCantidad();}
        return aux;

    }

}

