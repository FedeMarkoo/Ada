package com.fedemarkoo.AdaLovelaceIA.utils;

import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class FraseUtil {
	private String frase;
	private String fraseCanonica;
	private FraseAccion fraseAccion;
	private List<String> verbos;
	private Map<String, Integer> sustantivos;
	private List<String> adjetivos;

	public FraseUtil(String frase) {
		this.frase = frase;
	}

	public List<String> getVerbos() {
		if (verbos != null) {
			return verbos;
		}
		return verbos = new ArrayList<>();
	}

	public Map<String, Integer> getSustantivos() {
		if (sustantivos == null) {
			sustantivos = new HashMap<>();
		}
		return sustantivos;
	}

	@SneakyThrows
	public List<String> getSustantivosListSorted() {
		if (faltanSustantivos()) throw new ClassNotFoundException();
		return getSustantivos().entrySet().stream()
				.sorted(Comparator.comparingInt(Map.Entry::getValue))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
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

	public void addSustantivo(String sustantivo, Integer prioridad) {
		getSustantivos().put(sustantivo, prioridad);
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
				addSustantivo(palabraCanonica, cache.getPrioridad());
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
		if (faltanSustantivos())
			throw new UnknownClassException();
		return getSustantivosListSorted().get(0);
	}

	public String getPrimerVerbo() {
		if (faltanVerbos())
			return null;
		return getVerbos().get(0);
	}

	public boolean faltaInfo() {
		return faltanVerbos() || faltanSustantivos();
	}

	public boolean faltanVerbos() {
		return getVerbos().isEmpty();
	}

	public boolean faltanSustantivos() {
		return getSustantivos().isEmpty();
	}
}
