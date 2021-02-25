package com.fedemarkoo.AdaLovelaceIA.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("AdaIA")
public class ObjectUtil {
	@Id
	private String object;
	private String className;
	private List<String> sinonimos;
	private List<Metodo> metodos;

	public List<Metodo> getMetodos() {
		if (metodos != null) {
			return metodos;
		}
		return metodos = new ArrayList<>();
	}

	public List<String> getSinonimos() {
		if (sinonimos != null) {
			return sinonimos;
		}
		return sinonimos = new ArrayList<>();
	}
}
