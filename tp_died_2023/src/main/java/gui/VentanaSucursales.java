package gui;

import clases.Estado;
import clases.Sucursal;
import servicios.SucursalServicios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaSucursales extends JFrame {

	private JPanel contentPane;
	SucursalServicios sucursalServicios = new SucursalServicios();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSucursales frame = new VentanaSucursales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaSucursales() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Gestion de Sucursales");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton altabtn = new JButton("Agregar Sucursal");
		JButton buscarbtn = new JButton("Buscar Sucursal");
		JButton bajabtn = new JButton("Dar de Baja Sucursal");
		JButton stockbtn = new JButton("Manejo de Stock");
		JButton ordenbtn = new JButton("Generar Orden de Provision");
		JButton cerrarbtn = new JButton("Cerrar");

		contentPane.add(altabtn);
		contentPane.add(buscarbtn);
		contentPane.add(bajabtn);
		contentPane.add(stockbtn);
		contentPane.add(ordenbtn);
		contentPane.add(cerrarbtn);
		setContentPane(contentPane);

		altabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


			}
		});

		buscarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBuscarPanel();

			}
		});

		bajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


			}
		});

		stockbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


			}
		});

		ordenbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


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
	public void showBuscarPanel(){
		JFrame frame = new JFrame("Busqueda");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(4, 1));

		JPanel radioButtonPanel = new JPanel();
		JRadioButton idbtn = new JRadioButton("Id");
		JRadioButton nombrebtn = new JRadioButton("Nombre");
		JRadioButton estadobtn = new JRadioButton("Estado");

		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(idbtn);
		radioButtonGroup.add(nombrebtn);
		radioButtonGroup.add(estadobtn);

		radioButtonPanel.add(idbtn);
		radioButtonPanel.add(nombrebtn);
		radioButtonPanel.add(estadobtn);

		JPanel panelAtributos= new JPanel(new GridLayout(4,2));

		JLabel idlbl = new JLabel("Id: ");
		JLabel nombrelbl = new JLabel("Nombre: ");
		JLabel estadolbl = new JLabel("Estado: ");

		JComboBox estadoComboBox = new JComboBox<>(Estado.values());
		JTextField idtxt = new JTextField();
		JTextField nombretxt = new JTextField();

		estadoComboBox.setEnabled(false);
		idtxt.setEnabled(false);
		nombretxt.setEnabled(false);

		JButton buscarbtn = new JButton("Buscar");
		JButton cerrarbtn = new JButton("Cerrar");

		panelAtributos.add(idlbl);
		panelAtributos.add(idtxt);
		panelAtributos.add(nombrelbl);
		panelAtributos.add(nombretxt);
		panelAtributos.add(estadolbl);
		panelAtributos.add(estadoComboBox);
		panelAtributos.add(buscarbtn);
		panelAtributos.add(cerrarbtn);


		idbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(true);
				nombretxt.setEnabled(false);
				estadoComboBox.setEnabled(false);

			}
		});
		nombrebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(false);
				nombretxt.setEnabled(true);
				estadoComboBox.setEnabled(false);

			}
		});
		estadobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(false);
				nombretxt.setEnabled(false);
				estadoComboBox.setEnabled(true);

			}
		});
		buscarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(idbtn.isSelected()){
					try {
						buscar(Integer.parseInt(idtxt.getText()));
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
				if(nombrebtn.isSelected()){
					try {
						buscar(idtxt.getText());
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
				if(estadobtn.isSelected()){
					try {
						buscar((Estado) estadoComboBox.getSelectedItem());
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
				frame.dispose();

			}
		});
		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

			}
		});

		panel.add(radioButtonPanel);
		panel.add(panelAtributos);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);


	}
	private void buscar(int id) throws SQLException {
		Sucursal sucursal= sucursalServicios.buscarSucursal(id);
		List<Sucursal> sucursales = new ArrayList<>();
		if(sucursal == null){
			showSinResultadosDialog();
		}
		else{
			sucursales.add(sucursal);
			showResultadoBusquedaPanel(sucursales);
		}

	}
	private void buscar(String nombre) throws SQLException {
		Sucursal sucursal= sucursalServicios.buscarSucursal(nombre);
		List<Sucursal> sucursales = new ArrayList<>();
		if(sucursal == null){
			showSinResultadosDialog();
		}
		else{
			sucursales.add(sucursal);
			showResultadoBusquedaPanel(sucursales);
		}
	}
	private void buscar(Estado estado) throws SQLException {
		List<Sucursal> sucursales= sucursalServicios.buscarSucursales(estado);
		if(sucursales == null || sucursales.isEmpty()){
			showSinResultadosDialog();
		}
		else{
			showResultadoBusquedaPanel(sucursales);
		}

	}

	public void showSinResultadosDialog(){
		JOptionPane.showMessageDialog(this,"No se encontró la sucursal",
				"Sin Resultados",JOptionPane.OK_OPTION);
	}

	public void showResultadoBusquedaPanel(List<Sucursal> sucursales){
		JFrame resultadoFrame = new JFrame("Resultado Busqueda:");
		resultadoFrame.setSize(500, 200);
		resultadoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new FlowLayout());

		String[] columnNames = {"Id", "Nombre", "Hora Apertura", "Hora Cierre", "Estado"};
		JTable tablaResultados = new JTable(new DefaultTableModel());

		DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
		model.setColumnIdentifiers(columnNames);

		for(Sucursal s:sucursales){
			String[] fila = {String.valueOf(s.getId()),s.getNombre(),String.valueOf(s.getHoraApertura()),
					String.valueOf(s.getHoraCierre()),String.valueOf(s.getEstado())};
			model.addRow(fila);

		}

		JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
		int maxVisibleRows = 7;
		int rowHeight = tablaResultados.getRowHeight();
		int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
		Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
				maxVisibleRows * rowHeight + headerHeight);
		contenedorTabla.setPreferredSize(preferredSize);

		JButton editarbtn = new JButton("Editar");
		JButton darBajabtn = new JButton("Dar de Baja");
		JButton stockbtn = new JButton("Administrar Stock");
		JButton estadobtn = new JButton("Modificar Estado");
		JButton ordenbtn = new JButton("Generar Orden");
		JButton cerrarbtn = new JButton("Cerrar");

		panel.add(contenedorTabla);
		panel.add(editarbtn);
		panel.add(darBajabtn);
		panel.add(stockbtn);
		panel.add(estadobtn);
		panel.add(ordenbtn);
		panel.add(cerrarbtn);

		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultadoFrame.dispose();

			}
		});
		editarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		darBajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		estadobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		stockbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		ordenbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		resultadoFrame.add(panel);
		resultadoFrame.setVisible(true);

	}

	public void showBajaDialog(){}

	public void showEditarPanel(){}
	public void showStockPanel(){}
	public void showOrden(){}

}
