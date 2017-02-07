package io.leopard.topnb;

import io.leopard.topnb.methodtime.EntryService;
import io.leopard.topnb.methodtime.MethodTimeHandler;
import io.leopard.topnb.methodtime.MethodTimeHandlerImpl;
import io.leopard.topnb.methodtime.MethodTimeService;
import io.leopard.topnb.methodtime.MethodTimeServiceImpl;
import io.leopard.topnb.methodtime.ModuleService;
import io.leopard.topnb.methodtime.ModuleServiceImpl;
import io.leopard.topnb.request.RequestService;
import io.leopard.topnb.thread.ThreadService;
import io.leopard.topnb.thread.ThreadServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TopnbBeanFactory {
	protected static final Log logger = LogFactory.getLog(TopnbBeanFactory.class);

	private static MethodTimeService performanceService;
	private static MethodTimeHandler performanceHandler;
	private static ModuleService moduleService;
	// private static EntranceData entranceData;
	private static ThreadService threadService;
	private static RequestService requestService;
	private static EntryService entryService;

	public static synchronized EntryService getEntryService() {
		if (entryService == null) {
			entryService = new EntryService();
		}
		return entryService;
	}

	public static synchronized RequestService getRequestService() {
		if (requestService == null) {
			requestService = new RequestService();
		}
		return requestService;
	}

	public static synchronized ThreadService getThreadService() {
		if (threadService == null) {
			threadService = new ThreadServiceImpl();
		}
		return threadService;
	}

	public static synchronized MethodTimeHandler getPerformanceHandler() {
		if (performanceHandler == null) {
			performanceHandler = new MethodTimeHandlerImpl();
		}
		return performanceHandler;
	}

	public static synchronized ModuleService getModuleService() {
		if (moduleService == null) {
			moduleService = new ModuleServiceImpl();
		}
		return moduleService;
	}

	// public static synchronized EntranceData getEntranceData() {
	// if (entranceData == null) {
	// entranceData = new EntranceData();
	// }
	// return entranceData;
	// }

	public static synchronized MethodTimeService getMethodTimeService() {
		if (performanceService == null) {
			performanceService = new MethodTimeServiceImpl();
		}
		return performanceService;
	}

}
