package io.leopard.monitor.connection;

import java.util.Comparator;

public class ConnectionComparatorUtil {

	public static Comparator<ConnectionInfo> getComparator(String order) {
		if ("connectionCount".equals(order)) {
			return new CountConnectionComparator();
		}
		else if ("avgTime".equals(order)) {
			return new AvgTimeConnectionComparator();
		}
		return new TotalTimeConnectionComparator();
	}
}
