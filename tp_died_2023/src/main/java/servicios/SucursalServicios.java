package servicios;

import clases.Estado;
import clases.Sucursal;
import dao.SucursalRepository;

import java.sql.SQLException;
import java.util.List;

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

    public List<Sucursal> buscarSucursal(Estado estado) throws SQLException {
        boolean e;
        if(estado == Estado.OPERATIVO){
            e=true;
        }
        else{
            e=false;
        }
        return null;
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
    public void editarSucursal(Sucursal sucursal){
        sucursalRepository.editarSucursal(sucursal);
    }

}

