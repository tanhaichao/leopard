package io.leopard.data.signature;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.core.exception.invalid.SignatureInvalidException;
import io.leopard.redis.Redis;
import io.leopard.redis.RedisImpl;

import java.util.Date;

public class SignatureServiceImpl extends ContextImpl implements SignatureService {

	private SignatureDao signatureDao;

	private Signature signature;

	private Redis redis;
	private String server;
	private String redisKey;
	private String publicKey;
	private boolean useBase16;
	private boolean checkUsed;// 是否检查key是否已被使用?

	private int maxActive;
	private int timeout;
	private int initialPoolSize;// 默认初始化连接数量

	private SignatureQueueManager queueManager;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setUseBase16(boolean useBase16) {
		this.useBase16 = useBase16;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public void setCheckUsed(boolean checkUsed) {
		this.checkUsed = checkUsed;
	}

	@Override
	public void init() {
		SignatureDaoRedisImpl signatureDaoRedisImpl = new SignatureDaoRedisImpl();
		if (redis == null) {
			this.redis = new RedisImpl(server, maxActive, initialPoolSize, false, timeout);
			redis.init();
		}

		signatureDaoRedisImpl.setRedis(redis);

		signatureDaoRedisImpl.setRedisKey(redisKey);
		this.signatureDao = signatureDaoRedisImpl;
		this.signature = new SignatureTimeoutImpl(publicKey, 60 * 20, useBase16);

		this.queueManager = new SignatureQueueManager() {
			@Override
			public void consume(String sha1) {
				signatureDao.add(sha1);
			}
		};
	}

	@Override
	public SignatureInfo checkSignature(Long yyuid, String key) {
		AssertUtil.assertNotNull(yyuid, "参数yyuid不能为空.");

		String user = yyuid.toString();
		SignatureInfo signatureInfo = this.checkSignature2(key);
		if (!signatureInfo.getUser().equals(user)) {
			throw new SignatureInvalidException("用户名不匹配[" + signatureInfo.getUser() + ":" + user + "].");
		}
		return signatureInfo;
	}

	@Override
	public SignatureInfo checkSignature(String user, String encodeString) {

		SignatureInfo signatureInfo = this.checkSignature2(encodeString);
		if (!signatureInfo.getUser().equals(user)) {
			throw new SignatureInvalidException("用户名不匹配[" + signatureInfo.getUser() + ":" + user + "].");
		}
		return signatureInfo;
	}

	// 这个方法是为了方法耗时能监控到而建.
	protected SignatureInfo decode(String encodeString) {
		return signature.decode(encodeString);
	}

	// 这个方法是为了方法耗时能监控到而建.
	protected boolean exist(String sha1) {
		return signatureDao.exist(sha1);
	}

	// 这个方法是为了方法耗时能监控到而建.
	protected boolean add(String sha1) {
		// return this.signatureDao.add(sha1);
		return this.queueManager.add(sha1);
	}

	protected SignatureInfo checkSignature2(String encodeString) {
		SignatureInfo signatureInfo = this.decode(encodeString);
		if (!checkUsed) {
			return signatureInfo;
		}
		String sha1 = signatureInfo.getSha1();
		boolean exist = this.exist(sha1);
		if (exist) {
			throw new SignatureInvalidException("key[" + sha1 + "]已经过期.");
		}
		this.add(sha1);
		return signatureInfo;
	}

	@Override
	public String encode(String user) {
		return signature.encode(user, new Date());
	}

}
