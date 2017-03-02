package io.leopard.burrow.lang.datatype;

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

	public Location() {

	}

	public Location(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

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
