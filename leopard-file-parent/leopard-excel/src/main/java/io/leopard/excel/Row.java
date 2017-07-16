package io.leopard.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
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

	public Row addCell(Bnum bnum) throws WriteException {
		String str = bnum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Inum inum) throws WriteException {
		String str = inum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Snum snum) throws WriteException {
		String str = snum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(int num) throws WriteException {
		String str = Integer.toString(num);
		return this.addCell(str);
	}

	public Row addCell(long num) throws WriteException {
		String str = Long.toString(num);
		return this.addCell(str);
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
