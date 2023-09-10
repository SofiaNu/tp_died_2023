package dao;

import clases.Estado;
import clases.StockProducto;
import clases.Sucursal;
import connectionpool.ConnectionPool;

import java.sql.*;
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

        boolean estado;
        if(sucursal.getEstado()==Estado.OPERATIVO){
            estado =true;
        }
        else{
            estado=false;
        }
        //String query = "INSERT INTO tp_tablas.\"STOCK_PRODUCTO\" (\"PRODUCTO\",\"SUCURSAL\",\"CANTIDAD\") VALUES ("+producto+","+sucursal+","+cantidad+")";

        String query = "INSERT INTO tp_tablas.\"SUCURSAL\" (\"NOMBRE\",\"HORA_APERTURA\",\"HORA_CIERRE\",\"ESTADO\",\"CAPACIDAD\") "+
                "VALUES ('"+sucursal.getNombre()+"','"+Time.valueOf(sucursal.getHoraApertura())+"','"+Time.valueOf(sucursal.getHoraCierre())+"',"
                +estado+","+sucursal.getCapacidad()+")";
        ejecutarQuery(query);

    }

    public Sucursal buscarSucursal(int n){
        String query="SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"ID\"= "+n;
        return busqueda(query);
    }
    public Sucursal buscarSucursal(String n){
        String query="SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"NOMBRE\"='"+n+"'";
        return busqueda(query);

    }
    public List<Sucursal> buscarSucursal(boolean estado){

        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            pstm = conn.prepareStatement("SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\"=?");
            pstm.setBoolean(1, estado);
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
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
        return sucursales;
    }
    public boolean bajaSucursal(Sucursal sucursal) throws SQLException {
        String query ="DELETE FROM tp_tablas.\"SUCURSAL\" WHERE \"ID\"=" + sucursal.getId();
        return ejecutarQuery(query);

    }
    public List<Sucursal> listarOperativos(){
        String statement = "SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\"= true";
        return ejecutarListado(statement);
    }
    /*public List<Sucursal> listarNoOperativos(){
        String statement = "SELECT * FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\"= false";
        return ejecutarListado(statement);
    }*/
    public List<Sucursal> listarSucursal(){
        String statement = "SELECT * FROM tp_tablas.\"SUCURSAL\"";
        return ejecutarListado(statement);
    }

    public List<Sucursal> ejecutarListado(String stm){
        List<Sucursal> sucursales= new ArrayList<Sucursal>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm =null;
        ResultSet rs= null;
        try{
            pstm = conn.prepareStatement(stm);
            rs= pstm.executeQuery();
            while(rs.next()){
                sucursales.add(getSucursal(rs));
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
        sucursal.setStock(getStockSucursal(rs.getInt("ID")));
        return sucursal;
    }

    private List<StockProducto> getStockSucursal(int id){
        StockProductoRepository stockProductoRepository = StockProductoRepository.getInstance();
        return stockProductoRepository.listarStockProductosEnSucursal(id);
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

    public void editarSucursal(Sucursal sucursal) {
        boolean estado;
        if(sucursal.getEstado()==Estado.OPERATIVO){
            estado =true;
        }
        else{
            estado=false;
        }
        String query = "UPDATE tp_tablas.\"SUCURSAL\" SET \"NOMBRE\"= '"+sucursal.getNombre()+"',\"HORA_APERTURA\"= '"+Time.valueOf(sucursal.getHoraApertura())+
                "',\"HORA_CIERRE\"= '"+Time.valueOf(sucursal.getHoraCierre())+"',\"ESTADO\"= "+estado+ " WHERE \"ID\"= "+sucursal.getId();
        ejecutarQuery(query);

    }
    private Sucursal busqueda(String query){
        //Conexion conn = Conexion.getInstance();
        Connection conn = ConnectionPool.getConnection();
        Sucursal sucursal = new Sucursal();
        PreparedStatement pstm= null;
        ResultSet rs = null;

        try{
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            if(rs.next()) {
                sucursal = getSucursal(rs);
            }
            else{
                sucursal = null;
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
        return sucursal;
    };
    //public List<Sucursal> PageRankSucursales(){
      //  List<Sucursal> sucursales = this.listarSucursal();
    //}
}
