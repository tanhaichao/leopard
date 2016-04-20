package io.leopard.biz.lucky;

public class LotteryManager {

	private LuckyLottery lottery;

	private int index;

	public LotteryManager(LuckyLottery lottery) {
		this.lottery = lottery;
		this.index = lottery.getIndexCount() - 1;
	}

	public int randomWeight() {
		int weight = lottery.randomWeight(index);
		// System.out.println("index:" + index + " weight:" + weight);
		if (weight >= index && index > 0) {
			index--;
		}
		return weight;
	}

}
