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

}
