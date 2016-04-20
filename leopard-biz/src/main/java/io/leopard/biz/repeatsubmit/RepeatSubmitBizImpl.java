package io.leopard.biz.repeatsubmit;

import io.leopard.memcache.Memcache;

public class RepeatSubmitBizImpl implements RepeatSubmitBiz {

	private Memcache memcache;

	public RepeatSubmitBizImpl(Memcache memcache) {
		this.memcache = memcache;
	}

	private String getkey(String md5) {
		return "md5:" + md5;
	}

	@Override
	public void checkSubmit(String md5) {
		{
			RepeatSubmit repeatSubmit = this.get(md5);
			if (repeatSubmit != null) {
				throw new RepeatSubmitException("禁止重复提交.");
			}
		}
		RepeatSubmit repeatSubmit = new RepeatSubmit();
		repeatSubmit.setMd5(md5);
		this.add(repeatSubmit);
	}

	@Override
	public RepeatSubmit get(String md5) {
		// AssertUtil.assertNotEmpty(md5, "参数md5不能为空.");
		if (md5 == null || md5.length() == 0) {
			throw new IllegalArgumentException("参数md5不能为空.");
		}
		String key = this.getkey(md5);
		String value = this.memcache.get(key);
		if (value == null || value.length() == 0) {
			return null;
		}
		else {
			RepeatSubmit repeatSubmit = new RepeatSubmit();
			repeatSubmit.setMd5(value);
			return repeatSubmit;
		}
	}

	@Override
	public boolean add(RepeatSubmit repeatSubmit) {
		String md5 = repeatSubmit.getMd5();
		if (md5 == null || md5.length() == 0) {
			throw new IllegalArgumentException("参数md5不能为空.");
		}
		// AssertUtil.assertNotEmpty(repeatSubmit.getMd5(), "参数md5不能为空.");
		String key = this.getkey(md5);
		return this.memcache.add(key, md5);
	}

}
