package gui;

import clases.Sucursal;
import servicios.CaminoServicios;
import servicios.ProductoServicios;
import servicios.SucursalServicios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaCaminos extends JFrame {
	CaminoServicios caminoServicios = new CaminoServicios();
	SucursalServicios sucursalServicios = new SucursalServicios();
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCaminos frame = new VentanaCaminos();
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
	public VentanaCaminos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Gestion de Caminos");
		setSize(400, 200);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton altabtn = new JButton("Agregar camino");
		JButton bajabtn = new JButton("Inhabilitar camino");
		JButton busquedabtn = new JButton("Buscar camino");
		JButton editarbtn = new JButton("editar caminos");
		JButton listarbtn = new JButton("Listar caminos");

		// Add buttons to the main frame
		contentPane.setLayout(new FlowLayout());
		contentPane.add(altabtn);
		contentPane.add(bajabtn);
		contentPane.add(busquedabtn);
		contentPane.add(editarbtn);
		contentPane.add(listarbtn);
		setContentPane(contentPane);

		altabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAltaCaminoPanel();
			}
		});

		bajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBusquedaPanel();
			}
		});

		listarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showListarCaminosPanel();
			}
		});
		editarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBusquedaPanel();
			}
		});
		busquedabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBusquedaPanel();
			}
		});
		contentPane.setVisible(true);
	}

	public void showAltaCaminoPanel(){
		JFrame altaFrame = new JFrame("Dar Alta");
		altaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		altaFrame.setLayout(new BorderLayout());

		JPanel panelAlta = new JPanel(new GridLayout(6, 2 ));

		// Labels
		JLabel label1 = new JLabel("Origen:");
		JLabel label2 = new JLabel("Destino:");
		JLabel label3 = new JLabel("Tiempo en Transito:");
		JLabel label4 = new JLabel("Capacidad Maxima:");
		JLabel label5 = new JLabel("Estado:");

		// Textboxes
		JTextField tiempotxt = new JTextField(10);
		JTextField capacidadtxt = new JTextField(10);

		List<Sucursal> sucursales = listaSucursales();
		DefaultComboBoxModel<Sucursal> sucursalesComboBox = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));

		JComboBox<Sucursal> origenCombo = new JComboBox<Sucursal>(sucursalesComboBox);
		JComboBox<Sucursal> destinoCombo = new JComboBox<Sucursal>(sucursalesComboBox);
		JComboBox<String> estadoCombo = new JComboBox<>(new String[]{"Operativo", "No operativo"});

		// "Agregar" Button
		JButton agregar = new JButton("Agregar");
		agregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String origen =  (String) origenCombo.getSelectedItem();
				String destino =  (String) destinoCombo.getSelectedItem();
				float tiempo = Float.parseFloat(tiempotxt.getText());
				float capacidad = Float.parseFloat(capacidadtxt.getText());
				boolean estado;
				if(estadoCombo.getSelectedItem() == "Operativo"){estado = true;}
				else{estado =false;}
				darAlta(origen,destino,tiempo,capacidad,estado);
			}
		});

		// Add components to the panelAlta
		panelAlta.add(label1);
		panelAlta.add(origenCombo);
		panelAlta.add(label2);
		panelAlta.add(destinoCombo);
		panelAlta.add(label3);
		panelAlta.add(tiempotxt);
		panelAlta.add(label4);
		panelAlta.add(capacidadtxt);
		panelAlta.add(label5);
		panelAlta.add(estadoCombo);
		panelAlta.add(agregar);

		altaFrame.add(panelAlta, BorderLayout.CENTER);
		altaFrame.pack();
		altaFrame.setLocationRelativeTo(null);
		altaFrame.setVisible(true);
	}



	public void showBusquedaPanel(){
		JFrame frame = new JFrame("Búsqueda");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridLayout(4, 1));
		JLabel mainLabel = new JLabel("¿Cómo desea realizar la búsqueda?");
		mainPanel.add(mainLabel);

		JPanel radioButtonPanel = new JPanel();
		JRadioButton idRadiobtn = new JRadioButton("Id camino");
		JRadioButton originDestRadioButton = new JRadioButton("Origen-Destino");
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(idRadiobtn);
		radioButtonGroup.add(originDestRadioButton);
		radioButtonPanel.add(idRadiobtn);
		radioButtonPanel.add(originDestRadioButton);

		JPanel textFieldPanel = new JPanel(new GridLayout(3, 2));
		JLabel label1 = new JLabel("Id camino:");
		JLabel label2 = new JLabel("Id Origen:");
		JLabel label3 = new JLabel("Id Destino:");
		JTextField idtxt = new JTextField();
		JTextField origentxt = new JTextField();
		JTextField destinotxt = new JTextField();
		textFieldPanel.add(label1);
		textFieldPanel.add(idtxt);
		textFieldPanel.add(label2);
		textFieldPanel.add(origentxt);
		textFieldPanel.add(label3);
		textFieldPanel.add(destinotxt);

		// Initially disable the text fields
		idtxt.setEnabled(false);
		origentxt.setEnabled(false);
		destinotxt.setEnabled(false);

		idRadiobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(true);
				origentxt.setEnabled(false);
				destinotxt.setEnabled(false);
			}
		});

		originDestRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(false);
				origentxt.setEnabled(true);
				destinotxt.setEnabled(true);
			}
		});

		JButton searchButton = new JButton("Buscar");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add code here for the search functionality
				String searchText;
				if (idRadiobtn.isSelected()) {
					buscar(Integer.parseInt(idtxt.getText()));
				} else {
					int origen = Integer.parseInt(origentxt.getText());
					int destino = Integer.parseInt(destinotxt.getText());
					buscar(origen,destino);
				}
				// Add the logic for the search operation based on the selected radio button
			}
		});

		mainPanel.add(radioButtonPanel);
		mainPanel.add(textFieldPanel);
		mainPanel.add(searchButton);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public void showListarCaminosPanel(){

	}

	private void buscar(int camino){

	}
	private void buscar(int origen, int destino){

	};

	private void darAlta(String origen, String destino, float tiempo, float capacidad, boolean estado){

	}

	private Sucursal getSucursal(String nombre){
		//return sucursalServicios.buscarSucursal(nombre);
		return null;
	}
	private List<Sucursal> listaSucursales(){
		//return sucursalServicios.listarSucursales();
		return null;
	}

}
