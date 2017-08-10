package io.leopard.baidu;

public interface BaiduGeoClient {

	/**
	 * 
	 * @param lat 纬度
	 * @param lng 经度
	 */
	Geo geoAddress(double lat,double lng);
}
