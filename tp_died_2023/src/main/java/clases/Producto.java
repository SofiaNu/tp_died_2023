package clases;

public class Producto {
	int id;
	String nombre;
	double precioUnitario;
	float peso;
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Producto(int id, String nombre, double precioUnitario, float peso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precioUnitario = precioUnitario;
		this.peso = peso;
	}
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
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	

}
