package dao;

import clases.Producto;
import clases.Sucursal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SucursalRepository {
    private static SucursalRepository  _INSTANCE;

    private SucursalRepository(){}

    public static SucursalRepository getInstance(){
        if (_INSTANCE == null){
            _INSTANCE = new SucursalRepository();
        }
        return _INSTANCE;
    }
    public void altaSucursal(Sucursal sucursal){
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs=null;
        try {
            conn.abrir();
            pstm = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"SUCURSAL\" " +
                    "(\"NOMBRE\",\"HORA_APERTURA\",\"HORA_CIERRE\",\"ESTADO\",\"CAPACIDAD\") values (?,?,?,?,?)");
            //pstm.setInt(1,producto.getId());
            pstm.setString(1, sucursal.getNombre());
            pstm.setTime(2, Time.valueOf(sucursal.getHoraApertura()));
            pstm.setTime(3, Time.valueOf(sucursal.getHoraCierre()));
            pstm.setBoolean(4, sucursal.isEstado());
            pstm.setFloat(5, sucursal.getCapacidad());
            pstm.executeQuery();
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

    public Sucursal buscarSucursal(int n){
        Sucursal sucursal= new Sucursal();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"ID\"="+n);
            rs= pstm.executeQuery();
            if(rs.next()){
                sucursal = getSucursal(rs);
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
        return sucursal;
    }
    private Sucursal getSucursal(ResultSet rs) throws SQLException {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(rs.getString("NOMBRE"));
        sucursal.setHoraApertura(rs.getTime("HORA_APERTURA").toLocalTime());
        sucursal.setHoraCierre(rs.getTime("HORA_CIERRE").toLocalTime());
        sucursal.setEstado(rs.getBoolean("ESTADO"));
        sucursal.setCapacidad(rs.getFloat("CAPACIDAD"));
        sucursal.setId(rs.getInt("ID"));

        return sucursal;
    }


    public void bajaSucursal(Sucursal sucursal) throws SQLException { //MANEJAR POSIBLE ERROR DE NO ENCONTRAR LA FILA
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn.abrir();
            pstm = conn.conexion.prepareStatement("DELETE FROM tp_tablas.\"SUCURSAL\" WHERE \"ID\"=" + sucursal.getId());
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
    public List<Sucursal> listarSucursal() throws SQLException {
        List<Sucursal> sucursales= new ArrayList<Sucursal>();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"SUCURSAL\"");
            rs= pstm.executeQuery();
            while(rs.next()){
                sucursales.add(getSucursal(rs));
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
        return sucursales;
    }
    public void editarSucursal(Sucursal sucursal) {
    }
}
