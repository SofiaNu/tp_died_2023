package clases;

import java.time.LocalTime;
import java.util.List;

public class Sucursal {
	int id;
	String nombre;
	LocalTime horaApertura;
	LocalTime horaCierre;
	boolean estado;
	float capacidad;
	List<StockProducto> stock;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalTime getHoraApertura() {
		return horaApertura;
	}
	public void setHoraApertura(LocalTime horaApertura) {
		this.horaApertura = horaApertura;
	}
	public LocalTime getHoraCierre() {
		return horaCierre;
	}
	public void setHoraCierre(LocalTime horaCierre) {
		this.horaCierre = horaCierre;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public float getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(float capacidad) {
		this.capacidad = capacidad;
	}
	public List<StockProducto> getStock() {
		return stock;
	}
	public void setStock(List<StockProducto> stock) {
		this.stock = stock;
	}
	public Sucursal( String nombre, LocalTime horaApertura, LocalTime horaCierre, boolean estado, float capacidad) {
		super();
		this.nombre = nombre;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.estado = estado;
		this.capacidad = capacidad;
	}
	public Sucursal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		return nombre;
	}
}
