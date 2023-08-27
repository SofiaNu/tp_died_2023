package servicios;

import clases.Camino;
import clases.Estado;
import dao.CaminoRepository;

import java.util.ArrayList;
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

    public List<Camino> listarCaminosOperativos(){
        return caminoRepository.listarOperativos();
    }
    public Camino buscarCamino(int id){
        return caminoRepository.buscarCamino(id);
    };

    public Camino buscarCamino(int origen, int destino){

        return caminoRepository.buscarCamino(origen,destino);
    }

    public void editarCamino(Camino camino){
        caminoRepository.editarCamino(camino);
    }

    public List<Camino> listarCaminosAModificarEstado(int idSucursal, Estado estado) {//si ingresa estado en operativo, significa que quiero que quede en esa posicion
        List<Camino> caminos = new ArrayList<>();
        if (estado == Estado.OPERATIVO){//quiero pasar a operativo, entonces tiene que estar como no operativa mi sucursal
            caminos = caminoRepository.AModificar(idSucursal, false);
        }else {//quiero pasar a no operativo, entonces tiene que estar como operativas ambas sucursales
            caminos = caminoRepository.AModificar(idSucursal, true);
        }
        return caminos;
    }

    public void modificarEstados(List<Camino> caminos) {
        for(int i=0;i<caminos.size();i++){
            caminoRepository.modificarEstado(caminos.get(i).getId(), caminos.get(i).getEstado());
        }
    }
}
