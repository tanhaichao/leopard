package io.leopard.test;

/**
 * 模块名称解析.
 * 
 * @author 阿海
 *
 */
public interface ModuleParserLei {
	/**
	 * 判断是否单模块项目?
	 * 
	 * @return
	 */
	boolean isSingleModule();

	// /work/news/news/news-dao

	String getModuleName();
}
