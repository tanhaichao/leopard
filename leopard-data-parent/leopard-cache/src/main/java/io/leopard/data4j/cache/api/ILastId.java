package io.leopard.data4j.cache.api;

/**
 * 获取自增长ID(注意：只能在并发量不大的表中使用)
 * 
 * @author 阿海
 * 
 */
public interface ILastId {

	long getLastId();
}
