package gui;

import clases.*;
import servicios.ProductoServicios;
import servicios.SucursalServicios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaOrden extends JFrame{
    private JPanel contentPane;
    SucursalServicios sucursalServicios = new SucursalServicios();
    Sucursal sucursal;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VentanaOrden(Sucursal s) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Gestion de Sucursales");
        this.sucursal = s;
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton altabtn = new JButton("Crear Orden Provision");
        JButton listarbtn = new JButton("Listar Ordenes Pendientes");
        JButton buscarbtn = new JButton("Buscar Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        contentPane.add(altabtn);
        contentPane.add(listarbtn);
        contentPane.add(buscarbtn);
        contentPane.add(cerrarbtn);
        setContentPane(contentPane);

        altabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showAltaPanel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buscarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuscarDialog();

            }
        });

        listarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showListarPanel();
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

    public void showAltaPanel() throws SQLException {
        ProductoServicios productoServicios = new ProductoServicios();
        List<Producto> listaProductos =productoServicios.listarProductos();

        JFrame frame = new JFrame("Crear Orden con destino: "+sucursal.getNombre());
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(2,2));

        JLabel tiempolbl = new JLabel("Tiempo minimo: ");
        JTextField tiempotxt = new JTextField();

        List<ProductoProvisto> productos = new ArrayList<>();

        JButton agregarbtn = new JButton("Agregar Producto a la orden");
        JButton guardarbtn = new JButton("Generar Orden");
        JButton cerrarbtn = new JButton("Cancelar");

        String[] columnNames = {"Producto", "Cantidad"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);


        JScrollPane contenedorTabla = new JScrollPane(tablaResultados);
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        agregarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoProvisto producto = showAgregarProdDialog(listaProductos);
                productos.add(producto);
                model.addRow(new String[]{producto.getProducto().getNombre(),String.valueOf(producto.getCantidad())});
            }
        });

        cerrarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        guardarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenProvision orden = new OrdenProvision();
                orden.setDestino(sucursal);
                orden.setFecha(LocalDate.now());
                orden.setPeso(calcularPeso(productos));
                orden.setTiempoLimite(Float.valueOf(tiempotxt.getText()));
                orden.setEstado(EstadoOrden.PENDIENTE);
                orden.setListaProductos(productos);
                //ordenProvisionServicios.altaOrden(orden);
            }
        });



    }

    public ProductoProvisto showAgregarProdDialog(List<Producto> productos){
        ProductoProvisto prod = new ProductoProvisto();
        JPanel panel = new JPanel(new GridLayout(3,1));
        JLabel lbl= new JLabel("Seleccione el producto y la cantidad");
        DefaultComboBoxModel<Producto> comboBoxModel = new DefaultComboBoxModel<>(productos.toArray(new Producto[0]));
        JComboBox<Producto> productosComboBox = new JComboBox<>(comboBoxModel);
        JTextField cantidadtxt = new JTextField(3);

        panel.add(lbl);
        panel.add(productosComboBox);
        panel.add(cantidadtxt);
        JOptionPane.showInputDialog(this,panel,"Agregar Producto",JOptionPane.PLAIN_MESSAGE);
        prod.setCantidad(Integer.valueOf(cantidadtxt.getText()));
        prod.setProducto((Producto) productosComboBox.getSelectedItem());

        return prod;
    }
    private float calcularPeso(List<ProductoProvisto> lista){
        float peso = 0;
        List<Producto> productos = lista.stream().map(produc-> produc.getProducto()).collect(Collectors.toList());
        for(Producto p: productos){
            peso = p.getPeso();
        }
        return peso;
    }
    public void showBuscarDialog(){
        String id =JOptionPane.showInputDialog(this, "Id: ","Buscar Orden");
        if(!id.isEmpty() || id!=null){
            buscarOrden(Integer.valueOf(id));
        }

    }
    public void showListarPanel(){
        JFrame listarFrame = new JFrame("Resultado Busqueda:");
        listarFrame.setSize(500, 300);
        listarFrame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelListar = new JPanel();

        List<OrdenProvision> listaOrdenes = null;

        String[] columnNames = {"Id", "Fecha", "Tiempo Limite", "Peso", "Estado"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        for(OrdenProvision o: listaOrdenes){
            String[] fila = {String.valueOf(o.getId()),String.valueOf(o.getFecha()),
                    String.valueOf(o.getTiempoLimite()),String.valueOf(o.getPeso()),String.valueOf(o.getEstado())};
            model.addRow(fila);
        }

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        JButton verProductosbtn = new JButton("Ver Listado Productos");
        JButton bajabtn = new JButton("Dar de Baja Orden");
        JButton editarbtn = new JButton("Editar Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        panelListar.add(contenedorTabla);
        panelListar.add(verProductosbtn);
        panelListar.add(bajabtn);
        panelListar.add(editarbtn);
        panelListar.add(cerrarbtn);

        cerrarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarFrame.dispose();

            }
        });

        verProductosbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tablaResultados.getSelectedRow();
                if(index != -1) {
                    showListaProductos(listaOrdenes.get(index));
                }
            }
        });

        bajabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaResultados.getSelectedRow();
                if (row != -1) {
                    OrdenProvision orden = listaOrdenes.get(row);
                    boolean confirm = false;
                    confirm = showBorrarOrdenDialog(orden);
                    if (confirm) {
                        model.removeRow(row);
                    }
                }
            }
        });

        editarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarFrame.dispose();

            }
        });
        listarFrame.add(panelListar);
        listarFrame.setVisible(true);
    }

    public boolean showBorrarOrdenDialog(OrdenProvision orden){
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar la orden" +
                        "de provision?","Eliminar Orden",JOptionPane.YES_NO_OPTION);
        if(opcion==JOptionPane.YES_OPTION){
            //ordenServicios.borrarOrden(orden);
            return true;
        }
        else{
            return false;
        }
    }

    public void showListaProductos(OrdenProvision orden) {

        JPanel panelListar = new JPanel();

        List<ProductoProvisto> listaProductos = null;

        String[] columnNames = {"Producto", "Cantidad"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        for (ProductoProvisto p : listaProductos) {
            String[] fila = {p.getProducto().getNombre(), String.valueOf(p.getCantidad())};
            model.addRow(fila);
        }

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        panelListar.add(contenedorTabla);

        JOptionPane.showConfirmDialog(this,panelListar,"Productos en la Orden",JOptionPane.OK_OPTION);

    }

    private void buscarOrden(int id){
        //OrdenProvision orden = ordenProvisionServicios.buscarOrden(id);
        OrdenProvision orden = null;
        if(orden != null){
            showOrdenPanel(orden);
        }
        else{
            JOptionPane.showMessageDialog(this,"No se encontro una orden de provision con ese id");
        }
    }

    public void agregarProductos(){}
    public void showOrdenPanel(OrdenProvision o){
        JFrame frame = new JFrame("Resultado Busqueda:");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel resultadobusqueda = new JPanel();
        String[] columnNames = {"Id", "Fecha", "Tiempo Limite", "Peso", "Estado"};
        String[][] resultado ={{String.valueOf(o.getId()),String.valueOf(o.getFecha()),
                String.valueOf(o.getTiempoLimite()),String.valueOf(o.getPeso()),String.valueOf(o.getEstado())}};

        JTable tablaResultados = new JTable(resultado,columnNames);
        JButton verProductosbtn = new JButton("Ver Listado Productos");
        JButton bajabtn = new JButton("Dar de Baja Orden");
        JButton editarbtn = new JButton("Editar Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        resultadobusqueda.add(tablaResultados);
        resultadobusqueda.add(verProductosbtn);
        resultadobusqueda.add(bajabtn);
        resultadobusqueda.add(editarbtn);
        resultadobusqueda.add(cerrarbtn);

        cerrarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

            }
        });

        verProductosbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              showListaProductos(o);
            }
        });

        bajabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBorrarOrdenDialog(o);
            }
        });

        editarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

            }
        });

        frame.add(resultadobusqueda);
        frame.setVisible(true);

    }
}
