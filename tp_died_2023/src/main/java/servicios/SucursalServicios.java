package servicios;

import clases.Sucursal;
import dao.SucursalRepository;

import java.sql.SQLException;

public class SucursalServicios {
    private SucursalRepository sucursalRepository;

    public SucursalServicios(){
        super();
        sucursalRepository = SucursalRepository.getInstance();

    }

    public Sucursal buscarSucursal(int id ) throws SQLException {
        return sucursalRepository.buscarSucursal(id);
    }
    public Sucursal buscarSucursal(String nombre ) throws SQLException {
        return sucursalRepository.buscarSucursal(nombre);
    }
    public void agregarSucursal(Sucursal sucursal) throws SQLException {
        sucursalRepository.altaSucursal(sucursal);
    }
    public void borrarSucursal(Sucursal sucursal) throws SQLException {
        sucursalRepository.bajaSucursal(sucursal);
    }

    public void editarSucursal(Sucursal sucursal){
        sucursalRepository.editarSucursal(sucursal);
    }

}

