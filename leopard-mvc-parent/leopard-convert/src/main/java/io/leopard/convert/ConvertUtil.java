package io.leopard.convert;

import io.leopard.lang.Paging;

import java.util.List;

public class ConvertUtil {

	public static <S, T> T bean(S source, Class<T> clazz) {
		return new BeanConvert<S, T>(source, clazz).convert();
	}

	public static <S, T> List<T> list(List<S> list, Class<T> clazz) {
		return new ListConvert<S, T>(list, clazz).convert();
	}

	public static <S, T> Paging<T> paging(Paging<S> paging, Class<T> clazz) {
		return new PagingConvert<S, T>(paging, clazz).convert();
	}
}
