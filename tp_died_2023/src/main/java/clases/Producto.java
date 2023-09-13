package clases;

public class Producto {
	int id;
	String nombre;
	double precioUnitario;
	float peso;

	String detalle;
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Producto(String nombre, double precioUnitario, float peso, String detalle) {
		super();
		this.nombre = nombre;
		this.precioUnitario = precioUnitario;
		this.peso = peso;
		this.detalle = detalle;
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

	public String getDetalle() {
		return detalle;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
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

	public String toString(){
		return nombre;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Producto){
			Producto p = (Producto) o;
		if(p.getId() == this.id){
			return true;}
		else{return false;}
		}
		else{ return false;}
	}

}
