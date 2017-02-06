package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.InsertParser;

import java.util.LinkedHashSet;

import org.junit.Assert;
import org.junit.Test;

public class InsertParserTest {

	@Test
	public void parseParams() {
		// AUTO
	}

	@Test
	public void getFields() {
		InsertParser parser = new InsertParser("insert into table(username,nickname) values(?,?);");
		LinkedHashSet<String> set = parser.getFields();
		System.out.println("set:" + set);
		Assert.assertEquals("[username, nickname]", set.toString());
	}

}