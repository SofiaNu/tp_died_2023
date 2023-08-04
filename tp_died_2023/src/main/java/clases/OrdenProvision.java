package clases;

import java.time.LocalDate;
import java.util.List;

public class OrdenProvision {

	int id;
	LocalDate fecha;
	Sucursal destino;
	float tiempoLimite;
	float peso;
	boolean estado; //tiene pendiente y en proceso (no hay listo aclarado pero asumo que deberia ir y ahi seria enum)
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
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
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
	public OrdenProvision(LocalDate fecha, Sucursal destino, float tiempoLimite, float peso, boolean estado,
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
