package io.leopard.data.alldb;

import io.leopard.jdbc.Jdbc;
import io.leopard.redis.Redis;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * XxxDaoAlldbImpl的Alldb属性初始化操作.
 * 
 * @author 阿海
 *
 */
public class AlldbBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	protected ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	protected void initAlldbBean(Object bean) throws Exception {
		Class<?> clazz = bean.getClass();
		Field field = clazz.getDeclaredField("alldb");
		// System.err.println("clazz:" + clazz.getName() + " field:" + field);
		if (field == null) {
			return;
		}
		field.setAccessible(true);
		AlldbImpl alldb = new AlldbImpl();
		this.initMysql(field, alldb);
		this.initRedis(field, alldb);
		this.initMemdb(field, alldb);
		field.set(bean, alldb);
	}

	protected boolean initMysql(Field field, AlldbImpl alldb) {
		Mysql mysql = field.getAnnotation(Mysql.class);
		if (mysql == null) {
			return false;
		}
		String jdbcBeanId = mysql.jdbc();
		Jdbc jdbc = (Jdbc) beanFactory.getBean(jdbcBeanId);
		MysqlImpl mysqlImpl = new MysqlImpl();
		mysqlImpl.setJdbc(jdbc);
		mysqlImpl.setTableName(mysql.table());
		mysqlImpl.setKeys(mysql.key());
		alldb.setMysqlImpl(mysqlImpl);
		return true;
	}

	protected void initRedis(Field field, AlldbImpl alldb) {
		boolean success = this.initRedisStrings(field, alldb);
		if (success) {
			return;
		}
		this.initRedisHashs(field, alldb);
	}

	protected boolean initRedisStrings(Field field, AlldbImpl alldb) {
		Strings strings = field.getAnnotation(Strings.class);
		if (strings == null) {
			return false;
		}
		String redisBeanId = strings.redis();
		Redis redis = (Redis) beanFactory.getBean(redisBeanId);

		StringsImpl stringsImpl = new StringsImpl();
		stringsImpl.setRedis(redis);
		stringsImpl.setSeconds(strings.seconds());
		stringsImpl.setKeyPattern(strings.key());

		alldb.setStringsImpl(stringsImpl);

		return true;
	}

	protected boolean initRedisHashs(Field field, AlldbImpl alldb) {
		Hashs hashs = field.getAnnotation(Hashs.class);
		if (hashs == null) {
			return false;
		}
		String redisBeanId = hashs.redis();
		Redis redis = (Redis) beanFactory.getBean(redisBeanId);
		HashsImpl hashsImpl = new HashsImpl();
		hashsImpl.setRedis(redis);
		hashsImpl.setKey(hashs.key());
		hashsImpl.setFieldPattern(hashs.field());

		alldb.setHashsImpl(hashsImpl);
		return true;
	}

	protected boolean initMemdb(Field field, AlldbImpl alldb) {
		Memdb memdb = field.getAnnotation(Memdb.class);
		if (memdb == null) {
			return false;
		}
		int size = memdb.size();
		io.leopard.data4j.memdb.Memdb memdb2 = null;
		if (memdb.rsync()) {
			memdb2 = new io.leopard.data4j.memdb.MemdbRsyncImpl(size);
		}
		else {
			memdb2 = new io.leopard.data4j.memdb.MemdbLruImpl(size);
		}
		MemdbImpl memdbImpl = new MemdbImpl();
		memdbImpl.setMemdb(memdb2);
		alldb.setMemdbImpl(memdbImpl);
		return true;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (beanName.endsWith("DaoAlldbImpl")) {
			try {
				initAlldbBean(bean);
			}
			catch (RuntimeException e) {
				throw e;
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
