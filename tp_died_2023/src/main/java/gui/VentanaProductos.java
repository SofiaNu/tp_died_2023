package gui;

import clases.Producto;
import servicios.ProductoServicios;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class VentanaProductos extends JFrame {

	private JPanel contentPane;
	ProductoServicios servicio = new ProductoServicios();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaProductos frame = new VentanaProductos();
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
	public VentanaProductos() {
		setTitle("Ventana Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		// Create buttons
		JButton altaProductobtn = new JButton("Alta Producto");
		JButton busquedabtn = new JButton("Busqueda");
		JButton listarbtn = new JButton("ListarProductos");

		// Add buttons to the main frame
		setLayout(new FlowLayout());
		add(altaProductobtn);
		add(busquedabtn);
		add(listarbtn);

		// Action listeners for buttons
		altaProductobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAltaProductoPanel();
			}
		});

		busquedabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showBusquedaDialog();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

	private void showEditarPanel(Producto producto) {
		JFrame editarProductoFrame = new JFrame("Editar Producto");
		editarProductoFrame.setSize(400, 200);
		editarProductoFrame.setLocationRelativeTo(null);

		JLabel nombrelbl = new JLabel("Nombre:");
		JTextField nombretxt = new JTextField();
		JLabel descripcionlbl = new JLabel("Descripcion:");
		JTextField descripciontxt = new JTextField();
		JLabel preciolbl = new JLabel("Precio:");
		JTextField preciotxt = new JTextField();
		JLabel pesolbl = new JLabel("Peso en KG:");
		JTextField pesotxt = new JTextField();

		JButton guardarbtn = new JButton("Guardar");

		JPanel editarProductoPanel = new JPanel();
		editarProductoPanel.setLayout(new GridLayout(5, 2));
		editarProductoPanel.add(nombrelbl);
		editarProductoPanel.add(nombretxt);
		editarProductoPanel.add(descripcionlbl);
		editarProductoPanel.add(descripciontxt);
		editarProductoPanel.add(preciolbl);
		editarProductoPanel.add(preciotxt);
		editarProductoPanel.add(pesolbl);
		editarProductoPanel.add(pesotxt);
		editarProductoPanel.add(guardarbtn);

		guardarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!String.valueOf(nombretxt.getText()).isEmpty())
				producto.setNombre(String.valueOf(nombretxt.getText()));
				if(!String.valueOf(descripciontxt.getText()).isEmpty())
				producto.setDetalle(String.valueOf(descripciontxt.getText()));
				try {
					if(!String.valueOf(preciotxt.getText()).isEmpty())
					producto.setPrecioUnitario(Double.parseDouble(preciotxt.getText()));

					if(!String.valueOf(pesotxt.getText()).isEmpty())
					producto.setPeso(Float.parseFloat(pesotxt.getText()));
				}
				catch(NumberFormatException ex){
					ex.printStackTrace();
				}
				servicio.editarProducto(producto);
				editarProductoFrame.dispose();
			}
		});

		editarProductoFrame.add(editarProductoPanel);
		editarProductoFrame.setVisible(true);
	}

	private void showAltaProductoPanel() {
		JFrame altaProductoFrame = new JFrame("Alta Producto");
		altaProductoFrame.setSize(400, 200);
		altaProductoFrame.setLocationRelativeTo(null);

		// Create labels and text fields
		JLabel nombrelbl = new JLabel("Nombre:");
		JTextField nombretxt = new JTextField();
		JLabel descripcionlbl = new JLabel("Descripcion:");
		JTextField descripciontxt = new JTextField();
		JLabel preciolbl = new JLabel("Precio:");
		JTextField preciotxt = new JTextField();
		JLabel pesolbl = new JLabel("Peso en KG:");
		JTextField pesotxt = new JTextField();


		// Create "Guardar" button
		JButton guardarbtn = new JButton("Guardar Cambios");

		// Add components to the panel
		JPanel altaProductoPanel = new JPanel();
		altaProductoPanel.setLayout(new GridLayout(5, 2));
		altaProductoPanel.add(nombrelbl);
		altaProductoPanel.add(nombretxt);
		altaProductoPanel.add(descripcionlbl);
		altaProductoPanel.add(descripciontxt);
		altaProductoPanel.add(preciolbl);
		altaProductoPanel.add(preciotxt);
		altaProductoPanel.add(pesolbl);
		altaProductoPanel.add(pesotxt);
		altaProductoPanel.add(guardarbtn);

		// Action listener for "Guardar" button
		guardarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Producto producto = new Producto();
				producto.setNombre(String.valueOf(nombretxt.getText()));
				producto.setDetalle(String.valueOf(descripciontxt.getText()));
				try {
					producto.setPrecioUnitario(Double.parseDouble(preciotxt.getText()));
					producto.setPeso(Float.parseFloat(pesotxt.getText()));
				}
				catch(NumberFormatException ex){
					ex.printStackTrace();
				}
				// Save the data or perform any other actions
				try {
					servicio.agregarProducto(producto);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				altaProductoFrame.dispose(); // Close the Alta Producto frame after saving
			}
		});

		altaProductoFrame.add(altaProductoPanel);
		altaProductoFrame.setVisible(true);
	}

	private void showBusquedaDialog() throws SQLException {
		String searchTerm = JOptionPane.showInputDialog(this, "Buscar:");
		if (searchTerm != null && !searchTerm.isEmpty()) {
			buscar(searchTerm);
		}
	}

	private void showResultadoPanel(Producto producto) {
		JFrame resultadoProductoFrame = new JFrame("Editar Producto");
		resultadoProductoFrame.setSize(500, 200);
		resultadoProductoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] columnNames = {"nombre", "descripcion", "precio", "peso [kg]"};
		String[][] prod ={{producto.getNombre(),producto.getDetalle(), String.valueOf(producto.getPrecioUnitario()),
				String.valueOf(producto.getPeso())}};

		JTable resultado = new JTable(new DefaultTableModel(prod, columnNames));
		JScrollPane contenedorTabla = new JScrollPane(resultado); //Sin esto no se muestra el nombre de las columnas

		//controla el tamaño (si no no se ven los botones)
		int maxVisibleRows = 3; // Change this value to limit the number of visible rows
		int rowHeight = resultado.getRowHeight();
		int headerHeight = resultado.getTableHeader().getPreferredSize().height;
		Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
				maxVisibleRows * rowHeight + headerHeight);
		contenedorTabla.setPreferredSize(preferredSize);


		// Create Editar, Dar de Baja, and Close buttons
		JButton editarButton = new JButton("Editar");
		JButton darDeBajaButton = new JButton("Dar de Baja");
		JButton cerrarButton = new JButton("Cerrar");

		JPanel panel = new JPanel();
		panel.add(contenedorTabla);
		panel.add(editarButton);
		panel.add(darDeBajaButton);
		panel.add(cerrarButton);

		editarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditarPanel(producto);
				resultadoProductoFrame.dispose();
			}
		});

		darDeBajaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showDarBajaDialog(producto);
					resultadoProductoFrame.dispose();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		cerrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultadoProductoFrame.dispose();
			}
		});

		resultadoProductoFrame.add(panel);
		resultadoProductoFrame.setVisible(true);
	}

	private void showDarBajaDialog(Producto producto) throws SQLException {
		int opcion = JOptionPane.showConfirmDialog(this,"¿Seguro desea dar de baja el producto"+
				producto.getNombre(),"Dar de Baja",JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION) {
			servicio.borrarProducto(producto.getId());
			}
	}
		private void buscar(String prodNombre) throws SQLException {
			//METODO QUE REALIZA LA BUSQUEDA
			Producto producto =servicio.buscarProducto(prodNombre);
			if(producto != null){
				showResultadoPanel(producto);
			}
			else{
				JOptionPane.showMessageDialog(this,"Producto no encontrado","Error",JOptionPane.OK_OPTION);
			}
		}

}
