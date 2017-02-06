package io.leopard.lang;

/**
 * 经纬度.
 * 
 * @author ahai
 *
 */
public class Location {

	/**
	 * 纬度
	 */
	private Double lat;

	/**
	 * 经度
	 */
	private Double lng;

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "lat:" + this.lat + " lng:" + this.lng;
	}
}
