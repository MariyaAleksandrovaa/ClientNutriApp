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
import app.model.User;
import app.objects.GroupUnitObj;
import app.objects.MenuObj;
import app.objects.AlergensFood;
import app.objects.ComponentsFood;
import app.objects.DateObj;
import app.repository.CompanyRepository;
import app.repository.MenuRepository;

@Controller
public class ControllerMVC {

	@Autowired
	private MenuRepository menuRepo;

	public String path;
	public String path2;

	@Autowired
	public CompanyRepository companyRepo;

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
	public ModelAndView selectDateMenu(HttpServletRequest request, HttpSession session,
			@PathVariable("id_local") int id_local) {
		ModelAndView model = new ModelAndView("escoger_fecha_menu");

		DateObj dateObj = new DateObj();
		model.addObject("dateObj", dateObj);
		model.addObject("id_local", id_local);

		return model;

	}

	public String dateCheck = "";

	@RequestMapping(value = { "/client/chooseDate/{id_local}" }, method = RequestMethod.GET)
	public ModelAndView selectMenu(HttpServletRequest request, HttpSession session,
			@PathVariable("id_local") int id_local, DateObj dateObj) {
		ModelAndView model = new ModelAndView("mostrar_menus_facultad");
		Map<Integer, String> mapaMenusFacultad = new HashMap<Integer, String>();

		path = request.getServletPath();
		dateCheck = dateObj.getDate();

		session.setAttribute("path", path);

		try {
			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery("select m.id_menu, m.nombre_menu\r\n"
					+ "from menus as m left join locales_menus as lm on m.id_menu = lm.idMenu\r\n"
					+ "right join locales as l on  lm.idLocal = l.id_local \r\n" + "where l.id_local =" + id_local
					+ "\r\n" + "and\r\n" + "m.fecha_publicacion='" + dateObj.getDate() + "'; ");

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
		model.addObject("id_local", id_local);
		model.addObject("date", dateCheck);

		return model;

	}

	@RequestMapping(value = { "/client/{id_local}/{date}" }, method = RequestMethod.GET)
	public ModelAndView selectMenu2(HttpServletRequest request, HttpSession session,
			@PathVariable("id_local") int id_local, DateObj dateObj, @PathVariable("date") String date) {
		ModelAndView model = new ModelAndView("mostrar_menus_facultad");
		Map<Integer, String> mapaMenusFacultad = new HashMap<Integer, String>();

		path = request.getServletPath();
		dateCheck = dateObj.getDate();

		session.setAttribute("path", path);

		try {
			Statement st = Application.con.createStatement();

			ResultSet rs = st.executeQuery("select m.id_menu, m.nombre_menu\r\n"
					+ "from menus as m left join locales_menus as lm on m.id_menu = lm.idMenu\r\n"
					+ "right join locales as l on  lm.idLocal = l.id_local \r\n" + "where l.id_local =" + id_local
					+ "\r\n" + "and\r\n" + "m.fecha_publicacion='" + dateObj.getDate() + "'; ");

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
		model.addObject("id_local", id_local);
		model.addObject("date", dateCheck);

		return model;

	}

	@RequestMapping(value = { "/client/chooseAlergensComponents/{id_local}/{date}//" }, method = RequestMethod.GET)
	public ModelAndView choseComponentsAlergens(Menu menu, HttpServletRequest request, HttpSession session,
			@PathVariable("id_local") int id_local, @PathVariable("date") String date) {

		ModelAndView model = new ModelAndView("chooseAlergensComponents");

		path2 = request.getServletPath();

		model.addObject("id_local", id_local);
		model.addObject("id_menu", menu.getId_menu());
		model.addObject("date", date);

		return model;

	}

	@RequestMapping(value = {
			"/client/chooseAlergensComponents/{id_local}/{date}/{id_menu}" }, method = RequestMethod.GET)
	public ModelAndView choseComponentsAlergens2(Menu menu, HttpServletRequest request, HttpSession session,
			@PathVariable("id_local") int id_local, @PathVariable("date") String date,
			@PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("chooseAlergensComponents");

		path2 = request.getServletPath();

		model.addObject("id_local", id_local);
		model.addObject("id_menu", id_menu);
		model.addObject("date", date);

		return model;

	}

	@RequestMapping("/client/ComponentesMenu/{id_menu}/{id_local}")
	public String checkIdComponents(@PathVariable("id_menu") int id_menu, @PathVariable("id_local") int id_local) {

		Menu menu = menuRepo.findById(id_menu).get();

		if (menu.getDescripcion().equals("Menú grupal")) {
			return "redirect:/client/escoger_platos_menu_grupal_componentes/{id_menu}/{id_local}";

		} else if (menu.getDescripcion().equals("Menú individual")) {
			return "redirect:/client/componentes_menu_individual/{id_menu}/{id_local}";
		}

		return "";
	}

	@RequestMapping("/client/AlergenosMenu/{id_menu}/{id_local}")
	public String checkIdAlergens(@PathVariable("id_menu") int id_menu) {

		Menu menu = menuRepo.findById(id_menu).get();

		if (menu.getDescripcion().equals("Menú grupal")) {
			return "redirect:/client/escoger_platos_menu_grupal_alergenos/{id_menu}/{id_local}";

		} else if (menu.getDescripcion().equals("Menú individual")) {
			return "redirect:/client/alergenos_menu_individual/{id_menu}/{id_local}";
		}

		return "";
	}

	@RequestMapping({ "/client/alergenos_menu_colectivo/{id_menu}/{id_local}/{date}" })
	public ModelAndView alergenosMenusColectivos(MenuObj menuObj, @PathVariable("id_menu") int id_menu,
			@PathVariable("id_local") int id_local,
			@PathVariable("date") String date) {
		ModelAndView model = new ModelAndView("alergenos_menu");

		Map<String, String> mapAlergensMenu = new HashMap<String, String>();

		model.addObject("path", path);
		model.addObject("id_menu", id_menu);
		model.addObject("id_local", id_local);
		model.addObject("date", date);

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

	@GetMapping("/client/escoger_platos_menu_grupal_alergenos/{id_menu}/{id_local}")
	public ModelAndView escogerPlatosMenuGrupalAlergenos(@PathVariable("id_menu") int id_menu,
			@PathVariable("id_local") int id_local) {

		ModelAndView model = new ModelAndView("escoger_platos_menu_grupal_alergenos");

		model.addObject("path", path);
		model.addObject("id_menu", id_menu);
		model.addObject("id_local", id_local);
		model.addObject("date", dateCheck);

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

	@GetMapping({ "/client/alergenos_menu_individual/{id_menu}/{id_local}" })
	public ModelAndView alergenosMenusIndividuales(@PathVariable("id_menu") int id_menu, @PathVariable("id_local") int id_local) {
		ModelAndView model = new ModelAndView("alergenos_menu_ind");
		Map<String, String> mapAlergensMenu = obtenerAlergenosMenu(id_menu);
		
		model.addObject("mapAlergensMenu", mapAlergensMenu);
		model.addObject("id_local", id_local);
		model.addObject("date", dateCheck);

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

	@GetMapping({ "/client/componentes_menu_individual/{id_menu}/{id_local}" })
	public ModelAndView componentesMenuIndividual(@PathVariable("id_menu") int id_menu,
			@PathVariable("id_local") int id_local) {

		ModelAndView model = new ModelAndView("componentes_menu_ind");

//		List<ComponentsDishTable> listComponentsDish = obtenerComponentesMenu(id_menu);
		
		obtenerComponentesMenuIndividual(id_menu, model);

		model.addObject("id_menu", id_menu);
		model.addObject("id_local", id_local);
		model.addObject("path", path);
//		model.addObject("listComponentsDish", listComponentsDish);
		model.addObject("date", dateCheck);

		return model;
	}
	
	public void obtenerComponentesMenuIndividual(int id_menu, ModelAndView model) {
		List<Integer> listDishes = obtenerPlatosMenu(id_menu);

		Map<String, List<ComponentsDishTable>> mapComponents = clasificarComponentes(
				obtenerComponentesMenuGrupal(listDishes));
		Map<String, Double> valoresProximales = calculoProximales(mapComponents);
		Map<String, Double> valoresHC = calculoHC(mapComponents);

		Double hcTotales = valoresHC.get("fibra") + valoresHC.get("azucar") + valoresHC.get("otrosHC");
		Double acGrasos = calcularAcGrasos(mapComponents);

		Double proporcionGrasas = valoresProximales.get("grasa") * 9;
		Double proporcionProteinas = valoresProximales.get("proteina") * 4;
		Double proporcionHC = hcTotales * 4;
		Double proporcionValorOtrosHC = valoresHC.get("otrosHC") * 4;

		Double proporcionValorFibra = valoresHC.get("fibra") * 4;
		Double proporcionValorAzúcar = valoresHC.get("azucar") * 4;
		Double proporcionAcGrasos = acGrasos * 9;

		Double total = /*valoresProximales.get("energia") +*/ proporcionGrasas + proporcionProteinas + proporcionHC;

		List<ComponentsDishTable> listComponentsDishOrdered = ordenarPorUnidad(mapComponents);

//		Cargo datos de tabla completa
		model.addObject("listComponentsDish", listComponentsDishOrdered);

//		Cargo datos de tablas 
		model.addObject("componentsDishTableProximal", mapComponents.get("proximales"));
		model.addObject("componentsDishTableHcarbono", mapComponents.get("hc"));
		model.addObject("componentsDishTableGrasa", mapComponents.get("grasas"));
		model.addObject("componentsDishTableVitaminas", mapComponents.get("vitaminas"));
		model.addObject("componentsDishTableMinerales", mapComponents.get("minerales"));

//	Cargo datos del grafico
		model.addObject("porcentajeGrasa", calcularPorcentaje(proporcionGrasas, total));
		model.addObject("porcentajeProteinas", calcularPorcentaje(proporcionProteinas, total));
		model.addObject("porcentajeHC", calcularPorcentaje(proporcionHC, total));
//		model.addObject("porcentajeEnergia", calcularPorcentaje(valoresProximales.get("energia"), total));
		model.addObject("porcentajeOtrosHC", calcularPorcentaje(proporcionValorOtrosHC, total));
		model.addObject("porcentajeFibra", calcularPorcentaje(proporcionValorFibra, total));
		model.addObject("porcentajeAzucar", calcularPorcentaje(proporcionValorAzúcar, total));
		model.addObject("porcentajeAcGrasos", calcularPorcentaje(proporcionAcGrasos, total));

		model.addObject("porcentajeOtrasGrasas",
				round(calcularPorcentaje(proporcionGrasas, total) - calcularPorcentaje(proporcionAcGrasos, total), 2));


	}
	
	@GetMapping({ "/client/componentes_menu_individual/{id_menu}" })
	public ModelAndView componentesMenuIndividualAdmin(@PathVariable("id_menu") int id_menu) {

		ModelAndView model = new ModelAndView("componentes_plato");

		model.addObject("menuName", menuRepo.findById(id_menu).get().getNombre_menu());
		obtenerComponentesMenuIndividual(id_menu, model);
		return model;
	}

	public List<ComponentsDishTable> obtenerComponentesMenu(int id_menu) {
		List<ComponentsDishTable> listListComponentsDish = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> listComponentsDish;
		Map<String, Double> mapComponentsMenu = new HashMap<String, Double>();

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
							Double oldAmount = mapComponentsMenu.get(listComponentsDish.get(i).getNameComponent());
							Double newAmount = listComponentsDish.get(i).getAmount();
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
					Double valor = rs2.getDouble(3);
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

			Map<String, Double> mapComponentsDish2 = new HashMap<String, Double>();

			for (var ingrediente : mapComponentsDish.entrySet()) {

				ingrediente.getKey();
				List<ComponentsFood> listaCompon = ingrediente.getValue();

				for (int i = 0; i < listaCompon.size(); i++) {
					if (mapComponentsDish2.get(listaCompon.get(i).getC_ori_name()) == null) {

						Double amount = listaCompon.get(i).getBest_location();

						String proportion = listaCompon.get(i).getDescripcion();
						Double propor = null;
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
						mapComponentsDish2.put(listaCompon.get(i).getC_ori_name(),
								round(propor * quantity.floatValue(), 2));

					} else {
						Double value = mapComponentsDish2.get(listaCompon.get(i).getC_ori_name());
						Double amount = listaCompon.get(i).getBest_location();

						String proportion = listaCompon.get(i).getDescripcion();
						Double propor = null;
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
						Double sum = value + propor * quantity.floatValue();
						mapComponentsDish2.replace(listaCompon.get(i).getC_ori_name(), round(sum, 2));

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
	@RequestMapping("/client/escoger_platos_menu_grupal_componentes/{id_menu}/{id_local}")
	public ModelAndView escogerPlatosMenuGrupalComponentes_user(@PathVariable("id_menu") int id_menu,
			@PathVariable("id_local") int id_local) {

		ModelAndView model = new ModelAndView("escoger_platos_menu_grupal_componentes_user");

		model.addObject("path2", path2);
//		String path = request.getServletPath();
		model.addObject("path", path);
		model.addObject("date", dateCheck);
		model.addObject("id_local", id_local);
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

	@RequestMapping("/client/componentes_menu_colectivo/{id_menu}/{id_local}/{date}")
	public ModelAndView componentesMenuColectivo_user(MenuObj menuObj, HttpSession session, HttpServletRequest request,
			@PathVariable("id_menu") int id_menu, @PathVariable("id_local") int id_local,
			@PathVariable("date") String date) {

		ModelAndView model = new ModelAndView("componentes_plato");
		model.addObject("menuName", menuRepo.findById(id_menu).get().getNombre_menu());
		model.addObject("id_menu", id_menu);
		obtenerComponentesMenucolectivo(menuObj, model);
		return model;
	}

	public void obtenerComponentesMenucolectivo(MenuObj menuObj, ModelAndView model) {
		List<Integer> listDishes = new ArrayList<Integer>();

		if (menuObj.getFirst_dish() != null) {
			listDishes.add(menuObj.getFirst_dish());

		}
		if (menuObj.getSecond_dish() != null) {
			listDishes.add(menuObj.getSecond_dish());

		}
		if (menuObj.getThird_dish() != null) {
			listDishes.add(menuObj.getThird_dish());
		}

		Map<String, List<ComponentsDishTable>> mapComponents = clasificarComponentes(
				obtenerComponentesMenuGrupal(listDishes));
		Map<String, Double> valoresProximales = calculoProximales(mapComponents);
		Map<String, Double> valoresHC = calculoHC(mapComponents);

		Double hcTotales = valoresHC.get("fibra") + valoresHC.get("azucar") + valoresHC.get("otrosHC");
		Double acGrasos = calcularAcGrasos(mapComponents);

		Double proporcionGrasas = valoresProximales.get("grasa") * 9;
		Double proporcionProteinas = valoresProximales.get("proteina") * 4;
		Double proporcionHC = hcTotales * 4;
		Double proporcionValorOtrosHC = valoresHC.get("otrosHC") * 4;

		Double proporcionValorFibra = valoresHC.get("fibra") * 4;
		Double proporcionValorAzúcar = valoresHC.get("azucar") * 4;
		Double proporcionAcGrasos = acGrasos * 9;

		Double total = /* valoresProximales.get("energia") + */ proporcionGrasas + proporcionProteinas + proporcionHC;

		List<ComponentsDishTable> listComponentsDishOrdered = ordenarPorUnidad(mapComponents);

//		Cargo datos de tabla completa
		model.addObject("listComponentsDish", listComponentsDishOrdered);

//		Cargo datos de tablas 
		model.addObject("componentsDishTableProximal", mapComponents.get("proximales"));
		model.addObject("componentsDishTableHcarbono", mapComponents.get("hc"));
		model.addObject("componentsDishTableGrasa", mapComponents.get("grasas"));
		model.addObject("componentsDishTableVitaminas", mapComponents.get("vitaminas"));
		model.addObject("componentsDishTableMinerales", mapComponents.get("minerales"));

//	Cargo datos del grafico
		model.addObject("porcentajeGrasa", calcularPorcentaje(proporcionGrasas, total));
		model.addObject("porcentajeProteinas", calcularPorcentaje(proporcionProteinas, total));
		model.addObject("porcentajeHC", calcularPorcentaje(proporcionHC, total));
//		model.addObject("porcentajeEnergia", calcularPorcentaje(valoresProximales.get("energia"), total));
		model.addObject("porcentajeOtrosHC", calcularPorcentaje(proporcionValorOtrosHC, total));
		model.addObject("porcentajeFibra", calcularPorcentaje(proporcionValorFibra, total));
		model.addObject("porcentajeAzucar", calcularPorcentaje(proporcionValorAzúcar, total));
		model.addObject("porcentajeAcGrasos", calcularPorcentaje(proporcionAcGrasos, total));

		model.addObject("porcentajeOtrasGrasas",
				round(calcularPorcentaje(proporcionGrasas, total) - calcularPorcentaje(proporcionAcGrasos, total), 2));

	}

	public Map<String, Double> calculoProximales(Map<String, List<ComponentsDishTable>> mapComponents) {

		Map<String, Double> valoresProximales = new HashMap<String, Double>();

		Double valorProteína = Double.valueOf(0);
		Double valorEnergético = Double.valueOf(0);
		Double valorGrasa = Double.valueOf(0);

		for (ComponentsDishTable componentsDishTable : mapComponents.get("proximales")) {

			if (componentsDishTable.getNameComponent().equals("proteina, total")) {
				valorProteína += componentsDishTable.getAmount();

			} else if (componentsDishTable.getNameComponent().equals("energía, total")) {
				valorEnergético += componentsDishTable.getAmount();

			} else if (componentsDishTable.getNameComponent().equals("grasa, total (lipidos totales)")) {
				valorGrasa += componentsDishTable.getAmount();
			}
		}
		valoresProximales.put("proteina", valorProteína);
		valoresProximales.put("energia", valorEnergético);
		valoresProximales.put("grasa", valorGrasa);

		return valoresProximales;
	}

	public Map<String, Double> calculoHC(Map<String, List<ComponentsDishTable>> mapComponents) {
		Map<String, Double> valoresHC = new HashMap<String, Double>();

		Double valorOtrosHC = Double.valueOf(0);
		Double valorFibra = Double.valueOf(0);
		Double valorAzúcar = Double.valueOf(0);

		for (ComponentsDishTable componentsDishTable : mapComponents.get("hc")) {

			if (componentsDishTable.getNameComponent().equals("fibra, dietetica total")) {
				valorFibra += componentsDishTable.getAmount();

			} else if (componentsDishTable.getNameComponent().equals("azucares, totales")) {
				valorAzúcar += componentsDishTable.getAmount();
			} else {
				valorOtrosHC += componentsDishTable.getAmount();
			}
		}

		valoresHC.put("fibra", valorFibra);
		valoresHC.put("azucar", valorAzúcar);
		valoresHC.put("otrosHC", valorOtrosHC);

		return valoresHC;
	}

	public Double calcularAcGrasos(Map<String, List<ComponentsDishTable>> mapComponents) {
		Double acGrasos = Double.valueOf(0);
		for (ComponentsDishTable componentsDishTable : mapComponents.get("grasas")) {

			if (componentsDishTable.getNameComponent().contains("ácidos grasos")) {
				acGrasos += componentsDishTable.getAmount();

			}
		}
		return acGrasos;
	}

	public List<ComponentsDishTable> ordenarComponentesPorUnidadCantidad(
			List<ComponentsDishTable> componentsDishTableVitaminas) {

		List<ComponentsDishTable> listComponentsDishTable = new ArrayList<ComponentsDishTable>();

		List<ComponentsDishTable> componentsDishTableKcal = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableG = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableMG = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableUG = new ArrayList<ComponentsDishTable>();

		for (ComponentsDishTable componentsDishTable : componentsDishTableVitaminas) {
			if (componentsDishTable.getUnit().equals("kcal") || componentsDishTable.getUnit().equals("kj(kcal)")) {
				componentsDishTableKcal.add(componentsDishTable);

			} else if (componentsDishTable.getUnit().equals("g")) {
				componentsDishTableG.add(componentsDishTable);

			} else if (componentsDishTable.getUnit().equals("mg")) {
				componentsDishTableMG.add(componentsDishTable);

			} else if (componentsDishTable.getUnit().equals("ug")) {
				componentsDishTableUG.add(componentsDishTable);

			}
		}
		listComponentsDishTable.addAll(componentsDishTableKcal);
		listComponentsDishTable.addAll(componentsDishTableG);
		listComponentsDishTable.addAll(componentsDishTableMG);
		listComponentsDishTable.addAll(componentsDishTableUG);

		return listComponentsDishTable;

	}

	public List<ComponentsDishTable> ordenarPorUnidad(Map<String, List<ComponentsDishTable>> mapComponents) {
		List<ComponentsDishTable> listComponentsDishOrdered = new ArrayList<ComponentsDishTable>();

		listComponentsDishOrdered.addAll(ordenarComponentesPorUnidadCantidad(mapComponents.get("proximales")));
		listComponentsDishOrdered.addAll(ordenarComponentesPorUnidadCantidad(mapComponents.get("hc")));
		listComponentsDishOrdered.addAll(ordenarComponentesPorUnidadCantidad(mapComponents.get("grasas")));
		listComponentsDishOrdered.addAll(ordenarComponentesPorUnidadCantidad(mapComponents.get("vitaminas")));
		listComponentsDishOrdered.addAll(ordenarComponentesPorUnidadCantidad(mapComponents.get("minerales")));

		return listComponentsDishOrdered;
	}

	public double calcularPorcentaje(double proporcionGrasas, Double total) {
		double porcentajeGrasa2 = proporcionGrasas / total;
		return Math.round(porcentajeGrasa2 * 100 * 100) / 100.0;
	}

	public List<Integer> obtenerPlatosMenu(int id_menu) {
		List<Integer> listDishes = new ArrayList<Integer>();
		ResultSet rs;
		try {
			Statement st = Application.con.createStatement();
			rs = st.executeQuery("select idPlato from menus_platos where idMenu = " + id_menu + ";");

			while (rs.next()) {
				listDishes.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listDishes;
	}

	public List<ComponentsDishTable> obtenerComponentesMenuGrupal(List<Integer> listDishes) {
		List<ComponentsDishTable> listComponentsDishAux = new ArrayList<ComponentsDishTable>();

		for (int i = 0; i < listDishes.size(); i++) {
			if (listDishes.get(i) != 0) {

				List<ComponentsDishTable> listComponentsDish = obtenerBDcomponentesPlato(listDishes.get(i));
				for (int j = 0; j < listComponentsDish.size(); j++) {

					boolean flag = false;
					for (int k = 0; k < listComponentsDishAux.size(); k++) {
						if (listComponentsDishAux.get(k).getNameComponent()
								.equals(listComponentsDish.get(j).getNameComponent())) {
							flag = true;
							listComponentsDishAux.get(k).setAmount(round(
									listComponentsDishAux.get(k).getAmount() + listComponentsDish.get(j).getAmount(),
									2));
						}
					}
					if (!flag) {
						listComponentsDishAux.add(listComponentsDish.get(j));

					}
					flag = false;

				}
			}

		}
		return listComponentsDishAux;
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public Map<String, List<ComponentsDishTable>> clasificarComponentes(List<ComponentsDishTable> listComponentsDish) {

		Map<String, List<ComponentsDishTable>> listOrderedComponents = new HashMap<String, List<ComponentsDishTable>>();

		List<ComponentsDishTable> componentsDishTableProximal = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableHcarbono = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableGrasa = new ArrayList<ComponentsDishTable>();

		List<ComponentsDishTable> componentsDishTableVitaminas = new ArrayList<ComponentsDishTable>();
		List<ComponentsDishTable> componentsDishTableMinerales = new ArrayList<ComponentsDishTable>();

		for (int i = 0; i < listComponentsDish.size(); i++) {
			ComponentsDishTable componentsDishTable = listComponentsDish.get(i);

			if (componentsDishTable.getGroupComponent().equals("Proximales")) {
				componentsDishTableProximal.add(componentsDishTable);

			} else if (componentsDishTable.getGroupComponent().equals("Hidratos de Carbono")) {
				componentsDishTableHcarbono.add(componentsDishTable);

			} else if (componentsDishTable.getGroupComponent().equals("Grasas")) {
				componentsDishTableGrasa.add(componentsDishTable);

			} else if (componentsDishTable.getGroupComponent().equals("Vitaminas")) {
				componentsDishTableVitaminas.add(componentsDishTable);

			} else if (componentsDishTable.getGroupComponent().equals("Minerales")) {
				componentsDishTableMinerales.add(componentsDishTable);

			}
		}
		listOrderedComponents.put("minerales", componentsDishTableMinerales);
		listOrderedComponents.put("vitaminas", componentsDishTableVitaminas);
		listOrderedComponents.put("grasas", componentsDishTableGrasa);
		listOrderedComponents.put("hc", componentsDishTableHcarbono);
		listOrderedComponents.put("proximales", componentsDishTableProximal);

		return listOrderedComponents;
	}

}
