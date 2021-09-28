package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tiposplatos")
public class TypeDish {

	@Id
	public int id_tipos_platos;
	public String plato;
	public String descripcion;

	public int getIdtiposPlatos() {
		return id_tipos_platos;
	}

	public void setIdtiposPlatos(int id_tipos_platos) {
		this.id_tipos_platos = id_tipos_platos;
	}

	public String getPlato() {
		return plato;
	}

	public void setPlato(String plato) {
		this.plato = plato;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
