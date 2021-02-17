package com.fedemarkoo.AdaLovelaceIA.exceptions;

public class UnknownMethodException extends UnknownException {
	public UnknownMethodException(ReflectiveOperationException e) {
		super(e);
	}
	public UnknownMethodException() {
		super();
	}
}
