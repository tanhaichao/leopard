package io.leopard.convert;

public class BeanConvert<S, T> {

	private S source;
	private T target;

	private Class<T> clazz;

	// public BeanConvert(S source) {
	// this(source, null);
	// }

	public BeanConvert(S source, Class<T> clazz) {
		this.source = source;
		if (source != null) {
			String json = ConvertJson.toJson(source);
			this.target = ConvertJson.toObject(json, clazz);
			FillerContext.fill(source, target);
		}
	}

	public T get() {
		return this.target;
	}

	public T convert() {
		if (target == null) {
			return this.target;
		}
		ConverterContext.convert(target, this.source);
		return this.target;
	}

}
