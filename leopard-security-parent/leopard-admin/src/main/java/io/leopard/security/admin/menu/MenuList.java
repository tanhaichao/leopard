package io.leopard.security.admin.menu;

import io.leopard.burrow.ArrayList;
import io.leopard.json.Json;

public class MenuList extends ArrayList<Menu> {

	private static final long serialVersionUID = 1L;

	public MenuList() {
		this.add(createMainNavigation());
		this.add(createDashboard());
	}

	public void addHeading(String text) {
		Menu menu = new Menu();
		menu.setText(text);
		menu.setHeading(true);
		this.add(menu);
	}

	public Menu addMenu(String text, String sref) {
		Menu menu = new Menu();
		menu.setText(text);
		menu.setSref(sref);
		menu.setIcon("icon-graduation");
		this.add(menu);
		return menu;
	}

	public String toJson() {
		return Json.toFormatJson(this);
	}

	public static Menu createMainNavigation() {
		Menu menu = new Menu();
		menu.setText("Main Navigation");
		menu.setHeading(true);
		menu.setTranslate("sidebar.heading.HEADER");
		return menu;
	}

	public static Menu createDashboard() {
		Menu menu = new Menu();
		menu.setText("Dashboard");
		menu.setSref("app.dashboard");
		menu.setIcon("icon-speedometer");
		menu.setAlert("2");
		menu.setLabel("label label-info");
		menu.setTranslate("sidebar.nav.DASHBOARD");
		return menu;
	}

}
