package servicios;

import clases.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class gestionOrden {
    SucursalServicios sucursalServicios = new SucursalServicios();
    OrdenProvisionServicios ordenProvisionServicios = new OrdenProvisionServicios();
    CaminoServicios caminoServicios = new CaminoServicios();
    private List<Camino> caminos = caminoServicios.listarCaminos();
    private List<Sucursal> sucursales = sucursalServicios.listarSucursales();

    public gestionOrden() throws SQLException {};


    public List<Sucursal> sucursalesValidas(OrdenProvision orden) throws SQLException {
        sucursales = sucursales.stream()
                .filter(s-> s.getEstado()==Estado.OPERATIVO &&
                        tieneStock(s.getStock(), orden.getListaProductos()) &&
                        noEsSumidero(s))
                .collect(Collectors.toList());
        return sucursales;
    }
//ESTO PROBABLEMENTE TAMBIEN A SUCURSAL
    public boolean tieneStock(List<StockProducto> stock, List<ProductoProvisto> orden){
        boolean r=true;
        int i=0;
        while(i< orden.size() && r==true) {
            ProductoProvisto o = orden.get(i);
            if(stock.stream().anyMatch(s->s.getProducto().getId()==o.getProducto().getId()
                    && s.getCantidad()>=o.getCantidad())) {
                i++;
            }
            else {
                r=false;
            }

        }
        return r;
    }
    //ESTO SE DEBERIA HACER EN LA CLASE SUCURSAL
    public boolean noEsSumidero(Sucursal s){
        return caminos.stream().anyMatch(c-> c.getOrigen() == s);
    }

    public List<Sucursal> dfs(Sucursal origen) throws SQLException {
        List<Sucursal> resultado = new ArrayList<>();
        Stack<Sucursal> pendientes = new Stack<>();
        Set<Sucursal> marcados = new HashSet<>();
        marcados.add(origen);
        pendientes.push(origen);

        while(!pendientes.isEmpty()){
            Sucursal actual = pendientes.pop();
            List<Sucursal> vecinos = vecinos(actual);
            resultado.add(actual);
            for(Sucursal v : vecinos){
                if(!marcados.contains(v)){
                    pendientes.push(v);
                    marcados.add(v);
                }
            }
        }
        return resultado;
    }

    public List<Sucursal> vecinos(Sucursal sucursal){
        List<Sucursal> resultado = new ArrayList<>();
        for(Camino c: caminos){
            if(c.getOrigen().equals(sucursal)){
                resultado.add(c.getDestino());
            }
        }
        return resultado;
    }

    public int gradoEntrada(Sucursal sucursal){
        return (int)caminos.stream().filter(c->c.getDestino()==sucursal).count();
    }

    public int gradoSalida(Sucursal sucursal){
        return (int)caminos.stream().filter(c->c.getOrigen()==sucursal).count();
    }

    public List<List<Sucursal>> encontrarRuta(Sucursal origen,Sucursal destino){
        List<List<Sucursal>> resultado = new ArrayList<>();
        List<Sucursal> marcados = new ArrayList<>();
        marcados.add(origen);
        encontrarRutaAux(origen,destino,marcados,resultado);

        return resultado;
    }

    public void encontrarRutaAux(Sucursal origen, Sucursal destino, List<Sucursal> marcados, List<List<Sucursal>> resultado){
        List<Sucursal> adyacentes = vecinos(origen);
        List<Sucursal>  copiaMarcados = null;
        for(Sucursal ady: adyacentes){
            copiaMarcados = marcados.stream().collect(Collectors.toList());
            if(ady.equals(destino)) {
                copiaMarcados.add(destino);
                resultado.add(new ArrayList<>(copiaMarcados));
            } else {
                if( !copiaMarcados.contains(ady)) {
                    copiaMarcados.add(ady);
                    this.encontrarRutaAux(ady,destino,copiaMarcados,resultado);
                }
            }
        }
    }

    public double tiempoEnRuta (List<Camino> ruta){
        return ruta.stream().mapToDouble(r-> (double)r.getTiempoTransito()).sum();
    }

    public void prueba(){
        Sucursal origen = sucursales.get(0);
        Sucursal destino = sucursales.get(6);
        System.out.println("CAMINOS DESDE: "+origen.toString()+" HASTA: "+destino.toString());
        System.out.println(encontrarRuta(origen,destino));
    }
}
