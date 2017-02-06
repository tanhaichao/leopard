package io.leopard.treedata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TreeDataManagerImpl implements TreeDataManager {

	@Override
	public List<Children> parse(String path) throws IOException {
		List<Row> rows = this.load(path);
		List<Children> list = new ArrayList<Children>();
		for (Row row : rows) {
			if (row.getFloor() == 0) {
				Children children = new Children(row.getData(0), row.getData(1), row.getLine());
				list.add(children);
			}
		}

		int preFloor = 0;
		Children preChildren = null;
		Map<Integer, Children> parentMap = new HashMap<Integer, Children>();

		int index = 0;// 大类的位置
		for (Row row : rows) {
			if (row.getFloor() <= 0) {
				parentMap.clear();
				preChildren = list.get(index);
				parentMap.put(0, preChildren);
				index++;
				continue;
			}

			System.out.println("row:" + row.getLine());
			String name = row.getData(0);
			String colour = row.getData(1);
			String line = row.getLine();

			int floor = row.getFloor();
			int diff = row.getFloor() - preFloor;
			preFloor = row.getFloor();
			if (diff > 0) {
				// 下一级
				Children c = preChildren.add(name, colour, line);
				// System.err.println("下 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				parentMap.put(floor, c);
				preChildren = c;
			}
			else if (diff < 0) {
				// 上一级
				Children c = parentMap.get(floor - 1);
				// System.err.println("上 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				preChildren = c.add(name, colour, line);
				parentMap.put(floor, preChildren);
			}
			else {
				// 同级
				// System.out.println("floor - 1:" + (floor - 1));
				Children c = parentMap.get(floor - 1);
				Children current = c.add(name, colour, line);
				parentMap.put(floor, current);
				// System.err.println("同 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				preChildren = current;
			}
		}
		return list;
	}

	@Override
	public List<Row> load(String path) throws IOException {
		Resource resource = new ClassPathResource(path);
		InputStream is = resource.getInputStream();
		String content = IOUtils.toString(is);
		is.close();
		content = content.replace("\r", "");
		String[] lines = content.split("\n");
		List<Row> list = new ArrayList<Row>();
		for (String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			int floor = this.floor(line);
			Row row = new Row(floor, line);
			// System.out.println("floor:" + floor);
			list.add(row);
		}
		return list;
	}

	protected int floor(String line) {
		int floor = 0;
		for (char c : line.toCharArray()) {
			if (c == '\t') {
				floor++;
			}
			else {
				break;
			}
		}
		return floor;
	}
}
