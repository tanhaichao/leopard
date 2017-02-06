package io.leopard.security.admin.menu;

import java.util.List;

import io.leopard.burrow.util.ListUtil;

/**
 * 带角色判断的菜单.
 * 
 * @author 谭海潮
 *
 */
public class RoleMenuList extends MenuList {

	private static final long serialVersionUID = 1L;

	private List<String> roleList;

	public RoleMenuList(List<String> roleList) {
		this.roleList = ListUtil.defaultList(roleList);
		// System.err.println("roleList:" + roleList);
	}

	protected boolean hasRole(Role... roles) {
		if (roles.length == 0) {
			return true;
		}
		for (Role role : roles) {
			String str = role.getRole();
			if (this.roleList.contains(str)) {
				return true;
			}
		}
		return false;
	}

	public void addHeading(String text, Role... roles) {
		if (!hasRole(roles)) {
			return;
		}

		super.addHeading(text);
	}

	public void addMenu(String text, String sref, Role... roles) {
		if (!hasRole(roles)) {
			return;
		}

		super.addMenu(text, sref);
	}
}
