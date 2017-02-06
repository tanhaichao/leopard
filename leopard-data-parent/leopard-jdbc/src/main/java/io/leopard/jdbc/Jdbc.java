package io.leopard.jdbc;

import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.ReplaceBuilder;
import io.leopard.jdbc.builder.SqlBuilder;
import io.leopard.lang.Paging;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Jdbc接口(MySQL操作).
 * 
 * @author 阿海
 * 
 */
public interface Jdbc {

	// /**
	// * 事务回滚.
	// *
	// * @return 成功返回true,失败返回false.
	// */
	// @Deprecated
	// boolean rollback();
	//
	// /**
	// * 提交.
	// *
	// * @return 成功返回true,失败返回false.
	// */
	// @Deprecated
	// boolean commit();
	//
	// /**
	// * 当n个sql 视为一个整体（原子性），要么都执行，要么一个不执行时候.通过 setAutoCommit 进行控制.
	// *
	// * @param autoCommit
	// * 是否支持事物
	// * @return 成功返回true,失败返回false.
	// */
	// @Deprecated
	// boolean setAutoCommit(boolean autoCommit);

	/**
	 * 获取数据源.
	 * 
	 * @return 获得的数据源
	 */
	DataSource getDataSource();

	// @Deprecated
	JdbcTemplate getJdbcTemplate();

	/**
	 * 输出组装装好的sql,log.info(sql).
	 * 
	 * @param sql 未替换参数的sql
	 * @param param 参数对象
	 * @return 替换参数后的sql
	 */
	String printSQL(Log logger, String sql, StatementParameter param);

	/**
	 * 获得替换参数后的sql.
	 * 
	 * @param sql 未替换参数的sql
	 * @param param 参数对象
	 * @return 替换参数后的sql
	 */
	String getSQL(String sql, StatementParameter param);

	/**
	 * 是否存在sql要查询的数据.
	 * 
	 * @param sql sql
	 * @return 如果存在返回true,不存在返回false.
	 */
	boolean exist(String sql);

	/**
	 * 是否存在sql要查询的数据.
	 * 
	 * @param sql 未替换参数的sql
	 * @param param 参数对象
	 * @return 如果存在返回true,不存在返回false.
	 */
	boolean exist(String sql, StatementParameter param);

	/**
	 * 批量执行sql.
	 * 
	 * @param sql sql语句
	 * @param setter 需要实现BatchPreparedStatementSetter接口，有setValues，getBatchSize二个接口 . 具体可以参考spring接口.
	 * @return 数组，返回形如[1,0,1]这样的数组。1表示成功，0表示失败.
	 */
	int[] batchUpdate(String sql, BatchPreparedStatementSetter setter);

	/**
	 * 根据sql查询数据，返回elementType参数对象.
	 * 
	 * @param sql
	 * @param elementType Class类型
	 * @return 返回查询的单个对象
	 */
	<T> T query(String sql, Class<T> elementType);

	/**
	 * 根据sql查询数据，返回elementType参数对象.
	 * 
	 * @param sql
	 * @param elementType Class类型
	 * @param param 参数对象
	 * @return 返回查询的单个对象
	 */
	<T> T query(String sql, Class<T> elementType, StatementParameter param);

	/**
	 * 根据sql查询数据，返回elementType参数对象.
	 * 
	 * @param sql
	 * @param elementType Class类型
	 * @param params 参数列表
	 * @return 返回查询的单个对象
	 */
	<T> T query(String sql, Class<T> elementType, Object... params);

	/**
	 * 根据sql查询数据，返回Map对象的List.
	 * 
	 * @param sql
	 * @return List
	 */
	List<Map<String, Object>> queryForMaps(String sql);

	/**
	 * 根据sql查询数据，返回Map对象的List.
	 * 
	 * @param sql
	 * @return List
	 */
	List<Map<String, Object>> queryForMaps(String sql, Object... params);

	/**
	 * 根据sql查询数据.
	 * 
	 * @param sql 查询数据的sql
	 * @param elementType 数据对应的model对象
	 * @return 查询的数据
	 */

	<T> List<T> queryForList(String sql, Class<T> elementType);

	// /**
	// * 根据sql查询数据.
	// *
	// * @param sql
	// * 查询数据的sql
	// * @param elementType
	// * 数据对应的model对象
	// * @param start
	// * 查询起点
	// * @param size
	// * 查询条数
	// * @return 查询的数据
	// */
	// <T> List<T> queryForList(String sql, Class<T> elementType, int start, int size);

	/**
	 * 根据sql查询数据.
	 * 
	 * @param sql 查询数据的sql
	 * @param elementType 数据对应的model对象
	 * @param params 参数列表
	 * @return 查询的数据
	 */
	<T> List<T> queryForList(String sql, Class<T> elementType, Object... params);

	/**
	 * 根据sql查询数据，返回elementType参数对象的List.
	 * 
	 * @param sql sql
	 * @param elementType 数据对应的model对象
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	<T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param);

	/**
	 * 根据sql查询数据，返回elementType参数对象的List.
	 * 
	 * @param sql sql
	 * @param elementType 数据对应的model对象
	 * @param param 参数列表
	 * @param start 查询起点
	 * @param size 查询条数
	 * @return 查询的数据
	 */
	<T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param, int start, int size);

	/**
	 * 根据sql查询数据，返回Long的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	List<Long> queryForLongs(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回Long的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @param start 查询起点
	 * @param size 查询条数
	 * @return 查询的数据
	 */
	List<Long> queryForLongs(String sql, StatementParameter param, int start, int size);

	/**
	 * 根据sql查询数据，返回Integer的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	List<Integer> queryForInts(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回Integer的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @param start 查询起点
	 * @param size 查询条数
	 * @return 查询的数据
	 */
	List<Integer> queryForInts(String sql, StatementParameter param, int start, int size);

	/**
	 * 根据sql查询数据，返回String的List.
	 * 
	 * @param sql sql
	 * @return 查询的数据
	 */
	List<String> queryForStrings(String sql);

	/**
	 * 根据sql查询数据，返回String的List.
	 * 
	 * @param sql sql
	 * @param start 查询起点
	 * @param size 查询条数
	 * @return 查询的数据
	 */
	List<String> queryForStrings(String sql, int start, int size);

	/**
	 * 根据sql查询数据，返回String的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	List<String> queryForStrings(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回String的List.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @param start 查询起点
	 * @param size 查询条数
	 * @return 查询的数据
	 */
	List<String> queryForStrings(String sql, StatementParameter param, int start, int size);

	/**
	 * 根据sql查询数据，返回long值.
	 * 
	 * @param sql sql
	 * @return 查询的数据
	 */
	Long queryForLong(String sql);

	/**
	 * 根据sql查询数据，返回long值.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	Long queryForLong(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回long值.
	 * 
	 * @param sql sql
	 * @param params 参数列表
	 * @return 查询的数据
	 */
	Long queryForLong(String sql, Object... params);

	/**
	 * 根据sql查询数据，返回int值.
	 * 
	 * @param sql sql
	 * @return 查询的数据
	 */
	Integer queryForInt(String sql);

	/**
	 * 根据sql查询数据，返回int值.
	 * 
	 * @param sql sql
	 * @param param params 参数列表
	 * @return 查询的数据
	 */
	Integer queryForInt(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回double值.
	 * 
	 * @param sql sql
	 * @param param params 参数列表
	 * @return 查询的数据
	 */
	Double queryForDouble(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回float值.
	 * 
	 * @param sql sql
	 * @param param params 参数列表
	 * @return 查询的数据
	 */
	Float queryForFloat(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回int值.
	 * 
	 * @param sql sql
	 * @param params 参数列表
	 * @return 查询的数据
	 */
	Integer queryForInt(String sql, Object... params);

	/**
	 * 根据sql查询数据，返回double值.
	 * 
	 * @param sql sql
	 * @param params 参数列表
	 * @return 查询的数据
	 */
	Double queryForDouble(String sql, Object... params);

	/**
	 * 根据sql查询数据，返回Date对象.
	 * 
	 * @param sql sql
	 * @return 查询的数据
	 */
	java.util.Date queryForDate(String sql);

	/**
	 * 
	 * 根据sql查询数据，返回Date对象.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	java.util.Date queryForDate(String sql, StatementParameter param);

	/**
	 * 根据sql查询数据，返回String.
	 * 
	 * @param sql sql
	 * @return 查询的数据
	 */
	String queryForString(String sql);

	/**
	 * 根据sql查询数据，返回String值.
	 * 
	 * @param sql sql
	 * @param params 参数列表
	 * @return 查询的数据
	 */
	String queryForString(String sql, Object... params);

	/**
	 * 根据sql查询数据，返回String.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 查询的数据
	 */
	String queryForString(String sql, StatementParameter param);

	/**
	 * 执行insert，不抛出DuplicateKeyException.
	 * 
	 * @param builder InsertBuilder
	 * @return 成功返回true，失败返回false
	 */
	@Deprecated
	boolean insertIgnoreForBoolean(InsertBuilder builder);

	/**
	 * 执行replace into，不抛出DuplicateKeyException.
	 * 
	 * @param builder ReplaceBuilder
	 * @return 成功返回true，失败返回false
	 */
	@Deprecated
	boolean insertIgnoreForBoolean(ReplaceBuilder builder);

	/**
	 * 执行insert，不抛出DuplicateKeyException.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 成功返回true，失败返回false
	 */
	// @Deprecated
	boolean insertIgnoreForBoolean(String sql, StatementParameter param);

	/**
	 * 执行insert.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 成功返回true，失败返回false
	 */
	boolean insertForBoolean(String sql, StatementParameter param);

	boolean insertForBoolean(String sql, Object... params);

	/**
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	Long incr(String sql, StatementParameter param);

	/**
	 * 执行insert.
	 * 
	 * @param builder InsertBuilder
	 * @return 成功返回true，失败返回false
	 */
	boolean insertForBoolean(InsertBuilder builder);

	/**
	 * 执行replace into.
	 * 
	 * @param builder ReplaceBuilder
	 * @return 成功返回true，失败返回false
	 */
	boolean insertForBoolean(ReplaceBuilder builder);

	/**
	 * 执行update.
	 * 
	 * @param sql sql
	 * @param params 参数列表
	 * @return 成功返回true，失败返回false
	 */
	boolean updateForBoolean(String sql, Object... params);

	/**
	 * 执行update.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 成功返回true，失败返回false
	 */
	boolean updateForBoolean(String sql, StatementParameter param);

	/**
	 * 执行update.
	 * 
	 * @param builder SqlBuilder
	 * @return 成功返回true，失败返回false
	 */
	boolean updateForBoolean(SqlBuilder builder);

	/**
	 * 执行update.
	 * 
	 * @param builder SqlBuilder
	 * @return 返回更新成功的记录数
	 */
	int update(SqlBuilder builder);

	/**
	 * 执行update.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 返回更新成功的记录数
	 */
	int update(String sql, StatementParameter param);

	/**
	 * 执行更新sql.
	 * 
	 * @param sql
	 * @return 成功返回1,失败返回0.
	 */
	int update(String sql);

	/**
	 * 执行插入sql,并返回id.
	 * 
	 * @param sql 要执行的sql
	 * @param param 参数对象
	 * @return 执行成功后的id.
	 */
	// @Deprecated
	long insertForLastId(String sql, StatementParameter param);

	/**
	 * 批量执行sql.
	 * 
	 * @param sqls
	 * @return
	 */
	int[] batchUpdate(String[] sqls);

	boolean insert(String tableName, Object bean);

	boolean insertByBean(String sql, Object bean);

	boolean updateByBean(String sql, Object bean);

	<T> Paging<T> queryForPaging(String sql, Class<T> elementType);

	// <T> Paging<T> queryForPaging(String sql, Class<T> elementType, int start, int size);

	<T> Paging<T> queryForPaging(String sql, Class<T> elementType, Object... params);

	<T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param);

	<T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param, int start, int size);

}
