package io.leopard.sysconfig.dynamicenum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.leopard.data.env.LeopardPropertyPlaceholderConfigurer;
import io.leopard.data4j.pubsub.IPubSub;
import io.leopard.data4j.pubsub.Publisher;
import io.leopard.jdbc.Jdbc;
import io.leopard.json.Json;
import io.leopard.lang.inum.daynamic.DynamicEnum;
import io.leopard.lang.inum.daynamic.EnumConstant;
import io.leopard.redis.Redis;
import io.leopard.sysconfig.viewer.DynamicEnumDataVO;
import io.leopard.sysconfig.viewer.DynamicEnumVO;

@Component
public class DynamicEnumScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware, DynamicEnumResolver, IPubSub {

	protected Log logger = LogFactory.getLog(this.getClass());

	private ApplicationContext applicationContext;

	private Jdbc jdbc;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// logger.info("setApplicationContext");
		this.applicationContext = applicationContext;
		jdbc = applicationContext.getBean(Jdbc.class);
		Redis redis = (Redis) applicationContext.getBean("sessionRedis");
		Publisher.listen(this, redis);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// logger.info("postProcessBeanFactory");
		LeopardPropertyPlaceholderConfigurer configurer = beanFactory.getBean(LeopardPropertyPlaceholderConfigurer.class);
		String basePackage = configurer.getProperty("base.package");
		logger.info("basePackage:" + basePackage);
		if (StringUtils.isEmpty(basePackage)) {
			throw new RuntimeException("app.properties没有配置base.package属性.");
		}
		System.err.println("DynamicEnumScannerConfigurer postProcessBeanFactory");
		DynamicEnumScanner scanner = new DynamicEnumScanner((BeanDefinitionRegistry) beanFactory) {
			@Override
			protected void registerEnum(String enumId, String beanClassName) {
				DynamicEnumInfo enumInfo = new DynamicEnumInfo();
				enumInfo.setEnumId(enumId);
				enumInfo.setBeanClassName(beanClassName);
				enumList.add(enumInfo);
			}

		};
		scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);

		this.update();
	}

	@Override
	public boolean update() {
		DynamicEnumDao dynamicEnumDao = new DynamicEnumDaoJdbcImpl(jdbc);
		for (DynamicEnumInfo enumInfo : enumList) {
			String enumId = enumInfo.getEnumId();
			String className = enumInfo.getBeanClassName();
			Class<?> type;
			try {
				type = Class.forName(className);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			List<EnumConstant> constantList = dynamicEnumDao.resolve(enumId, type);
			// System.out.println("className:" + className);
			// Json.printList(constantList, "constantList");
			DynamicEnum.setEnumConstantList(className, constantList);
		}
		lmodify = new Date();
		return true;
	}

	private List<DynamicEnumInfo> enumList = new ArrayList<DynamicEnumInfo>();

	private Date lmodify;

	@Override
	public DynamicEnumDataVO get() {
		DynamicEnumDataVO sysconfigVO = new DynamicEnumDataVO();
		List<DynamicEnumVO> enumVOList = new ArrayList<DynamicEnumVO>();
		sysconfigVO.setEnumList(enumVOList);
		for (DynamicEnumInfo enumInfo : enumList) {
			String enumId = enumInfo.getEnumId();
			String className = enumInfo.getBeanClassName();
			List<EnumConstant> constantList = DynamicEnum.allOf(className);
			DynamicEnumVO enumVO = new DynamicEnumVO();
			enumVO.setEnumId(enumId);
			enumVO.setConstantList(constantList);
			enumVOList.add(enumVO);
		}
		sysconfigVO.setLmodify(lmodify);
		return sysconfigVO;
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		if (isMySelf) {
			return;
		}
		logger.info("subscribe message:" + message + " isMySelf:" + isMySelf);
		this.update();
	}

	@Override
	public boolean publish() {
		boolean success = this.update();
		Publisher.publish(this, "update");
		return success;
	}

}
