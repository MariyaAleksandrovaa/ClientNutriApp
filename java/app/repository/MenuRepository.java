package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
