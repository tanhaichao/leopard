package io.leopard.topnb.methodtime;

import io.leopard.topnb.methodtime.MethodTimeComparator.DefaultMethodComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DefaultPerformanceComparatorTest {

	@Test
	public void compare() {
		List<MethodDto> performanceVOList = new ArrayList<MethodDto>();
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(1);
			performanceVO.setName("clazz.methodName1");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(1);
			performanceVO.setName("clazz.methodName2");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(3);
			performanceVO.setName("clazz.methodName3");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(2);
			performanceVO.setName("clazz.methodName4");
			performanceVOList.add(performanceVO);
		}
		Collections.sort(performanceVOList, new DefaultMethodComparator());

		Assert.assertEquals(3D, performanceVOList.get(0).getAllTime(), 0);
		Assert.assertEquals(2D, performanceVOList.get(1).getAllTime(), 0);
	}

	@Test
	public void compare2() {
		List<MethodDto> performanceVOList = new ArrayList<MethodDto>();

		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(1);
			performanceVO.setName("com.duowan.news.dao.mysql.UserDaoMysqlImpl.methodName2");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(3);
			performanceVO.setName("com.duowan.news.dao.redis.UserDaoRedisImpl.methodName3");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(2);
			performanceVO.setName("com.duowan.news.dao.memcached.UserDaoMemcachedImpl.methodName4");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllTime(1);
			performanceVO.setName("com.duowan.news.dao.mysql.UserDaoMysqlImpl.methodName1");
			performanceVOList.add(performanceVO);
		}
		Collections.sort(performanceVOList, new DefaultMethodComparator());

		Assert.assertEquals(1D, performanceVOList.get(0).getAllTime(), 0);
		Assert.assertEquals(1D, performanceVOList.get(1).getAllTime(), 0);
		Assert.assertEquals(3D, performanceVOList.get(2).getAllTime(), 0);
		Assert.assertEquals(2D, performanceVOList.get(3).getAllTime(), 0);
	}

}