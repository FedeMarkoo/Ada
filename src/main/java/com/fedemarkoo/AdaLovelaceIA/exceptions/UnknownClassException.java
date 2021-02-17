package com.fedemarkoo.AdaLovelaceIA.exceptions;

public class UnknownClassException extends UnknownException {
	public UnknownClassException(ReflectiveOperationException e) {
		super(e);
	}

	public UnknownClassException() {
	}
}
