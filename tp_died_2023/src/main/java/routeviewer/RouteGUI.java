package routeviewer;

import clases.Camino;
import clases.OrdenProvision;
import clases.Sucursal;
import servicios.OrdenProvisionServicios;
import servicios.SucursalServicios;
import servicios.gestionOrden;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class RouteGUI extends JPanel {

    RouteDrawingManager routeDrawingManager;
    public RouteGUI(){

        List<List<Camino>> caminos = new ArrayList<List<Camino>>();
        List<List<Sucursal>> rutasSucursal = new ArrayList<List<Sucursal>>();
        //OrdenProvisionServicios ops = new OrdenProvisionServicios();

        try{
            //gestionOrden go = new gestionOrden();
            //OrdenProvision orden = ops.buscarOrden(5);
            //go.generarResultadosValidos(orden, rutasSucursal, caminos);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.black);
        routeDrawingManager = new RouteDrawingManager();
        //routeDrawingManager.setSize(getWidth(), getHeight());
        caminos = routeDrawingManager.crearPruebasQM();
        routeDrawingManager.setCaminosToDraw(caminos);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        routeDrawingManager.drawContents(g2d, getWidth(), getHeight());
    }
}
