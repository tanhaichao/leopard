package io.leopard.security.admin.menu;

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

}
