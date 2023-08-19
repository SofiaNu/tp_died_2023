package routeviewer.drawable;

import clases.Sucursal;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SucursalDrawable extends Drawable{
    private float THICKNESS = 4;
    private float RADIUS_X = 70, RADIUS_Y = 70;

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    private Sucursal sucursal;
    public SucursalDrawable(int x, int y, Color baseColor) {
        super(x, y, baseColor);
    }

    @Override
    public void draw(Graphics2D g) {
        Ellipse2D ellipse2D = new Ellipse2D.Double(getX(),getY(),RADIUS_X,RADIUS_Y);
        g.setColor(this.getBaseColor());
        g.setStroke(new BasicStroke(THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(ellipse2D);
        //g.drawString("Sucursal", getX() , getY() - RADIUS_Y * 0.35f);
    }

    public boolean hit(float x, float y) {
        // square for now :B
        return (
                this.x - RADIUS_X <= x &&
                x <= this.x + RADIUS_X &&
                this.y - RADIUS_Y <= y &&
                y <= this.y + RADIUS_Y
        );
    }
}
