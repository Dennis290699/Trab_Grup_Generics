package Util;

public class Producto implements Comparable<Producto> {
	public String Supermercados;
	public String Categoria;
	public String Nombre;
	public double Precio;
	public double PrecioR;
	public String UnidadR;

	public Producto(String S, String c, String n, double p, double pR, String u) {
		this.Supermercados = S;
		this.Categoria = c;
		this.Nombre = n;
		this.Precio = p;
		this.PrecioR = pR;
		this.UnidadR = u;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public double getPrecio() {
		return Precio;
	}

	public void setPrecio(double precio) {
		Precio = precio;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public String getUnidadR() {
		return UnidadR;
	}

	public void setUnidadR(String unidadR) {
		UnidadR = unidadR;
	}

	public String getSupermercados() {
		return Supermercados;
	}

	public void setSupermercados(String supermercados) {
		Supermercados = supermercados;
	}

	@Override
	public int compareTo(Producto o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getPrecioR() {
		return PrecioR;
	}

	public void setPrecioR(double precioR) {
		PrecioR = precioR;
	}

	@Override
	public String toString() {
		return "Supermercados= " + Supermercados + " | Categoria= " + Categoria + " | Nombre= " + Nombre
				+ "    | Precio= " + Precio + "   | PrecioR=  " + PrecioR;
	}
}// FINAL CLASS