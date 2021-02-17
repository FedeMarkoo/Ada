package com.fedemarkoo.AdaLovelaceIA.utils;

import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownMethodException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FraseUtil {
	private String frase;
	private String fraseCanonica;
	private List<String> verbos;
	private List<String> sustantivos;
	private List<String> adjetivos;

	public List<String> getVerbos() {
		if (verbos != null) {
			return verbos;
		}
		return verbos = new ArrayList<>();
	}

	public List<String> getSustantivos() {
		if (sustantivos != null) {
			return sustantivos;
		}
		return sustantivos = new ArrayList<>();
	}

	public List<String> getAdjetivos() {
		if (adjetivos != null) {
			return adjetivos;
		}
		return adjetivos = new ArrayList<>();
	}

	public void addVerbo(String verbo) {
		getVerbos().add(verbo);
	}

	public void addSustantivo(String sustantivo) {
		getSustantivos().add(sustantivo);
	}

	public void addAdjetivos(String adjetivo) {
		getAdjetivos().add(adjetivo);
	}

	private void concatCanonical(String word) {
		String frase = this.getFraseCanonica();
		if (frase == null) frase = word.toLowerCase();
		else frase += " " + word.toLowerCase();
		setFraseCanonica(frase);
	}

	public void addPalabra(DiccionarioCache cache) {
		String palabraCanonica = cache.getPalabraCanonica();
		concatCanonical(palabraCanonica);
		switch (cache.getTipo()) {
			case SUSTANTIVO:
				addSustantivo(palabraCanonica);
				break;
			case VERBO:
				addVerbo(palabraCanonica);
				break;
			case ADJETIVO:
				addAdjetivos(palabraCanonica);
				break;
		}
	}

	public String getPrimerSustantivo() throws UnknownClassException {
		if (getSustantivos().isEmpty())
			throw new UnknownClassException();
		return getSustantivos().get(0);
	}

	public String getPrimerVerbo() throws UnknownMethodException {
		if (getVerbos().isEmpty())
			return null;
		return getVerbos().get(0);
	}
}
