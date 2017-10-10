package io.leopard.jdbc.oracle;

import java.util.List;

import io.leopard.jdbc.oracle.model.UserTable;
import io.leopard.jdbc.oracle.model.UserTableComment;

public interface OracleManager {

	List<UserTable> listUserTables();

	List<UserTableComment> listUserTableComments();

}
