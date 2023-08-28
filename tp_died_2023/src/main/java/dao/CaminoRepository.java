package dao;

import clases.Camino;
import clases.Estado;
import connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaminoRepository {
    private static CaminoRepository _INSTANCE;
    private CaminoRepository(){};


    public static CaminoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE= new CaminoRepository();
        }
        return _INSTANCE;
    }

    public void altaCamino(Camino camino){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        try{

            boolean estado;
            if(camino.getEstado()==Estado.OPERATIVO){
                estado=true;
            }
            else {
                estado =false;
            }

            pstm = conn.prepareStatement("INSERT INTO tp_tablas.\"CAMINO\"" +
                    "(\"SUCURSAL_ORIGEN\", \"SUCURSAL_DESTINO\", \"TIEMPO_TRANSITO\", " +
                    "\"CAPACIDAD_MAXIMA\", \"ESTADO\") values (?,?,?,?,?)");
            pstm.setInt(1,camino.getOrigen().getId());
            pstm.setInt(2,camino.getDestino().getId());
            pstm.setFloat(3,camino.getTiempoTransito());
            pstm.setFloat(4,camino.getCapacidadMaxima());
            pstm.setBoolean(5,estado);
            pstm.executeQuery();

        }catch (SQLException e){
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

    }
    public void setNoOperativoCamino(int id){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        try{
            pstm = conn.prepareStatement("UPDATE tp_tablas.\"CAMINO\" SET \"ESTADO\"=?" +
                    "WHERE \"ID\"="+id );
            pstm.setBoolean(1,false);
            pstm.executeQuery();
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
    }

    public void modificarEstado(int id, Estado estado){
        String query;
        if(estado == Estado.OPERATIVO){
            query="UPDATE tp_tablas.\"CAMINO\" SET \"ESTADO\"=false "+
                    "WHERE \"ID\"="+id;
        }
        else{
            query="UPDATE tp_tablas.\"CAMINO\" SET \"ESTADO\"=true "+
                    "WHERE \"ID\"="+id;
        }
        ejecutarQuery(query);
    }
    public void modificarEstados(List<Camino> caminos, Estado estado){
        for (int i = 0; i<caminos.size(); i++) {
            modificarEstado(caminos.get(i).getId(),estado);
        }
    }

    public List<Camino> listarOperativos(){
        String stm = "SELECT * FROM tp_tablas.\"CAMINO\" WHERE \"ESTADO\"=true";
        return listarCaminosQuery(stm);
    }

    public List<Camino> listarCaminos(){
        String stm = "SELECT * FROM tp_tablas.\"CAMINO\"";
        return listarCaminosQuery(stm);
    }
    public List<Camino> listarCaminosPorSucursal(int id){
        String stm = "SELECT * FROM tp_tablas.\"CAMINO\" WHERE \"SUCURSAL_ORIGEN\"="+id+" OR \"SUCURSAL_DESTINO\"=\""+id;
        return listarCaminosQuery(stm);
    }
    public List<Camino> AModificar(int id,boolean estado){
        String stm = "SELECT * " +
                "FROM tp_tablas.\"CAMINO\" c " +
                "WHERE " +
                "    (c.\"SUCURSAL_ORIGEN\" IN (SELECT \"ID\" FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\" = "+estado+" AND \"ID\" = "+id+") " +
                " AND c.\"SUCURSAL_DESTINO\" IN (SELECT \"ID\" FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\" = true)) " +
                "    OR " +
                "(c.\"SUCURSAL_ORIGEN\" IN (SELECT \"ID\" FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\" = true) " +
                " AND c.\"SUCURSAL_DESTINO\" IN (SELECT \"ID\" FROM tp_tablas.\"SUCURSAL\" WHERE \"ESTADO\" = "+estado+" AND \"ID\" = "+id+" ))";
        return listarCaminosQuery(stm);
    }
    public List<Camino> listarCaminosQuery(String stm){
        List<Camino> caminos =new ArrayList<Camino>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        ResultSet rs= null;
        try{
            pstm=conn.prepareStatement(stm);
            rs= pstm.executeQuery();
            while(rs.next()){
                caminos.add(getCamino(rs));
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
            if (conn != null)  {
                ConnectionPool.releaseConnection(conn);
            }
        }
        return caminos;
    }

    public Camino buscarCamino(int origen, int destino){
        String query= "SELECT * FROM tp_tablas.\"CAMINO\" "+
                "WHERE \"SUCURSAL_ORIGEN\"="+origen+
                "AND \"SUCURSAL_DESTINO\"="+destino;
        return busqueda(query);
    }
    public Camino buscarCamino(int id){
        String query = "SELECT * FROM tp_tablas.\"CAMINO\" " +
                "WHERE \"ID\"="+id;
        return busqueda(query);
    }

    public void editarCamino(Camino camino){
        String query= "UPDATE tp_tablas.\"CAMINO\" SET \"SUCURSAL_ORIGEN\"=? , \"SUCURSAL_DESTINO\"=? ," +
                " \"TIEMPO_TRANSITO\"=? , \"CAPACIDAD_MAXIMA\"=? , \"ESTADO\"=? "+
                "WHERE \"ID\"="+camino.getId();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm= null;
        try{
            boolean estado;
            if(camino.getEstado()==Estado.OPERATIVO){
                estado=true;
            }
            else {
                estado =false;
            }
            pstm = conn.prepareStatement(query);
            pstm.setInt(1,camino.getOrigen().getId());
            pstm.setInt(2,camino.getDestino().getId());
            pstm.setFloat(3,camino.getTiempoTransito());
            pstm.setFloat(4,camino.getCapacidadMaxima());
            pstm.setBoolean(5,estado);

            pstm.executeQuery();
        }catch(SQLException e){
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
    }
    public void bajaCamino(int id){
        String query ="DELETE FROM tp_tablas.\"CAMINO\" WHERE \"ID\" ="+ id;
        ejecutarQuery(query);
    }

    private void ejecutarQuery(String query){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm=null;
        try{
            pstm = conn.prepareStatement(query);
            pstm.executeQuery();
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
    }

    private Camino busqueda(String query){
        Connection conn = ConnectionPool.getConnection();
        Camino camino = new Camino();
        PreparedStatement pstm= null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            if(rs.next()) {
                camino = getCamino(rs);
            }
            else{
                camino = null;
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
        return camino;
    };
    private Camino getCamino(ResultSet rs) throws SQLException {
        Camino camino = new Camino();
        camino.setId(rs.getInt("ID"));
        SucursalRepository sucursalRepository = SucursalRepository.getInstance();
        camino.setOrigen(sucursalRepository.buscarSucursal(rs.getInt("SUCURSAL_ORIGEN")));
        camino.setDestino(sucursalRepository.buscarSucursal(rs.getInt("SUCURSAL_DESTINO")));
        camino.setTiempoTransito(rs.getFloat("TIEMPO_TRANSITO"));
        camino.setCapacidadMaxima(rs.getFloat("CAPACIDAD_MAXIMA"));
        if(rs.getBoolean("ESTADO")){
            camino.setEstado(Estado.OPERATIVO);
        }
        else{
            camino.setEstado(Estado.NO_OPERATIVO);
        }
        return camino;
    }
/*
    public void modificarEstadosCaminos(int id, Estado estado) {
        List<Camino> caminos= this.listarCaminosPorSucursal(id);
        if(estado != Estado.OPERATIVO ){
            for (Camino camino : caminos) {
                if (camino.getEstado() == Estado.OPERATIVO) {
                    this.setNoOperativoCamino(camino.getId());
                }
            }
        }else{
            SucursalRepository sucursalRepository = SucursalRepository.getInstance();
            for (Camino camino : caminos) {
                if (camino.getEstado() == Estado.NO_OPERATIVO) {

                    this.setNoOperativoCamino(camino.getId());
                }
            }
        }
    }*/
}
