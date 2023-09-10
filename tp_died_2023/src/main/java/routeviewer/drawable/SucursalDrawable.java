package routeviewer.drawable;

import clases.Sucursal;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SucursalDrawable extends Drawable{
    private float THICKNESS = 4;
    private float RADIUS_X = 35;

    private Color borderColor = Color.yellow;
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public float getRADIUS_X() {
        return RADIUS_X;
    }

    public void setRADIUS_X(float RADIUS_X) {
        this.RADIUS_X = RADIUS_X;
    }

    public float getRADIUS_Y() {
        return RADIUS_Y;
    }

    public void setRADIUS_Y(float RADIUS_Y) {
        this.RADIUS_Y = RADIUS_Y;
    }

    private float RADIUS_Y = 35;

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
        Ellipse2D ellipse2D = new Ellipse2D.Double(getX(),getY(),RADIUS_X * 2,RADIUS_Y * 2);
        g.setColor(this.getBorderColor());
        g.setStroke(new BasicStroke(THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(ellipse2D);
        g.setColor(this.getBaseColor());
        g.setColor(this.getBaseColor());
        g.fillOval((int)getX(),(int)getY(),(int)RADIUS_X * 2,(int)RADIUS_Y * 2);
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
