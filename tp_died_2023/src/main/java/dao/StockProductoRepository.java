package dao;

import clases.Estado;
import clases.Producto;
import clases.Sucursal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            /*pstm = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"STOCK_PRODUCTO\" " +
                    "(\"PRODUCTO\",\"SUCURSAL\",\"CANTIDAD\") values (?,?,?)");

            pstm.setInt(1, producto);
            pstm.setInt(2, sucursal);
            pstm.setInt(3, cantidad);
            rs = pstm.executeUpdate(pstm);*/
            String insertQuery = "INSERT INTO tp_tablas.STOCK_PRODUCTO (PRODUCTO,SUCURSAL,CANTIDAD) VALUES (producto,sucursal,cantidad)";
            Statement statement = conn.conexion.createStatement();
            int rowsAffected = statement.executeUpdate(insertQuery);
            System.out.println("Filas afectadas: " + rowsAffected);
            //System.out.println(rs.getInt(1)+rs.getInt(2));
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




}
