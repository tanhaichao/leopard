package io.leopard.web.xparam.controller;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;

public class Mvcparam {

	private Month month;
	private OnlyDate date;

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public OnlyDate getDate() {
		return date;
	}

	public void setDate(OnlyDate date) {
		this.date = date;
	}

}
