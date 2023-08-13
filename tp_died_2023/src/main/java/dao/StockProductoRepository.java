package dao;

import clases.*;
import connectionpool.ConnectionPool;
import servicios.ProductoServicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockProductoRepository {

    private static StockProductoRepository _INSTANCE;

    private StockProductoRepository(){}

    public static StockProductoRepository getInstance(){
        if (_INSTANCE == null){
            _INSTANCE = new StockProductoRepository();
        }
        return _INSTANCE;
    }

    public boolean altaStockProducto(int producto, int sucursal, int cantidad) throws SQLException {
        String query = "INSERT INTO tp_tablas.\"STOCK_PRODUCTO\" (\"PRODUCTO\",\"SUCURSAL\",\"CANTIDAD\") VALUES ("+producto+","+sucursal+","+cantidad+")";
        return ejecutarQuery(query);

    }
    public boolean editarStockProductoEnSucursal(int producto,int sucursal,int cantidad){
        String query= "UPDATE tp_tablas.\"STOCK_PRODUCTO\" SET \"PRODUCTO\"= "+producto+ ", \"SUCURSAL\"= "+sucursal+ " ," +
                " \"CANTIDAD\"= "+cantidad+ "WHERE \"PRODUCTO\"= "+producto+" AND \"SUCURSAL\"= "+sucursal;
        return ejecutarQuery(query);
    }
    public boolean bajaStockProductoEnSucursal(int producto, int sucursal){
        String query ="DELETE FROM tp_tablas.\"STOCK_PRODUCTO\" WHERE \"PRODUCTO\" ="+ producto+" AND \"SUCURSAL\" =" + sucursal;
        return ejecutarQuery(query);
    }
    public List<StockProducto> listarStockProductosEnSucursal(int sucursal){
        List<StockProducto> stockProductos =new ArrayList<StockProducto>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        ResultSet rs= null;
        ProductoRepository productoRepository = ProductoRepository.getInstance();
        try{
            pstm=conn.prepareStatement("SELECT * FROM tp_tablas.\"STOCK_PRODUCTO\" WHERE \"SUCURSAL\" ="+ sucursal);
            rs= pstm.executeQuery();
            while(rs.next()){
                stockProductos.add(getStockProducto(rs,productoRepository));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
        return stockProductos;
    }

    public StockProducto buscarEnSucursal(int producto, int sucursal){
        String query= "SELECT * FROM tp_tablas.\"STOCK_PRODUCTO\" "+
                "WHERE \"PRODUCTO\"="+producto+
                "AND \"SUCURSAL\"="+sucursal;
        return busqueda(query);
    }

    private boolean ejecutarQuery(String query){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        boolean r=false;
        try{
            Statement statement = conn.createStatement();
            statement.execute(query);
            r=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
        return r;
    }

    private StockProducto busqueda(String query){
        Connection conn = ConnectionPool.getConnection();
        StockProducto stockProducto = new StockProducto();
        PreparedStatement pstm= null;
        ResultSet rs = null;
        ProductoRepository productoRepository = ProductoRepository.getInstance();
        try{
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            if(rs.next()) {
                stockProducto = getStockProducto(rs,productoRepository);
            }
            else{
                stockProducto = null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
        return stockProducto;
    };
    private StockProducto getStockProducto(ResultSet rs, ProductoRepository productoRepository) throws SQLException {
        StockProducto stockProducto = new StockProducto();
        stockProducto.setProducto(productoRepository.buscarProducto(rs.getInt("PRODUCTO")));
        stockProducto.setCantidad(rs.getInt("CANTIDAD"));
        System.out.println(stockProducto.getProducto().getNombre() + stockProducto.getCantidad() );

        return stockProducto;
    }


}
