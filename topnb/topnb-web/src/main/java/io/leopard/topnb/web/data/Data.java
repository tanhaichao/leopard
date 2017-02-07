package io.leopard.topnb.web.data;

import java.util.Map;

/**
 * 方法耗时数据.
 * 
 * 入口:全部、入口1、入口2、入口3 时间:全部、今天、小时、分钟 速度(耗时)、全部、慢、特慢
 * 
 * @author 阿海
 * 
 */
public interface Data {

	boolean add(String name, double time, int count);

	CountDto remove(String name);

	Map<String, CountDto> clearAndReturn();

}
