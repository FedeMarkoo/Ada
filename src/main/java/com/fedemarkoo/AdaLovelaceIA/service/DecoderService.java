package com.fedemarkoo.AdaLovelaceIA.service;

import com.fedemarkoo.AdaLovelaceIA.dao.ActionDao;
import com.fedemarkoo.AdaLovelaceIA.dao.FraseAccionDao;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownMethodException;
import com.fedemarkoo.AdaLovelaceIA.utils.Action;
import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseAccion;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import com.fedemarkoo.AdaLovelaceIA.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		FraseUtil fraseUtil = new FraseUtil();
		for (String word : frase.toLowerCase().split(" ")) {
			DiccionarioCache cache = dictionaryService.getDiccionario(word);
			fraseUtil.addPalabra(cache);
		}
		return fraseUtil;
	}

	private String decodeMethod(FraseUtil fraseUtil) throws UnknownMethodException {
		String verbo = fraseUtil.getPrimerVerbo();
		if (verbo != null) {
			return verbo;
		}
		verbo = decodeMethodFromFrase(fraseUtil);
		return verbo;
	}

	private String decodeMethodFromFrase(FraseUtil fraseUtil) {
		FraseAccion fraseAccion = fraseAccionDao.findById(fraseUtil.getFraseCanonica());
		return fraseAccion.getVerbo();
	}
}
