package gui;

import clases.Estado;
import clases.Producto;
import clases.StockProducto;
import clases.Sucursal;
import servicios.ProductoServicios;
import servicios.SucursalServicios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalTime;
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
		JButton stockbtn = new JButton("Gestion de Stock");
		JButton ordenbtn = new JButton("Generar Orden de Provision");
		JButton cerrarbtn = new JButton("Cerrar");

		contentPane.add(altabtn);
		contentPane.add(buscarbtn);
		contentPane.add(bajabtn);
		contentPane.add(stockbtn);
		contentPane.add(ordenbtn);
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

		bajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBuscarPanel();
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
				CrearOrdenProvisionUI crearOrderProvisionUI = new CrearOrdenProvisionUI();
				crearOrderProvisionUI.init();
				crearOrderProvisionUI.show();
			}
		});

		contentPane.setVisible(true);

	}

	public void showAltaPanel(){
		JFrame frameAlta = new JFrame("Nueva Sucursal");
		frameAlta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAlta.setLayout(new BorderLayout());

		AtributosSucursalesPanel panel = new AtributosSucursalesPanel(new GridLayout(6,2));

		JButton guardarbtn = new JButton("Agregar Sucursal");
		JButton cancelarbtn = new JButton("Cancelar");

		panel.add(guardarbtn);
		panel.add(cancelarbtn);

		guardarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sucursal sucursal = new Sucursal();
				sucursal.setNombre(panel.nombretxt.getText());
				sucursal.setHoraApertura(LocalTime.of((int)panel.horaApertura.getValue(), (int)panel.minApertura.getValue()));
				sucursal.setHoraCierre(LocalTime.of((int)panel.horaCierre.getValue(), (int)panel.minCierre.getValue()));
				sucursal.setEstado((Estado) panel.estadoComboBox.getSelectedItem());
				sucursal.setCapacidad(Float.valueOf(panel.capacidadtxt.getText()));
				try {
					sucursalServicios.agregarSucursal(sucursal);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				frameAlta.dispose();
			}
		});

		cancelarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameAlta.dispose();

			}
		});

		frameAlta.add(panel);
		frameAlta.pack();
		frameAlta.setLocationRelativeTo(null);
		frameAlta.setVisible(true);

	}

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
						buscar(nombretxt.getText());
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
		List<Sucursal> sucursales= sucursalServicios.buscarSucursalesEstado(estado);
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
		resultadoFrame.setSize(500, 300);
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
		//hel´l
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
				int index = tablaResultados.getSelectedRow();
				showEditarPanel(sucursales.get(index));

			}
		});
		darBajabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tablaResultados.getSelectedRow();
				try {
					showBajaDialog(sucursales.get(index));
					resultadoFrame.dispose();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		estadobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tablaResultados.getSelectedRow();
				showModificarEstadoDialog(sucursales.get(index));
				resultadoFrame.dispose();
			}
		});
		stockbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tablaResultados.getSelectedRow();
				showStockPanel(sucursales.get(index));
				resultadoFrame.dispose();
			}
		});
		ordenbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tablaResultados.getSelectedRow();
				showOrden(sucursales.get(index));
				resultadoFrame.dispose();
			}
		});

		resultadoFrame.add(panel);
		resultadoFrame.setVisible(true);

	}

	public void showBajaDialog(Sucursal sucursal) throws SQLException {
		int option = JOptionPane.showConfirmDialog(this,"¿Seguro desea dar de baja permanentemente la sucusal",
				"Dar de baja",JOptionPane.YES_NO_OPTION);
		if(option== JOptionPane.YES_OPTION){
			sucursalServicios.borrarSucursal(sucursal);
		}
	}

	public void  showModificarEstadoDialog(Sucursal sucursal){
		String msg="Actualmente el camino se encuentra "+String.valueOf(sucursal.getEstado())+
				"¿Desea modificarlo?";
		int opcion =JOptionPane.showConfirmDialog(this,msg,"Confirmacion",JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION){
			sucursalServicios.modificarEstado(sucursal);
		}
	}
	public void showEditarPanel(Sucursal sucursal){

		JFrame frameEditar = new JFrame("Editar Informacion sobre: "+sucursal.getNombre());
		frameEditar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEditar.setLayout(new BorderLayout());

		AtributosSucursalesPanel panel = new AtributosSucursalesPanel(new GridLayout(6,2));

		JButton guardarbtn = new JButton("Guardar Cambios");
		JButton cancelarbtn = new JButton("Cancelar");

		panel.horaApertura.setValue(sucursal.getHoraApertura().getHour());
		panel.minApertura.setValue(sucursal.getHoraApertura().getMinute());
		panel.horaCierre.setValue(sucursal.getHoraCierre().getHour());
		panel.minCierre.setValue(sucursal.getHoraCierre().getMinute());
		panel.estadoComboBox.setSelectedItem(sucursal.getEstado());
		panel.add(guardarbtn);
		panel.add(cancelarbtn);

		guardarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!panel.nombretxt.getText().isEmpty()){
				sucursal.setNombre(panel.nombretxt.getText());}
				sucursal.setHoraApertura(LocalTime.of((int)panel.horaApertura.getValue(), (int)panel.minApertura.getValue()));
				sucursal.setHoraCierre(LocalTime.of((int)panel.horaCierre.getValue(), (int)panel.minCierre.getValue()));
				sucursal.setEstado((Estado) panel.estadoComboBox.getSelectedItem());
				if(!panel.capacidadtxt.getText().isEmpty()){
				sucursal.setCapacidad(Float.valueOf(panel.capacidadtxt.getText()));}
				sucursalServicios.editarSucursal(sucursal);
				frameEditar.dispose();
			}
		});

		cancelarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameEditar.dispose();

			}
		});

		frameEditar.add(panel);
		frameEditar.pack();
		frameEditar.setLocationRelativeTo(null);
		frameEditar.setVisible(true);
	}
	public void showStockPanel(Sucursal sucursal){
		JFrame stockFrame = new JFrame("Resultado Busqueda:");
		stockFrame.setSize(500, 300);
		stockFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new FlowLayout());


		String[] columnNames = {"Producto","Cantidad"};

		JTable tablaResultados = new JTable(new DefaultTableModel());

		DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
		model.setColumnIdentifiers(columnNames);
		if(sucursal.getStock()!=null) {
			for (StockProducto s : sucursal.getStock()) {
				String[] fila = {s.getProducto().getNombre(), String.valueOf(s.getCantidad())};
				model.addRow(fila);
			}
		}

		JScrollPane contenedorTabla = new JScrollPane(tablaResultados);
		int maxVisibleRows = 7;
		int rowHeight = tablaResultados.getRowHeight();
		int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
		Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
				maxVisibleRows * rowHeight + headerHeight);
		contenedorTabla.setPreferredSize(preferredSize);

		JButton agregarbtn = new JButton("Añadir Producto");
		JButton borrarbtn = new JButton("Eliminar Producto");
		JButton actualizarbtn = new JButton("Actualizar Stock");
		JButton cerrarbtn = new JButton("Cerrar");

		actualizarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row =tablaResultados.getSelectedRow();
				if (row!=-1 && sucursal.getStock()!=null){
					Producto prod= sucursal.getStock().get(row).getProducto();
					String cantidadAct = null;
					try {
						cantidadAct = showActualizarDialog(prod, sucursal,row);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
					tablaResultados.setValueAt(cantidadAct,row,1);
				}
			}
		});

		borrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row =tablaResultados.getSelectedRow();
				if (row!=-1){
					Producto prod= sucursal.getStock().get(row).getProducto();
					boolean confirm = false;
					try {
						confirm = showBorrarProdStockDialog(prod,sucursal);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
					if(confirm){
						model.removeRow(row);
					}
				}

			}
		});

		agregarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StockProducto nuevoStock = null;
				try {
					nuevoStock = showAgregarStockDialog(sucursal);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				if(nuevoStock!=null) {
					model.addRow(new String[]{nuevoStock.getProducto().getNombre(), String.valueOf(nuevoStock.getCantidad())});
				}
			}
		});
		cerrarbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stockFrame.dispose();

			}
		});

		panel.add(contenedorTabla);
		panel.add(agregarbtn);
		panel.add(borrarbtn);
		panel.add(actualizarbtn);
		panel.add(cerrarbtn);
		stockFrame.add(panel);
		stockFrame.setVisible(true);

	}

	public StockProducto showAgregarStockDialog(Sucursal sucursal) throws SQLException {
		StockProducto stock = new StockProducto();
		JPanel panel = new JPanel(new GridLayout(3,1));
		JLabel lbl= new JLabel("Seleccione el producto y la cantidad");
		List<Producto> productos= listaProductos();
		DefaultComboBoxModel<Producto> comboBoxModel = new DefaultComboBoxModel<>(productos.toArray(new Producto[0]));
		JComboBox<Producto> productosComboBox = new JComboBox<>(comboBoxModel);
		JTextField cantidadtxt = new JTextField(3);

		panel.add(lbl);
		panel.add(productosComboBox);
		panel.add(cantidadtxt);
		int opcion =JOptionPane.showOptionDialog(this,panel,"Nuevo Stock",
				JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);
		if(opcion==JOptionPane.OK_OPTION){
			//agregar;
			stock.setProducto((Producto) productosComboBox.getSelectedItem());
			stock.setCantidad(Integer.valueOf(cantidadtxt.getText()));
			sucursal.addStock(stock);
			sucursalServicios.altaStockProducto(stock.getProducto(),sucursal,stock.getCantidad());
		}
		else{
			stock = null;
		}
		return stock;
	}
	public boolean showBorrarProdStockDialog(Producto prod,Sucursal sucursal) throws SQLException {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar el stock del" +
				"producto"+prod.getNombre(),"Eliminar Stock",JOptionPane.YES_NO_OPTION);
		if(opcion==JOptionPane.YES_OPTION){
			sucursalServicios.borrarStockProducto(prod,sucursal);
			return true;
		}
		else{
			return false;
		}
	}
	public String showActualizarDialog(Producto prod,Sucursal sucursal, int fila) throws SQLException {
		String msj = "Ingrese la nueva cantidad de stock para el producto "+prod.getNombre();
		String cant = JOptionPane.showInputDialog(null,
				msj,JOptionPane.PLAIN_MESSAGE);
		if(cant!=null){
			sucursalServicios.editarStockProducto(prod,sucursal, Integer.parseInt(cant));
		}
		return cant;
	}

	private List<Producto> listaProductos() throws SQLException {
		ProductoServicios productoServicios = new ProductoServicios();
		return productoServicios.listarProductos();
	}
	public void showOrden(Sucursal sucursal){}

}
