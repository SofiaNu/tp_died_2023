package dao;

import clases.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    private static ProductoRepository _INSTANCE;

    private ProductoRepository(){}

    public static ProductoRepository getInstance(){
        if (_INSTANCE == null){
            _INSTANCE = new ProductoRepository();
        }
        return _INSTANCE;
    }


    public void altaProducto(Producto producto) throws SQLException {
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs=null;
        try {
            conn.abrir();
            pstm = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"PRODUCTO\" " +
                    "(\"NOMBRE\",\"PRECIO_UNITARIO\",\"PESO\",\"DETALLE\") values (?,?,?,?)");
            //pstm.setInt(1,producto.getId());
            pstm.setString(1, producto.getNombre());
            pstm.setDouble(2, producto.getPrecioUnitario());
            pstm.setFloat(3, producto.getPeso());
            pstm.setString(4, producto.getDetalle());
            rs = pstm.executeQuery();
            System.out.println(rs.getString(2));
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
    }
    public void bajaProducto(int p) throws SQLException { //MANEJAR POSIBLE ERROR DE NO ENCONTRAR LA FILA
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn.abrir();
            pstm = conn.conexion.prepareStatement("DELETE FROM tp_tablas.\"PRODUCTO\" WHERE \"ID\"=" + p);
            pstm.executeQuery();
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

    public List<Producto> listarProductos() throws SQLException {
        List<Producto> productos= new ArrayList<Producto>();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"PRODUCTO\"");
            rs= pstm.executeQuery();
            while(rs.next()){
                productos.add(getProducto(rs));
                String aux= rs.getString("NOMBRE");
                System.out.println(aux);
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
        return productos;
    }

    public Producto buscarProducto(String n){
        Producto producto= new Producto();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"PRODUCTO\" WHERE \"NOMBRE\"=?");
            pstm.setString(1,n);
            rs= pstm.executeQuery();
            if(rs.next()){
                producto = getProducto(rs);
                String aux= rs.getString("NOMBRE");
                System.out.println(aux);
            }
            else{
                System.out.print("No existe el producto");
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
        return producto;
    }

    public Producto buscarProducto(int n){
        Producto producto= new Producto();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"PRODUCTO\" WHERE \"ID\"="+n);
            rs= pstm.executeQuery();
            if(rs.next()){
                producto = getProducto(rs);
                String aux= rs.getString("NOMBRE");
                System.out.println(aux);
            }
            else{
                System.out.print("No existe el producto");
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
        return producto;
    }

    public void editarProducto(Producto producto){ //MANEJAR POSIBLE ERROR DE NO ENCONTRAR LA FILA A MODIF.
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs=null;
        try {
            conn.abrir();
            pstm = conn.conexion.prepareStatement("UPDATE tp_tablas.\"PRODUCTO\" " +
                    "SET \"NOMBRE\"=?,\"PRECIO_UNITARIO\"=?,\"PESO\"=?,\"DETALLE\"=?" +
                    "WHERE \"ID\"="+producto.getId());
            pstm.setString(1, producto.getNombre());
            pstm.setDouble(2, producto.getPrecioUnitario());
            pstm.setFloat(3, producto.getPeso());
            pstm.setString(4, producto.getDetalle());
            rs = pstm.executeQuery();
            System.out.println(rs.getString(2));
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
    }
    private Producto getProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
            producto.setNombre(rs.getString("NOMBRE"));
            producto.setPrecioUnitario(rs.getDouble("PRECIO_UNITARIO"));
            producto.setPeso(rs.getFloat("PESO"));
            producto.setDetalle(rs.getString("DETALLE"));
            producto.setId(rs.getInt("ID"));

        return producto;
    }
}
