package gui;

import clases.OrdenProvision;
import clases.Sucursal;
import dao.OrdenProvisionRepository;
import dao.StockProductoRepository;
import servicios.SucursalServicios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaOrden extends JFrame{
    private JPanel contentPane;
    SucursalServicios sucursalServicios = new SucursalServicios();
    Sucursal sucursal;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VentanaOrden(Sucursal s) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Gestion de Sucursales");
        this.sucursal = s;
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton altabtn = new JButton("Crear Orden Provision");
        JButton listarbtn = new JButton("Listar Ordenes Pendientes");
        JButton buscarbtn = new JButton("Buscar Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        contentPane.add(altabtn);
        contentPane.add(listarbtn);
        contentPane.add(buscarbtn);
        contentPane.add(cerrarbtn);
        setContentPane(contentPane);

        altabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAltaPanel();
            }
        });

        buscarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuscarPanel();

            }
        });

        listarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showListarPanel();
            }
        });

        cerrarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.setVisible(true);

    }

    public void showAltaPanel(){}

    public void showBuscarPanel(){}
    public void showListarPanel(){
        JPanel panelListar = new JPanel();

        List<OrdenProvision> listaOrdenes = null;

        String[] columnNames = {"Id", "Fecha", "Tiempo Limite", "Peso", "Estado"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        for(OrdenProvision o: listaOrdenes){
            String[] fila = {String.valueOf(o.getId()),String.valueOf(o.getFecha()),
                    String.valueOf(o.getTiempoLimite())};
        }

    }
}
