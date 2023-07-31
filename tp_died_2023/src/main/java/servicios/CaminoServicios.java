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

    public void altaCamino(Camino camino){
        caminoRepository.altaCamino(camino);
    }

    public void bajaCamino(Camino camino){
        caminoRepository.bajaCamino(camino.getId());
    };

    public List<Camino> listarCaminos(){
        return caminoRepository.listarCaminos();
    };

    public Camino buscarCamino(int id){
        return caminoRepository.buscarCamino(id);
    };

    public Camino buscarCamino(Sucursal origen, Sucursal destino){

        return caminoRepository.buscarCamino(origen,destino);
    }

    public void editarCamino(Camino camino){
        caminoRepository.editarCamino(camino);
    }
}
