package io.leopard.chart.line;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.leopard.burrow.util.DateUtil;
import io.leopard.lang.TimeRange;

public class LineChartBuilder {

	private List<LineData> list = new ArrayList<LineData>();

	public void add(String label, String color, LineDataProvider provider) {
		this.add(label, color, provider, null);
	}

	public void add(String label, String color, LineDataProvider provider, String format) {
		LineData line = new LineData();
		line.setLabel(label);
		line.setColor(color);
		try {
			line.setData(this.toData(provider.execute(), format));
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		list.add(line);
	}

	protected List<Object[]> toData(List<?> dataList, String format) throws Exception {
		if (dataList == null) {
			return null;
		}
		List<Object[]> list = new ArrayList<Object[]>();
		for (Object obj : dataList) {
			Field[] fields = obj.getClass().getDeclaredFields();
			Object[] data = new Object[fields.length];
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				data[i] = field.get(obj);

				if (i == 0) {
					// System.err.println("format:" + format + " data[i]:" + data[i] + " " + data[i].getClass().getName());
					if (format != null && data[i] instanceof Date) {
						data[i] = new SimpleDateFormat(format).format((Date) data[i]);
					}
				}

			}
			list.add(data);
		}
		return list;
	}

	public List<LineData> build() {
		// List<TrackTrendVO> list = new ArrayList<TrackTrendVO>();
		// list.add(this.getTrackTrendVO(range.getStartTime(), "#5ab1ef"));
		// list.add(this.getTrackTrendVO(range.getEndTime(), "#f5994e"));
		return list;
	}

	/**
	 * 填充没有数据的.
	 * 
	 * @return
	 */
	public static List<LineDayVO> fill(List<LineDayVO> list, TimeRange range) {
		// Json.print(range, "range2");
		if (range == null || range.getStartTime() == null || range.getEndTime() == null) {
			return list;
		}
		// Json.print(list, "list");
		// List<LineDayVO> list2 = new ArrayList<LineDayVO>();
		Date startTime = range.getStartTime();
		int dayCount = Math.abs(DateUtil.getDayCount(range.getStartTime(), range.getEndTime()));
		for (int i = 0; i < dayCount; i++) {
			Date curdate = DateUtil.addDate(startTime, i);
			LineDayVO dayVO = null;
			Date postdate = null;
			if (list.size() - 1 >= i) {
				dayVO = list.get(i);
				postdate = dayVO.getPostdate();
			}
			if (curdate.equals(postdate)) {
				continue;
			}
			LineDayVO dayVO2 = new LineDayVO();
			dayVO2.setPostdate(curdate);
			list.add(i, dayVO2);
		}
		return list;
	}

}
