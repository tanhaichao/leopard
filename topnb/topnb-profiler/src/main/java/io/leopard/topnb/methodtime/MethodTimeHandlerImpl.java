package io.leopard.topnb.methodtime;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.methodtime.MethodTimeComparator.DefaultMethodComparator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MethodTimeHandlerImpl implements MethodTimeHandler {
	protected Log logger = LogFactory.getLog(this.getClass());
	private MethodTimeService methodTimeService = TopnbBeanFactory.getMethodTimeService();
	private ModuleService moduleService = TopnbBeanFactory.getModuleService();

	@Override
	public List<MethodDto> list(String entryName) {
		List<MethodDto> methodDtoList = methodTimeService.listAll(entryName);
		if (methodDtoList == null || methodDtoList.size() == 0) {
			return new ArrayList<MethodDto>();
		}

		List<MethodDto> performanceVOList = new ArrayList<MethodDto>();
		for (MethodDto methodDto : methodDtoList) {
			performanceVOList.add(this.toMethodVO(methodDto));
		}
		this.ratio(performanceVOList, entryName);

		Collections.sort(performanceVOList, new DefaultMethodComparator());// 默认排序,先按分类排序，再按总次数排序.
		// Collections.sort(performanceVOList, new AvgTimeComparator());//
		// 默认排序,先按分类排序，再按总次数排序.

		return performanceVOList;
	}

	/**
	 * 获取各接口的总耗时.
	 * 
	 * @param performanceVOList
	 * @return
	 */
	protected Map<String, Double> getInterfaceTotalTime(List<MethodDto> performanceVOList) {
		Map<String, Double> map = new HashMap<String, Double>();
		for (MethodDto performanceVO : performanceVOList) {
			String simpleClassName = performanceVO.getSimpleClassName();

			if (simpleClassName.endsWith("CacheImpl")) {
				continue;
			}
			String interfaceName = performanceVO.getInterfaceName();
			Double totalTime = map.get(interfaceName);
			if (totalTime == null) {
				totalTime = 0D;
			}
			totalTime += performanceVO.getAllTime();
			map.put(interfaceName, totalTime);
		}
		return map;
	}

	/**
	 * 获取各模块的总耗时.
	 * 
	 * @param performanceVOList
	 * @return
	 */
	protected double getAllTotalTime(List<MethodDto> performanceVOList, String threadName) {
		double totalTime = 0;
		if (threadName != null && threadName.length() > 0) {
			totalTime = this.getAllTotalTimeByEntry(performanceVOList);
			if (totalTime > -1) {
				return totalTime;
			}
			else {
				totalTime = 0;
			}
		}

		for (MethodDto performanceVO : performanceVOList) {
			totalTime += performanceVO.getAllTime();
		}
		return totalTime;
	}

	protected double getAllTotalTimeByEntry(List<MethodDto> performanceVOList) {
		for (MethodDto performanceVO : performanceVOList) {
			if (this.isController(performanceVO.getName())) {
				return performanceVO.getAllTime();
			}
		}
		return -1;

	}

	/**
	 * 计算耗时比率.
	 * 
	 * @param performanceVOList
	 */
	protected void ratio(List<MethodDto> performanceVOList, String entryName) {
		double allTotalTime = this.getAllTotalTime(performanceVOList, entryName);
		Map<String, Double> interfaceTotalTimeMap = this.getInterfaceTotalTime(performanceVOList);
		for (MethodDto performanceVO : performanceVOList) {
			String interfaceName = performanceVO.getInterfaceName();
			Double interfaceTotalTime = interfaceTotalTimeMap.get(interfaceName);

			double interfaceRatio = this.percent(performanceVO.getAllTime(), interfaceTotalTime);
			double timeRatio = this.percent(performanceVO.getAllTime(), allTotalTime);

			performanceVO.setInterfaceRatio(interfaceRatio);
			performanceVO.setTimeRatio(timeRatio);
		}
	}

	protected double percent(double current, Double total) {
		if (current == 0) {
			return 0;
		}
		if (total == null || total == 0) {
			return 0;
		}
		double avg = current / total;
		double percent = (avg * 100);
		String percent2 = new DecimalFormat(".0").format(percent);
		return Double.parseDouble(percent2);
	}

	/**
	 * 每秒平均值.
	 * 
	 * @param count
	 *            总数
	 * @param time
	 *            秒数
	 * @return 平均值
	 */
	public static long perSecondAvg(long count, long time) {
		if (time <= 0) {
			return 0;
		}
		return count * 1000 / time;
	}

	protected MethodDto toMethodVO(MethodDto methodDto) {
		String longMethodName = methodDto.getName();

		String categoryName = moduleService.getModuleName(longMethodName);
		String interfaceName = ProfilerUtil.getInterfaceName(longMethodName);
		String simpleMethodName = ProfilerUtil.parseSimpleMethodName(longMethodName);
		String simpleClassName = ProfilerUtil.parseSimpleClassName(longMethodName);

		methodDto.setSimpleMethodName(simpleMethodName);
		methodDto.setSimpleClassName(simpleClassName);

		methodDto.setCategoryName(categoryName);
		methodDto.setInterfaceName(interfaceName);

		return methodDto;
	}

	private boolean isController(String longMethodName) {
		String className = ProfilerUtil.parseClassName(longMethodName);
		if (className.endsWith("Controller")) {
			if (!className.endsWith("ErrorController")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTypeName(String entryName) {
		if (entryName == null || entryName.length() == 0) {
			entryName = "全部入口";
		}
		return entryName;
	}
}
