package clases;

public class Camino {
	public Camino(Sucursal origen, Sucursal destino, float tiempoTransito, float capacidadMaxima,
			boolean estado) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.tiempoTransito = tiempoTransito;
		this.capacidadMaxima = capacidadMaxima;
		this.estado = estado;
	}
	public Camino() {
		super();
		// TODO Auto-generated constructor stub
	}
	int id;
	Sucursal origen;
	Sucursal destino;
	float tiempoTransito;
	float capacidadMaxima;
	boolean estado;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Sucursal getOrigen() {
		return origen;
	}
	public void setOrigen(Sucursal origen) {
		this.origen = origen;
	}
	public Sucursal getDestino() {
		return destino;
	}
	public void setDestino(Sucursal destino) {
		this.destino = destino;
	}
	public float getTiempoTransito() {
		return tiempoTransito;
	}
	public void setTiempoTransito(float tiempoTransito) {
		this.tiempoTransito = tiempoTransito;
	}
	public float getCapacidadMaxima() {
		return capacidadMaxima;
	}
	public void setCapacidadMaxima(float capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
