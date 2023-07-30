package servicios;

import clases.Camino;
import clases.Sucursal;
import dao.CaminoRepository;
import gui.Inicio;

import java.sql.SQLException;
import java.util.List;

public class CaminoServicios {
    CaminoRepository caminoRepository;
    public CaminoServicios(){
        super();
        caminoRepository= CaminoRepository.getInstance();
    };

    public void altaCamino(Camino camino){}

    public void bajaCamino(Camino camino){
        //CAMBIA ESTADO A NO OPERATIVO!
    };

    public List<Camino> listarCaminos(){
        return null;
    };

    public Camino buscarCamino(int id){
        return null;
    };

    public Camino buscarCamino(Sucursal origen, Sucursal destino){
        return null;
    }

    public void editarCamino(Camino camino){
    }
}
