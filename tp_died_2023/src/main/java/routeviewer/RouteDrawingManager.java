package routeviewer;

import clases.Camino;
import clases.Sucursal;
import routeviewer.drawable.CaminoDrawable;
import routeviewer.drawable.SucursalDrawable;

import java.sql.Array;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class RouteDrawingManager {
    List<List<CaminoDrawable>> internalCaminoDrawables;
    List<SucursalDrawable> internalSucursalDrawables;

    SucursalDrawable sucursalDrawable = new SucursalDrawable(50,50, Color.ORANGE);
    CaminoDrawable caminoDrawable = new CaminoDrawable(100,100, 350, 350, Color.CYAN);
    CaminoDrawable caminoDrawable2 = new CaminoDrawable(350,350, 100, 100, Color.ORANGE);

    CaminoDrawable caminoDrawable3 = new CaminoDrawable(100,100, 350, 100, Color.CYAN);
    CaminoDrawable caminoDrawable4 = new CaminoDrawable(350,100, 100, 100, Color.ORANGE);
    CaminoDrawable caminoDrawable5 = new CaminoDrawable(100,100, 100, 350, Color.CYAN);
    CaminoDrawable caminoDrawable6 = new CaminoDrawable(100,350, 100, 100, Color.ORANGE);
    private int height;
    private int width;

    public RouteDrawingManager(){
        this.internalCaminoDrawables = new ArrayList<List<CaminoDrawable>>();
        this.internalSucursalDrawables = new ArrayList<SucursalDrawable>();
    }


    public void setSize(int w, int h){
        height = h;
        width = w;
    }

    public void drawContents(Graphics2D g, int w, int h){
        //AA
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        setSize(w, h);
//        g.setColor(new Color(66, 75, 84));
//        g.fillRect(0,0, width, height);
//        sucursalDrawable.draw(g);
        clearCanvas(g);
        sucursalDrawable.draw(g);
        caminoDrawable.draw(g);
        caminoDrawable2.draw(g);
        caminoDrawable3.draw(g);
        caminoDrawable4.draw(g);
        caminoDrawable5.draw(g);
        caminoDrawable6.draw(g);
        drawCaminos(g);
        drawSucursales(g);
    }

    private void drawSucursales(Graphics2D g){
        for(SucursalDrawable sucursalDrawable : internalSucursalDrawables){
            sucursalDrawable.draw(g);
        }
    }

    private void drawCaminos(Graphics2D g){
        for(List<CaminoDrawable> recorrido : internalCaminoDrawables){
            for(CaminoDrawable caminoDrawable : recorrido){
                caminoDrawable.draw(g);
            }
        }
    }

    // or draw the background

    private void clearSection(Graphics2D g, int w, int h){
        g.setColor(new Color(66, 75, 84));
        g.fillRect(0,0, w, h);
    }

    public void setCaminosToDraw(List<List<Camino>> recorridos){
        clearInternalData();
        for (List<Camino> recorrido : recorridos) {
            //create caminos drawable
            createDrawablesFromRecorrido(recorrido);
            //create sucursale drawable (if not exists)
        }
    }
    private void clearInternalData(){
        this.internalCaminoDrawables = new ArrayList<List<CaminoDrawable>>();
        this.internalSucursalDrawables = new ArrayList<SucursalDrawable>();
    }
    private void createDrawablesFromRecorrido(List<Camino> recorrido){
        List<CaminoDrawable> nuevosCaminos = new ArrayList<>();
        for(Camino camino : recorrido){
            if(camino == null) {
                System.out.println("Camino null!");
                System.exit(-1);
            }
            SucursalDrawable origenSucFromCamino = createIfNotExistsSucursalDrawable(camino.getOrigen());
            SucursalDrawable destinoSucFromCamino = createIfNotExistsSucursalDrawable(camino.getDestino());

            // dummy positions
            CaminoDrawable caminoDrawable = new CaminoDrawable(0,0,100,100, Color.CYAN);
            caminoDrawable.setOrigen(origenSucFromCamino);
            caminoDrawable.setDestino(destinoSucFromCamino);
            nuevosCaminos.add(caminoDrawable);
        }

        internalCaminoDrawables.add(nuevosCaminos);
    }

    //se inserta a la lista interna si no existe
    private SucursalDrawable createIfNotExistsSucursalDrawable(Sucursal sucursal){
        SucursalDrawable sucursalDrawable;
        Optional<SucursalDrawable> optionalSuc = internalSucursalDrawables.stream().filter(s -> s.getSucursal().getId() == sucursal.getId()).findFirst();
        if(optionalSuc.isPresent()) {
            return optionalSuc.get();
        }

        sucursalDrawable = new SucursalDrawable(0, 0, Color.ORANGE);
        sucursalDrawable.setSucursal(sucursal);
        internalSucursalDrawables.add(sucursalDrawable);
        return sucursalDrawable;
    }

    private void setDrawableInitialPositions(){

    }

    private void clearCanvas(Graphics2D g){
        clearSection(g, this.width, this.height);
    }

    // relates CaminoDrawable to SucursalDrawables

}

