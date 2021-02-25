package com.fedemarkoo.AdaLovelaceIA.service;

import com.fedemarkoo.AdaLovelaceIA.dao.ActionDao;
import com.fedemarkoo.AdaLovelaceIA.dao.FraseAccionDao;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownException;
import com.fedemarkoo.AdaLovelaceIA.utils.Action;
import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseAccion;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import com.fedemarkoo.AdaLovelaceIA.utils.ObjectUtil;
import com.fedemarkoo.AdaLovelaceIA.utils.TipoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecoderService {

	@Autowired
	private ActionDao actionDao;
	@Autowired
	private FraseAccionDao fraseAccionDao;

	@Autowired
	private DictionaryService dictionaryService;

	private Action getAction(FraseUtil fraseUtil) throws UnknownClassException {
		ObjectUtil object = actionDao.getObject(fraseUtil.getPrimerSustantivo());
		if (object == null)
			throw new UnknownClassException();

		Action action = new Action();
		action.setObject(object);
		return action;
	}

	public Action decodeAction(FraseUtil fraseUtil) throws UnknownException {
		Action action = this.getAction(fraseUtil);
		action.setMethod(this.decodeMethod(fraseUtil));
		return action;
	}

	public FraseUtil decodeFrase(String frase) {
		FraseUtil fraseUtil = new FraseUtil(frase);
		for (String word : frase.toLowerCase().split(" ")) {
			DiccionarioCache cache = dictionaryService.getDiccionario(word);
			fraseUtil.addPalabra(cache);
		}
		if (fraseUtil.faltaInfo()) {
			FraseAccion fromFrase = decodeFromFrase(fraseUtil);
			fraseUtil.setFraseAccion(fromFrase);
			if (fromFrase != null) {
				if (fromFrase.getVerbo() != null) {
					DiccionarioCache diccionario = dictionaryService.getDiccionarioNoThrows(fromFrase.getVerbo());
					diccionario.setTipo(TipoEnum.VERBO);
					fraseUtil.addPalabra(diccionario);
				}
				if (fromFrase.getSustantivo() != null) {
					DiccionarioCache diccionario = dictionaryService.getDiccionarioNoThrows(fromFrase.getSustantivo());
					diccionario.setTipo(TipoEnum.SUSTANTIVO);
					fraseUtil.addPalabra(diccionario);
				}
			}
		}
		return fraseUtil;
	}

	private String decodeMethod(FraseUtil fraseUtil) {
		return fraseUtil.getPrimerVerbo();
	}

	private FraseAccion decodeFromFrase(FraseUtil fraseUtil) {
		List<FraseAccion> all = fraseAccionDao.findAll();
		final String f = fraseUtil.getFrase().trim();
		final String c = fraseUtil.getFraseCanonica().trim();
		Optional<FraseAccion> first = all.stream().filter(o -> f.matches(o.getFrase().trim()) || c.matches(o.getFrase().trim())).findFirst();
		return first.orElse(null);
	}
}
