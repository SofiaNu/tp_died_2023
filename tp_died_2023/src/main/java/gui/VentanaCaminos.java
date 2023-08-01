package gui;

import clases.Camino;
import clases.Sucursal;
import servicios.CaminoServicios;
import servicios.ProductoServicios;
import servicios.SucursalServicios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
				try {
					showAltaCaminoPanel();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		bajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showBusquedaPanel();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
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
				try {
					showBusquedaPanel();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		busquedabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showBusquedaPanel();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		contentPane.setVisible(true);
	}

	public void showAltaCaminoPanel() throws SQLException {
		JFrame altaFrame = new JFrame("Dar Alta");
		altaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		altaFrame.setLayout(new BorderLayout());

		JPanel panelAlta = new JPanel(new GridLayout(6, 2 ));


		JLabel label1 = new JLabel("Origen:");
		JLabel label2 = new JLabel("Destino:");
		JLabel label3 = new JLabel("Tiempo en Transito:");
		JLabel label4 = new JLabel("Capacidad Maxima:");
		JLabel label5 = new JLabel("Estado:");


		JTextField tiempotxt = new JTextField(10);
		JTextField capacidadtxt = new JTextField(10);

		List<Sucursal> sucursales = listaSucursales();
		DefaultComboBoxModel<Sucursal> origenModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));
		DefaultComboBoxModel<Sucursal> destinoModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));

		JComboBox<Sucursal> origenCombo = new JComboBox<Sucursal>(origenModel);
		JComboBox<Sucursal> destinoCombo = new JComboBox<Sucursal>(destinoModel);
		JComboBox<String> estadoCombo = new JComboBox<>(new String[]{"Operativo", "No operativo"});

		JButton agregar = new JButton("Agregar");
		agregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sucursal origen = (Sucursal) origenCombo.getSelectedItem();
				Sucursal destino =  (Sucursal) destinoCombo.getSelectedItem();
				float tiempo= 0;
				float capacidad=0;
				if(!tiempotxt.getText().isEmpty()){tiempo = Float.parseFloat(tiempotxt.getText());}
				if(!capacidadtxt.getText().isEmpty()){capacidad = Float.parseFloat(capacidadtxt.getText());}
				boolean estado;
				if(estadoCombo.getSelectedItem() == "Operativo"){estado = true;}
				else{estado =false;}
				darAlta(origen,destino,tiempo,capacidad,estado);
			}
		});


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



	public void showBusquedaPanel() throws SQLException {
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

		JPanel panelAtributos = new JPanel(new GridLayout(4, 2));
		JLabel label1 = new JLabel("Id camino:");
		JLabel label2 = new JLabel("Origen:");
		JLabel label3 = new JLabel("Destino:");
		JTextField idtxt = new JTextField();

		List<Sucursal> sucursales = listaSucursales();

		DefaultComboBoxModel<Sucursal> origenModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));
		DefaultComboBoxModel<Sucursal> destinoModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));

		JComboBox<Sucursal> origenCombo = new JComboBox<Sucursal>(origenModel);
		JComboBox<Sucursal> destinoCombo = new JComboBox<Sucursal>(destinoModel);
		JButton buscarbtn = new JButton("Buscar");
		panelAtributos.add(label1);
		panelAtributos.add(idtxt);
		panelAtributos.add(label2);
		panelAtributos.add(origenCombo);
		panelAtributos.add(label3);
		panelAtributos.add(destinoCombo);
		panelAtributos.add(buscarbtn);


		idtxt.setEnabled(false);
		origenCombo.setEnabled(false);
		destinoCombo.setEnabled(false);

		idRadiobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(true);
				origenCombo.setEnabled(false);
				destinoCombo.setEnabled(false);
			}
		});

		originDestRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idtxt.setEnabled(false);
				origenCombo.setEnabled(true);
				destinoCombo.setEnabled(true);
			}
		});

		buscarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (idRadiobtn.isSelected()) {
					buscar(Integer.parseInt(idtxt.getText()));
				} else {
					int origen = ((Sucursal)origenCombo.getSelectedItem()).getId();
					int destino = ((Sucursal)destinoCombo.getSelectedItem()).getId();
					buscar(origen,destino);
				}
			}
		});

		mainPanel.add(radioButtonPanel);
		mainPanel.add(panelAtributos);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public void showListarCaminosPanel(){

	}

	private void buscar(int id){
		Camino camino = caminoServicios.buscarCamino(id);
		if(camino !=null){
			showResultadoBusqueda(camino);
		}
		else{
			showCaminoNoEncontradoDialog();
		}

	}
	private void buscar(int origen, int destino){
		Camino camino =caminoServicios.buscarCamino(origen,destino);
		if(camino!=null){
			showResultadoBusqueda(camino);
		}
		else{
			showCaminoNoEncontradoDialog();
		}
	};

	public void showCaminoNoEncontradoDialog(){
		JOptionPane.showMessageDialog(this,"Camino no encontrado","Error",JOptionPane.OK_OPTION);
	}

	public void showResultadoBusqueda(Camino camino){

	}

	private void darAlta(Sucursal origen, Sucursal destino, float tiempo, float capacidad, boolean estado){
		Camino camino = new Camino(origen,destino,tiempo,capacidad,estado);
		caminoServicios.altaCamino(camino);
	}

	private Sucursal getSucursal(String nombre) throws SQLException {
		return sucursalServicios.buscarSucursal(nombre);
	}
	private List<Sucursal> listaSucursales() throws SQLException {
		return sucursalServicios.listarSucursales();
	}

}
