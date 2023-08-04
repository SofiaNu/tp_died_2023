package gui;

import clases.Estado;

import javax.swing.*;
import java.awt.*;

public class AtributosSucursalesPanel extends JPanel {

    public AtributosSucursalesPanel(GridLayout layout) {
        setLayout(layout);
        JPanel panelHoras1 =new JPanel(new GridLayout(1,2));
        JPanel panelHoras2 =new JPanel(new GridLayout(1,2));

        JLabel nombrelbl = new JLabel("Nombre: ");
        JLabel horaAperturalbl = new JLabel("Hora Apertura: ");
        JLabel horaCierrelbl = new JLabel("Hora Cierre: ");
        JLabel estadolbl = new JLabel("Estado: ");
        JLabel capacidadlbl = new JLabel("Capacidad de Recepcion: ");

        panelHoras1.add(horaApertura);
        panelHoras1.add(minApertura);
        panelHoras2.add(horaCierre);
        panelHoras2.add(minCierre);

        this.add(nombrelbl);
        this.add(nombretxt);
        this.add(horaAperturalbl);
        this.add(panelHoras1);
        this.add(horaCierrelbl);
        this.add(panelHoras2);
        this.add(estadolbl);
        this.add(estadoComboBox);
        this.add(capacidadlbl);
        this.add(capacidadtxt);

    }
    JTextField nombretxt = new JTextField();
    JTextField capacidadtxt = new JTextField();
    JComboBox estadoComboBox = new JComboBox<>(Estado.values());

    JSpinner horaApertura = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
    JSpinner horaCierre = new JSpinner(new SpinnerNumberModel(0, 00, 23, 1));
    JSpinner minApertura = new JSpinner(new SpinnerNumberModel(00, 00, 59, 15));
    JSpinner minCierre = new JSpinner(new SpinnerNumberModel(0, 00, 59, 15));



}
