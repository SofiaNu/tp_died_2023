package dao;

import clases.Camino;
import clases.Sucursal;

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
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm=null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("INSERT INTO tp_tablas.\"CAMINO\"" +
                    "(\"ID\",\"SUCURSAL_ORIGEN\", \"SUCURSAL_DESTINO\", \"TIEMPO_TRANSITO\", " +
                    "\"CAPACIDAD_MAXIMA\", \"ESTADO)\" values (?,?,?,?,?,?)");
            pstm.setInt(1,camino.getId());
            pstm.setInt(2,camino.getOrigen().getId());
            pstm.setInt(3,camino.getDestino().getId());
            pstm.setFloat(4,camino.getTiempoTransito());
            pstm.setFloat(5,camino.getCapacidadMaxima());
            pstm.setBoolean(6,camino.isEstado());
            pstm.executeQuery();

        }catch (SQLException e){
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
    public void bajaCamino(int id){
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm=null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("UPDATE tp_tablas.\"CAMINO\" SET \"ESTADO\"=?" +
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
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Camino> listarCaminos(){
        List<Camino> caminos =new ArrayList<Camino>();
        Conexion conn = Conexion.getInstance();
        PreparedStatement pstm=null;
        ResultSet rs= null;
        try{
            conn.abrir();
            pstm=conn.conexion.prepareStatement("SELECT * FROM tp_tablas.\"CAMINO\"");
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
            if (conn != null) try {
                conn.cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return caminos;
    }

    public Camino buscarCamino(int id){
        Conexion conn = Conexion.getInstance();
        Camino camino = new Camino();
        PreparedStatement pstm= null;
        ResultSet rs = null;
        try{
            conn.abrir();
            pstm = conn.conexion.prepareStatement("SELECT * FROM tp_tables.\"CAMINO\" " +
                    "WHERE \"ID\"="+id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                camino = getCamino(rs);
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

        return camino;
    }
    private Camino getCamino(ResultSet rs) throws SQLException {
        Camino camino = new Camino();
        camino.setId(rs.getInt("ID"));
        //VA A TENER QUE HABER CONTROL DE QUE LAS SUCURSALES EXISTAN CREO!
        /*SucursalRepository sucursalRepository = SucursalRepository.getInstance();
        camino.setOrigen(sucursalRepository.buscarSucursal(rs.getInt("SUCURSAL_ORIGEN")));
        camino.setDestino(sucursalRepository.buscarSucursal(rs.getInt("SUCURSAL_DESTINO")));*/
        camino.setTiempoTransito(rs.getFloat("TIEMPO_TRANSITO"));
        camino.setCapacidadMaxima(rs.getFloat("CAPACIDAD_MAXIMA"));
        camino.setEstado(rs.getBoolean("ESTADO"));
        return camino;
    }
}
