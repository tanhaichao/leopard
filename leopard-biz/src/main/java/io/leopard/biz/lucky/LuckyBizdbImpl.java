package io.leopard.biz.lucky;

import io.leopard.redis.Redis;

public class LuckyBizdbImpl implements LuckyBizdb {

	protected LuckyFullDb fullDb;
	protected LuckySetDb[] setDbs;

	private LuckyLottery lottery = new LuckyLottery();

	public LuckyBizdbImpl(Redis redis, String name, int[] weights) {
		fullDb = new LuckyFullDb(redis, name);
		setDbs = new LuckySetDb[weights.length];
		for (int i = 0; i < weights.length; i++) {
			setDbs[i] = new LuckySetDb(redis, name, i);
			this.lottery.add(weights[i]);
		}
	}

	@Override
	public boolean add(String member, int weight) {
		int oldWeight = fullDb.getWeight(member);
		if (oldWeight == weight) {
			return false;
		}
		if (oldWeight > -1) {
			if (oldWeight < setDbs.length) {
				setDbs[oldWeight].delete(member);
			}
		}
		setDbs[weight].add(member);
		return fullDb.add(member, weight);
	}

	@Override
	public int count() {
		return fullDb.count();
	}

	@Override
	public int count(int weight) {
		return setDbs[weight].count();
	}

	@Override
	public String lucky() {
		LotteryManager manager = new LotteryManager(lottery);
		for (int i = 0; i < lottery.getIndexCount(); i++) {
			int weight = manager.randomWeight();
			String member = this.setDbs[weight].random();
			if (member != null) {
				return member;
			}
		}
		return this.fullDb.random();
	}

	@Override
	public boolean delete(String member) {
		int oldWeight = fullDb.getWeight(member);
		if (oldWeight <= -1) {
			return false;
		}
		setDbs[oldWeight].delete(member);
		return fullDb.delete(member);
	}

	@Override
	public boolean clean() {
		fullDb.clean();
		for (int i = 0; i < setDbs.length; i++) {
			setDbs[i].clean();
		}
		return true;
	}

	@Override
	public boolean exist(String member) {
		int weight = this.fullDb.getWeight(member);
		return weight > -1;
	}

}
