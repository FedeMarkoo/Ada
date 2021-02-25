package com.fedemarkoo.AdaLovelaceIA.objects;

import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.service.GeneratorService;
import com.fedemarkoo.AdaLovelaceIA.utils.Context;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import com.fedemarkoo.AdaLovelaceIA.utils.User;
import lombok.Singleton;

@Singleton
public class MySelf extends Object {

	public String saludar(FraseUtil fraseUtil) {
		Context context = getContext();
		User user = context.getUser();
		if (user == null) {
			setExpectedMethod("settearUsuario", getClass());
			return "buenos días, me dirias tu nombre?";
		}
		return "buenos dias " + user.getName();
	}

	public String settearUsuario(FraseUtil fraseUtil) {
		try {
			User user = new User();
			user.setName(fraseUtil.getPrimerSustantivo());
			getContext().setUser(user);
		} catch (UnknownClassException e) {
			e.printStackTrace();
		}
		return saludar(fraseUtil);
	}

	public String corregirPalabra(FraseUtil fraseUtil) {


		GeneratorService.corregirDiccionario(fraseUtil);
		return "listo!";
	}
}
