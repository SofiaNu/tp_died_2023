package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearOrdenProvisionUI {
    JFrame frameCrearOrdenProvision = new JFrame("Nueva Orden de provision");
    public void init(){

        frameCrearOrdenProvision.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCrearOrdenProvision.setLayout(new BorderLayout());

//        AtributosSucursalesPanel panel = new AtributosSucursalesPanel(new GridLayout(6,2));

        JButton guardarbtn = new JButton("Agregar Sucursal");
        JButton cancelarbtn = new JButton("Cancelar");

        JPanel nbtpanel = new JPanel();
        nbtpanel.setLayout(new GridLayout(2, 2, 30, 32)); // 3 rows, 3 columns, 5px horizontal and vertical gaps

        // Add buttons to the grid
        for (int i = 1; i <= 4; i++) {
            JButton button = new JButton("btna " + i);
            nbtpanel.add(button);
        }

//        panel.add(guardarbtn);
//        panel.add(cancelarbtn);
        JLabel titleLabel = new JLabel("Testing brid title", SwingConstants.CENTER);

        frameCrearOrdenProvision.add(nbtpanel);
        frameCrearOrdenProvision.add(titleLabel);

        guardarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Sucursal sucursal = new Sucursal();
//                sucursal.setNombre(panel.nombretxt.getText());
//                sucursal.setHoraApertura(LocalTime.of((int)panel.horaApertura.getValue(), (int)panel.minApertura.getValue()));
//                sucursal.setHoraCierre(LocalTime.of((int)panel.horaCierre.getValue(), (int)panel.minCierre.getValue()));
//                sucursal.setEstado((Estado) panel.estadoComboBox.getSelectedItem());
//                sucursal.setCapacidad(Float.valueOf(panel.capacidadtxt.getText()));
//                try {
//                    sucursalServicios.agregarSucursal(sucursal);
//                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
//                }
//                frameAlta.dispose();
            }
        });

        cancelarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameCrearOrdenProvision.dispose();
            }
        });

//        frameCrearOrdenProvision.add(panel);
        frameCrearOrdenProvision.pack();
        frameCrearOrdenProvision.setLocationRelativeTo(null);
    }

    public void show(){
        frameCrearOrdenProvision.setVisible(true);
    }

    public void hide(){
        frameCrearOrdenProvision.setVisible(false);
    }

}
