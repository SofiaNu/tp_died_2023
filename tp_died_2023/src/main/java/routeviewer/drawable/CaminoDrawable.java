package routeviewer.drawable;

import clases.Camino;
import clases.Sucursal;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;

public class CaminoDrawable extends Drawable{
    private float THICKNESS = 4;
    private float RADIUS_X = 70, RADIUS_Y = 70;

    public Camino getCamino() {
        return camino;
    }

    public void setCamino(Camino camino) {
        this.camino = camino;
    }

    private Camino camino;

    public SucursalDrawable getOrigen() {
        return origen;
    }

    public void setOrigen(SucursalDrawable origen) {
        this.origen = origen;
    }

    public SucursalDrawable getDestino() {
        return destino;
    }

    public void setDestino(SucursalDrawable destino) {
        this.destino = destino;
    }

    private SucursalDrawable origen;
    private SucursalDrawable destino;
    float xF, yF;
    QuadCurve2D.Float quadCurve = null;

    public float getxF() {
        return xF;
    }

    public void setxF(float xF) {
        this.xF = xF;
    }

    public float getyF() {
        return yF;
    }

    public void setyF(float yF) {
        this.yF = yF;
    }

    public CaminoDrawable(float x, float y, float xf, float yf, Color baseColor) {
        super(x, y, baseColor);
        //x,y initial point
        yF = yf;
        xF = xf;

    }

    @Override
    public void draw(Graphics2D g) {
        Boolean isVertical = false;
        Boolean isHorizontal = false;
        float ctrlFactor = 0.1f;
        float m = 0;
        float perpM = 0;
        float xDif = (xF - x);
        float yDif = (yF - y);

        float ctrlX = 0, ctrlY = 0;

        if(xDif == 0){
            isVertical = true;
        }
        else if(yDif == 0){
            isHorizontal = true;
        }
        else {
            m = yDif / xDif;
            perpM = (-1 / m);
        }

        float midX = x + 0.5f * xDif;
        float midY = y + 0.5f * yDif;
        float ctrlDisplc = (float)Math.sqrt(midX*midX + midY*midY) * ctrlFactor;

        if(isVertical){
            ctrlY = midY;
            ctrlX = yF > y ? midX + ctrlDisplc : midX -  ctrlDisplc;
        } else if(isHorizontal){
            ctrlY = xF > x ? midY - ctrlDisplc : midY + ctrlDisplc;
            ctrlX = midX;
        }
        else{
            ctrlX = xF > x ? midX +  ctrlDisplc : midX -  ctrlDisplc;
            ctrlY = perpM * (ctrlX - midX) + midY;
        }

        System.out.println(" xi " + x+ " yi " + y+ " ctrX "+ctrlX+ " ctrY " + ctrlY+ " xF " + xF + " yF " + yF + "centerX " + midX + "centerY" + midY);
        quadCurve = new QuadCurve2D.Float(x,y, ctrlX, ctrlY, xF, yF);

        g.setColor(this.getBaseColor());
        g.setStroke(new BasicStroke(THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(quadCurve);
    }

    public boolean hit(float x, float y) {
        return quadCurve.contains(x, y);
    }
}
