package gui;

import clases.Producto;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaProductos extends JFrame {

	private JPanel contentPane;

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
				showBusquedaDialog();
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
		editarProductoPanel.setLayout(new GridLayout(4, 2));
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
				// Implement your "Guardar" functionality here

				// Save the data or perform any other actions
				editarProductoFrame.dispose(); // Close the Alta Producto frame after saving
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
		altaProductoPanel.setLayout(new GridLayout(4, 2));
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
				// Implement your "Guardar" functionality here

				// Save the data or perform any other actions
				altaProductoFrame.dispose(); // Close the Alta Producto frame after saving
			}
		});

		altaProductoFrame.add(altaProductoPanel);
		altaProductoFrame.setVisible(true);
	}

	private void showBusquedaDialog() {
		String searchTerm = JOptionPane.showInputDialog(this, "Buscar:");
		if (searchTerm != null && !searchTerm.isEmpty()) {
			buscar(searchTerm);
		}
	}

	private void  showModificarPanel(Producto producto) {
		JFrame modificarProductoFrame = new JFrame("Editar Producto");
		modificarProductoFrame.setSize(400, 200);
		modificarProductoFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] columnNames = {"nombre", "descripcion", "precio", "peso [kg]"};
		String[][] resultado ={{producto.getNombre(),producto.getDetalle(), String.valueOf(producto.getPrecioUnitario()),
				String.valueOf(producto.getPeso())}};
		JTable table = new JTable(new DefaultTableModel(resultado, columnNames));

		// Create Editar, Dar de Baja, and Close buttons
		JButton editarButton = new JButton("Editar");
		JButton darDeBajaButton = new JButton("Dar de Baja");
		JButton closeButton = new JButton("Close");

		JPanel panel = new JPanel();
		panel.add(table);
		panel.add(editarButton);
		panel.add(darDeBajaButton);
		panel.add(closeButton);

		editarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditarPanel(producto);
			}
		});

		darDeBajaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDarBajaDialog(producto);
			}
		});

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//cerrarFrame;?
			}
		});

		modificarProductoFrame.add(panel);
		modificarProductoFrame.setVisible(true);
	}

	private void showDarBajaDialog(Producto producto){
		int opcion = JOptionPane.showConfirmDialog(this,"¿Seguro desea dar de baja el producto"+
				producto.getNombre(),"Dar de Baja",JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION) {
			//DAR DE BAJA DE VERDAD
			}
	}
		private Producto buscar(String prodNombre){
			//METODO QUE REALIZA LA BUSQUEDA
			//si encuentra, abre panel, si no, msg producto no encontrado
			return null;
		}

}
