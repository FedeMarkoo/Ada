package com.fedemarkoo.AdaLovelaceIA.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Cache")
public class DiccionarioCache {
	@Id
	private String palabra;
	private String palabraCanonica;
	private Integer prioridad = Integer.MAX_VALUE;
	private TipoEnum tipo;

	public DiccionarioCache(String word, String palabra, String type) {
		this.setPalabra(word);
		this.setPalabraCanonica(palabra);
		this.tipo = TipoEnum.getByValue(type);
	}

	public DiccionarioCache(String word) {
		this(word, word, null);
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra.toLowerCase();
	}

	public void setPalabraCanonica(String palabraCanonica) {
		if (palabraCanonica != null) {
			this.palabraCanonica = palabraCanonica.toLowerCase();
		}
	}
}
