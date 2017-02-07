package io.leopard.myjetty.webapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DirectoryDaoImpl implements DirectoryDao {

	@Override
	public boolean add(Directory directory) {
		// String projectId = directory.getProjectId();

		StringBuilder sb = new StringBuilder();
		for (String host : directory.getHostList()) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(host);
		}
		String data = directory.getWar() + "\n" + sb.toString();
		try {
			FileUtils.writeStringToFile(getFile(directory.getWar()), data);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return true;
	}

	protected File getFile(String war) {
		return new File("/tmp/webappDir_" + war.hashCode());
	}

	@Override
	public Directory get(String war) {
		String data;
		try {
			data = FileUtils.readFileToString(getFile(war));
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String[] lines = data.split("\n");
		Directory directory = new Directory();
		directory.setWar(lines[0].trim());
		List<String> hostList = new ArrayList<String>();
		for (String host : lines[1].trim().split(",")) {
			hostList.add(host);
		}
		directory.setHostList(hostList);
		return directory;
	}

}
