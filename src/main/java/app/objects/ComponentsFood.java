package app.objects;

import java.math.BigDecimal;

public class ComponentsFood {

	public String grupo;
	public String c_ori_name;
	public Double best_location;
	public String v_unit;
	public String descripcion;
	public BigDecimal calorias;
	public String unity;

	public ComponentsFood(String grupo, String c_ori_name, Double best_location, String v_unit, String descripcion) {
		this.grupo = grupo;
		this.c_ori_name = c_ori_name;
		this.best_location = best_location;
		this.v_unit = v_unit;
		this.descripcion = descripcion;
	}

	public ComponentsFood() {

	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getC_ori_name() {
		return c_ori_name;
	}

	public void setC_ori_name(String c_ori_name) {
		this.c_ori_name = c_ori_name;
	}

	public Double getBest_location() {
		return best_location;
	}

	public void setBest_location(Double best_location) {
		this.best_location = best_location;
	}

	public String getV_unit() {
		return v_unit;
	}

	public void setV_unit(String v_unit) {
		this.v_unit = v_unit;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
