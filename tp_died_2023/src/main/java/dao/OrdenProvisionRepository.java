package dao;

import clases.*;
import connectionpool.ConnectionPool;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrdenProvisionRepository {
    private static OrdenProvisionRepository  _INSTANCE;

    private OrdenProvisionRepository(){}

    public static OrdenProvisionRepository getInstance(){
        if (_INSTANCE == null){
            _INSTANCE = new OrdenProvisionRepository();
        }
        return _INSTANCE;
    }
    public void altaOrdenProvision(OrdenProvision ordenProvision){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ordenInsertStatement = null;
        PreparedStatement stockProductoInsertStatement = null;

        //ordenProvision should not be empty?
//        if(ordenProvision.getListaProductos() == null || ordenProvision.getListaProductos().size() == 0){
//            throw new Exception("Lista de productos no debe estar vacia");
//        }
//
        if(ordenProvision.getDestino() == null){
            Sucursal d = new Sucursal();
            d.setId(2);
            ordenProvision.setDestino(d);
//            throw new Exception("El destino de la orden debe estar definido");
        }

        try {
            
//          conn.setAutoCommit(false);
            String ordenProvisionInsertSqlStr =
                            """
                            INSERT INTO tp_tablas."ORDEN_PROVISION"\
                            ("FECHA","SUCURSAL_DESTINO","TIEMPO_LIMITE","ESTADO")\
                            values (?,?,?,?)
                            """;

            ordenInsertStatement = conn.prepareStatement(ordenProvisionInsertSqlStr, Statement.RETURN_GENERATED_KEYS);

            ordenInsertStatement.setDate(1, java.sql.Date.valueOf(ordenProvision.getFecha()));
            ordenInsertStatement.setInt(2, ordenProvision.getDestino().getId());
            ordenInsertStatement.setFloat(3, ordenProvision.getTiempoLimite());
            if(ordenProvision.getEstado()==EstadoOrden.PENDIENTE){
                ordenInsertStatement.setBoolean(4,true);
            }
            else{
                ordenInsertStatement.setBoolean(4,false);
            }

            try{
                ordenInsertStatement.execute();
            } catch(Exception e){
                e.printStackTrace();
            }

            try {
                ResultSet generatedKeys = ordenInsertStatement.getGeneratedKeys();

                if(generatedKeys.next()){
                    ordenProvision.setId(generatedKeys.getInt(1));
                    System.out.println(ordenProvision.getId());
                }else{
                    throw new SQLException("Not orderProvision created");
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            String productoProvistoInsertSqlStr =
                    """
                    INSERT INTO tp_tablas."PRODUCTO_PROVISTO"
                    ("ORDEN_PROVISION","PRODUCTO","CANTIDAD")
                    values (?,?,?)
                    """;
            stockProductoInsertStatement = conn.prepareStatement(productoProvistoInsertSqlStr);

            if(ordenProvision.getListaProductos() != null && ordenProvision.getListaProductos().size() > 0){
                for(ProductoProvisto pp : ordenProvision.getListaProductos()){
                    stockProductoInsertStatement.setInt(1, ordenProvision.getId());
                    stockProductoInsertStatement.setInt(2, pp.getProducto().getId());
                    stockProductoInsertStatement.setInt(3, pp.getCantidad());
                    stockProductoInsertStatement.addBatch();

                }
            }
            stockProductoInsertStatement.executeBatch();
//            ResultSet rsStockProd = stockProductoInsertStatement.getResultSet();
//            if(rsStockProd != null){
//                while(rsStockProd.next()){
//
//                }
//            }

            //conn.commit();
            ordenInsertStatement.close();
            if(stockProductoInsertStatement != null ) stockProductoInsertStatement.close();


            //System.out.println(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            } 
        }
    }

    public void bajaOrden(OrdenProvision ordenProvision){
        if(ordenProvision == null || ordenProvision.getId() <= 0) return;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            
            pstm = conn.prepareStatement("DELETE FROM tp_tablas.\"ORDEN_PROVISION\" WHERE \"ID\"= ?");
            pstm.setInt(1, ordenProvision.getId());
            pstm.execute();
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
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            } 
        }
    }

    public List<OrdenProvision> ordenesDeSucursal(Sucursal sucursal){
            List<OrdenProvision> ordenes = new ArrayList<OrdenProvision>();
            if(sucursal == null || sucursal.getId() <= 0){
                System.out.println("ordenesDeSucursal, sucnull or did null");
                return ordenes;
            }

            Connection conn = ConnectionPool.getConnection();
            PreparedStatement pstm =null;
            ResultSet rs= null;
            try{
                
                pstm = conn.prepareStatement("SELECT * FROM tp_tablas.\"ORDEN_PROVISION\" WHERE \"SUCURSAL_DESTINO\" = ?");
                pstm.setInt(1, sucursal.getId());
                rs= pstm.executeQuery();
                while(rs.next()){
                    OrdenProvision op = buildOPFromRs(rs);
                    op.setDestino(sucursal);
                    ordenes.add(op);
                    op.setListaProductos(productosProvistosDeOrden(op));
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
                if (conn != null)  {
                    ConnectionPool.releaseConnection(conn);
                }
            }
            return ordenes;
    }

    public List<ProductoProvisto> productosProvistosDeOrden(OrdenProvision op){
        List<ProductoProvisto> productosProvistos = new ArrayList<ProductoProvisto>();
        if(op == null || op.getId() <= 0){
            return productosProvistos;
        }
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm =null;
        PreparedStatement pstmProd = null;
        ResultSet rsProd = null;
        ResultSet rs= null;
        try{
            
            pstm = conn.prepareStatement("SELECT * FROM tp_tablas.\"PRODUCTO_PROVISTO\" WHERE \"ORDEN_PROVISION\" = ?");
            pstm.setInt(1, op.getId());
            rs= pstm.executeQuery();
            while(rs.next()){
                ProductoProvisto pp = buildPPFromRs(rs);
                pp.setOrdenProvision(op);
                productosProvistos.add(pp);
            }

            HashSet<Integer> idProductos = new HashSet<Integer>();
            for(ProductoProvisto pp : productosProvistos){
                Producto p = pp.getProducto();
                if(p != null){
                    idProductos.add(p.getId());
                }
            }
            List<Integer> idProductosList = idProductos.stream().toList();
            String sqlStm = "SELECT * FROM tp_tablas.\"PRODUCTO\" WHERE ";
            for(int i = 0; i<idProductosList.size(); i++){
                Integer prodId = idProductosList.get(i);
                sqlStm = sqlStm += " \"ID\" = " + prodId;
                if(i < (idProductosList.size() - 1)){
                    sqlStm += " OR ";
                }
            }

            pstmProd = conn.prepareStatement(sqlStm);
            rsProd = pstmProd.executeQuery();

            HashMap hashMapProductos = new HashMap<Integer, Producto>();
            while(rsProd.next()){
                Producto prod = getProducto(rsProd);
                hashMapProductos.put(prod.getId(), prod);
            }

            for(ProductoProvisto productoProvisto : productosProvistos){
                Integer idProd = Integer.valueOf(productoProvisto.getProducto().getId());
                Producto prod = (Producto)hashMapProductos.get(idProd);
                productoProvisto.setProducto(prod);
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
        return productosProvistos;
    }
    public OrdenProvision buscar(int id) {
        OrdenProvision ordenProvision = null;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            
            pstm = conn.prepareStatement("SELECT * FROM tp_tablas.\"ORDEN_PROVISION\" WHERE \"ID\"=?");
            pstm.setInt(1, id);
            rs= pstm.executeQuery();
            if(rs.next()){
                ordenProvision = buildOPFromRs(rs);
                ordenProvision.setListaProductos(productosProvistosDeOrden(ordenProvision));
                //String aux= rs.getString("NOMBRE");
                //System.out.println(aux);
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
        return ordenProvision;
    }

    private OrdenProvision buildOPFromRs(ResultSet rs){
        try{
            Sucursal destino = new Sucursal();
            OrdenProvision ordenProvision = new OrdenProvision();

            destino.setId(rs.getInt("SUCURSAL_DESTINO"));

            ordenProvision.setId(rs.getInt("ID"));
            ordenProvision.setFecha(rs.getDate("FECHA").toLocalDate());
            ordenProvision.setDestino(destino);
            ordenProvision.setTiempoLimite(rs.getFloat("TIEMPO_LIMITE"));
            EstadoOrden estadoOrden = rs.getBoolean("ESTADO") ? EstadoOrden.PENDIENTE : EstadoOrden.EN_PROCESO;
            ordenProvision.setEstado(estadoOrden);
            return ordenProvision;
        } catch (SQLException sqlException) {
            return null;
        }

    }

    public boolean asignarRecorridoOrdenProvision(List<Camino> recorridoAAsignar, OrdenProvision ordenProvision){
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement pstm =null;
        PreparedStatement updateOP = null;
        ResultSet rs=null;
        try {
            String sqlString = "INSERT INTO tp_tablas.\"ORDEN_PROVISION_CAMINO\" " +  "(\"CAMINO\",\"ORDEN_PROVISION\",\"PARADA_NUMERO\") " + "VALUES ";
            int iteratorIndex = 0;
            for(Camino c : recorridoAAsignar){
                int paradaNumero = iteratorIndex + 1;
                String rowToInsert = "(" + String.valueOf(c.getId()) + ", " + String.valueOf(ordenProvision.getId()) + ", " + String.valueOf(paradaNumero)  + ")";
                if(iteratorIndex < recorridoAAsignar.size()-1){
                    rowToInsert = rowToInsert + ",";
                }
                sqlString += rowToInsert;
                iteratorIndex++;
            }
            sqlString +=  ";";
            pstm = conn.prepareStatement(sqlString);
            pstm.execute();
            updateOP = conn.prepareStatement("UPDATE tp_tablas.\"ORDEN_PROVISION\"SET \"ESTADO\" = FALSE WHERE \"ID\" = " +String.valueOf(ordenProvision.getId()) + "; ");
            updateOP.execute();
            //.out.println(rs.getString(2));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    }

    private ProductoProvisto buildPPFromRs(ResultSet rs){
        try{
            OrdenProvision ordenProvision = new OrdenProvision();
            Producto producto = new Producto();
            ProductoProvisto productoProvisto = new ProductoProvisto();

            ordenProvision.setId(rs.getInt("ORDEN_PROVISION"));
            producto.setId(rs.getInt("PRODUCTO"));
            productoProvisto.setCantidad(rs.getInt("CANTIDAD"));

            productoProvisto.setProducto(producto);
            productoProvisto.setOrdenProvision(ordenProvision);
            return productoProvisto;
        } catch (SQLException sqlException) {
            return null;
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
