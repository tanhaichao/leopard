package io.leopard.data.alldb;

import io.leopard.jdbc.Jdbc;
import io.leopard.redis.Redis;

import java.util.Date;


//数据有三种存储(Memdb、Redis Strings、Mysql）
//@Mysql，jdbc:是mysql jdbc数据源beanId，table:是用户表名称，key:主键字段(可以是数组)
//@Strings，redis：redis数据源beanId，key:redis key规则
//@Memdb，size:内存中最大记录条数，rsync:是否多JVM同步
public interface Alldb {

	<T> T get(Class<T> elementType, Object... keys);

	boolean delete(Object key);

	boolean delete(Object key, long opuid, Date lmodify);

	Jdbc jdbc();

	Redis redis();

	Memdb memdb();
}
