package io.leopard.topnb.methodtime;

import java.util.Comparator;

public class MethodTimeComparator {

	protected static double avgtime(long count, double time) {
		if (count <= 0 || time <= 0) {
			return 0;
		}
		double avgtime = time / (double) count;
		return avgtime;
	}

	protected static Comparator<MethodDto> get(String order) {
		if ("allTime".equals(order)) {
			return new AllTimeMethodComparator();
		}
		else if ("allAvgTime".equals(order)) {
			return new AllAvgTimeMethodComparator();
		}
		else if ("allCount".equals(order)) {
			return new AllCountMethodComparator();
		}
		// //
		else if ("slowTime".equals(order)) {
			return new SlowTimeMethodComparator();
		}
		else if ("slowAvgTime".equals(order)) {
			return new SlowAvgTimeMethodComparator();
		}
		else if ("slowCount".equals(order)) {
			return new SlowCountMethodComparator();
		}
		return new DefaultMethodComparator();
	}

	public static class DefaultMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			int order1 = ProfilerUtil.getCategoryOrderByMethodName(o1.getName());
			int order2 = ProfilerUtil.getCategoryOrderByMethodName(o2.getName());
			long order = order2 - order1;
			if (order > 0) {
				return 1;
			}
			else if (order == 0) {
				return byAllTime(o1, o2);
			}
			else {
				return -1;
			}
		}

		/**
		 * 按总次数排序.
		 * 
		 * @param o1
		 * @param o2
		 * @return
		 */
		protected int byAllTime(MethodDto o1, MethodDto o2) {
			double count = o2.getAllTime() - o1.getAllTime();
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

	public static class AllCountMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double count = o2.getAllCount() - o1.getAllCount();
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

	public static class AllAvgTimeMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double avgtime2 = avgtime(o2.getAllCount(), o2.getAllTime());
			double avgtime1 = avgtime(o1.getAllCount(), o1.getAllTime());
			double count = avgtime2 - avgtime1;
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

	public static class AllTimeMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double count = o2.getAllTime() - o1.getAllTime();
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

	// /////////////////

	public static class SlowCountMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double count = o2.getSlowCount() - o1.getSlowCount();
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

	public static class SlowAvgTimeMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double avgtime2 = avgtime(o2.getSlowCount(), o2.getSlowTime());
			double avgtime1 = avgtime(o1.getSlowCount(), o1.getSlowTime());
			double count = avgtime2 - avgtime1;
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

	public static class SlowTimeMethodComparator implements Comparator<MethodDto> {

		@Override
		public int compare(MethodDto o1, MethodDto o2) {
			double count = o2.getSlowTime() - o1.getSlowTime();
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

}
