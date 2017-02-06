package io.leopard.security.admin.version2;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.NullInsertBuilder;
import io.leopard.jdbc.builder.NullUpdateBuilder;
import io.leopard.jdbc.builder.UpdateBuilder;
import io.leopard.lang.Paging;

@Repository
public class AdminDaoMysqlImpl implements AdminDao {

	@Resource
	private Jdbc jdbc;

	@Override
	public boolean add(Admin admin) {
		InsertBuilder builder = new NullInsertBuilder("admin");
		builder.setLong("adminId", admin.getAdminId());
		builder.setString("username", admin.getUsername());
		builder.setString("password", admin.getPassword());
		builder.setList("roles", admin.getRoleList());
		builder.setString("salt", admin.getSalt());
		builder.setString("name", admin.getName());
		builder.setBool("disabled", admin.isDisabled());
		builder.setDate("posttime", admin.getPosttime());
		return jdbc.insertForBoolean(builder);
	}

	@Override
	public Admin get(Long adminId) {
		String sql = "select * from admin where adminId=? limit 1;";
		return jdbc.query(sql, Admin.class, adminId);
	}

	@Override
	public boolean delete(Long adminId, long opuid, Date lmodify) {
		String sql = "delete from admin where adminId=?;";
		return jdbc.updateForBoolean(sql, adminId);
	}

	@Override
	public List<Admin> list(int start, int size) {
		String sql = "select * from admin order by posttime desc limit ?,?;";
		return jdbc.queryForList(sql, Admin.class, start, size);
	}

	@Override
	public Paging<Admin> paging(int start, int size) {
		String sql = "select * from admin order by posttime desc limit ?,?;";
		return jdbc.queryForPaging(sql, Admin.class, start, size);
	}

	@Override
	public boolean update(Admin admin) {
		UpdateBuilder builder = new NullUpdateBuilder("admin");
		builder.setString("username", admin.getUsername());
		builder.setString("password", admin.getPassword());
		builder.setList("roles", admin.getRoleList());
		builder.setString("salt", admin.getSalt());
		builder.setString("name", admin.getName());
		builder.setBool("disabled", admin.isDisabled());
		builder.setDate("posttime", admin.getPosttime());
		// where
		builder.where.setLong("adminId", admin.getAdminId());
		return jdbc.updateForBoolean(builder);
	}

	@Override
	public Admin getByUsername(String username) {
		String sql = "select * from admin where username=? limit 1;";
		return jdbc.query(sql, Admin.class, username);
	}
}