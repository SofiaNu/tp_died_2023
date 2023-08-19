package routeviewer.drawable;

import java.awt.*;

public abstract class Drawable{

    public Drawable(float x, float y, Color baseColor) {
        this.x = x;
        this.y = y;
        this.baseColor = baseColor;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    protected float x;
    protected float y;

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    protected float centerX;
    protected float centerY;
    private Color baseColor;

    public abstract void draw(Graphics2D g);
    public abstract boolean hit(float x, float y);
}