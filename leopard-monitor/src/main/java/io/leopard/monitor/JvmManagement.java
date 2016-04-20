package io.leopard.monitor;

import io.leopard.burrow.util.ListUtil;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class JvmManagement {

	private static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

	@SuppressWarnings("restriction")
	private static com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	private static MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	private static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

	public static int getPeakThreadCount() {
		return threadMXBean.getPeakThreadCount();
	}

	public static long getTotalStartedThreadCount() {
		return threadMXBean.getTotalStartedThreadCount();
	}

	public static long getDaemonThreadCount() {
		return threadMXBean.getTotalStartedThreadCount();
	}

	public static long[] findDeadlockedThreads() {
		return threadMXBean.findDeadlockedThreads();
	}

	public static int findDeadlockedThreadCount() {
		long[] ids = findDeadlockedThreads();
		// if (ids == null) {
		// return 0;
		// }
		// else {
		// return ids.length;
		// }
		return ListUtil.size(ids);
	}

	public static int getThreadCount() {
		return threadMXBean.getThreadCount();
	}

	public static long getMaxHeapMemorySize() {
		return memoryMXBean.getHeapMemoryUsage().getMax();
	}

	/**
	 * 获取堆已使用的内存大小.
	 * 
	 * @return
	 */
	public static long getUsedHeapMemorySize() {
		return memoryMXBean.getHeapMemoryUsage().getUsed();
	}

	public static long getInitHeapMemorySize() {
		return memoryMXBean.getHeapMemoryUsage().getInit();
	}

	public static long getUsedNonHeapMemorySize() {
		return memoryMXBean.getNonHeapMemoryUsage().getUsed();
	}

	public static long getInitNonHeapMemorySize() {
		return memoryMXBean.getNonHeapMemoryUsage().getInit();
	}

	/**
	 * 获取进程ID.
	 * 
	 * @return
	 */
	public static int getProcessId() {
		String name = runtimeMXBean.getName();
		String id = name.split("@")[0];
		return Integer.parseInt(id);
	}

	public static long getStartTime() {
		return runtimeMXBean.getStartTime();
	}

	public static double getSystemLoadAverage() {
		return operatingSystemMXBean.getSystemLoadAverage();
	}

	@SuppressWarnings("restriction")
	public static long getProcessCpuTime() {
		return operatingSystemMXBean.getProcessCpuTime();
	}

	@SuppressWarnings("restriction")
	public static long getFreePhysicalMemorySize() {
		return operatingSystemMXBean.getFreePhysicalMemorySize();
	}

	@SuppressWarnings("restriction")
	public static long getTotalSwapSpaceSize() {
		return operatingSystemMXBean.getTotalSwapSpaceSize();
	}

	@SuppressWarnings("restriction")
	public static long getTotalPhysicalMemorySize() {
		// operatingSystemMXBean.getCommittedVirtualMemorySize()
		return operatingSystemMXBean.getTotalPhysicalMemorySize();
	}

	public static void printMemoryPool() {
		// 获取多个内存池的使用情况
		System.out.println("=======================MemoryPoolMXBean============================ ");
		List<MemoryPoolMXBean> mpmList = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean mpm : mpmList) {
			System.out.println("getUsage " + mpm.getUsage());
			System.out.println("getMemoryManagerNames " + StringUtils.join(mpm.getMemoryManagerNames(), ","));
		}
	}

	public static void printGC() {
		System.out.println("=======================MemoryPoolMXBean============================ ");
		List<GarbageCollectorMXBean> gcmList = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean gcm : gcmList) {
			System.out.println("getName " + gcm.getName() + " count:" + gcm.getCollectionCount() + " time:" + gcm.getCollectionTime());

			// System.out.println("getMemoryPoolNames " + gcm.getMemoryPoolNames());

		}
	}

	public static void printAllInfo() {
		String message = "";
		message += "processId:" + JvmManagement.getProcessId();
		// message += " deadlockedThreadCount:" + JvmManagement.findDeadlockedThreadCount();
		message += " threadCount:" + JvmManagement.getThreadCount();
		message += " heapUsedMemory:" + CapacityUtil.toMega(JvmManagement.getUsedHeapMemorySize());
		message += " heapInitMemory:" + CapacityUtil.toMega(JvmManagement.getInitHeapMemorySize());
		message += " nonHeapUsedMemory:" + CapacityUtil.toMega(JvmManagement.getUsedNonHeapMemorySize());
		message += " nonHeapInitMemory:" + CapacityUtil.toMega(JvmManagement.getInitNonHeapMemorySize());
		message += " freePhysicalMemorySize:" + CapacityUtil.toMega(JvmManagement.getFreePhysicalMemorySize());
		message += " totalSwapSpaceSize:" + CapacityUtil.toMega(JvmManagement.getTotalSwapSpaceSize());
		message += " totalPhysicalMemorySize:" + CapacityUtil.toMega(JvmManagement.getTotalPhysicalMemorySize());

		System.err.println(message);
	}

}
