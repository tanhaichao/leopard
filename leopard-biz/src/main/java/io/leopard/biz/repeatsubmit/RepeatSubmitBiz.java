package io.leopard.biz.repeatsubmit;

import io.leopard.data4j.cache.api.IGet;

public interface RepeatSubmitBiz extends IGet<RepeatSubmit, String> {
	@Override
	boolean add(RepeatSubmit repeatSubmit);

	@Override
	RepeatSubmit get(String md5);

	void checkSubmit(String md5);

}
