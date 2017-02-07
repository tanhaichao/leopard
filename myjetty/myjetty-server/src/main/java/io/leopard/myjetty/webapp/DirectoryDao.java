package io.leopard.myjetty.webapp;

public interface DirectoryDao {

	boolean add(Directory directory);

	Directory get(String projectId);

}
