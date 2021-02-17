package com.fedemarkoo.AdaLovelaceIA.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("Cache")
public class DiccionarioCache {
	@Id
	private String palabra;
	private String palabraCanonica;
	private TipoEnum tipo;

	public DiccionarioCache(String word, String palabra, String type) {
		this.setPalabra(word);
		this.setPalabraCanonica(palabra);
		this.tipo = TipoEnum.getByValue(type);
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra.toLowerCase();
	}

	public void setPalabraCanonica(String palabraCanonica) {
		this.palabraCanonica = palabraCanonica.toLowerCase();
	}
}
