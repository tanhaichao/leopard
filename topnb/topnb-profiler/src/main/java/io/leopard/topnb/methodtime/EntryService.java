package io.leopard.topnb.methodtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.topnb.TopnbBeanFactory;

public class EntryService {

	private static Map<String, String> URI_MAP = Collections.synchronizedMap(new LinkedHashMap<String, String>());

	private static MethodTimeService methodTimeService = TopnbBeanFactory.getMethodTimeService();

	public static void add(String uri) {
		URI_MAP.put(uri, uri);
	}

	public List<String> listAllEntryName() {
		Collection<String> c = URI_MAP.values();
		List<String> entryNameList = new ArrayList<String>();
		entryNameList.addAll(c);

		this.sortEntryName(entryNameList);
		return sub(entryNameList, 10);
	}

	public static <T> List<T> sub(List<T> list, int max) {
		if (list == null || list.size() == 0) {
			return new ArrayList<T>();
		}
		List<T> newList = new ArrayList<T>();
		int count = 0;
		for (T element : list) {
			newList.add(element);
			count++;
			if (count >= max) {
				break;
			}
		}
		return newList;
	}

	protected void sortEntryName(List<String> entryNameList) {
		Collections.sort(entryNameList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				long count1 = methodTimeService.getCount(o1);
				long count2 = methodTimeService.getCount(o2);
				// System.err.println("count1:" + count1 + " count2:" + count2);
				long diff = count2 - count1;
				if (diff > 0) {
					return 1;
				}
				else if (diff < 0) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});
	}
}
