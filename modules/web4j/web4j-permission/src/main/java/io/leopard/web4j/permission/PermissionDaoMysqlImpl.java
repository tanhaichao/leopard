package io.leopard.web4j.permission;

import io.leopard.burrow.util.ListUtil;
import io.leopard.jdbc.CreateTableUtil;
import io.leopard.jdbc.CreateTableUtil.GetSql;
import io.leopard.jdbc.Jdbc;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoMysqlImpl implements PermissionDao {

	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean add(Permission permission) {

		return false;
	}

	@PostConstruct
	public void init() {
		createTable();
	}

	@Override
	public Permission get(PermissionKey permissionKey) {
		// String sql = "select * from permission where uri=? and ip=?";

		// String ip = permissionKey.getIp();
		// return jdbc.query(sql, Permission.class, uri, ip);
		String uri = permissionKey.getUri();
		String ip = permissionKey.getIp();
		List<Permission> permissionList = this.list(ip);
		permissionList = ListUtil.defaultList(permissionList);
		for (Permission permission : permissionList) {
			if (uri.startsWith(permission.getUri())) {
				return permission;
			}
		}
		return null;
	}

	protected List<Permission> list(String ip) {
		String sql = "select * from permission where ip=?";
		return jdbc.queryForList(sql, Permission.class, ip);
	}

	protected void createTable() {
		CreateTableUtil.createTable(jdbc, "permission", new GetSql() {
			@Override
			public String getSql() {
				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE  if not exists `permission` (");
				sb.append("`uri` varchar(100) NOT NULL DEFAULT '',");
				sb.append("`ip` varchar(23) NOT NULL DEFAULT '',");
				sb.append("`username` varchar(50) NOT NULL DEFAULT '',");
				sb.append("`posttime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',");
				sb.append("`content` text,");
				sb.append("PRIMARY KEY (`uri`,`ip`),");
				sb.append("KEY `ip_idx` (`ip`)");
				sb.append(");");
				return sb.toString();
			}

		});
	}

}
