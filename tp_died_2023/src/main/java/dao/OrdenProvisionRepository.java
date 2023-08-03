package dao;

import clases.Estado;
import clases.OrdenProvision;
import clases.Sucursal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OrdenProvisionRepository {
    private static OrdenProvisionRepository  _INSTANCE;

    private OrdenProvisionRepository(){}

    public static OrdenProvisionRepository getInstance(){
        if (_INSTANCE == null){
            _INSTANCE = new OrdenProvisionRepository();
        }
        return _INSTANCE;
    }
    public void altaOrdenProvision(OrdenProvision ordenProvision) throws Exception{
        Conexion conn = Conexion.getInstance();
        //PreparedStatement pstm =null;
        //ResultSet rs= null;
        PreparedStatement ordenInsertStatement = null;
        PreparedStatement stockProductoInsertStatement = null;

        //ordenProvision should not be empty?
        if(ordenProvision.getListaProductos() == null || ordenProvision.getListaProductos().size() == 0){
            throw new Exception("Lista de productos no debe estar vacia");
        }

        if(ordenProvision.getDestino() == null){
            throw new Exception("El destino de la orden debe estar definido");
        }

        // ordenProvisionRepository 'alta' validations :: or in its service

        try {
            conn.abrir();
            conn.conexion.setAutoCommit(false);
            String ordenProvisionInsertSql =
                            """
                            INSERT INTO tp_tablas."ORDEN_PROVISION"
                            ("FECHA","SUCURSAL_DESTINO","TIEMPO_LIMITE") 
                            values (?,?,?)
                            """;

            //PreparedStatement insertOrden = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"ORDEN_PROVISION\" " +
                    //"(\"FECHA\",\"SUCURSAL_DESTINO\",\"TIEMPO_LIMITE\") values (?,?,?)");
            ordenInsertStatement = conn.conexion.prepareStatement(ordenProvisionInsertSql);

            ordenInsertStatement.setDate(1, java.sql.Date.valueOf(ordenProvision.getFecha()));
            ordenInsertStatement.setInt(2, ordenProvision.getDestino().getId());
            ordenInsertStatement.setFloat(3, ordenProvision.getTiempoLimite());

            ordenInsertStatement.executeQuery();

            try(ResultSet generatedKeys = ordenInsertStatement.getGeneratedKeys()) {
                if(generatedKeys.next()){
                    ordenProvision.setId(generatedKeys.getInt(1));
                }else{
                    throw new SQLException("Not orderProvision created");
                }
            }

            String stockProductoInsertSql =
                    """
                    INSERT INTO tp_tablas."STOCK_PRODUCTO"
                    ("ID","SUCURSAL_DESTINO","TIEMPO_LIMITE") 
                    values (?,?,?)
                    """;
            stockProductoInsertStatement = conn.conexion.prepareStatement(ordenProvisionInsertSql);
            conn.conexion.commit();
            conn.cerrar();
            //System.out.println(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
//            if (rs != null) try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            if (stockProductoInsertStatement != null) try {
                stockProductoInsertStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ordenInsertStatement != null) try {
                ordenInsertStatement.close();
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
                sucursal = null;
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
    public Sucursal buscarSucursal(String n){
        Sucursal sucursal= new Sucursal();
        Conexion conn =Conexion.getInstance();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"NOMBRE\"=?");
            pstm.setString(1,n);
            rs= pstm.executeQuery();
            if(rs.next()){
                sucursal = getSucursal(rs);
                String aux= rs.getString("NOMBRE");
                System.out.println(aux);
            }
            else{
                sucursal = null;
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
    private Sucursal  getSucursal(ResultSet rs) throws SQLException {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(rs.getString("NOMBRE"));
        sucursal.setHoraApertura(rs.getTime("HORA_APERTURA").toLocalTime());
        sucursal.setHoraCierre(rs.getTime("HORA_CIERRE").toLocalTime());
        if(rs.getBoolean("ESTADO")){
            sucursal.setEstado(Estado.OPERATIVO);}
        else{
            sucursal.setEstado(Estado.NO_OPERATIVO);
        }
        sucursal.setCapacidad(rs.getFloat("CAPACIDAD"));
        sucursal.setId(rs.getInt("ID"));

        return sucursal;
    }
    public void modificarEstado(int id, Estado estado){
        String query;
        if(estado == Estado.OPERATIVO){
            query="UPDATE tp_tablas.\"SUCURSAL\" SET \"ESTADO\"=false "+
                    "WHERE \"ID\"="+id;
        }
        else{
            query="UPDATE tp_tablas.\"SUCURSAL\" SET \"ESTADO\"=true "+
                    "WHERE \"ID\"="+id;
        }
        ejecutarQuery(query);
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

    public void editarSucursal(Sucursal sucursal) {
    }
}