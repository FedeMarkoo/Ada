package com.fedemarkoo.AdaLovelaceIA.service;

import com.fedemarkoo.AdaLovelaceIA.dao.DiccionarioDao;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseAccion;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeneratorService {
	private static DiccionarioDao dicDao;

	public static void corregirDiccionario(FraseUtil fraseUtil) {
		FraseAccion fraseAccion = fraseUtil.getFraseAccion();
		String regex = fraseAccion.getFrase();
		Matcher matcher = Pattern.compile(regex).matcher(fraseUtil.getFraseCanonica());
		if (matcher.find()) {
			String palabraACorregir = matcher.group(1);
			String palabraAReal = matcher.group(2);
			dicDao.update(palabraACorregir, palabraAReal);
		} else {
			matcher = Pattern.compile(regex).matcher(fraseUtil.getFrase());
			if (matcher.find()) {
				String palabraACorregir = matcher.group(1);
				String palabraAReal = matcher.group(2);
				dicDao.update(palabraACorregir, palabraAReal);
			}
		}
	}

	@Autowired
	public void setDicDao(DiccionarioDao dicDao) {
		GeneratorService.dicDao = dicDao;
	}

	public Object generarClase(FraseUtil frase) {
		return null;
	}

	public Object generarAccion(FraseUtil frase) {
		return null;
	}

	public Object generarDiccionario(FraseUtil frase) {
		return null;
	}
}
