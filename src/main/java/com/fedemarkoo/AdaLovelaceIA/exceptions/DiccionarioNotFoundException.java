package com.fedemarkoo.AdaLovelaceIA.exceptions;

public class DiccionarioNotFoundException extends UnknownException {
	private final String word;

	public DiccionarioNotFoundException(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}
}
