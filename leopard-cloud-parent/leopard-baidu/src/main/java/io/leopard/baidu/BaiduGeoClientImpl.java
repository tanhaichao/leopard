package io.leopard.baidu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.leopard.baidu.Geo.NearbyIO;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

@Service
public class BaiduGeoClientImpl implements BaiduGeoClient {

	@Value("${baidu.ak}")
	private String ak;

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Geo geoAddress(double lat, double lng) {
		String url = "http://api.map.baidu.com/geocoder/v2/?location=%s,%s&output=json&pois=1&radius=1000&ak=" + ak;
		String jsonResult = Httpnb.doGet(String.format(url, lat, lng));
		Map<String, Object> map = Json.toMap(jsonResult);
		int status = (int) map.get("status");
		if (status != 0) {
			String message = String.valueOf(map.get("message"));
			logger.error("response: 错误码:" + status + " 错误信息：" + message);
			throw new RuntimeException(message);
		}
		Geo geo = new Geo();
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = (Map<String, Object>) map.get("result");
		{
			String address = (String) resultMap.get("formatted_address");
			geo.setAddress(address);
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> poisList = (List<Map<String, Object>>) resultMap.get("pois");
		List<NearbyIO> nearbyList = new ArrayList<>();
		for (Map<String, Object> mapKey : poisList) {
			String address = (String) mapKey.get("addr");
			String name = (String) mapKey.get("name");

			NearbyIO nearby = new NearbyIO();
			nearby.setAddress(address);
			nearby.setName(name);
			nearbyList.add(nearby);
		}
		geo.setNearbyList(nearbyList);
		return geo;

	}

}
