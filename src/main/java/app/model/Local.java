package app.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locales")
public class Local {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id_local;

	public int id_empresa;

	public String nombre;
	public String direccion;

	public Local(String nombre, String direccion, int id_local, int id_empresa) {

		this.nombre = nombre;
		this.direccion = direccion;
		this.id_local = id_local;
		this.id_empresa = id_empresa;
	}

	public Local() {

	}

	public int getIdLocal() {
		return id_local;
	}

	public void setIdLocal(int idLocal) {
		this.id_local = idLocal;
	}

	public int getIdEmpresa() {
		return id_empresa;
	}

	public void setIdEmpresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
