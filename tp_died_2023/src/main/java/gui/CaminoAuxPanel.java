package gui;

import clases.Estado;
import clases.Sucursal;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CaminoAuxPanel extends JPanel{
    JLabel label1 = new JLabel("Origen:");
    JLabel label2 = new JLabel("Destino:");
    JLabel label3 = new JLabel("Tiempo en Transito:");
    JLabel label4 = new JLabel("Capacidad Maxima:");
    JLabel estadolbl = new JLabel("Estado: ");
    JComboBox estadoComboBox = new JComboBox<>(Estado.values());
    JTextField tiempotxt = new JTextField(10);
    JTextField capacidadtxt = new JTextField(10);

    JComboBox<Sucursal> origenCombo = new JComboBox<Sucursal>();
    JComboBox<Sucursal> destinoCombo = new JComboBox<Sucursal>();

    public CaminoAuxPanel(List<Sucursal> sucursales, GridLayout layout){
        setLayout(layout);
        DefaultComboBoxModel<Sucursal> origenModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));
        DefaultComboBoxModel<Sucursal> destinoModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));
        origenCombo.setModel(origenModel);
        destinoCombo.setModel(destinoModel);

        add(label1);
        add(origenCombo);
        add(label2);
        add(destinoCombo);
        add(label3);
        add(tiempotxt);
        add(label4);
        add(capacidadtxt);
        add(estadolbl);
        add(estadoComboBox);
    }

}
