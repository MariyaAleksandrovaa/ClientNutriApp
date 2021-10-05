package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.model.Empresa;

@Repository
public interface CompanyRepository extends JpaRepository<Empresa, Integer> {
	
	@Query("select e from Empresa e where e.cif = ?1")
	public Empresa findByCif(String cif);
	
	@Query("select e from Empresa e where e.nombre = ?1")
	public Empresa findByNameCompany(String company);
}
