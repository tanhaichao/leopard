package io.leopard.data4j.log;

public interface LogRollOver {

	String getFilename();

	void autoRollOver() throws Exception;
}
