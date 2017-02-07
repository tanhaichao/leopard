package io.leopard.topnb.request;

import io.leopard.topnb.web.data.BaseData;
import io.leopard.topnb.web.data.CountDto;

import java.util.ArrayList;
import java.util.List;

public class RequestService {

	private static final long SLOW_TIME = 50;
	private static final long VERYSLOW_TIME = 500;

	private BaseData allData = new BaseData();
	private BaseData slowData = new BaseData();
	private BaseData verySlowData = new BaseData();

	// public RequestService() {
	// new Exception("ok").printStackTrace();
	// }

	private void add(String name, double time) {
		allData.add(name, time, 1);
		if (time > SLOW_TIME) {
			slowData.add(name, time, 1);
		}
		if (time > VERYSLOW_TIME) {
			verySlowData.add(name, time, 1);
		}
		// this.allData.addMinute(name, time, 1);
	}

	public void add(String url, long time) {
		time = time / 1000;
		double millis = (double) time / 1000D;
		this.add(url, millis);
	}

	public List<RequestDto> list() {
		List<CountDto> countList = allData.listAll();
		// System.out.println("countList:" + countList);
		List<RequestDto> list = new ArrayList<RequestDto>();
		for (CountDto countDto : countList) {
			String url = countDto.getName();
			RequestDto requestDto = new RequestDto();
			requestDto.setUrl(url);
			requestDto.setAllCount(countDto.getCount());
			requestDto.setAllTime(countDto.getTime());

			{
				CountDto countDto2 = slowData.get(url);
				if (countDto2 != null) {
					requestDto.setSlowCount(countDto2.getCount());
					requestDto.setSlowTime(countDto2.getTime());
				}
			}
			{
				CountDto countDto2 = verySlowData.get(url);
				if (countDto2 != null) {
					requestDto.setVerySlowCount(countDto2.getCount());
					requestDto.setVerySlowTime(countDto2.getTime());
				}
			}
			// {
			// MinuteCounter minuteCounter = allData.getCounter(url);
			// if (minuteCounter != null) {
			// requestDto.setMaxDate(minuteCounter.);
			// requestDto.setMaxCount(maxCount);
			// requestDto.setMaxTime(maxTime);
			// }
			// }

			list.add(requestDto);
		}
		return list;
	}
}
