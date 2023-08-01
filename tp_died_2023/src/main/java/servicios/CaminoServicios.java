package servicios;

import clases.Camino;
import dao.CaminoRepository;

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

    public void caminoNoOperativo(Camino camino){
        caminoRepository.setNoOperativoCamino(camino.getId());
    };

    public void modificarEstado(Camino camino){ caminoRepository.modificarEstado(camino.getId(),camino.getEstado());}
    public void bajaCamino(Camino camino){
        caminoRepository.bajaCamino(camino.getId());
    };

    public List<Camino> listarCaminos(){
        return caminoRepository.listarCaminos();
    };

    public Camino buscarCamino(int id){
        return caminoRepository.buscarCamino(id);
    };

    public Camino buscarCamino(int origen, int destino){

        return caminoRepository.buscarCamino(origen,destino);
    }

    public void editarCamino(Camino camino){
        caminoRepository.editarCamino(camino);
    }
}
