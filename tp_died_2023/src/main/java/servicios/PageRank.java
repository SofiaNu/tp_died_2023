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
        int cantSalientes;
        double pageRank;
        List<NodoSucursal> entrantes; //esto deberia cargarse con algo de lo de sofi.

        public int getId(){
            return this.id;
        }
        public void setId(int id){
            this.id=id;
        }
        public int getSalientes(){
            return this.cantSalientes;
        }
        public void setSalientes(int cantCaminos){
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

        public NodoSucursal() {}
    }
    private List<NodoSucursal> sucursales;
    private static final double DAMPING_FACTOR = 0.85; // Factor de amortiguación típico de PageRank
    private static final double EPSILON = 0.0001; // Criterio de convergencia


    public int gradoSalida(Sucursal sucursal,List<Camino> caminos){
        return (int)caminos.stream().filter(c->c.getOrigen()==sucursal).count();
    }
    public List<NodoSucursal> entrantes(NodoSucursal sucursal,List<Camino> caminos){
        List<NodoSucursal> resultado = new ArrayList<>();
        List<Sucursal> aux = new ArrayList<>();
        for(Camino c: caminos){
            if(c.getDestino().getId() == sucursal.getId()){//dup
                aux.add(c.getOrigen());
            }
        }
        for(int i=0;i<aux.size();i++){
            resultado.add(obtenerNodoSucursal(aux.get(i)));
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
           sucursales.add(sucursal);
        }
        for(int i=0; i < sucursales.size();i++){
            sucursales.get(i).setPageRank(1);
            sucursales.get(i).setEntrantes(this.entrantes(sucursales.get(i),caminos));
        }

    }
    public void calcularPR(int maxIteracion){

        int cantSucursales = sucursales.size();

        for (int i = 0; i < maxIteracion; i++) {
            double[] newPageRanks = new double[cantSucursales];

            for (int j = 0; j < cantSucursales; j++) {
                NodoSucursal nodoSucursal = sucursales.get(j);
                double newPageRank = (1.0 - DAMPING_FACTOR) / cantSucursales;//esto no se si realmente es util, principalmente el problema es que divida por sucursales

                for (NodoSucursal nodoEntrante : nodoSucursal.getEntrantes()) {
                    newPageRank += DAMPING_FACTOR * nodoEntrante.getPageRank() / nodoEntrante.getSalientes();
                }

                newPageRanks[i] = newPageRank;
            }

            // Actualizar los valores de PageRank después de una iteración
            for (int j = 0; j < cantSucursales; j++) {
                sucursales.get(i).setPageRank(newPageRanks[j]);
            }

            // Verificar la convergencia
            if (checkConvergencia(newPageRanks)) {
                break;
            }


        }


    }
    public void printPageRank(){
        for (NodoSucursal node : sucursales ){
            System.out.println("Node " + node.getId() + ": PageRank = " + node.getPageRank());
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
        // Crear nodos y establecer enlaces entre ellos (similar al ejemplo anterior)

        PageRank pageRank = new PageRank();
        pageRank.calcularPR(1); // Número máximo de iteraciones
        pageRank.printPR();
    }

}

    /*import java.util.ArrayList;
import java.util.List;

public class PageRank {
    private static final double DAMPING_FACTOR = 0.85; // Factor de amortiguación típico de PageRank
    private static final double EPSILON = 0.0001; // Criterio de convergencia

    private List<Node> nodes;

    public PageRank(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void calculatePageRank(int maxIterations) {
        int numNodes = nodes.size();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            double[] newPageRanks = new double[numNodes];

            for (int i = 0; i < numNodes; i++) {
                Node node = nodes.get(i);
                double newPageRank = (1.0 - DAMPING_FACTOR) / numNodes;

                for (Node linkingNode : node.getLinks()) {
                    newPageRank += DAMPING_FACTOR * linkingNode.getPageRank() / linkingNode.getLinks().size();
                }

                newPageRanks[i] = newPageRank;
            }

            // Actualizar los valores de PageRank después de una iteración
            for (int i = 0; i < numNodes; i++) {
                nodes.get(i).setPageRank(newPageRanks[i]);
            }

            // Verificar la convergencia
            if (checkConvergence(newPageRanks)) {
                break;
            }
        }
    }

    private boolean checkConvergence(double[] newPageRanks) {
        for (int i = 0; i < newPageRanks.length; i++) {
            double diff = Math.abs(newPageRanks[i] - nodes.get(i).getPageRank());
            if (diff > EPSILON) {
                return false;
            }
        }
        return true;
    }

    public void printPageRank() {
        for (Node node : nodes) {
            System.out.println("Node " + node.getId() + ": PageRank = " + node.getPageRank());
        }
    }

    public static void main(String[] args) {
        // Crear nodos y establecer enlaces entre ellos (similar al ejemplo anterior)

        List<Node> nodes = new ArrayList<>();
        // Agregar nodos a la lista

        PageRank pageRank = new PageRank(nodes);
        pageRank.calculatePageRank(100); // Número máximo de iteraciones
        pageRank.printPageRank();
    }
}*/






