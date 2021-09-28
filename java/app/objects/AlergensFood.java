package app.objects;

public class AlergensFood {

	public String alergeno;
	public String descripcion;

	public AlergensFood(String alergeno, String descripcion) {
		this.alergeno = alergeno;
		this.descripcion = descripcion;
	}

	public AlergensFood() {

	}

	public String getAlergeno() {
		return alergeno;
	}

	public void setAlergeno(String alergeno) {
		this.alergeno = alergeno;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
