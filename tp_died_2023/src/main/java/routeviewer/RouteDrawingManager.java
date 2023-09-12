package routeviewer;

import clases.Camino;
import clases.Estado;
import clases.Sucursal;
import routeviewer.drawable.CaminoDrawable;
import routeviewer.drawable.SucursalDrawable;

import java.time.LocalTime;
import java.util.*;
import java.awt.*;
import java.util.List;

public class RouteDrawingManager {
    List<List<CaminoDrawable>> internalCaminoDrawables;
    List<SucursalDrawable> internalSucursalDrawables;

    int selectedRecorridoIndex = -1;

    SucursalDrawable sucursalDrawable = new SucursalDrawable(50,50, Color.ORANGE);
    CaminoDrawable caminoDrawable = new CaminoDrawable(100,100, 350, 350, Color.CYAN);
    CaminoDrawable caminoDrawable2 = new CaminoDrawable(350,350, 100, 100, Color.ORANGE);

    CaminoDrawable caminoDrawable3 = new CaminoDrawable(100,100, 350, 100, Color.CYAN);
    CaminoDrawable caminoDrawable4 = new CaminoDrawable(350,100, 100, 100, Color.ORANGE);
    CaminoDrawable caminoDrawable5 = new CaminoDrawable(100,100, 500, 375, Color.CYAN);
    CaminoDrawable caminoDrawable6 = new CaminoDrawable(0,0, 490, 490, Color.ORANGE);
    private int height = 500;
    private int width = 500;

    public boolean isShouldSelectFirstOnListAfterSettingCaminos() {
        return shouldSelectFirstOnListAfterSettingCaminos;
    }

    public void setShouldSelectFirstOnListAfterSettingCaminos(boolean shouldSelectFirstOnListAfterSettingCaminos) {
        this.shouldSelectFirstOnListAfterSettingCaminos = shouldSelectFirstOnListAfterSettingCaminos;
    }

    private boolean shouldSelectFirstOnListAfterSettingCaminos = false;


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
//        sucursalDrawable.draw(g);
//        caminoDrawable.draw(g);
//        caminoDrawable2.draw(g);
//        caminoDrawable3.draw(g);
//        caminoDrawable4.draw(g);
//        caminoDrawable5.draw(g);
//        caminoDrawable6.draw(g);
        drawCaminos(g);
        drawSucursales(g);
        drawTextLabels(g);
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

    private void drawTextLabels(Graphics2D g){
        for(SucursalDrawable sucursalDrawable : internalSucursalDrawables){
            sucursalDrawable.drawTextLabels(g);
        }
    }

    // or draw the background

    private void clearSection(Graphics2D g, int w, int h){
        g.setColor(new Color(66, 75, 84));
        g.fillRect(0,0, w, h);
    }

    public int getSelectedRecorridoIndex() {
        return selectedRecorridoIndex;
    }

    public void setSelectedRecorridoIndex(int index){
        if(index < 0) return;
        if(internalCaminoDrawables.size() < index) return;

        if(selectedRecorridoIndex == index) {
            System.out.println("Index not changed");
            return;
        }
        for(SucursalDrawable sd : internalSucursalDrawables){
            //resets selected
            sd.setSelected(false);
            sd.setSelectedString(null);
        }
        int currRecorridoIndex = 0;
        for(List<CaminoDrawable> recorrido : internalCaminoDrawables){
            int currCaminoIndex = 0;
            for(CaminoDrawable cd : recorrido){
                if(index == currRecorridoIndex){
                    cd.setSelected(true);

                    // <Should be moved to SucursalDrawable>
                    int origenSucursalNumber = currCaminoIndex;
                    int destinoSucursalNumber = currCaminoIndex + 1;
                    String origenSucursalStr = "[ " +  String.valueOf(origenSucursalNumber)+ " ]";
                    String destinoSucursalStr = "[ " +  String.valueOf(destinoSucursalNumber)+ " ]";;

                    if(origenSucursalNumber == 0){
                        origenSucursalStr = "[ I ]";
                    }
                    if(destinoSucursalNumber == recorrido.size()){
                        destinoSucursalStr = "[ F ]";
                    }

                    cd.getOrigen().setSelected(true);
                    cd.getOrigen().setSelectedString(origenSucursalStr);

                    cd.getDestino().setSelected(true);
                    cd.getDestino().setSelectedString(destinoSucursalStr);
                    //</Should be moved>

                }else {
                    cd.setSelected(false);
                }
                // mas obj, flecha en caminos o algo que marque dir quizas degradez o mas grueso hacia mas fino?
                // falta la gui de seeccionar, sera un dropdown y algo mas supongo
                // ademas de probar que la parte de backend sea compatible
                // nombres de suc
                // better layout strategy maybe si sobra el time
                // sleepy saturday, sunday rather
                // dios mio este doble loop es lo mas ineficiente que hice, es horrible de leer estaba dormido
                currCaminoIndex++;
            }
            currRecorridoIndex++;
        }

    }
    public void setSucursalesToDraw(List<Sucursal> sucursalesToConvert){
        for(Sucursal suc : sucursalesToConvert){
            createIfNotExistsSucursalDrawable(suc);
        }
    }
    public void setCaminosToDraw(List<List<Camino>> recorridos){
        clearInternalData(false);
        for (List<Camino> recorrido : recorridos) {
            //create caminos drawable
            createDrawablesFromRecorrido(recorrido);
            //create sucursale drawable (if not exists)
        }

        setDrawableInitialPositions();
        if(shouldSelectFirstOnListAfterSettingCaminos){
            setSelectedRecorridoIndex(0);
        }
    }
    private void clearInternalData(boolean clearSuc){
        if(clearSuc){
            this.internalSucursalDrawables = new ArrayList<SucursalDrawable>();
        }

        this.internalCaminoDrawables = new ArrayList<List<CaminoDrawable>>();
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
            CaminoDrawable caminoDrawable = new CaminoDrawable(0,0,300,300, Color.CYAN);
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

        sucursalDrawable = new SucursalDrawable(200, 200, Color.ORANGE);
        sucursalDrawable.setSucursal(sucursal);
        if(sucursal.getEstado() == Estado.NO_OPERATIVO){
            sucursalDrawable.setInactive(true);
        }
        internalSucursalDrawables.add(sucursalDrawable);
        return sucursalDrawable;
    }

    private void setDrawableInitialPositions(){
        //pos sucursales first
        int sucursalCount = internalSucursalDrawables.size();
        //int areaHeight = (int)(0.8 * this.height);
        //int areaWidth = (int)(0.8 * this.width);

        int xCellCount = (int) Math.sqrt(sucursalCount) + 1;
        int xCellSize = this.width / (xCellCount);
        int yCellCount = (int) Math.sqrt(sucursalCount) + 1;
        int yCellSize = this.height / (yCellCount);


        int currentXCellCenter = xCellSize / 2;
        int currentYCellCenter = yCellSize / 2;
        int currentSucursal = 0;
        boolean done = false;
        while(currentYCellCenter  < this.height && !done){
            while(currentXCellCenter < this.width && !done){
                if(currentSucursal >= sucursalCount ){
                    done = true;
                    break;
                }

                SucursalDrawable suc = internalSucursalDrawables.get(currentSucursal);
                suc.setY(currentYCellCenter - suc.getRADIUS_Y());
                suc.setX(currentXCellCenter - suc.getRADIUS_X());

                currentSucursal++;

                currentXCellCenter += xCellSize;
            }

            currentYCellCenter += yCellSize;
            currentXCellCenter = xCellSize / 2;
        }

        for (List<CaminoDrawable> recorrido : internalCaminoDrawables) {
            //create caminos drawable
            for(CaminoDrawable cd : recorrido){
                float x = cd.getOrigen().getX() + cd.getOrigen().getRADIUS_X();
                float y = cd.getOrigen().getY() + cd.getOrigen().getRADIUS_Y();
                float xf = cd.getDestino().getX() + cd.getOrigen().getRADIUS_X();
                float yf = cd.getDestino().getY() + cd.getOrigen().getRADIUS_Y();

                cd.setX(x);
                cd.setY(y);
                cd.setxF(xf);
                cd.setyF(yf);

            }
            //create sucursale drawable (if not exists)
        }
    }

    private void clearCanvas(Graphics2D g){
        clearSection(g, this.width, this.height);
    }

    // relates CaminoDrawable to SucursalDrawables
    public List<List<Camino>> crearPruebasQM (){
        ArrayList<List<Camino>> caminos = new ArrayList<>();

        int sucId = 1;
        Sucursal s1 = new Sucursal("Sucursal1", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s1.setId(sucId++);
        Sucursal s2 = new Sucursal("Sucursal2", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s2.setId(sucId++);
        Sucursal s3 = new Sucursal("Sucursal3", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s3.setId(sucId++);
        Sucursal s4 = new Sucursal("Sucursal4", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s4.setId(sucId++);
        Sucursal s5 = new Sucursal("Sucursal5", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s5.setId(sucId++);


        List<Camino> rec1 = new ArrayList<>();
        Camino r1_c1 = new Camino(s5,s3,10.0f, 50.0f, Estado.OPERATIVO);
        Camino r1_c2 = new Camino(s3,s4,10.0f, 50.0f, Estado.OPERATIVO);
        Camino r1_c3 = new Camino(s4,s1,10.0f, 50.0f, Estado.OPERATIVO);
        Collections.addAll(rec1, r1_c1, r1_c2, r1_c3);

        List<Camino> rec2 = new ArrayList<>();
        Camino r2_c1 = new Camino(s2,s3,10.0f, 50.0f, Estado.OPERATIVO);
        Camino r2_c2 = new Camino(s3,s5,10.0f, 50.0f, Estado.OPERATIVO);
        Camino r2_c3 = new Camino(s5,s1,10.0f, 50.0f, Estado.OPERATIVO);
        Collections.addAll(rec2, r2_c1, r2_c2, r2_c3);


        Collections.addAll(caminos, rec1, rec2);
        return caminos;
    }


    public List<Sucursal> obtenerSucursalesPruebaQM(){
        List<Sucursal> allSucursales;
        //allSucursales = sucursalesToConvert;
        allSucursales = new ArrayList<>();
        int sucId = 1;
        Sucursal s1 = new Sucursal("Sucursal1", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s1.setId(sucId++);
        Sucursal s2 = new Sucursal("Sucursal2", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s2.setId(sucId++);
        Sucursal s3 = new Sucursal("Sucursal3", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s3.setId(sucId++);
        Sucursal s4 = new Sucursal("Sucursal4", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s4.setId(sucId++);
        Sucursal s5 = new Sucursal("Sucursal5", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s5.setId(sucId++);
        Sucursal s6 = new Sucursal("Sucursal6", LocalTime.of(4,0), LocalTime.of(22,0),Estado.NO_OPERATIVO, 100.0f);
        s6.setId(sucId++);
        Sucursal s7 = new Sucursal("Sucursal7", LocalTime.of(4,0), LocalTime.of(22,0),Estado.OPERATIVO, 100.0f);
        s7.setId(sucId++);
        Collections.addAll(allSucursales, s1, s2, s3, s4, s5, s6, s7);
        return allSucursales;
    }
}

