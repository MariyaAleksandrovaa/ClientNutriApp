package app.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alimentos")
public class Food {

	@Column(nullable = true)
	private Integer id_grupo_alimento;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = true)
	public Integer id_alimento;

	@Column(length = 90, nullable = true)
	private String nombre;

	@Column(length = 400, nullable = true)
	private String langual;

	@Column(length = 90, nullable = true)
	private String ingles;

	@Column(length = 90, nullable = true)
	private String origen;

	@Column(nullable = true)
	private BigDecimal edible_portion;

	@Column(nullable = true)
	private int activo;

	@Column(length = 90, nullable = true)
	private String c_ori_name;

	@Column(length = 800, nullable = true)
	private String glos_esp;

	@Column(length = 50, nullable = true)
	private String cg_descripcion;

	@Column(length = 4, nullable = true)
	private String v_unit;

	@Column(length = 400, nullable = true)
	private String mu_descripcion;

	@Column(nullable = true)
	private Integer ref_id;

	@Column(length = 400, nullable = true)
	private String at_descripcion;

	@Column(length = 400, nullable = true)
	private String pt_descripcion;

	private BigDecimal calorias;
	private String unity;

	public Integer getIdGrupo() {
		return id_grupo_alimento;
	}

	public void setIdGrupo(Integer id_grupo_alimento) {
		this.id_grupo_alimento = id_grupo_alimento;
	}

	public Integer getIdAlimento() {
		return id_alimento;
	}

	public void setIdAlimento(Integer id_alimento) {
		this.id_alimento = id_alimento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLangual() {
		return langual;
	}

	public void setLangual(String langual) {
		this.langual = langual;
	}

	public String getIngles() {
		return ingles;
	}

	public void setIngles(String ingles) {
		this.ingles = ingles;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public BigDecimal getEdible_portion() {
		return edible_portion;
	}

	public void setEdible_portion(BigDecimal edible_portion) {
		this.edible_portion = edible_portion;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getC_ori_name() {
		return c_ori_name;
	}

	public void setC_ori_name(String c_ori_name) {
		this.c_ori_name = c_ori_name;
	}

	public String getGlos_esp() {
		return glos_esp;
	}

	public void setGlos_esp(String glos_esp) {
		this.glos_esp = glos_esp;
	}

	public String getCg_descripcion() {
		return cg_descripcion;
	}

	public void setCg_descripcion(String cg_descripcion) {
		this.cg_descripcion = cg_descripcion;
	}

	public String getV_unit() {
		return v_unit;
	}

	public void setV_unit(String v_unit) {
		this.v_unit = v_unit;
	}

	public String getMu_descripcion() {
		return mu_descripcion;
	}

	public void setMu_descripcion(String mu_descripcion) {
		this.mu_descripcion = mu_descripcion;
	}

	public Integer getRef_id() {
		return ref_id;
	}

	public void setRef_id(Integer ref_id) {
		this.ref_id = ref_id;
	}

	public String getAt_descripcion() {
		return at_descripcion;
	}

	public void setAt_descripcion(String at_descripcion) {
		this.at_descripcion = at_descripcion;
	}

	public String getPt_descripcion() {
		return pt_descripcion;
	}

	public void setPt_descripcion(String pt_descripcion) {
		this.pt_descripcion = pt_descripcion;
	}

	public BigDecimal getCalorias() {
		return calorias;
	}

	public void setCalorias(BigDecimal calorias) {
		this.calorias = calorias;
	}

	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

}
