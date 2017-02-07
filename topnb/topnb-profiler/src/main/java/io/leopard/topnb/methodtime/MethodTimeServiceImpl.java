package io.leopard.topnb.methodtime;

import io.leopard.topnb.web.data.CountDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MethodTimeServiceImpl implements MethodTimeService {

	private EntranceData allData = new EntranceData();
	private EntranceData slowData = new EntranceData();

	@Override
	public boolean add(String methodName, long time) {
		time = time / 1000;
		double millis = (double) time / 1000D;

		allData.add(methodName, millis, 1);
		if (millis > 50) {
			slowData.add(methodName, millis, 1);
		}
		return true;
	}

	@Override
	public List<MethodDto> listAll(String entryName) {
		Map<String, CountDto> allBaseData = allData.getBaseData(entryName).getData();
		Map<String, CountDto> slowBaseData = slowData.getBaseData(entryName).getData();

		Iterator<CountDto> iterator = allBaseData.values().iterator();

		List<MethodDto> methodList = new ArrayList<MethodDto>();
		while (iterator.hasNext()) {
			CountDto allCountDto = iterator.next();
			CountDto slowCountDto = slowBaseData.get(allCountDto.getName());

			MethodDto methodDto = new MethodDto();
			methodDto.setName(allCountDto.getName());
			methodDto.setAllCount(allCountDto.getCount());
			methodDto.setAllTime(allCountDto.getTime());

			if (slowCountDto != null) {
				methodDto.setSlowCount(slowCountDto.getCount());
				methodDto.setSlowTime(slowCountDto.getTime());
			}

			methodList.add(methodDto);
		}
		return methodList;

		// List<CountDto> performanceList = allData.getBaseData(entryName).listAll();
		// this.filterIgnoreMethod(performanceList);
		// return performanceList;
	}

	private static final Set<String> IGNORE_METHOD = new HashSet<String>();
	static {
		IGNORE_METHOD.add("io.leopard.web.userinfo.service.UserinfoServiceImpl");
		IGNORE_METHOD.add("io.leopard.web.admin.dao.AdminLoginServiceImpl");
	}

	protected void filterIgnoreMethod(List<CountDto> performanceList) {
		if (performanceList == null) {
			return;
		}
		Iterator<CountDto> iterator = performanceList.iterator();
		while (iterator.hasNext()) {
			CountDto performance = iterator.next();
			String methodName = performance.getName();
			if (this.isIgnoreMethod(methodName)) {
				iterator.remove();
			}
		}
	}

	protected boolean isIgnoreMethod(String methodName) {
		for (String method : IGNORE_METHOD) {
			if (methodName.startsWith(method)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long getCount(String entryName) {
		return allData.getCount(entryName);
	}

}
