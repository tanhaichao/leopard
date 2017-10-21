package io.leopard.exporter;

import java.lang.reflect.Field;

public interface FieldTypeResolver {

	FieldType resolveType(Field field);

}
