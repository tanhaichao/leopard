package io.leopard.monitor.alarm;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class AlarmModule implements Module {

	private static Injector injector;

	@Override
	public void configure(Binder binder) {
		binder.bind(AlarmDao.class).to(AlarmDaoAllImpl.class).in(Scopes.SINGLETON);

		binder.bind(AlarmService.class).to(AlarmServiceImpl.class).in(Scopes.SINGLETON);

		binder.bind(RobotDao.class).to(RobotDaoHttpImpl.class).in(Scopes.SINGLETON);
		binder.bind(RobotService.class).to(RobotServiceImpl.class).in(Scopes.SINGLETON);

		// binder.bind(Jdbc.class).to(JdbcH2Impl.class).in(Scopes.SINGLETON);
		// binder.bind(SqlSession.class).to(SqlSessionH2Impl.class).in(Scopes.SINGLETON);
		// binder.bind(H2Dao.class).to(H2DaoImpl.class).in(Scopes.SINGLETON);
		//
		// binder.bind(DataSource.class).toProvider(DataSourceProvider.class).in(Scopes.SINGLETON);
		//
		// binder.bind(JedisTestnbImpl.class).toInstance(new JedisTestnbImpl());
		// binder.bind(TransactionTestnbImpl.class).toInstance(new TransactionTestnbImpl());
	}

	public static <T> T getInstance(Class<T> clazz) {
		if (injector == null) {
			initInjector();
		}
		return injector.getInstance(clazz);
	}

	protected static synchronized Injector initInjector() {
		if (injector == null) {
			injector = Guice.createInjector(new AlarmModule());
		}
		return injector;
	}

	public static AlarmService getAlarmService() {
		return AlarmModule.getInstance(AlarmService.class);
	}

	public static RobotService getRobotService() {
		return AlarmModule.getInstance(RobotService.class);
	}

}
