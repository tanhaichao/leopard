package io.leopard.lang.inum;

import java.util.List;

/**
 * 枚举位运算
 * 
 * @author 谭海潮
 *
 */
public class BitInumUtil {

	public static int getValue(List<? extends Inum> constantList) {
		int value = 0;
		if (constantList != null) {
			for (Inum inum : constantList) {
				value |= inum.getKey();
			}
		}
		return value;
	}
}
