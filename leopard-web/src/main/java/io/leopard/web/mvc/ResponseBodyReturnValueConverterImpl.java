package io.leopard.web.mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ResponseBodyReturnValueConverterImpl implements ResponseBodyReturnValueConverter{

	private final static ResponseBodyReturnValueConverter instance = new ResponseBodyReturnValueConverterImpl();

	public static ResponseBodyReturnValueConverter getInstance() {
		return instance;
	}

	private List<ResponseBodyReturnValueConverter> list = new ArrayList<ResponseBodyReturnValueConverter>();

	public ResponseBodyReturnValueConverterImpl() {
		Iterator<ResponseBodyReturnValueConverter> iterator = ServiceLoader.load(ResponseBodyReturnValueConverter.class).iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
	}

	@Override
	public Object convert(Object data) {
		for (ResponseBodyReturnValueConverter converter: list) {
			data = converter.convert(data);
		}
		return data;
	}
}
