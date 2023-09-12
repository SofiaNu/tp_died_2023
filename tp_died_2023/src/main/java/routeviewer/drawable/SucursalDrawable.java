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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSelectedString() {
        return selectedString;
    }

    public void setSelectedString(String selectedString) {
        this.selectedString = selectedString;
    }

    private boolean isSelected = false;
    private String selectedString = null;

    public boolean isInactive() {
        return isInactive;
    }

    public void setInactive(boolean inactive) {
        isInactive = inactive;
        setBaseColor(inactive ? Color.PINK: getBaseColor());
    }

    private boolean isInactive = false;

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


    public void drawTextLabels(Graphics2D g){
        String nombreString = "Sucursal";
        if(sucursal != null){
            nombreString = sucursal.getNombre() + "(" + String.valueOf(sucursal.getId())+  ")";
        }
        // nombre
        Font nombreFont = new Font("Verdana", Font.BOLD, (int)(RADIUS_Y / 3.7));
        g.setFont(nombreFont);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(nombreString);

        float nombreStrX = getX() + (float)RADIUS_X - (textWidth / 2.0f);
        float nombreStrY = getY() + RADIUS_Y * 0.85f;

        g.setColor(Color.WHITE);
        g.drawString(nombreString, nombreStrX, nombreStrY);

        //pos
        if(isSelected && getSelectedString() != null){
            Font positionfont = new Font("Verdana", Font.BOLD, (int)(RADIUS_Y / 2.5));
            g.setFont(positionfont);

            fm = g.getFontMetrics();
            textWidth = fm.stringWidth(getSelectedString());

            float positionStrX = getX() + (float)RADIUS_X - (textWidth / 2.0f);
            float positionStrY = getY() + RADIUS_Y * 1.5f;

            g.setColor(Color.WHITE);
            g.drawString(getSelectedString(), positionStrX, positionStrY);
        }
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
