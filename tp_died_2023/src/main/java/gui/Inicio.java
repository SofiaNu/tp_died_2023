package gui;

import clases.Camino;
import routeviewer.RouteGUI;
import java.awt.EventQueue;
import clases.Sucursal;
import servicios.CaminoServicios;
import servicios.FlujoMaximo;
import servicios.PageRank;
import servicios.SucursalServicios;

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
					showVentanaFlujo();
					//showMsgFlujoMaximo();
			}
		});
		contentPane.add(btnFlujo);
	}

	public void showVentanaFlujo()  {
		FlujoMaximo flujoMaximo = null;
		try {
			flujoMaximo = new FlujoMaximo();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		JFrame frame = new JFrame(("FLujo maximo de transporte"));
		frame.setSize(500, 800);
		frame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new FlowLayout());

		SucursalServicios sucursalServicios = new SucursalServicios();
		CaminoServicios caminoServicios = new CaminoServicios();
		List<Sucursal> allSucursales = new ArrayList<>();
		List<Camino> allCaminos = new ArrayList<>();
		try{
			allSucursales = sucursalServicios.listarSucursales();
		} catch( Exception exc){
			System.out.println("Error obtener sucursales");
			System.exit(-1);
		}

		try{
			allCaminos = caminoServicios.listarCaminos();
		} catch( Exception exc){
			System.out.println("Error obtener caminos");
			System.exit(-1);
		}

		List<List<Camino>> recorridosMinimos = new ArrayList<>();

		float fmaximo = flujoMaximo.flujoMaximo(recorridosMinimos);

		JLabel info = new JLabel("La maxima capacidad de transporte desde el puerto a la sucursal final");
		RouteGUI routeGUI = new RouteGUI();
		//List<List<Camino>> caminosbase = new ArrayList<>();
		//caminosbase.add(allCaminos);
		routeGUI.setRecorridos(recorridosMinimos,allSucursales);
		JLabel fmax = new JLabel(String.valueOf(fmaximo));

		JButton cerrarbtn = new JButton("Cerrar");

		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});


		panel.add(info);
		panel.add(fmax);
		panel.add(routeGUI);
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

	public void showMsgFlujoMaximo(){
		FlujoMaximo flujoMaximo = null;
		try {
			flujoMaximo = new FlujoMaximo();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		JLabel info = new JLabel("La maxima capacidad de transporte desde el puerto a la sucursal final es de: ");
		float cap = flujoMaximo.flujoMaximo();
		JLabel capacidad = new JLabel(String.valueOf(cap));
		JOptionPane.showMessageDialog(null ,info + String.valueOf(capacidad));
	}

}
