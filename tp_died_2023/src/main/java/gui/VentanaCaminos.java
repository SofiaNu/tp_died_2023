package gui;

import clases.Camino;
import clases.Estado;
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
import javax.swing.table.DefaultTableModel;

public class VentanaCaminos extends JFrame {
	CaminoServicios caminoServicios = new CaminoServicios();
	SucursalServicios sucursalServicios = new SucursalServicios();
	List<Sucursal> sucursales = sucursalServicios.listarSucursales();
	private JPanel contentPane;

	public VentanaCaminos() throws SQLException {
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
		JButton cerrarbtn = new JButton("Cerrar");

		// Add buttons to the main frame
		contentPane.setLayout(new FlowLayout());
		contentPane.add(altabtn);
		contentPane.add(bajabtn);
		contentPane.add(busquedabtn);
		contentPane.add(cerrarbtn);
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
				try {
					showBusquedaPanel();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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

	public void showAltaCaminoPanel(){
		JFrame altaFrame = new JFrame("Dar Alta");
		altaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		altaFrame.setLayout(new BorderLayout());

		CaminoAuxPanel panelAlta = new CaminoAuxPanel(sucursales,new GridLayout(6, 2 ));

		JButton agregar = new JButton("Agregar");
		JButton cancelar = new JButton("Cancelar");
		agregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sucursal origen = (Sucursal) panelAlta.origenCombo.getSelectedItem();
				Sucursal destino =  (Sucursal) panelAlta.destinoCombo.getSelectedItem();
				float tiempo= 0;
				float capacidad=0;
				if(!panelAlta.tiempotxt.getText().isEmpty()){
					tiempo = Float.parseFloat(panelAlta.tiempotxt.getText());
				}
				if(!panelAlta.capacidadtxt.getText().isEmpty()){
					capacidad = Float.parseFloat(panelAlta.capacidadtxt.getText());
				}
				darAlta(origen,destino,tiempo,capacidad,(Estado) panelAlta.estadoComboBox.getSelectedItem());

				altaFrame.dispose();
			}
		});

		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				altaFrame.dispose();
			}
		});
		panelAlta.add(agregar);
		panelAlta.add(cancelar);
		altaFrame.add(panelAlta);
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
				frame.dispose();
			}
		});

		mainPanel.add(radioButtonPanel);
		mainPanel.add(panelAtributos);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void showEditarPanel(Camino camino) throws SQLException{
		JFrame frameEditar = new JFrame("Resultado Busqueda:");
		frameEditar.setSize(500, 200);
		frameEditar.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CaminoAuxPanel panelAtributos = new CaminoAuxPanel(sucursales,new GridLayout(6, 2 ));
		JButton guardarbtn = new JButton("Guardar: ");
		JButton cancelarbtn = new JButton("Cancelar: ");
		panelAtributos.add(guardarbtn);
		panelAtributos.add(cancelarbtn);


		panelAtributos.estadoComboBox.setSelectedItem(camino.getEstado());
		panelAtributos.origenCombo.setSelectedItem(camino.getOrigen());
		panelAtributos.destinoCombo.setSelectedItem(camino.getDestino());
		cancelarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameEditar.dispose();
			}
		});

		guardarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				camino.setOrigen((Sucursal) panelAtributos.origenCombo.getSelectedItem());
				camino.setDestino((Sucursal) panelAtributos.destinoCombo.getSelectedItem());
				if(!panelAtributos.tiempotxt.getText().isEmpty()){
					camino.setTiempoTransito(Float.parseFloat(panelAtributos.tiempotxt.getText()));}

				if(!panelAtributos.capacidadtxt.getText().isEmpty()){
					camino.setCapacidadMaxima(Float.parseFloat(panelAtributos.capacidadtxt.getText()));}
					camino.setEstado((Estado) panelAtributos.estadoComboBox.getSelectedItem());
				caminoServicios.editarCamino(camino);
				frameEditar.dispose();
			}
		});
		frameEditar.add(panelAtributos);
		frameEditar.setVisible(true);
	}

	public void showCaminoNoEncontradoDialog(){
		JOptionPane.showMessageDialog(this,"Camino no encontrado","Error",JOptionPane.OK_OPTION);
	}

	public void showResultadoBusqueda(Camino camino){
		JFrame resultadoFrame = new JFrame("Resultado Busqueda:");
		resultadoFrame.setSize(500, 200);
		resultadoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] columnNames = {"Id", "Origen", "Destino", "Tiempo en transito", "Capacidad Maxima","Estado"};
		String[][] caminoFila = {{String.valueOf(camino.getId()), camino.getOrigen().getNombre(),
					camino.getDestino().getNombre(), String.valueOf(camino.getTiempoTransito()),
					String.valueOf(camino.getCapacidadMaxima()), String.valueOf(camino.getEstado())}};

		JTable resultado = new JTable(new DefaultTableModel(caminoFila, columnNames));
		JScrollPane contenedorTabla = new JScrollPane(resultado); //Sin esto no se muestra el nombre de las columnas

		//controla el tamaño (si no no se ven los botones)
		int maxVisibleRows = 1;
		int rowHeight = resultado.getRowHeight();
		int headerHeight = resultado.getTableHeader().getPreferredSize().height;
		Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
				maxVisibleRows * rowHeight + headerHeight);
		contenedorTabla.setPreferredSize(preferredSize);


		// Create Editar, Dar de Baja, and Close buttons
		JButton editarButton = new JButton("Editar");
		JButton darDeBajaButton = new JButton("Eliminar camino");
		JButton modificarEstadoButton = new JButton("Modificar Estado");
		JButton cerrarButton = new JButton("Cerrar");



		editarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showEditarPanel(camino);
					resultadoFrame.dispose();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});


		darDeBajaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBajaDialog(camino);
			}
		});

		modificarEstadoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showModificarEstadoDialog(camino);
			}
		});

		cerrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultadoFrame.dispose();
			}
		});

		JPanel panel = new JPanel();
		panel.add(contenedorTabla);
		panel.add(editarButton);
		panel.add(darDeBajaButton);
		panel.add(modificarEstadoButton);
		panel.add(cerrarButton);
		resultadoFrame.add(panel);
		resultadoFrame.setVisible(true);


	}
	public void showModificarEstadoDialog(Camino camino){
		String msg="Actualmente el camino se encuentra "+String.valueOf(camino.getEstado())+
				"¿Desea modificarlo?";
		int opcion =JOptionPane.showConfirmDialog(this,msg,"Confirmacion",JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION){
			caminoServicios.modificarEstado(camino);
		}
	}


	public void showBajaDialog(Camino camino){
		int opcion = JOptionPane.showConfirmDialog(this,"Esta opcion eliminará para siempre el camino" +
				"¿Desea dar de baja?)","Cuidado",JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION){
			caminoServicios.bajaCamino(camino);
		}

	}

	private void darAlta(Sucursal origen, Sucursal destino, float tiempo, float capacidad, Estado estado){
		Camino camino = new Camino(origen,destino,tiempo,capacidad,estado);
		caminoServicios.altaCamino(camino);
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
	private Sucursal getSucursal(String nombre) throws SQLException {
		return sucursalServicios.buscarSucursal(nombre);
	}

	public JPanel setPanelAltaEdicion() throws SQLException {
		JPanel panelAtributos = new JPanel(new GridLayout(6, 2 ));


		JLabel label1 = new JLabel("Origen:");
		JLabel label2 = new JLabel("Destino:");
		JLabel label3 = new JLabel("Tiempo en Transito:");
		JLabel label4 = new JLabel("Capacidad Maxima:");
		JLabel label5 = new JLabel("Estado:");


		JTextField tiempotxt = new JTextField(10);
		JTextField capacidadtxt = new JTextField(10);

		DefaultComboBoxModel<Sucursal> origenModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));
		DefaultComboBoxModel<Sucursal> destinoModel = new DefaultComboBoxModel<>(sucursales.toArray(new Sucursal[0]));

		JComboBox<Sucursal> origenCombo = new JComboBox<Sucursal>(origenModel);
		JComboBox<Sucursal> destinoCombo = new JComboBox<Sucursal>(destinoModel);
		JComboBox<String> estadoCombo = new JComboBox<>(new String[]{"Operativo", "No operativo"});

		panelAtributos.add(label1);
		panelAtributos.add(origenCombo);
		panelAtributos.add(label2);
		panelAtributos.add(destinoCombo);
		panelAtributos.add(label3);
		panelAtributos.add(tiempotxt);
		panelAtributos.add(label4);
		panelAtributos.add(capacidadtxt);
		panelAtributos.add(label5);
		panelAtributos.add(estadoCombo);

		return panelAtributos;
	}
}
