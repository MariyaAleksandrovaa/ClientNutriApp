package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gruposalimentos")
public class GroupFood {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id_grupos_alimentos;
	

	public String grupo;
	
	public String nombre;
	
	
	
	public int getId_grupos_alimentos() {
		return id_grupos_alimentos;
	}

	public void setId_grupos_alimentos(int id_grupos_alimentos) {
		this.id_grupos_alimentos = id_grupos_alimentos;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
