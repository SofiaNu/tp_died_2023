package clases;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBadmin {

    public DBadmin(){
        super();
    }

    public void altaProducto(Producto producto) throws SQLException {
        Conexion conn = new Conexion();
        conn.abrir();
        try {
            PreparedStatement pstm = conn.conexion.prepareStatement("INSERT INTO PRODUCTO " +
                    "(NOMBRE,PRECIOUNITARIO,PESO) values (?,?,?)");
            pstm.setString(1, producto.getNombre());
            pstm.setDouble(2, producto.getPrecioUnitario());
            pstm.setFloat(3,producto.getPeso());
        }catch(SQLException e){

        };
    }
}
