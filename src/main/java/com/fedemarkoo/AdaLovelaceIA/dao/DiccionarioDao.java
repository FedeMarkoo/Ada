package com.fedemarkoo.AdaLovelaceIA.dao;

import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import org.springframework.stereotype.Service;

@Service
public class DiccionarioDao extends GeneralDao<DiccionarioCache> {

	public void update(String palabraACorregir, String palabraAReal) {
		DiccionarioCache finded = findById(palabraACorregir);
		finded.setPalabraCanonica(palabraAReal);
		super.mongo.save(finded);
	}
}
