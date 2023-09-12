package servicios;

import clases.*;
import connectionpool.ConnectionPool;
import gui.Inicio;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class gestionOrden {
     List<Camino> caminos ;
     List<Sucursal> sucursales ;

    public gestionOrden() throws SQLException {
        SucursalServicios sucursalServicios = new SucursalServicios();
        //OrdenProvisionServicios ordenProvisionServicios = new OrdenProvisionServicios();
        CaminoServicios caminoServicios = new CaminoServicios();
        caminos = caminoServicios.listarCaminosOperativos();
        sucursales = sucursalServicios.listarSucursalesOperativas();

    };

    public List<Sucursal> sucursalesValidas(OrdenProvision orden) throws SQLException {
        sucursales = sucursales.stream()
                .filter(s-> !s.equals(orden.getDestino()) && tieneStock(s.getStock(), orden.getListaProductos()) &&
                        noEsSumidero(s))
                .collect(Collectors.toList());
        sucursales = sucursales.stream().filter(s-> !(encontrarRuta(s, orden.getDestino()).isEmpty())).collect(Collectors.toList());
        return sucursales;
    }

    public void generarResultadosValidos(OrdenProvision orden, List<List<Sucursal>> rutasSucursal, List<List<Camino>> rutasCamino) throws SQLException {
        List<Sucursal> origenesValidos = sucursalesValidas(orden);
        for(Sucursal s: origenesValidos){
            rutasSucursal.addAll(encontrarRuta(s,orden.getDestino()));
        }
        rutasCamino.addAll(encontrarCaminos(rutasSucursal));
        filtrarRutasPorTiempo(rutasSucursal,rutasCamino,orden.getTiempoLimite());
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
        return caminos.stream().anyMatch(c-> c.getOrigen().equals(s));
    }

    public List<Sucursal> bfs(Sucursal inicio){
        List<Sucursal> resultado = new ArrayList<>();
        Queue<Sucursal> pendientes = new LinkedList<>();
        HashSet<Sucursal> marcados = new HashSet<>();
        marcados.add(inicio);
        pendientes.add(inicio);

        while(!pendientes.isEmpty()){
            Sucursal actual = pendientes.poll();
            List<Sucursal> adyacentes = this.vecinos(actual);
            resultado.add(actual);
            for(Sucursal s : adyacentes){
                if(!marcados.contains(s)){
                    pendientes.add(s);
                    marcados.add(s);
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

    public void filtrarRutasPorTiempo(List<List<Sucursal>> rSucursales, List<List<Camino>> rutas,double tiempo){
        for(List<Camino> r: rutas){
            if(tiempoEnRuta(r)> tiempo){
                rSucursales.remove(rutas.indexOf(r));
                rutas.remove(r);
            }
        }
    }

    public double tiempoEnRuta (List<Camino> ruta){
        return ruta.stream().mapToDouble(r-> (double)r.getTiempoTransito()).sum();
    }

    public List<List<Camino>> encontrarCaminos(List<List<Sucursal>> rutas){
       List<List<Camino>> resultado = new ArrayList<>();
       for(List<Sucursal> rutaSucursales: rutas){
           List<Camino> path = new ArrayList<>();
           for(int i = 0; i <= rutaSucursales.size() - 2; i++){
                path.add(getCamino(rutaSucursales.get(i),rutaSucursales.get(i+1)));
          }
        resultado.add(path);
       }
        return resultado;
    }

    public Camino getCamino(Sucursal o, Sucursal d){
        for(Camino c: caminos){
            if (c.getOrigen().equals(o) && c.getDestino().equals(d)){
                return c;
            }
        }
        return null; //????¡¡¡¡
    }
    public void prueba(){
        Sucursal origen = sucursales.get(0);
        Sucursal destino = sucursales.get(5);
        List<List<Sucursal>> resultado = encontrarRuta(origen,destino);
        System.out.println("CAMINOS DESDE: "+origen.toString()+" HASTA: "+destino.toString());
        System.out.println(resultado);
        System.out.println(encontrarCaminos(resultado));

    }
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        ConnectionPool.setup();
        OrdenProvisionServicios ordenProvisionServicios = new OrdenProvisionServicios();
        OrdenProvision orden = ordenProvisionServicios.buscarOrden(3);
        gestionOrden gestionOrden = new gestionOrden();

        //Sucursal sucursal = sucursalServicios.buscarSucursal(1);
        //Sucursal destino = sucursalServicios.buscarSucursal(4);
        List<List<Sucursal>> rsucursal = new ArrayList<>();
        List<List<Camino>> rutas = new ArrayList<>();
        gestionOrden.generarResultadosValidos(orden,rsucursal,rutas);

        System.out.println(rutas);


    }
}
