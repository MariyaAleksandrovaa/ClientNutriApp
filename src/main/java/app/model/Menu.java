package app.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menus")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id_menu;
	public String nombre_menu;
	public String descripcion;
	public java.sql.Timestamp fecha_creacion;
	public int id_empresa;

	public Menu(Integer id_menu, String nombre_menu, String descripcion, java.sql.Timestamp fecha_creacion, int id_empresa) {
		this.id_menu = id_menu;
		this.nombre_menu = nombre_menu;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
		this.id_empresa = id_empresa;
	}

	public Menu() {

	}

	public Integer getId_menu() {
		return id_menu;
	}

	public void setId_menu(Integer id_menu) {
		this.id_menu = id_menu;
	}

	public String getNombre_menu() {
		return nombre_menu;
	}

	public void setNombre_menu(String nombre_menu) {
		this.nombre_menu = nombre_menu;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public java.sql.Timestamp getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(java.sql.Timestamp fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public int getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

}
