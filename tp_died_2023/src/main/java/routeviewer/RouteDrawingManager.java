package routeviewer;

import routeviewer.drawable.CaminoDrawable;
import routeviewer.drawable.SucursalDrawable;

import java.awt.*;

public class RouteDrawingManager {

    SucursalDrawable sucursalDrawable = new SucursalDrawable(50,50, Color.ORANGE);
    CaminoDrawable caminoDrawable = new CaminoDrawable(100,100, 350, 350, Color.CYAN);
    CaminoDrawable caminoDrawable2 = new CaminoDrawable(350,350, 100, 100, Color.ORANGE);

    CaminoDrawable caminoDrawable3 = new CaminoDrawable(100,100, 350, 100, Color.CYAN);
    CaminoDrawable caminoDrawable4 = new CaminoDrawable(350,100, 100, 100, Color.ORANGE);
    CaminoDrawable caminoDrawable5 = new CaminoDrawable(100,100, 100, 350, Color.CYAN);
    CaminoDrawable caminoDrawable6 = new CaminoDrawable(100,350, 100, 100, Color.ORANGE);
    private int height;
    private int width;

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
    }

    // or draw the background

    private void clearSection(Graphics2D g, int w, int h){
        g.setColor(new Color(66, 75, 84));
        g.fillRect(0,0, w, h);
    }

    private void clearCanvas(Graphics2D g){
        clearSection(g, this.width, this.height);
    }
}

