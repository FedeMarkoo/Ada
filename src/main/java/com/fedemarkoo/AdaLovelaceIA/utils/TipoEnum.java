package com.fedemarkoo.AdaLovelaceIA.utils;

import java.util.Arrays;

public enum TipoEnum {
	VERBO("verb"), SUSTANTIVO("noun"), ADJETIVO("adjective"), OTRO("other");
	private final String text;

	TipoEnum(String text) {
		this.text = text;
	}

	public static TipoEnum getByValue(final String type) {
		return Arrays.stream(values()).filter(o -> o.text.equals(type)).findAny().orElse(OTRO);
	}
}
