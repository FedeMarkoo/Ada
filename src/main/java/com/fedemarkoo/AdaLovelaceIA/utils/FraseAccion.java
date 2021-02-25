package com.fedemarkoo.AdaLovelaceIA.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("FraseAccion")
public class FraseAccion {
	@Id
	private String frase;
	private String sustantivo;
	private String verbo;
}
