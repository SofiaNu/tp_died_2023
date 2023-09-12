package gui;

import routeviewer.RouteGUI;
import java.awt.EventQueue;
import clases.Sucursal;
import servicios.FlujoMaximo;
import servicios.PageRank;

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
		setBounds(100, 100, 600, 600);
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
		//RouteGUI rg = new RouteGUI();

		//contentPane.add(rg);
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
				try {
					showVentanaFlujo();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		contentPane.add(btnFlujo);
	}

	public void showVentanaFlujo() throws SQLException {
		FlujoMaximo flujoMaximo = new FlujoMaximo();

		JFrame frame = new JFrame(("FLujo maximo de transporte"));
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new FlowLayout());

		//ACA debería haber un mapa (¿supongo/talvez?)
		JLabel info = new JLabel("La maxima capacidad de transporte desde el puerto a la sucursal final");
		JLabel capacidad = new JLabel(String.valueOf(flujoMaximo.flujoMaximo()));

		JButton cerrarbtn = new JButton("Cerrar");

		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		panel.add(info);
		panel.add(capacidad);
		panel.add(cerrarbtn);
		frame.add(panel);
		frame.setVisible(true);
	};

	public void showPageRank(){

		JFrame resultadoFrame = new JFrame("Page Rank:");
		resultadoFrame.setSize(500, 300);
		resultadoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new FlowLayout());

		String[] columnNames = {"Id", "Nombre"};
		JTable tablaResultados = new JTable(new DefaultTableModel());
		PageRank pageRank = new PageRank();
		DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
		model.setColumnIdentifiers(columnNames);

		List<Sucursal> sucursales = pageRank.calcularPR(100);

		if(sucursales != null) {
			for (Sucursal s : sucursales) {
				String[] fila = {String.valueOf(s.getId()), s.getNombre()};
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

		JButton cerrarbtn = new JButton("Cerrar");
		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultadoFrame.setVisible(false);
			}
		});

		panel.add(contenedorTabla);
		panel.add(cerrarbtn);
		resultadoFrame.add(panel);
		resultadoFrame.setVisible(true);


	}

}
