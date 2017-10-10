package io.leopard.jdbc.oracle;

import java.util.List;

import io.leopard.jdbc.oracle.model.UserTableComment;

public interface OracleManager {

	List<UserTableComment> listUserTableComments();

}
