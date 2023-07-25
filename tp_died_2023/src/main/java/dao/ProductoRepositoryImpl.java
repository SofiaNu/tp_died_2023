package dao;

import clases.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryImpl {


    public void altaProducto(Producto producto) throws SQLException {
        Conexion conn = Conexion.getInstance();
        conn.abrir();
        try {
            PreparedStatement pstm = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"PRODUCTO\" " +
                    "(\"ID\",\"NOMBRE\",\"PRECIO_UNITARIO\",\"PESO\") values (?,?,?,?)");
            pstm.setInt(1,producto.getId());
            pstm.setString(2, producto.getNombre());
            pstm.setDouble(3, producto.getPrecioUnitario());
            pstm.setFloat(4, producto.getPeso());
            ResultSet rs = pstm.executeQuery();
            System.out.println(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.cerrar();
        ;
    }
    public void bajaProducto(int id) throws SQLException {
        Conexion conn =Conexion.getInstance();
        conn.abrir();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            pstm= conn.conexion.prepareStatement("DELETE FROM tp_tablas.\"PRODUCTO\" WHERE \"ID\"="+id);
            rs= pstm.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        conn.cerrar();
    }

    public List<Producto> buscarProducto() throws SQLException {
        List<Producto> productos= new ArrayList<Producto>();
        Conexion conn =Conexion.getInstance();
        conn.abrir();
        try{
            PreparedStatement pstm = conn.conexion.prepareStatement("SELECT \"NOMBRE\" FROM tp_tablas.\"PRODUCTO\"");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                String aux= rs.getString("NOMBRE");
                System.out.println(aux);
            }
        }catch(SQLException e){
            e.printStackTrace();
        };
        conn.cerrar();
        return null;
    }
}
