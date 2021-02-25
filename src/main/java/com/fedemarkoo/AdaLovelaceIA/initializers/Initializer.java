package com.fedemarkoo.AdaLovelaceIA.initializers;

import com.fedemarkoo.AdaLovelaceIA.dao.ActionDao;
import com.fedemarkoo.AdaLovelaceIA.dao.DiccionarioDao;
import com.fedemarkoo.AdaLovelaceIA.dao.FraseAccionDao;
import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseAccion;
import com.fedemarkoo.AdaLovelaceIA.utils.ObjectUtil;
import com.fedemarkoo.AdaLovelaceIA.utils.TipoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Initializer {
	@Value("ada.createInicialData")
	private String createInicialData;

	@Autowired
	private void initializer(DiccionarioDao dic, FraseAccionDao fraseAcc, ActionDao act) {

		act.save(new ObjectUtil("MySelf", "com.fedemarkoo.AdaLovelaceIA.objects.MySelf", new ArrayList<>(), new ArrayList<>()));

		dic.save(new DiccionarioCache("corregirpalabra", "corregirPalabra", null));
		dic.save(new DiccionarioCache("myself", "MySelf", 0, TipoEnum.SUSTANTIVO));
		dic.save(new DiccionarioCache("ada", "MySelf", 0, TipoEnum.SUSTANTIVO));

		fraseAcc.save(new FraseAccion("hola myself", null, "saludar"));
		fraseAcc.save(new FraseAccion("(\\w+) ser (?:una?)? (\\w+)", "myself", "corregirPalabra"));
	}
}
