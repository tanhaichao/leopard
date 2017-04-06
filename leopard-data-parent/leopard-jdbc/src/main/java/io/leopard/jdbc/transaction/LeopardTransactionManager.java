package io.leopard.jdbc.transaction;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class LeopardTransactionManager extends DataSourceTransactionManager {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		logger.info("doCommit:" + status);
		super.doCommit(status);
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status) {
		logger.info("doRollback:" + status);
		super.doRollback(status);
	}

}
