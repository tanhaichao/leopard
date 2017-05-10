package io.leopard.security.admin.menu;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 菜单
 * 
 * @author 谭海潮
 *
 */
public class Menu {
	// {
	// "text": "Main Navigation",
	// "heading": "true",
	// "translate": "sidebar.heading.HEADER"
	// },
	// {
	// "text": "Dashboard",
	// "sref": "app.dashboard",
	// "icon": "icon-speedometer",
	// "alert": "2" ,
	// "label": "label label-info",
	//
	// "translate": "sidebar.nav.DASHBOARD"
	// },

	private String text;

	private Boolean heading;

	private String translate;

	private String sref;

	private String icon;

	private String alert;

	private String label;

	@JsonProperty("submenu")
	private List<Submenu> submenuList;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getHeading() {
		return heading;
	}

	public void setHeading(Boolean heading) {
		this.heading = heading;
	}

	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	public String getSref() {
		return sref;
	}

	public void setSref(String sref) {
		this.sref = sref;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Submenu> getSubmenuList() {
		return submenuList;
	}

	public void setSubmenuList(List<Submenu> submenuList) {
		this.submenuList = submenuList;
	}

	public Submenu addSubmenu(String text, String sref) {
		if (this.submenuList == null) {
			this.submenuList = new ArrayList<Submenu>();
		}
		Submenu submenu = new Submenu();
		submenu.setText(text);
		submenu.setSref(sref);
		submenuList.add(submenu);
		return submenu;
	}

}
