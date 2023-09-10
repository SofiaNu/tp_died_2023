package servicios;

import clases.*;
import dao.CaminoRepository;
import dao.StockProductoRepository;
import dao.SucursalRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SucursalServicios {

    private SucursalRepository sucursalRepository;
    private StockProductoRepository stockProductoRepository;

    private CaminoServicios caminoServicios = new CaminoServicios();

    public SucursalServicios() {
        super();
        sucursalRepository = SucursalRepository.getInstance();
        stockProductoRepository = StockProductoRepository.getInstance();
    }

    public Sucursal buscarSucursal(int id) throws SQLException {
        return sucursalRepository.buscarSucursal(id);
    }

    public Sucursal buscarSucursal(String nombre) throws SQLException {
        return sucursalRepository.buscarSucursal(nombre);
    }

    public List<Sucursal> buscarSucursalesEstado(Estado estado) throws SQLException {
        boolean e;
        if (estado == Estado.OPERATIVO) {
            e = true;
        } else {
            e = false;
        }

        return sucursalRepository.buscarSucursal(e);
    }

    public void agregarSucursal(Sucursal sucursal) throws SQLException {
        sucursalRepository.altaSucursal(sucursal);
    }

    public void borrarSucursal(Sucursal sucursal) throws SQLException {

        sucursalRepository.bajaSucursal(sucursal);
    }

    public List<Sucursal> listarSucursales() throws SQLException {
        return sucursalRepository.listarSucursal();
    }

    public List<Sucursal> listarSucursalesOperativas() {
        return sucursalRepository.listarOperativos();
    }

    public void modificarEstado(Sucursal sucursal) {
        Estado aux;
        if (sucursal.getEstado() == Estado.OPERATIVO) {
            aux = Estado.NO_OPERATIVO;
        } else {
            aux = Estado.OPERATIVO;
        }

        List<Camino> caminos = caminoServicios.listarCaminosAModificarEstado(sucursal.getId(), aux);

        sucursalRepository.modificarEstado(sucursal.getId(), sucursal.getEstado());

        caminoServicios.modificarEstados(caminos);
    }

    public void editarSucursal(Sucursal sucursal) {
        sucursalRepository.editarSucursal(sucursal);
    }

    public void altaStockProducto(Producto producto, Sucursal sucursal, int cantidad) throws SQLException {
        if (stockProductoRepository.altaStockProducto(producto.getId(), sucursal.getId(), cantidad)) {
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }

    }

    public void borrarStockProducto(Producto producto, Sucursal sucursal) throws SQLException {
        if (stockProductoRepository.bajaStockProductoEnSucursal(producto.getId(), sucursal.getId())) {
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }
    }

    public void editarStockProducto(Producto producto, Sucursal sucursal, int cantidad) throws SQLException {
        stockProductoRepository.editarStockProductoEnSucursal(producto.getId(), sucursal.getId(), cantidad);
        if (stockProductoRepository.editarStockProductoEnSucursal(producto.getId(), sucursal.getId(), cantidad)) {
            sucursal.setStock(stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId()));
        }
    }

    public List<StockProducto> listarStockProducto(Sucursal sucursal) throws SQLException {
        return stockProductoRepository.listarStockProductosEnSucursal(sucursal.getId());
    }

    public StockProducto buscarStockProducto(Producto producto, Sucursal sucursal) {
        return stockProductoRepository.buscarEnSucursal(producto.getId(), sucursal.getId());
    }

    public int cantidadStockProducto(Producto producto, Sucursal sucursal) {
        StockProducto stockProducto = stockProductoRepository.buscarEnSucursal(producto.getId(), sucursal.getId());
        int aux = 0;
        if (stockProducto != null) {
            aux = stockProducto.getCantidad();
        }
        return aux;

    }

    public List<int[]> enlaces(List<Camino> caminos) {
        List<int[]> resultado = new ArrayList<>();
        for (Camino c : caminos) {
            int[] aux = {c.getOrigen().getId(), c.getDestino().getId()};
            resultado.add(aux);
        }
        return resultado;
    }
    /*public void pageRank(){
        CaminoServicios caminoServicios = new CaminoServicios();
        List<Camino> caminos = caminoServicios.listarCaminos();

        List<Sucursal> sucursales = sucursalRepository.listarSucursal();
        Map<Integer, Integer> indiceSucursal = new HashMap<>();

                // Crear un mapa para hacer coincidir las IDs de sucursales con índices de matriz

                int n = sucursales.size(); // Número de sucursales

                // Asignar un índice a cada ID de sucursal
                for (int i = 0; i < n; i++) {
                    indiceSucursal.put(sucursales.get(i).getId(), i);
                }

                // Crear una matriz de adyacencia
                int[][] matrizAdyacencia = new int[n][n];

                // Supongamos que tienes enlaces entre sucursales (puedes tener más enlaces)
                List<int[]> enlaces = this.enlaces(caminos);

                // Llenar la matriz de adyacencia según los enlaces
                for (int[] enlace : enlaces) {
                    int sucursalOrigen = enlace[0];
                    int sucursalDestino = enlace[1];

                    // Obtener los índices correspondientes en la matriz
                    int indice1 = indiceSucursal.get(sucursalOrigen);
                    int indice2 = indiceSucursal.get(sucursalDestino);
                    System.out.println("sucursales"+sucursalOrigen + sucursalDestino);
                    System.out.println("indices"+indice1 + indice2);
                    // Establecer la conexión en la matriz de adyacencia
                    matrizAdyacencia[indice1][indice2] = 1;

                }
                System.out.println(indiceSucursal);
                // Ahora tienes la matriz de adyacencia que representa el grafo
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(matrizAdyacencia[i][j] + " ");
                    }
                    System.out.println();
                }
            }*/
}





