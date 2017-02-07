package io.leopard.topnb.methodtime;

import io.leopard.topnb.methodtime.MethodTimeComparator.AllCountMethodComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TotalCountPerformanceComparatorTest {

	@Test
	public void compare() {
		List<MethodDto> performanceVOList = new ArrayList<MethodDto>();

		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllCount(1);
			performanceVO.setName("com.duowan.news.dao.mysql.UserDaoMysqlImpl.methodName2");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllCount(3);
			performanceVO.setName("com.duowan.news.dao.redis.UserDaoRedisImpl.methodName3");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllCount(2);
			performanceVO.setName("com.duowan.news.dao.memcached.UserDaoMemcachedImpl.methodName4");
			performanceVOList.add(performanceVO);
		}
		{
			MethodDto performanceVO = new MethodDto();
			performanceVO.setAllCount(1);
			performanceVO.setName("com.duowan.news.dao.mysql.UserDaoMysqlImpl.methodName1");
			performanceVOList.add(performanceVO);
		}
		Collections.sort(performanceVOList, new AllCountMethodComparator());
		Assert.assertEquals(3, performanceVOList.get(0).getAllCount());
		Assert.assertEquals(2, performanceVOList.get(1).getAllCount());
		Assert.assertEquals(1, performanceVOList.get(2).getAllCount());
		Assert.assertEquals(1, performanceVOList.get(3).getAllCount());

	}

}