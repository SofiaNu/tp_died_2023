package gui;

import clases.Sucursal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inicio extends JFrame{

	private JPanel contentPane;

	public Inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 300);
		setTitle("Inicio");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnSucursales = new JButton("Sucursales");
		btnSucursales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaSucursales ventanaSucursales= new VentanaSucursales();
				ventanaSucursales.setVisible(true);
			}
		});
		contentPane.add(btnSucursales);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaProductos ventanaProductos= new VentanaProductos();
				ventanaProductos.setVisible(true);
			}
		});
		contentPane.add(btnProductos);
		
		JButton btnCaminos = new JButton("Caminos");
		btnCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCaminos ventanaCaminos = null;
				try {
					ventanaCaminos = new VentanaCaminos();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				ventanaCaminos.setVisible(true);
			}
		});
		contentPane.add(btnCaminos);

		JButton btnOrdenes = new JButton("Gestion de Ordenes de Provision");
		btnOrdenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaOrden ventanaOrden = new VentanaOrden();
				ventanaOrden.setVisible(true);
			}
		});
		contentPane.add(btnOrdenes);

		JButton btnPageRank = new JButton("Page Rank");
		btnPageRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPageRank();
			}
		});
		contentPane.add(btnPageRank);

		JButton btnFlujo = new JButton("Flujo Maximo");
		btnFlujo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showVentanaFlujo();
			}
		});
		contentPane.add(btnFlujo);
	}

	public void showVentanaFlujo(){

	};

	public void showPageRank(){

		JFrame resultadoFrame = new JFrame("Page Rank:");
		resultadoFrame.setSize(500, 300);
		resultadoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new FlowLayout());

		String[] columnNames = {"Id", "Nombre", "Ranking"};
		JTable tablaResultados = new JTable(new DefaultTableModel());

		DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
		model.setColumnIdentifiers(columnNames);

		List<Sucursal> sucursales = new ArrayList<>(); //ACA VA LA LISTA

		if(sucursales != null) {
			for (Sucursal s : sucursales) {
				String[] fila = {String.valueOf(s.getId()), s.getNombre(), String.valueOf(s.getHoraApertura())};
				model.addRow(fila);
			}
		}
		JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
		int maxVisibleRows = 8;
		int rowHeight = tablaResultados.getRowHeight();
		int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
		Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
				maxVisibleRows * rowHeight + headerHeight);
		contenedorTabla.setPreferredSize(preferredSize);
		panel.add(contenedorTabla);

		resultadoFrame.add(panel);
		resultadoFrame.setVisible(true);


	}


}
