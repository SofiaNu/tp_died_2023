package dao;

import clases.*;
import servicios.ProductoServicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void altaStockProducto(int producto, int sucursal, int cantidad) throws SQLException {
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn.abrir();
            String insertQuery = "INSERT INTO tp_tablas.\"STOCK_PRODUCTO\" (\"PRODUCTO\",\"SUCURSAL\",\"CANTIDAD\") VALUES ("+producto+","+sucursal+","+cantidad+")";
            Statement statement = conn.conexion.createStatement();
            boolean rowsAffected = statement.execute(insertQuery);

            //System.out.println("Filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void editarStockProductoEnSucursal(int producto,int sucursal,int cantidad){
        String query= "UPDATE tp_tablas.\"STOCK_PRODUCTO\" SET \"PRODUCTO\"=? , \"SUCURSAL\"=? ," +
                " \"CANTIDAD\"=? "+ "WHERE \"PRODUCTO\"="+producto+" AND \"SUCURSAL\"="+sucursal;
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement(query);
            pstm.setInt(1,producto);
            pstm.setInt(2,sucursal);
            pstm.setInt(3,cantidad);
            pstm.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void bajaStockProductoEnSucursal(int producto, int sucursal){
        String query ="DELETE FROM tp_tablas.\"STOCK_PRODUCTO\" WHERE \"PRODUCTO\" ="+ producto+" AND \"SUCURSAL\" =" + sucursal;
        ejecutarQuery(query);
    }
    public List<StockProducto> listarStockProductosEnSucursal(int sucursal){
        List<StockProducto> stockProductos =new ArrayList<StockProducto>();
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm=null;
        ResultSet rs= null;
        ProductoRepository productoRepository = ProductoRepository.getInstance();
        try{
            conn.abrir();
            pstm=conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"STOCK_PRODUCTO\" WHERE \"SUCURSAL\" ="+ sucursal);
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
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
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

    private void ejecutarQuery(String query){
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm=null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement(query);
            pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private StockProducto busqueda(String query){
        Conexion conn = Conexion.getInstance();
        StockProducto stockProducto = new StockProducto();
        PreparedStatement pstm= null;
        ResultSet rs = null;
        ProductoRepository productoRepository = ProductoRepository.getInstance();
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement(query);
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
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
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
