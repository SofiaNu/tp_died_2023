package clases;

public class ProvisionProducto {
    Producto producto;
    OrdenProvision ordenProvision;
    int cantidad;
    public ProvisionProducto() {
        super();
    }
    public OrdenProvision getOrdenProvision() {
        return ordenProvision;
    }

    public void setOrdenProvision(OrdenProvision ordenProvision) {
        this.ordenProvision = ordenProvision;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public ProvisionProducto(Producto producto, int cantidad) {
        super();
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
