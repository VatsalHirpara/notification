package com.nagarro.notification.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ResponseTO<T> implements Serializable {
	public ResponseTO() {
		super();
	}

	private static final long serialVersionUID = 6472242732428902250L;

	/**
	 * The error.
	 */
	private boolean error;

	/**
	 * The errors.
	 */
	private List<Error> errors;

	/**
	 * The data.
	 */
	private T data;
}
