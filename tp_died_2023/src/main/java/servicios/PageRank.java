package servicios;

import clases.Camino;
import clases.Sucursal;
import dao.CaminoRepository;
import dao.SucursalRepository;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;


public class PageRank {
    private class NodoSucursal{
        int id;
        double cantSalientes;
        double pageRank;
        List<NodoSucursal> entrantes; //esto deberia cargarse con algo de lo de sofi.

        public int getId(){
            return this.id;
        }
        public void setId(int id){
            this.id=id;
        }
        public double getSalientes(){
            return this.cantSalientes;
        }
        public void setSalientes(double cantCaminos){
            this.cantSalientes=cantCaminos;
        }
        public double getPageRank(){
            return this.pageRank;
        }
        public void setPageRank(double pageRank){
            this.pageRank=pageRank;
        }
        public List<NodoSucursal> getEntrantes(){
            return this.entrantes;
        }
        public void setEntrantes(List<NodoSucursal> entrantes){
            this.entrantes=entrantes;
        }

        public NodoSucursal() {
            this.pageRank=1.0;
        }
    }
    private List<NodoSucursal> sucursales;
    private static final double DAMPING_FACTOR = 0.85; // Factor de amortiguación típico de PageRank
    private static final double EPSILON = 0.0001; // Criterio de convergencia


    public int gradoSalida(Sucursal sucursal,List<Camino> caminos){
        int retorno = 0;
        for (Camino camino: caminos) {
            if(camino.getOrigen().getId() == sucursal.getId()){
                retorno++;
            }
        }
        return retorno;
    }
    public List<NodoSucursal> entrantes(NodoSucursal sucursal,List<Camino> caminos){
        List<NodoSucursal> resultado = new ArrayList<>();
        List<Sucursal> aux = new ArrayList<>();
        for(Camino c: caminos){
            if(c.getDestino().getId() == sucursal.getId()){//dup
                aux.add(c.getOrigen());
            }
        }
        //volver al checkeo con stream
        for(int i=0;i<aux.size();i++){
            for(NodoSucursal sucursal1: sucursales){
                if(sucursal1.getId() == aux.get(i).getId()){
                    resultado.add(sucursal1);
                }
            }

        }
        return resultado;
    }
    private  NodoSucursal obtenerNodoSucursal(Sucursal sucursal){

      return sucursales.stream().filter(s->s.getId() == sucursal.getId()).findFirst().get();
    }

    public PageRank(){
        SucursalRepository sucursalRepository = SucursalRepository.getInstance();
        CaminoRepository caminoRepository = CaminoRepository.getInstance();
        List<Camino> caminos = caminoRepository.listarCaminos();
        List<Sucursal> sucursalesReales = sucursalRepository.listarSucursal();
        sucursales = new ArrayList<NodoSucursal>();
        for(int i =0; i<sucursalesReales.size(); i++){
           NodoSucursal sucursal = new NodoSucursal();
           sucursal.setId(sucursalesReales.get(i).getId());
           sucursal.setSalientes(this.gradoSalida(sucursalesReales.get(i),caminos));
           System.out.println("" +sucursal.getId()+ " "+ sucursal.getSalientes());
           sucursales.add(sucursal);
        }
        for(int i=0; i < sucursales.size();i++){
            sucursales.get(i).setEntrantes(this.entrantes(sucursales.get(i),caminos));
            System.out.println(""+ sucursales.get(i).getId()+ " " + sucursales.get(i).getEntrantes());
        }

    }
    public void calcularPR(int maxIteracion){

        double cantSucursales = (double) sucursales.size();

        for (int i = 0; i < maxIteracion; i++) {
            double[] newPageRanks = new double[(int) cantSucursales];

            for (int j = 0; j < cantSucursales; j++) {
                NodoSucursal nodoSucursal = sucursales.get(j);
                double newPageRank = (1.0 - DAMPING_FACTOR) + 1.0/ cantSucursales;

                for (NodoSucursal nodoEntrante : nodoSucursal.getEntrantes()) {

                        newPageRank += DAMPING_FACTOR * nodoEntrante.getPageRank() / nodoEntrante.getSalientes();

                }

                newPageRanks[j] = newPageRank;
            }

            // Actualizar los valores de PageRank después de una iteración
            for (int j = 0; j < cantSucursales; j++) {
                sucursales.get(j).setPageRank(newPageRanks[j]);
            }

            // Verificar la convergencia
            if (checkConvergencia(newPageRanks)) {
                break;
            }


        }


    }
    private boolean checkConvergencia(double[] newPageRanks) {
        for (int i = 0; i < newPageRanks.length; i++) {
            double diff = Math.abs(newPageRanks[i] - sucursales.get(i).getPageRank());
            if (diff > EPSILON) {
                return false;
            }
        }
        return true;
    }
    public void printPR() {
        for (NodoSucursal node : sucursales) {
            System.out.println("Node " + node.getId() + ": PageRank = " + node.getPageRank());
        }
    }


    public static void main(String[] args) {

        PageRank pageRank = new PageRank();
        pageRank.calcularPR(100);
        pageRank.printPR();
    }

}







