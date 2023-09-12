package gui;

import clases.*;
import dao.CaminoRepository;
import routeviewer.RouteGUI;
import servicios.OrdenProvisionServicios;
import servicios.ProductoServicios;
import servicios.SucursalServicios;
import servicios.gestionOrden;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaOrden extends JFrame{
    private JPanel contentPane;
    SucursalServicios sucursalServicios = new SucursalServicios();
    OrdenProvisionServicios ordenProvisionServicios = new OrdenProvisionServicios();
    Sucursal sucursal = null;

    public VentanaOrden(Sucursal s) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Gestion de Ordenes");
        this.sucursal = s;
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton altabtn = new JButton("Crear Orden Provision");
        JButton listarbtn = new JButton("Listar Ordenes de la Sucursal");
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
                try {
                    showBuscarDialog();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        listarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    showListarPorSucursalPanel();
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
        contentPane.setVisible(true);

    }

    public VentanaOrden(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Gestion de Ordenes");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton altabtn = new JButton("Crear Orden Provision");
        JButton buscarbtn = new JButton("Buscar Orden");
        JButton cerrarbtn = new JButton("Cerrar");
        JButton gestionarOrdenesDeSucursal = new JButton("Gestionar ordenes de sucursal");

        contentPane.add(altabtn);
        contentPane.add(buscarbtn);
        contentPane.add(cerrarbtn);
        contentPane.add(gestionarOrdenesDeSucursal);
        setContentPane(contentPane);

        altabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sucursal = getSucursalDestino();
                    showAltaPanel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buscarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showBuscarDialog();
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

        gestionarOrdenesDeSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Sucursal> sucursalList = null;
                try{
                    sucursalList = sucursalServicios.listarSucursales();
                    if(sucursalList == null || sucursalList.size() == 0){
                        System.out.println("SucusalList empty or null");
                        return;
                    }
                    showSelectSucursalToManageOrderDialog(sucursalList);
                } catch (Exception ex){
                    ex.printStackTrace();
                    System.exit(-1);
                }

//                String id =JOptionPane.showInputDialog(this, "Id de la Orden: ");
//                if(!id.isEmpty() || id!=null){
//                    buscarOrden(Integer.valueOf(id));
//                }
            }
        });

        contentPane.setVisible(true);
    }

    private void showSelectSucursalToManageOrderDialog(List<Sucursal> sucursalList){
        JFrame dialogFr = new JFrame("Gestionar ordenes de sucursal");
        dialogFr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComboBox<String> sucursalesCb = new JComboBox<>();
        sucursalesCb.addItem("Seleccionar sucursal");
        for(Sucursal s : sucursalList){
            sucursalesCb.addItem(s.getNombre() + "(Id " + s.getId() + ")");
        }

        JButton gestionarOrdenesDeSucBtn = new JButton("Gestionar Ordenes de sucursal");
        gestionarOrdenesDeSucBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sucursalesCb.getSelectedIndex() <= 0){
                    System.out.println("Select index <= 0");
                    return;
                }
                Sucursal selectedSucursal = sucursalList.get(sucursalesCb.getSelectedIndex() - 1);
                try{
                    System.out.println("Fetching ordenes");
                    List<OrdenProvision> ordenesDeSucursal = ordenProvisionServicios.listarOrdenes(selectedSucursal);
                    if(ordenesDeSucursal == null || ordenesDeSucursal.isEmpty()){
                        JOptionPane.showMessageDialog(dialogFr, "No hay ordenes de esta sucursal");
                    }else{
                        showMultiOrdenPanel(ordenesDeSucursal);
                        dialogFr.dispose();
                    }


                } catch (Exception ex){
                    System.out.println("Gestionar multi orden error");
                    ex.printStackTrace();

                }

            }
        });
        gestionarOrdenesDeSucBtn.setEnabled(false);

        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFr.dispose();
            }
        });
        sucursalesCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sucursalesCb.getSelectedIndex() <= 0){
                    gestionarOrdenesDeSucBtn.setEnabled(false);
                }else {
                    gestionarOrdenesDeSucBtn.setEnabled(true);
                }
            }
        });


        JPanel panel = new JPanel();
        panel.add(sucursalesCb, BorderLayout.NORTH);
        panel.add(gestionarOrdenesDeSucBtn, BorderLayout.SOUTH);
        panel.add(cancelBtn, BorderLayout.SOUTH);
        dialogFr.setLayout(new BorderLayout());
        dialogFr.add(panel);
        dialogFr.setSize(300, 200);
        dialogFr.setLocationRelativeTo(null);
        dialogFr.setVisible(true);

    }
    public void showAltaPanel() throws SQLException {
        ProductoServicios productoServicios = new ProductoServicios();
        List<Producto> listaProductos =productoServicios.listarProductos();

        JFrame frame = new JFrame("Crear Orden con destino: "+sucursal.getNombre());
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        JLabel tiempolbl = new JLabel("Tiempo minimo (en horas): ");
        JTextField tiempotxt = new JTextField(5);

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

        panel.add(tiempolbl);
        panel.add(tiempotxt);
        panel.add(contenedorTabla);
        panel.add(agregarbtn);
        panel.add(guardarbtn);
        panel.add(cerrarbtn);
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
                frame.dispose();
            }
        });

        guardarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenProvision orden = new OrdenProvision();
                orden.setDestino(sucursal);
                orden.setFecha(LocalDate.now());
                orden.setPeso(calcularPeso(productos));
                float tiempoLimite = 0;
                if(tiempotxt.getText() != null && !tiempotxt.getText().isEmpty()){
                    tiempoLimite = Float.valueOf(tiempotxt.getText());
                }
                orden.setTiempoLimite(tiempoLimite);
                orden.setEstado(EstadoOrden.PENDIENTE);
                if(productos!=null && !productos.isEmpty()){
                orden.setListaProductos(productos);}
                try {
                    ordenProvisionServicios.altaOrden(orden);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });
        frame.add(panel);
        frame.setVisible(true);

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
        JOptionPane.showMessageDialog(this,panel,"Agregar Producto",JOptionPane.PLAIN_MESSAGE);
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
    public void showBuscarDialog() throws SQLException {
        String id =JOptionPane.showInputDialog(this, "Id de la Orden: ");
        if(!id.isEmpty() || id!=null){
            buscarOrden(Integer.valueOf(id));
        }

    }
    public void showListarPorSucursalPanel() throws SQLException {
        JFrame listarFrame = new JFrame("Ordenes con Destino: "+sucursal.getNombre());
        listarFrame.setSize(500, 300);
        listarFrame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelListar = new JPanel();

        List<OrdenProvision> listaOrdenes = ordenProvisionServicios.listarOrdenes(sucursal);

        String[] columnNames = {"Id", "Fecha", "Tiempo Limite", "Peso", "Estado"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        if(listaOrdenes !=null){
        for(OrdenProvision o: listaOrdenes){
            String[] fila = {String.valueOf(o.getId()),String.valueOf(o.getFecha()),
                    String.valueOf(o.getTiempoLimite()),String.valueOf(o.getPeso()),String.valueOf(o.getEstado())};
            model.addRow(fila);
        }}

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        JButton verProductosbtn = new JButton("Ver Listado Productos");
        JButton bajabtn = new JButton("Dar de Baja Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        panelListar.add(contenedorTabla);
        panelListar.add(verProductosbtn);
        panelListar.add(bajabtn);
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
                    try {
                        confirm = showBorrarOrdenDialog(orden);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    if (confirm) {
                        model.removeRow(row);
                    }
                }
            }
        });

        listarFrame.add(panelListar);
        listarFrame.setVisible(true);
    }

    public void showListarPanel(){
        JFrame listarFrame = new JFrame("Lista de Ordenes");
        listarFrame.setSize(500, 300);
        listarFrame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelListar = new JPanel();

        List<OrdenProvision> listaOrdenes = null;

        String[] columnNames = {"Id","Destino","Fecha", "Tiempo Limite", "Peso", "Estado"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        if(listaOrdenes!=null){
        for(OrdenProvision o: listaOrdenes){
            String[] fila = {String.valueOf(o.getId()),o.getDestino().getNombre(),String.valueOf(o.getFecha()),
                    String.valueOf(o.getTiempoLimite()),String.valueOf(o.getPeso()),String.valueOf(o.getEstado())};
            model.addRow(fila);
        }}

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados);
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        JButton verProductosbtn = new JButton("Ver Listado Productos");
        JButton bajabtn = new JButton("Dar de Baja Orden");
        JButton cerrarbtn = new JButton("Cerrar");

        panelListar.add(contenedorTabla);
        panelListar.add(verProductosbtn);
        panelListar.add(bajabtn);
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
                    try {
                        confirm = showBorrarOrdenDialog(orden);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    if (confirm) {
                        model.removeRow(row);
                    }
                }
            }
        });

        listarFrame.add(panelListar);
        listarFrame.setVisible(true);
    }
    public boolean showBorrarOrdenDialog(OrdenProvision orden) throws Exception {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar la orden" +
                        "de provision?","Eliminar Orden",JOptionPane.YES_NO_OPTION);
        if(opcion==JOptionPane.YES_OPTION){
            ordenProvisionServicios.bajaOrden(orden);
            return true;
        }
        else{
            return false;
        }
    }

    public void showListaProductos(OrdenProvision orden) {

        JPanel panelListar = new JPanel();

        List<ProductoProvisto> listaProductos = orden.getListaProductos();

        String[] columnNames = {"Producto", "Cantidad"};
        JTable tablaResultados = new JTable(new DefaultTableModel());

        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setColumnIdentifiers(columnNames);

        if(listaProductos != null){
        for (ProductoProvisto p : listaProductos) {
            String[] fila = {p.getProducto().getNombre(), String.valueOf(p.getCantidad())};
            model.addRow(fila);
        }}

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
        int maxVisibleRows = 7;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        panelListar.add(contenedorTabla);

        JOptionPane.showMessageDialog(this,panelListar,"Productos en la Orden",JOptionPane.OK_OPTION,null);

    }

    private void buscarOrden(int id) throws SQLException {
        OrdenProvision orden = ordenProvisionServicios.buscarOrden(id);
        if(orden != null){
            showOrdenPanel(orden);
        }
        else{
            JOptionPane.showMessageDialog(this,"No se encontro una orden de provision con ese id");
        }
    }
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
        JButton mapabtn = new JButton("Seleccionar Recorrido");
        JButton cerrarbtn = new JButton("Cerrar");

        resultadobusqueda.add(tablaResultados);
        resultadobusqueda.add(verProductosbtn);
        resultadobusqueda.add(mapabtn);
        resultadobusqueda.add(bajabtn);
        resultadobusqueda.add(cerrarbtn);

        cerrarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

            }
        });

        mapabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionRecorridoOrden(o);
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
                try {
                    showBorrarOrdenDialog(o);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(resultadobusqueda);
        frame.setVisible(true);

    }

    public void showMultiOrdenPanel(List<OrdenProvision> ordenes){
        if(ordenes == null || ordenes.isEmpty()) return;

        JFrame frame = new JFrame("Resultado Busqueda:");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel resultadobusqueda = new JPanel();
        String[] columnNames = {"Id", "Fecha", "Tiempo Limite", "Peso", "Estado"};
        //List<String[]> resultados = new ArrayList<>();
//                {{String.valueOf(o.getId()),String.valueOf(o.getFecha()),
//                String.valueOf(o.getTiempoLimite()),String.valueOf(o.getPeso()),String.valueOf(o.getEstado())}};
        int idx = 0;
        String[][] resultadosArray = new String[ordenes.size()][columnNames.length];
        for(OrdenProvision o : ordenes){
            String[] row = {String.valueOf(o.getId()),
                    String.valueOf(o.getFecha()),
                    String.valueOf(o.getTiempoLimite()),
                    String.valueOf(o.getPeso()),
                    String.valueOf(o.getEstado())};
            resultadosArray[idx] = row;
            idx++;
        }


        JTable tablaResultados = new JTable(resultadosArray,columnNames);
        JButton verProductosbtn = new JButton("Ver Listado Productos");
        //JButton bajabtn = new JButton("Dar de Baja Orden");
        JButton cerrarbtn = new JButton("Cerrar");
        JButton seleccionarRecorridobtn = new JButton("Seleccionar recorrido");

        resultadobusqueda.add(tablaResultados);
        resultadobusqueda.add(verProductosbtn);
        resultadobusqueda.add(seleccionarRecorridobtn);
        //resultadobusqueda.add(bajabtn);
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
                OrdenProvision op = ordenes.get(tablaResultados.getSelectedRow());
                showListaProductos(op);
            }
        });

//        bajabtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    OrdenProvision op = ordenes.get(tablaResultados.getSelectedRow());
//                    showBorrarOrdenDialog(o);
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });

        seleccionarRecorridobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ordenIndex = tablaResultados.getSelectedRow();
                if(ordenIndex < 0){
                    JOptionPane.showMessageDialog(frame, "Seleccione una de la lista");
                    return;
                }
                OrdenProvision op = ordenes.get(tablaResultados.getSelectedRow());
                if(op.getEstado() == EstadoOrden.EN_PROCESO) {
                    JOptionPane.showMessageDialog(frame, "Esta orden de provision ya esta en proceso");
                    return;
                }
                showGestionRecorridoOrden(op);
                frame.dispose();
            }
        });

        frame.add(resultadobusqueda);
        frame.setVisible(true);

    }

    public void showGestionRecorridoOrden(OrdenProvision op){
        final List<List<Camino>> caminos = new ArrayList<List<Camino>>();//Decoment for real
        List<List<Sucursal>> rutasSucursal = new ArrayList<List<Sucursal>>();

        try{
            gestionOrden go = new gestionOrden();//Decoment for real
            go.generarResultadosValidos(op, rutasSucursal, caminos);//Decoment for real
        }catch(Exception e){
            System.out.println("Error gestion recorrido orden");
            e.printStackTrace();
            return;
        }
        RouteGUI routeGUI = new RouteGUI();
        //final List<List<Camino>> caminos = routeGUI.getPruebasCam(); //fortesting; Comment for real

        //---<other pruebas>
//        List<Camino> caminoPruebtotal = CaminoRepository.getInstance().listarCaminos();
//        List<List<Camino>> caminosB = new ArrayList<>();
//        caminosB.add(caminoPruebtotal);
//        final List<List<Camino>> caminos = caminosB; //fortesting; Comment for real
        //---</other pruebas>
        JFrame frame = new JFrame("Resultado Busqueda:");

        if(caminos == null || caminos.isEmpty()){
            JOptionPane.showMessageDialog(frame, "No hay caminos para mostrar");
            frame.dispose();
            return;
        }

        List<Sucursal> allSucursales = new ArrayList<>();
        try{
            allSucursales = sucursalServicios.listarSucursales();
        } catch( Exception exc){
            System.out.println("Error obtener sucursales");
            System.exit(-1);
        }
        routeGUI.setRecorridos(caminos, allSucursales);

        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        RouteGUI routeGUI = new RouteGUI();

        String[] columnNames = {"Sucursal proveedora", "Tiempo de envio"};
        int idx = 0;
        String[][] resultadosArray = new String[caminos.size()][columnNames.length];
        for(List<Camino> recorrido : caminos){
            String sucursalProv = recorrido.get(0).getOrigen().getNombre();
            String[] row = {
                    sucursalProv,
                    String.valueOf(tiempoCamino(recorrido)),
            };
            resultadosArray[idx] = row;
            idx++;
        }
        JPanel containerPanel = new JPanel(new FlowLayout());
        containerPanel.setSize(500,900);
        JPanel headerPanel = new JPanel();
        JLabel tituloLabel = new JLabel("Seleccione un camino de la lista inferior");
        JLabel subtituloLabel = new JLabel("Los caminos estan ordenados desde el mas optimo hasta el menos optimo " +
                "Luego de seleccionar un camino presione el boton asignar");
        JButton asignarBtn = new JButton("Asignar");

        JTable tablaResultados = new JTable(resultadosArray,columnNames);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if(resultadosArray.length > 0){
            tablaResultados.getSelectionModel().setSelectionInterval(0,0);
        }

        tablaResultados.setDefaultEditor(Object.class, null);

        JScrollPane contenedorTabla = new JScrollPane(tablaResultados); //Sin esto no se muestra el nombre de las columnas
        int maxVisibleRows = 4;
        int rowHeight = tablaResultados.getRowHeight();
        int headerHeight = tablaResultados.getTableHeader().getPreferredSize().height;
        Dimension preferredSize = new Dimension(contenedorTabla.getPreferredSize().width,
                maxVisibleRows * rowHeight + headerHeight);
        contenedorTabla.setPreferredSize(preferredSize);

        headerPanel.add(tituloLabel);
        headerPanel.add(subtituloLabel);
        containerPanel.add(headerPanel);
        containerPanel.add(contenedorTabla, BorderLayout.NORTH);
        containerPanel.add(routeGUI);
        containerPanel.add(asignarBtn, BorderLayout.SOUTH);
        frame.add(containerPanel);

        tablaResultados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tablaResultados.getSelectedRow();
                if(row < 0){
                    return;
                }

                routeGUI.setSelectedIndex(row);


            }
        });

        asignarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaResultados.getSelectedRow();
                if(row < 0){
                    JOptionPane.showMessageDialog(frame, "Seleccione una de la lista");
                    return;
                }
                List<Camino> selectedCaminoToAssign = caminos.get(row);
                boolean result = false;
                try{
                    result = ordenProvisionServicios.asignarRecorridoAOrdenProvision(selectedCaminoToAssign, op);

                } catch (Exception exception){
                    JOptionPane.showMessageDialog(frame, "Error!: " + exception.getMessage());
                }

                if(result){
                    JOptionPane.showMessageDialog(frame, "Asignado correctamente");
                }

            }
        });

        //resultadobusqueda.add(bajabtn);
        //resultadobusqueda.add(cerrarbtn);

        //frame.add(routeGUI, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public Sucursal getSucursalDestino() throws SQLException {
        Sucursal destino = null;
        String id =JOptionPane.showInputDialog(this, "Id Sucursal destino: ");
        if(!id.isEmpty() || id!=null){
            destino = sucursalServicios.buscarSucursal(Integer.valueOf(id));
        }
        return destino;
    }

    private double tiempoCamino(List<Camino> recorrido){
       return recorrido.stream().mapToDouble(c-> c.getTiempoTransito()).sum();
    }

}
