package io.leopard.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class Row {

	private WritableSheet sheet;

	private int rowNumber;

	private int currentCell;

	public Row(WritableSheet sheet, int rowNumber) {
		this.sheet = sheet;
		this.rowNumber = rowNumber;
		this.currentCell = 0;
	}

	private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Row addCell(String str) throws WriteException {
		sheet.addCell(new Label(currentCell++, rowNumber, str));
		return this;
	}

	public Row addFormatCell(double number) throws WriteException {
		String str;
		if (number > 0) {
			str = "+" + number;
		}
		else {
			str = number + "";
		}
		return this.addCell(str);
	}

	public Row addCell(Date date) throws WriteException {
		String time = GET_TIME_FORMAT.format(date);
		return this.addCell(time);
	}

}
