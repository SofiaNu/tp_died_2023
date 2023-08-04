package clases;

import java.time.LocalDate;
import java.util.List;

public class OrdenProvision {

	int id;
	LocalDate fecha;
	Sucursal destino;
	float tiempoLimite;
	float peso;
	EstadoOrden estado;
	List<ProductoProvisto> listaProductos;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Sucursal getDestino() {
		return destino;
	}
	public void setDestino(Sucursal destino) {
		this.destino = destino;
	}
	public float getTiempoLimite() {
		return tiempoLimite;
	}
	public void setTiempoLimite(float tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public EstadoOrden getEstado() {
		return estado;
	}
	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}
	public List<ProductoProvisto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(List<ProductoProvisto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public OrdenProvision() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdenProvision(LocalDate fecha, Sucursal destino, float tiempoLimite, float peso, EstadoOrden estado,
			List<ProductoProvisto> listaProductos) {
		super();
		this.fecha = fecha;
		this.destino = destino;
		this.tiempoLimite = tiempoLimite;
		this.peso = peso;
		this.estado = estado;
		this.listaProductos = listaProductos;
	}
}
