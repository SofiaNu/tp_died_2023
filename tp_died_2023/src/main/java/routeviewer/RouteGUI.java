package routeviewer;

import javax.swing.*;
import java.awt.*;

public class RouteGUI extends JPanel {

    RouteDrawingManager routeDrawingManager;
    public RouteGUI(){
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.black);
        routeDrawingManager = new RouteDrawingManager();
        routeDrawingManager.setSize(getWidth(), getHeight());
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        routeDrawingManager.drawContents(g2d, getWidth(), getHeight());
    }
}
