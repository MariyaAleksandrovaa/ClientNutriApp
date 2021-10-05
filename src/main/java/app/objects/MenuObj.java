package app.objects;

import java.util.ArrayList;
import java.util.List;

public class MenuObj {

	public String name_menu;
	public Integer first_dish;
	public Integer second_dish;
	public Integer third_dish;
	public List<Integer> list_id_local;
	public String date_publish;


	public MenuObj(String name_menu, Integer first_dish, Integer second_dish, Integer third_dish,
			ArrayList<Integer> list_id_local, String date_publish) {
		this.name_menu = name_menu;
		this.first_dish = first_dish;
		this.second_dish = second_dish;
		this.third_dish = third_dish;
		this.list_id_local = list_id_local;
		this.date_publish = date_publish;
	}

	public MenuObj() {

	}

	public String getDate_publish() {
		return date_publish;
	}

	public void setDate_publish(String date_publish) {
		this.date_publish = date_publish;
	}

	public List<Integer> getList_id_local() {
		return list_id_local;
	}

	public void setList_id_local(List<Integer> list_id_local) {
		this.list_id_local = list_id_local;
	}

	public String getName_menu() {
		return name_menu;
	}

	public void setName_menu(String name_menu) {
		this.name_menu = name_menu;
	}

	public Integer getFirst_dish() {
		return first_dish;
	}

	public void setFirst_dish(Integer first_dish) {
		this.first_dish = first_dish;
	}

	public Integer getSecond_dish() {
		return second_dish;
	}

	public void setSecond_dish(Integer second_dish) {
		this.second_dish = second_dish;
	}

	public Integer getThird_dish() {
		return third_dish;
	}

	public void setThird_dish(Integer third_dish) {
		this.third_dish = third_dish;
	}

}
