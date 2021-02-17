package com.fedemarkoo.AdaLovelaceIA.dao;

import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import org.springframework.stereotype.Service;

@Service
public class DiccionarioDao extends GeneralDao<DiccionarioCache>{

	public DiccionarioCache findById(String id) {
		return super.findById(id);
	}

	public void save(DiccionarioCache dic){
		super.save(dic);
	}
}
