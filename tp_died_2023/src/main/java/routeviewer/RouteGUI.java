package routeviewer;

import clases.Camino;
import clases.Sucursal;

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
        routeDrawingManager.setShouldSelectFirstOnListAfterSettingCaminos(true);
        //routeDrawingManager.setSize(getWidth(), getHeight());
        caminos = routeDrawingManager.crearPruebasQM();
        routeDrawingManager.setCaminosToDraw(caminos);
    }

    public List<List<Camino>> getPruebasCam(){
        return routeDrawingManager.crearPruebasQM();
    }

    public void setSelectedIndex(int index){
        routeDrawingManager.setSelectedRecorridoIndex(index);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        routeDrawingManager.drawContents(g2d, getWidth(), getHeight());
    }
}
