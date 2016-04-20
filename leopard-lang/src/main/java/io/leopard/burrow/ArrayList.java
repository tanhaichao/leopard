package io.leopard.burrow;

public class ArrayList<E> extends java.util.ArrayList<E> {
	private static final long serialVersionUID = 1L;

	public ArrayList() {

	}

	@SuppressWarnings("unchecked")
	public ArrayList(E... values) {
		for (E value : values) {
			this.add(value);
		}
	}

}
