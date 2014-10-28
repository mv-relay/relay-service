package org.landcycle.api.rest;

import java.io.Serializable;

public class JsonResponseSingleData<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private E attributes = null;

	public E getAttributes() {
		return attributes;
	}

	public void setAttributes(E attributes) {
		this.attributes = attributes;
	}

}
