package servicios;
import clases.*;
import dao.OrdenProvisionRepository;
import dao.ProductoRepository;

import java.sql.SQLException;
import java.util.List;

public class OrdenProvisionServicios {
    private ProductoRepository productoRepository;

    public OrdenProvisionServicios(){
        productoRepository = ProductoRepository.getInstance();

    };
    public List<OrdenProvision> listarOrdenes(Sucursal sucursal) throws SQLException {
        return OrdenProvisionRepository.getInstance().ordenesDeSucursal(sucursal);
    }

    public OrdenProvision buscarOrden(int id ) throws SQLException {
        return OrdenProvisionRepository.getInstance().buscar(id);
    }
    public void altaOrden(OrdenProvision ordenProvision) throws SQLException {
        OrdenProvisionRepository.getInstance().altaOrdenProvision(ordenProvision);
    }
    public void bajaOrden(OrdenProvision ordenProvision) throws Exception {
        if(ordenProvision == null || ordenProvision.getId() <= 0) throw new Exception("OrdenProvision invalida");
        OrdenProvisionRepository.getInstance().bajaOrden(ordenProvision);
    }

    public boolean asignarRecorridoAOrdenProvision(List<Camino> recorridoAAsignar, OrdenProvision ordenProvision) throws Exception{
        if(recorridoAAsignar == null || recorridoAAsignar.isEmpty() || ordenProvision == null || ordenProvision.getId()<= 0){
            throw new Exception("Ocurrio un error al intentar asignar un recorrido a la orden");
        }
        if(ordenProvision.getDestino() == null){
            System.out.println("op.getDestino not pop");
            throw new Exception("Ocurrio un error al intentar asignar un recorrido a la orden (Orden)");
        }
        Sucursal dest = ordenProvision.getDestino();
        int sucursalDestIdFromRecorrido = recorridoAAsignar.get(recorridoAAsignar.size() - 1).getDestino().getId();
        if(sucursalDestIdFromRecorrido != ordenProvision.getDestino().getId() ){
            System.out.println("Sucursal final != OP.SucursalFinal");
            throw new Exception("Ocurrio un error al intentar asignar un recorrido a la orden (Caminos)");
        }
        return OrdenProvisionRepository.getInstance().asignarRecorridoOrdenProvision(recorridoAAsignar, ordenProvision);
    }

}
