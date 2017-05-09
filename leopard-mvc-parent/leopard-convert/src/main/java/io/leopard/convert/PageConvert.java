package io.leopard.convert;

import io.leopard.lang.Page;
import io.leopard.lang.PageImpl;

public class PageConvert<S, T> {

	private Page<S> page;

	private Page<T> result;

	// private Class<T> clazz;

	public PageConvert(Page<S> page, Class<T> clazz) {
		this.page = page;
		if (page != null) {
			result = new PageImpl<T>(page);
			for (S source : page.getList()) {
				String json = ConvertJson.toJson(source);
				// System.err.println("json:" + json);
				T target = ConvertJson.toObject(json, clazz);

				try {
					this.fill(source, target);
				}
				catch (Exception e) {
					throw new ConvertException(e.getMessage(), e);
				}
				FillerContext.fill(source, target);
				result.add(target);
			}
		}
	}

	protected void fill(S source, T target) throws Exception {

	}

	public Page<T> get() {
		return this.result;
	}

	public Page<T> convert() {
		if (this.result == null || result.getList().isEmpty()) {
			return this.result;
		}
		int index = 0;
		for (T bean : result.getList()) {
			S source = this.page.get(index);
			ConverterContext.convert(bean, source);
			index++;
		}
		return this.result;
	}

}
