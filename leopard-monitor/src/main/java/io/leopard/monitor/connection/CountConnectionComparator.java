package io.leopard.monitor.connection;

import java.util.Comparator;

public class CountConnectionComparator implements Comparator<ConnectionInfo> {

	@Override
	public int compare(ConnectionInfo o1, ConnectionInfo o2) {
		double count = o2.getConnectionCount() - o1.getConnectionCount();
		if (count > 0) {
			return 1;
		}
		else if (count == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}

}
