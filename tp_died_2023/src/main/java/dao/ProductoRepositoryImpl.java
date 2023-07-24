package dao;

import clases.Producto;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoRepositoryImpl {


    public void altaProducto(Producto producto) throws SQLException {
        Conexion conn = Conexion.getInstance();
        conn.abrir();
        try {
            PreparedStatement pstm = conn.conexion.prepareStatement("INSERT INTO PRODUCTO " +
                    "(NOMBRE,PRECIOUNITARIO,PESO) values (?,?,?)");
            pstm.setString(1, producto.getNombre());
            pstm.setDouble(2, producto.getPrecioUnitario());
            pstm.setFloat(3, producto.getPeso());
        } catch (SQLException e) {

        }
        ;
    }
}
