package servicios;

import clases.Camino;
import clases.OrdenProvision;
import clases.Sucursal;
import connectionpool.ConnectionPool;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class FlujoMaximo extends gestionOrden{
    public FlujoMaximo() throws SQLException {super();}
    public float pesoMaximo(List<Camino> ruta){
        return (float)ruta.stream().mapToDouble(r-> (double)r.getCapacidadMaxima()).min().getAsDouble();
    }

    public float flujoMaximo(){
        Sucursal fuente = getFuente();
        Sucursal sumidero = getSumidero();
        List<List<Sucursal>> rSucursales = encontrarRuta(fuente,sumidero);
        List<List<Camino>> rutas = encontrarCaminos(rSucursales);
        float flow =0;
        for(List<Camino> r: rutas){
            float capacidad = pesoMaximo(r);
            if(capacidad !=0){
                flow+= capacidad;
                setMinimo(r,capacidad);
            }
        }
        return flow;
    }

    public float flujoMaximo(List<List<Camino>> rutasMinimas){
        Sucursal fuente = getFuente();
        Sucursal sumidero = getSumidero();
        List<List<Sucursal>> rSucursales = encontrarRuta(fuente,sumidero);
        List<List<Camino>> rutas = encontrarCaminos(rSucursales);
        float flow =0;
        for(List<Camino> r: rutas){
            float capacidad = pesoMaximo(r);
            if(capacidad !=0){
                flow+= capacidad;
                setMinimo(r,capacidad);
                rutasMinimas.add(r);
            }
        }
        return flow;
    }

    public void setMinimo(List<Camino> ruta, float capUtilizada){
        for(Camino c: ruta){
            c.setCapacidadMaxima(c.getCapacidadMaxima()-capUtilizada);
            //seteo su capacidad maxima como la capacidad residual para poder iterar
        }
    }

    public long gradoEntrada(Sucursal sucursal){
        return caminos.stream().filter(c->c.getDestino().equals(sucursal)).count();
    }

    public long gradoSalida(Sucursal sucursal){
        return caminos.stream().filter(c->c.getOrigen().equals(sucursal)).count();
    }
    private Sucursal getFuente(){
        return sucursales.stream().filter(s-> gradoEntrada(s)==0).findAny().get();
    }
    private Sucursal getSumidero(){
        return sucursales.stream().filter(s-> gradoSalida(s)==0).findFirst().get();
    }

   /* public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        ConnectionPool.setup();
        FlujoMaximo f = new FlujoMaximo();
        List<List<Camino>> caminos = new ArrayList<>();
        float cap = f.flujoMaximo(caminos);
        System.out.println(cap);
        System.out.println(caminos);


    } */

}
