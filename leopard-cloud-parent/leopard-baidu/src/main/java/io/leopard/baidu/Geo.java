package io.leopard.baidu;

import java.util.List;

public class Geo {

	/**
	 * 当前地址
	 */
	public String address;

	/**
	 * 附近的地址
	 */
	public List<NearbyIO> nearbyList;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<NearbyIO> getNearbyList() {
		return nearbyList;
	}

	public void setNearbyList(List<NearbyIO> nearbyList) {
		this.nearbyList = nearbyList;
	}

	/**
	 * 附近的地址
	 * 
	 * @author 谭海潮
	 *
	 */
	public static class NearbyIO {
		/**
		 * 地址
		 */
		private String address;

		/**
		 * 名称
		 */
		private String name;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
