package app.controller;

import java.math.BigDecimal;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.Application;
import app.model.ComponentsDishTable;
import app.model.Menu;
import app.objects.GroupUnitObj;
import app.objects.MenuObj;
import app.objects.AlergensFood;
import app.objects.ComponentsFood;
import app.objects.DateObj;
import app.repository.MenuRepository;

@Controller
public class ControllerMVC {
	
	@Autowired
	private MenuRepository menuRepo;
	
	public String path;
	public String path2;

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public ModelAndView viewHomePage() {

		Map<Integer, String> mapaLocales = new HashMap<Integer, String>();
		ModelAndView model = new ModelAndView();
		
		try {
			Statement st = Application.con.createStatement();
			ResultSet rs = st.executeQuery("select id_local, nombre from locales;");

			while (rs.next()) {
				Integer id_local = rs.getInt(1);
				String nombre_local = rs.getString(2);
				mapaLocales.put(id_local, nombre_local);
			}

			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		model.addObject("mapaLocales", mapaLocales);
		model.setViewName("index");
		return model;

	}

	@RequestMapping(value = { "/client/{id_local}" }, method = RequestMethod.GET)
	public ModelAndView selectDateMenu(HttpServletRequest request, HttpSession session, @PathVariable("id_local") int id_local) {
		ModelAndView model = new ModelAndView("escoger_fecha_menu");
		
		DateObj dateObj = new DateObj();
		model.addObject("dateObj", dateObj);
		model.addObject("id_local", id_local);

		return model;

	}
	@RequestMapping(value = { "/client/chooseDate/{id_local}" }, method = RequestMethod.GET)
	public ModelAndView selectMenu(HttpServletRequest request, HttpSession session, @PathVariable("id_local") int id_local, DateObj dateObj) {
		ModelAndView model = new ModelAndView("mostrar_menus_facultad");
		Map<Integer, String> mapaMenusFacultad = new HashMap<Integer, String>();
		
		path = request.getServletPath();

		session.setAttribute("path", path);

		try {
			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery("select m.id_menu, m.nombre_menu\r\n"
					+ "from menus as m left join locales_menus as lm on m.id_menu = lm.idMenu\r\n"
					+ "right join locales as l on  lm.idLocal = l.id_local \r\n"
					+ "where l.id_local =" + id_local + "\r\n"
					+ "and\r\n"
					+ "m.fecha_publicacion='" + dateObj.getDate() + "'; ");

			while (rs.next()) {
				Integer id_menu = rs.getInt(1);
				String nombre_menu = rs.getString(2);
				mapaMenusFacultad.put(id_menu, nombre_menu);
			}

			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int select = 0;

		model.addObject("mapaMenusFacultad", mapaMenusFacultad);

		Menu menu = new Menu();
		model.addObject("select", select);
		model.addObject("menu", menu);

		return model;

	}

	@RequestMapping(value = { "/client/chooseAlergensComponents" }, method = RequestMethod.GET)
	public ModelAndView choseComponentsAlergens(Menu menu, HttpServletRequest request, HttpSession session) {

		ModelAndView model = new ModelAndView("chooseAlergensComponents");
		
		path2 = request.getServletPath();
		
		
		model.addObject("id_menu", menu.getId_menu());
		return model;

	}

	
	@RequestMapping("/client/ComponentesMenu{id_menu}")
	public String checkIdComponents(@PathVariable("id_menu") int id_menu) {

		Menu menu = menuRepo.findById(id_menu).get();
		
		if(menu.getDescripcion().equals("Menú grupal")) {
			return "redirect:/client/escoger_platos_menu_grupal_componentes/{id_menu}";
			
		}else if(menu.getDescripcion().equals("Menú individual")) {
			return "redirect:/client/componentes_menu_individual/{id_menu}";
		}

		return "";
	}
	
	@RequestMapping("/client/AlergenosMenu{id_menu}")
	public String checkIdAlergens(@PathVariable("id_menu") int id_menu) {

		Menu menu = menuRepo.findById(id_menu).get();
		
		if(menu.getDescripcion().equals("Menú grupal")) {
			return "redirect:/client/escoger_platos_menu_grupal_alergenos/{id_menu}";
			
		}else if(menu.getDescripcion().equals("Menú individual")) {
			return "redirect:/client/alergenos_menu_individual/{id_menu}";
		}

		return "";
	}
	
	@RequestMapping({"/client/alergenos_menu_colectivo{id_menu}"})
	public ModelAndView alergenosMenusColectivos(MenuObj menuObj, @PathVariable("id_menu") int id_menu) {
		ModelAndView model = new ModelAndView("alergenos_menu");

		Map<String, String> mapAlergensMenu = new HashMap<String, String>();
		
		model.addObject("path", path);
		model.addObject("id_menu", id_menu);

		if (menuObj.getFirst_dish() != 0) {
			Map<String, String> mapFirstDish = obtenerAlergenosPlato(menuObj.getFirst_dish());

			for (var alergen : mapFirstDish.entrySet()) {
				mapAlergensMenu.put(alergen.getKey(), alergen.getValue());
			}

		}
		if (menuObj.getSecond_dish() != 0) {
			Map<String, String> mapSecondDish = obtenerAlergenosPlato(menuObj.getSecond_dish());

			for (var alergen : mapSecondDish.entrySet()) {
				mapAlergensMenu.put(alergen.getKey(), alergen.getValue());
			}

		}
		if (menuObj.getThird_dish() != 0) {
			Map<String, String> mapThirdDish = obtenerAlergenosPlato(menuObj.getThird_dish());

			for (var alergen : mapThirdDish.entrySet()) {
				mapAlergensMenu.put(alergen.getKey(), alergen.getValue());
			}

		}

		model.addObject("mapAlergensMenu", mapAlergensMenu);

		return model;
	}

	
	@GetMapping("/client/escoger_platos_menu_grupal_alergenos/{id_menu}")
	public ModelAndView escogerPlatosMenuGrupalAlergenos(@PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("escoger_platos_menu_grupal_alergenos");
		
		model.addObject("path", path);
		model.addObject("id_menu", id_menu);
		
		List<Map<Integer, String>> listMapsTypeDishes = getListMapsTypeDishes(id_menu);

		Map<Integer, String> mapaPlatosTipo1Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo2Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo3Menu = new HashMap<Integer, String>();

		for (int i = 0; i < listMapsTypeDishes.size(); i++) {
			if (i == 0) {
				mapaPlatosTipo1Menu = listMapsTypeDishes.get(0);
			} else if (i == 1) {
				mapaPlatosTipo2Menu = listMapsTypeDishes.get(1);
			} else if (i == 2) {
				mapaPlatosTipo3Menu = listMapsTypeDishes.get(2);
			}
		}

		model.addObject("mapaPlatosTipo1Menu", mapaPlatosTipo1Menu);
		model.addObject("mapaPlatosTipo2Menu", mapaPlatosTipo2Menu);
		model.addObject("mapaPlatosTipo3Menu", mapaPlatosTipo3Menu);

		MenuObj menuObj = new MenuObj();
		model.addObject("menuObj", menuObj);

		int select = 0;
		model.addObject("select", select);

		return model;
	}
	
	
	@GetMapping({"/client/alergenos_menu_individual/{id_menu}"})
	public ModelAndView alergenosMenusIndividuales(@PathVariable("id_menu") int id_menu) {
		ModelAndView model = new ModelAndView("alergenos_menu");
		Map<String, String> mapAlergensMenu = obtenerAlergenosMenu(id_menu);
		model.addObject("mapAlergensMenu", mapAlergensMenu);

		return model;
	}
	
	public Map<String, String> obtenerAlergenosMenu(int id_menu) {
		Map<String, String> mapAlergensMenu = new HashMap<String, String>();

		try {

			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery("select idPlato from menus_platos where idMenu = " + id_menu + ";");

			while (rs.next()) {

				int idPlato = rs.getInt(1);

				Map<String, String> mapAlergensDish = obtenerAlergenosPlato(idPlato);

				for (var alergenDish : mapAlergensDish.entrySet()) {

					mapAlergensMenu.put(alergenDish.getKey(), alergenDish.getValue());
				}

			}

			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapAlergensMenu;

	}
	
	public Map<String, String> obtenerAlergenosPlato(int id_plato) {

		Map<String, String> mapAlergensDish = null;

		try {
//			1º Obtener los ids de los alimentos que tiene un plato

			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery(
					"select idAlimento\r\n" + "from platos_alimentos\r\n" + "where idPlato =" + id_plato + ";");

			mapAlergensDish = new HashMap<String, String>();
			while (rs.next()) {

//				2º Por cada id obtener sus alérgenos

				List<AlergensFood> listAlergensFood = obtenerBDalergenosAlimento(rs.getInt(1));

				for (AlergensFood alergensFood : listAlergensFood) {

//					3º Almacenar en un mapa los alérgenos

					mapAlergensDish.put(alergensFood.getAlergeno(), alergensFood.getDescripcion());

				}

			}

			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapAlergensDish;
	}

	public List<AlergensFood> obtenerBDalergenosAlimento(int idAlimento) {

		List<AlergensFood> listaAlergenos = new ArrayList<AlergensFood>();

		try {

			Statement st = Application.con.createStatement();
			ResultSet rs = st.executeQuery("SELECT t.alergeno, t.descripcion, a.tieneAlergeno\r\n"
					+ "FROM tiposalergenos as t right join alimentos_tiposalergenos as a on t.idTiposAlergenos = a.idTiposAlergenos\r\n"
					+ "WHERE idAlimentos=" + idAlimento + " and\r\n" + "tieneAlergeno='si';\r\n" + "");

			while (rs.next()) {
				String nombreAlergeno = rs.getString(1);
				String descripcionAlergeno = rs.getString(2);

				AlergensFood alergeno = new AlergensFood(nombreAlergeno, descripcionAlergeno);
				listaAlergenos.add(alergeno);
			}
			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaAlergenos;
	}
	
	@GetMapping({"/client/componentes_menu_individual/{id_menu}"})
	public ModelAndView componentesMenuIndividual(@PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("componentes_plato");

		List<ComponentsDishTable> listComponentsDish = obtenerComponentesMenu(id_menu);
		
		model.addObject("id_menu", id_menu);
		model.addObject("path", path);
		model.addObject("listComponentsDish", listComponentsDish);

		return model;
	}
	
	

	public List<ComponentsDishTable> obtenerComponentesMenu(int id_menu) {
		List<ComponentsDishTable> listListComponentsDish = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> listComponentsDish;
		Map<String, Float> mapComponentsMenu = new HashMap<String, Float>();

		try {

			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery("select idPlato from menus_platos where idMenu = " + id_menu + ";");

			while (rs.next()) {

				int idPlato = rs.getInt(1);
				if (idPlato != 0) {
					// He obtenido los componentes que tiene un plato
					listComponentsDish = obtenerBDcomponentesPlato(idPlato);

					for (int i = 0; i < listComponentsDish.size(); i++) {
						// Cuando no existe entrada en mapa para ese componente
						if (mapComponentsMenu.get(listComponentsDish.get(i).getNameComponent()) == null) {
							mapComponentsMenu.put(listComponentsDish.get(i).getNameComponent(),
									listComponentsDish.get(i).getAmount());

						} else {
							float oldAmount = mapComponentsMenu.get(listComponentsDish.get(i).getNameComponent());
							float newAmount = listComponentsDish.get(i).getAmount();
							mapComponentsMenu.replace(listComponentsDish.get(i).getNameComponent(),
									oldAmount + newAmount);
						}
					}
					listListComponentsDish.addAll(listComponentsDish);
				}

			}

			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Mantengo lista con componentes sin repetir
//		List<ComponentsDishTable> listFinalComponents = new ArrayList<>();
		// Recorro las listas de listas
		for (int i = 0; i < listListComponentsDish.size(); i++) {

			String component = listListComponentsDish.get(i).getNameComponent();

			for (int j = 0; j < listListComponentsDish.size(); j++) {
				if (i != j) {
					String component2 = listListComponentsDish.get(j).getNameComponent();
					if (component.equals(component2)) {
						listListComponentsDish.remove(j);

					}
				}

			}
		}
		for (int i = 0; i < listListComponentsDish.size(); i++) {
			listListComponentsDish.get(i)
					.setAmount(mapComponentsMenu.get(listListComponentsDish.get(i).getNameComponent()));
		}

		return listListComponentsDish;

	}
	
	public List<ComponentsDishTable> obtenerBDcomponentesPlato(int id_plato) {

		List<ComponentsDishTable> listComponentDishTable = new ArrayList<ComponentsDishTable>();

		try {

			// 1º Buscar todos los ingredientes del plato y sus cantidades

			Map<Integer, BigDecimal> mapIngredientAmount = new HashMap<Integer, BigDecimal>();

			Statement st = Application.con.createStatement();
			ResultSet rs = st.executeQuery("select idAlimento, cantidad\r\n" + "from platos_alimentos\r\n"
					+ "where idPlato = " + id_plato + ";");

			while (rs.next()) {
				mapIngredientAmount.put(rs.getInt(1), rs.getBigDecimal(2));

			}
			rs.close();
			st.close();

			// 2º Encontrar los componentes quimicos de cada alimento

			// Mapa que contiene los ingredientes del plato
			Map<Integer, List<ComponentsFood>> mapComponentsDish = new HashMap<Integer, List<ComponentsFood>>();

			Statement st2 = Application.con.createStatement();

			// Lista que almacena los componentes de todos los alimentos del plato
			List<ComponentsFood> listaComponentes = new ArrayList<ComponentsFood>();

			// Almacenar el grupo y unidad de los componentess
			Map<String, GroupUnitObj> mapComponentUnit = new HashMap<String, GroupUnitObj>();

			for (var ingrediente : mapIngredientAmount.entrySet()) {
				ResultSet rs2 = st2.executeQuery(
						"SELECT g.nombre, ac.c_ori_name, ac.best_location, ac.v_unit, ac.mu_descripcion  \r\n"
								+ "FROM nutri_db.alimentos_componentesquimicos as ac left join componentesquimicos as c on ac.c_ori_name = c.c_ori_name \r\n"
								+ "left join gruposcomponentes as g on c.componentgroup_id = g.idGruposComponentes\r\n"
								+ "where idAlimento = " + ingrediente.getKey() + "\r\n" + "and ac.best_location > 0\r\n"
								+ "order by best_location desc;");

				while (rs2.next()) {
					String nombreComponente = rs2.getString(1);
					String descripcionComponente = rs2.getString(2);
					Float valor = rs2.getFloat(3);
					String unidad = rs2.getString(4);
					String descripcion = rs2.getString(5);

					ComponentsFood componente = new ComponentsFood(nombreComponente, descripcionComponente, valor,
							unidad, descripcion);

					listaComponentes.add(componente);

					GroupUnitObj groupUnitObj = new GroupUnitObj(nombreComponente, unidad);
					mapComponentUnit.put(descripcionComponente, groupUnitObj);

				}
				mapComponentsDish.put(ingrediente.getKey(), listaComponentes);

				// Reinicio lista de componentes para que los componentes de cada alimento los
				// almacene en una entrada distinta del mapa

				listaComponentes = new ArrayList<ComponentsFood>();

				rs2.close();

			}
			st2.close();

			Map<String, Float> mapComponentsDish2 = new HashMap<String, Float>();

			for (var ingrediente : mapComponentsDish.entrySet()) {

				ingrediente.getKey();
				List<ComponentsFood> listaCompon = ingrediente.getValue();

				for (int i = 0; i < listaCompon.size(); i++) {
					if (mapComponentsDish2.get(listaCompon.get(i).getC_ori_name()) == null) {

						Float amount = listaCompon.get(i).getBest_location();

						String proportion = listaCompon.get(i).getDescripcion();
						Float propor = null;
						switch (proportion) {
						case "por 100 g de porción comestible":
							propor = amount / 100;
							break;
						case "por Kg de parte comestible":
							propor = amount / 1000;
							break;
						case "por 100 g de peso en seco":
							propor = amount / 100;
							break;
						case "por ml de volumen del alimento":
							propor = amount;
							break;

						}
						BigDecimal quantity = mapIngredientAmount.get(ingrediente.getKey());
						mapComponentsDish2.put(listaCompon.get(i).getC_ori_name(), propor * quantity.floatValue());

					} else {
						Float value = mapComponentsDish2.get(listaCompon.get(i).getC_ori_name());
						Float amount = listaCompon.get(i).getBest_location();

						String proportion = listaCompon.get(i).getDescripcion();
						Float propor = null;
						switch (proportion) {
						case "por 100 g de porción comestible":
							propor = amount / 100;
							break;
						case "por Kg de parte comestible":
							propor = amount / 1000;
							break;
						case "por 100 g de peso en seco":
							propor = amount / 100;
							break;
						case "por ml de volumen del alimento":
							propor = amount;
							break;

						}
						BigDecimal quantity = mapIngredientAmount.get(ingrediente.getKey());
						Float sum = value + propor * quantity.floatValue();
						mapComponentsDish2.replace(listaCompon.get(i).getC_ori_name(), sum);

					}
				}

			}

			for (var componente : mapComponentsDish2.entrySet()) {

				ComponentsDishTable componentDishTable = new ComponentsDishTable();

				componentDishTable.setNameComponent(componente.getKey());
				componentDishTable.setAmount(componente.getValue());

				GroupUnitObj groupUnitObj = mapComponentUnit.get(componente.getKey());

				componentDishTable.setGroupComponent(groupUnitObj.getGroup());
				componentDishTable.setUnit(groupUnitObj.getUnit());

				listComponentDishTable.add(componentDishTable);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listComponentDishTable;
	}
	
//	Permite escoger los platos del menú que se van a consumir para proceder a calcular los alérgeno del mismo
	@RequestMapping("/client/escoger_platos_menu_grupal_componentes/{id_menu}")
	public ModelAndView escogerPlatosMenuGrupalComponentes_user(@PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("escoger_platos_menu_grupal_componentes_user");

		model.addObject("path2", path2);
//		String path = request.getServletPath();
		model.addObject("path", path);
//		session.setAttribute("path", path);
		
		List<Map<Integer, String>> listMapsTypeDishes = getListMapsTypeDishes(id_menu);

		Map<Integer, String> mapaPlatosTipo1Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo2Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo3Menu = new HashMap<Integer, String>();

		for (int i = 0; i < listMapsTypeDishes.size(); i++) {
			if (i == 0) {
				mapaPlatosTipo1Menu = listMapsTypeDishes.get(0);
			} else if (i == 1) {
				mapaPlatosTipo2Menu = listMapsTypeDishes.get(1);
			} else if (i == 2) {
				mapaPlatosTipo3Menu = listMapsTypeDishes.get(2);
			}
		}

		model.addObject("mapaPlatosTipo1Menu", mapaPlatosTipo1Menu);
		model.addObject("mapaPlatosTipo2Menu", mapaPlatosTipo2Menu);
		model.addObject("mapaPlatosTipo3Menu", mapaPlatosTipo3Menu);

		MenuObj menuObj = new MenuObj();
		model.addObject("menuObj", menuObj);

		int select = 0;
		model.addObject("select", select);

		return model;
	}
	public List<Map<Integer, String>> getListMapsTypeDishes(int id_menu) {

		List<Map<Integer, String>> listMapsTypeDishes = new ArrayList<Map<Integer, String>>();

		Map<Integer, String> mapaPlatosTipo1Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo2Menu = new HashMap<Integer, String>();
		Map<Integer, String> mapaPlatosTipo3Menu = new HashMap<Integer, String>();

		try {

			Statement st = Application.con.createStatement();

			for (int i = 1; i <= 3; i++) {
				ResultSet rs = st.executeQuery("select mp.idPlato, p.nombre_plato\r\n"
						+ "from menus_platos as mp right join platos as p on mp.idPlato = p.id_plato \r\n"
						+ "where mp.idMenu = " + id_menu + " and mp.descripcion = 'Menú grupal' and p.id_tipo_platos = "
						+ i + ";");

				while (rs.next()) {
					if (i == 1) {
						mapaPlatosTipo1Menu.put(rs.getInt(1), rs.getString(2));
					}
					if (i == 2) {
						mapaPlatosTipo2Menu.put(rs.getInt(1), rs.getString(2));
					}
					if (i == 3) {
						Integer id_plato = rs.getInt(1);
						String nombre_plato = rs.getString(2);

						if (id_plato != null) {
							mapaPlatosTipo3Menu.put(id_plato, nombre_plato);
						}

					}

				}

				rs.close();
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		listMapsTypeDishes.add(mapaPlatosTipo1Menu);
		listMapsTypeDishes.add(mapaPlatosTipo2Menu);
		listMapsTypeDishes.add(mapaPlatosTipo3Menu);

		return listMapsTypeDishes;
	}
	
	@RequestMapping("/client/componentes_menu_colectivo{id_menu}")
	public ModelAndView componentesMenuColectivo_user(MenuObj menuObj, HttpSession session, HttpServletRequest request, @PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("componentes_plato");
		model.addObject("id_menu", id_menu);
		
		
		String path = request.getServletPath();
		session.setAttribute("path", path);

		List<Integer> listDishes = new ArrayList<Integer>();
		listDishes.add(menuObj.getFirst_dish());
		listDishes.add(menuObj.getSecond_dish());
		listDishes.add(menuObj.getThird_dish());

		List<ComponentsDishTable> listListComponentsDish = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> listComponentsDish;
		Map<String, Float> mapComponentsMenu = new HashMap<String, Float>();

		for (int i = 0; i < listDishes.size(); i++) {
			if (listDishes.get(i) != 0) {
				// He obtenido los componentes que tiene un plato
				listComponentsDish = obtenerBDcomponentesPlato(listDishes.get(i));

				for (int j = 0; j < listComponentsDish.size(); j++) {
					// Cuando no existe entrada en mapa para ese componente
					if (mapComponentsMenu.get(listComponentsDish.get(j).getNameComponent()) == null) {
						mapComponentsMenu.put(listComponentsDish.get(j).getNameComponent(),
								listComponentsDish.get(j).getAmount());

					} else {
						float oldAmount = mapComponentsMenu.get(listComponentsDish.get(j).getNameComponent());
						float newAmount = listComponentsDish.get(j).getAmount();
						mapComponentsMenu.replace(listComponentsDish.get(j).getNameComponent(), oldAmount + newAmount);
					}
				}
				listListComponentsDish.addAll(listComponentsDish);

				// Mantengo lista con componentes sin repetir
				// Recorro las listas de listas
				for (int j = 0; j < listListComponentsDish.size(); j++) {

					String component = listListComponentsDish.get(j).getNameComponent();

					for (int k = 0; k < listListComponentsDish.size(); k++) {
						if (j != k) {
							String component2 = listListComponentsDish.get(k).getNameComponent();
							if (component.equals(component2)) {
								listListComponentsDish.remove(k);

							}
						}

					}
				}
				for (int j = 0; j < listListComponentsDish.size(); j++) {
					listListComponentsDish.get(j)
							.setAmount(mapComponentsMenu.get(listListComponentsDish.get(j).getNameComponent()));
				}
			}

		}
		listComponentsDish = listListComponentsDish;
		model.addObject("listComponentsDish", listComponentsDish);

		return model;
	}




	
	
}
